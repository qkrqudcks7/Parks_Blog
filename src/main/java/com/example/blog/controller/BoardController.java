package com.example.blog.controller;

import com.example.blog.exception.ValidationExceptionProvider;
import com.example.blog.payload.request.BoardRequest;
import com.example.blog.payload.response.BoardResponse;
import com.example.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/allboard")
    public ResponseEntity<List<BoardResponse>> getAllBoard() {
        return boardService.findAll();
    }

    @PostMapping("/board")
    public ResponseEntity<String> addBoard(@Valid BoardRequest boardRequest,
                                           MultipartFile multipartFile,
                                           Errors errors) throws IOException {
        if (errors.hasErrors()) {
            ValidationExceptionProvider.throwValidError(errors);
        }
        return boardService.addStudy(boardRequest,multipartFile);
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable("id") Long id) {
        return boardService.getOneBoard(id);
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<String> modifyBoard(@PathVariable("id") Long id,
                                              @Valid @RequestBody BoardRequest boardRequest,
                                              Errors errors) {
        if (errors.hasErrors()) {
            ValidationExceptionProvider.throwValidError(errors);
        }

        return boardService.modifyBoard(id,boardRequest);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable("id") Long id) {
        return boardService.deleteBoard(id);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BoardResponse>> search(@PathVariable("keyword") String keyword) {
        return boardService.searchBoard(keyword);
    }
}
