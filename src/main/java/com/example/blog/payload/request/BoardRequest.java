package com.example.blog.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class BoardRequest {

    @NotBlank
    @Length(min = 1, max = 15)
    private String subject;

    @NotBlank
    @Length(min = 1,max = 30)
    private String shortDescription;

    @NotBlank
    private String text;

    private String image;

    @NotBlank
    @Length(min = 1,max = 15)
    private String category;

    private String url;

    public void setImage(String image) {
        this.image = image;
    }
}
