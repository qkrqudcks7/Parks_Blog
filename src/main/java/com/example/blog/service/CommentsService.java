package com.example.blog.service;

import com.example.blog.domain.Board;
import com.example.blog.domain.Comments;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.CustomExceptionStatus;
import com.example.blog.payload.request.CommentsRequest;
import com.example.blog.payload.response.CommentsResponse;
import com.example.blog.repository.BoardRepository;
import com.example.blog.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseEntity<String> addComments(CommentsRequest commentsRequest) {
        Board b = boardRepository.findById(commentsRequest.getBoardId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.BOARD_ID_NOT_FOUND));

        Comments comments = Comments.builder()
                .board(b)
                .comment(commentsRequest.getComment())
                .localDateTime(LocalDateTime.now()).build();

        commentsRepository.save(comments);

        return ResponseEntity.ok("댓글 작성 완료");
    }

    public ResponseEntity<List<CommentsResponse>> allComments(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.BOARD_ID_NOT_FOUND));
        List<Comments> comments = board.getComments();

        List<CommentsResponse> collect = comments.stream().map(
                c -> new CommentsResponse(c.getId(), c.getComment(), c.getLocalDateTime())
        ).collect(Collectors.toList());

        return new ResponseEntity<>(collect, HttpStatus.OK);
    }
}
