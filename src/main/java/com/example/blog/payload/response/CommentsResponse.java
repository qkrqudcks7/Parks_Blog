package com.example.blog.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentsResponse {

    private Long id;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM.dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime localDateTime;
}
