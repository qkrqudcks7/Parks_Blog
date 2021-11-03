package com.example.blog.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CommentsRequest {

    private Long boardId;

    @NotBlank
    private String comment;

}
