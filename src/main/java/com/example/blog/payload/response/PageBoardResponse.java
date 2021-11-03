package com.example.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PageBoardResponse {

    private int total;

    private List<BoardResponse> boardResponses = new ArrayList<>();
}
