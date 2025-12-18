package com.socialmedia.fakemedium.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateArticleRequest {
    @NotBlank
    public String title;

    @NotBlank
    public String content;
}