package com.example.blog.controller;

import com.example.blog.exception.ValidationExceptionProvider;
import com.example.blog.payload.request.CommentsRequest;
import com.example.blog.payload.response.CommentsResponse;
import com.example.blog.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/comments")
    public ResponseEntity<String> addComments(@RequestBody @Valid CommentsRequest commentsRequest,
                                              Errors errors) {
        if (errors.hasErrors()) {
            ValidationExceptionProvider.throwValidError(errors);
        }

        return commentsService.addComments(commentsRequest);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentsResponse>> getAllComments(@PathVariable("id") Long id) {
        return commentsService.allComments(id);
    }

}
