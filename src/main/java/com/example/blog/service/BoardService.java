package com.example.blog.service;

import com.example.blog.S3.S3FileUploadService;
import com.example.blog.domain.Board;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.CustomExceptionStatus;
import com.example.blog.payload.request.BoardRequest;
import com.example.blog.payload.response.*;
import com.example.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final S3FileUploadService s3FileUploadService;


    @Transactional
    public ResponseEntity<String> addStudy(BoardRequest boardRequest, MultipartFile multipartFile) throws IOException {

        if (multipartFile != null) {
            boardRequest.setImage(s3FileUploadService.upload(multipartFile));
        }

        Board board = Board.builder()
                .subject(boardRequest.getSubject())
                .shortDescription(boardRequest.getShortDescription())
                .text(boardRequest.getText())
                .image(boardRequest.getImage())
                .localDateTime(LocalDateTime.now())
                .category(boardRequest.getCategory())
                .url(boardRequest.getUrl())
                .trackBackCount(0L)
                .viewCount(0L).build();
        boardRepository.save(board);

        if (boardRequest.getUrl() != null) {
            // ????????? uri ??????
            String uri = boardRequest.getUrl();
            String [] temp = uri.split("board");
            Long id = Long.valueOf(temp[1]);

            // ????????? count up
            Board trackBackBoard = boardRepository.findById(id)
                    .orElseThrow(() -> new CustomException(CustomExceptionStatus.BOARD_ID_NOT_FOUND));
            trackBackBoard.plusTracBackCount();
        }

        return ResponseEntity.ok("????????? ?????? ??????");
    }

    public ResponseEntity<PageBoardResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> all = boardRepository.findAll(pageable);
        List<BoardResponse> collect = all.stream().map(
                board -> new BoardResponse(board.getId(),board.getSubject(), board.getShortDescription(), board.getText(), board.getImage(), board.getCategory(), board.getLocalDateTime(), board.getViewCount(), board.getTrackBackCount()
                )).collect(Collectors.toList());
        Collections.reverse(collect);

        int total = boardRepository.findAll().size();

        PageBoardResponse result = new PageBoardResponse(total,collect);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<BoardResponse> getOneBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.BOARD_ID_NOT_FOUND));
        BoardResponse boardResponse =   new BoardResponse(board.getId(),board.getSubject(), board.getShortDescription(), board.getText(), board.getImage(), board.getCategory(), board.getLocalDateTime(), board.getViewCount(), board.getTrackBackCount());

        board.plusViewCount();

        return new ResponseEntity<>(boardResponse,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> modifyBoard(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.BOARD_ID_NOT_FOUND));
        board.modifyBoard(boardRequest);
        boardRepository.save(board);

        return ResponseEntity.ok("???????????? ??????????????????.");
    }

    @Transactional
    public ResponseEntity<String> deleteBoard(Long id) {
        boardRepository.deleteById(id);

        return ResponseEntity.ok("???????????? ??????????????????.");
    }

    public ResponseEntity<List<BoardResponse>> searchBoard(String keyword) {
        List<Board> search = boardRepository.findByCategoryContainingOrTextContainingOrSubjectContainingOrShortDescriptionContaining(keyword,keyword,keyword,keyword);

        List<BoardResponse> collect = search.stream().map(
                board -> new BoardResponse(board.getId(),board.getSubject(), board.getShortDescription(), board.getText(), board.getImage(), board.getCategory(), board.getLocalDateTime(), board.getViewCount(), board.getTrackBackCount()
                )).collect(Collectors.toList());
        Collections.reverse(collect);

        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    public ResponseEntity<PageAdminBoardResponse> findAllAdminBoard(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> all = boardRepository.findAll(pageable);

        List<AdminBoardResponse> collect = all.stream().map(
                board -> new AdminBoardResponse(board.getId(), board.getSubject(), board.getShortDescription(), board.getImage(), board.getViewCount(), board.getStringComments())
        ).collect(Collectors.toList());
        Collections.reverse(collect);

        int total = boardRepository.findAll().size();

        PageAdminBoardResponse result = new PageAdminBoardResponse(total,collect);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<TotalResponse> getTotal() {
        List<Board> all = boardRepository.findAll();
        int board = 0;
        int comment = 0;
        int scrap = 0;
        int view = 0;

        for (Board i : all) {
            board++;
            comment+= i.getStringComments().size();

            if (i.getUrl() != null) {
                scrap++;
            }
            view+=i.getViewCount();
        }
        TotalResponse totalResponse = new TotalResponse(board,comment,scrap,view);

        return new ResponseEntity<>(totalResponse,HttpStatus.OK);
    }

    public ResponseEntity<List<BoardResponse>> findByViewCount() {
        List<Board> result = boardRepository.findAllByOrderByViewCount();
        List<BoardResponse> collect = result.stream().map(
                board -> new BoardResponse(board.getId(),board.getSubject(), board.getShortDescription(), board.getText(), board.getImage(), board.getCategory(), board.getLocalDateTime(), board.getViewCount(), board.getTrackBackCount()
                )).collect(Collectors.toList());
        Collections.reverse(collect);

        return new ResponseEntity<>(collect, HttpStatus.OK);
    }
}
