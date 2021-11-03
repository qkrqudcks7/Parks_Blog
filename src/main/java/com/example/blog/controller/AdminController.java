package com.example.blog.controller;

import com.example.blog.payload.response.PageAdminBoardResponse;
import com.example.blog.payload.response.TotalResponse;
import com.example.blog.service.BoardService;
import com.example.blog.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final BoardService boardService;

    private final CommentsService commentsService;

    @GetMapping("/alladminboard")
    public ResponseEntity<PageAdminBoardResponse> getAllBoard(@RequestParam(name = "page",required = false, defaultValue = "0") Integer page,
                                                              @RequestParam(name = "size",required = false, defaultValue = "9") Integer size) {
        return boardService.findAllAdminBoard(page,size);
    }

    @GetMapping("/total")
    public ResponseEntity<TotalResponse> getTotalInfo() {
        return boardService.getTotal();
    }
}
