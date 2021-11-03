package com.example.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalResponse {

    private int board;

    private int comment;

    private int scrap;

    private int view;
}
