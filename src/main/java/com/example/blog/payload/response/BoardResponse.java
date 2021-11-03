package com.example.blog.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardResponse {

    private Long id;

    private String subject;

    private String shortDescription;

    private String text;

    private String image;

    private String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH시 mm분",timezone = "Asia/Seoul")
    private LocalDateTime time;

    private Long viewCount;

    private Long trackBackCount;
}
