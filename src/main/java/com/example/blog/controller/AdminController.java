package com.example.blog.controller;

import com.example.blog.payload.response.PageAdminBoardResponse;
import com.example.blog.payload.response.TotalResponse;
import com.example.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final BoardService boardService;

    @GetMapping("/alladminboard")
    public ResponseEntity<PageAdminBoardResponse> getAllBoard(@RequestParam(name = "page",required = false, defaultValue = "0") Integer page,
                                                              @RequestParam(name = "size",required = false, defaultValue = "9") Integer size) {
        return boardService.findAllAdminBoard(page,size);
    }

    @GetMapping("/alltotal")
    public ResponseEntity<TotalResponse> getTotalInfo() {
        return boardService.getTotal();
    }
}
