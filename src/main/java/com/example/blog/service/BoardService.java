package com.example.blog.service;

import com.example.blog.S3.S3FileUploadService;
import com.example.blog.domain.Board;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.CustomExceptionStatus;
import com.example.blog.payload.request.BoardRequest;
import com.example.blog.payload.response.BoardResponse;
import com.example.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        // 트랙백 uri 파싱
        String uri = boardRequest.getUrl();
        String [] temp = uri.split("board");
        Long id = Long.valueOf(temp[1]);

        // 트랙백 count up
        Board trackBackBoard = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.BOARD_ID_NOT_FOUND));
        trackBackBoard.plusTracBackCount();

        return ResponseEntity.ok("블로그 생성 완료");
    }

    public ResponseEntity<List<BoardResponse>> findAll() {
        List<Board> all = boardRepository.findAll();
        List<BoardResponse> collect = all.stream().map(
                board -> new BoardResponse(board.getId(),board.getSubject(), board.getShortDescription(), board.getText(), board.getImage(), board.getCategory(), board.getLocalDateTime(), board.getViewCount(), board.getTrackBackCount()
                )).collect(Collectors.toList());
        Collections.reverse(collect);

        return new ResponseEntity<>(collect, HttpStatus.OK);
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

        return ResponseEntity.ok("블로그를 수정했습니다.");
    }

    @Transactional
    public ResponseEntity<String> deleteBoard(Long id) {
        boardRepository.deleteById(id);

        return ResponseEntity.ok("블로그를 삭제했습니다.");
    }

    public ResponseEntity<List<BoardResponse>> searchBoard(String keyword) {
        List<Board> search = boardRepository.findByCategoryContainingOrTextContainingOrSubjectContainingOrShortDescriptionContaining(keyword,keyword,keyword,keyword);

        List<BoardResponse> collect = search.stream().map(
                board -> new BoardResponse(board.getId(),board.getSubject(), board.getShortDescription(), board.getText(), board.getImage(), board.getCategory(), board.getLocalDateTime(), board.getViewCount(), board.getTrackBackCount()
                )).collect(Collectors.toList());
        Collections.reverse(collect);

        return new ResponseEntity<>(collect, HttpStatus.OK);
    }
}
