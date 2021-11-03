package com.example.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PageAdminBoardResponse {

    private int total;


    private List<AdminBoardResponse> adminBoardResponses = new ArrayList<>();
}
