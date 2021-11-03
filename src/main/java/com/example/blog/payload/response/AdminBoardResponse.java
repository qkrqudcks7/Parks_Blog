package com.example.blog.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class AdminBoardResponse {

    private Long id;

    private String subject;

    private String shortDescription;

    private String image;

    private Long viewCount;

    private List<String> comments = new ArrayList<>();

}
