package com.socialmedia.fakemedium.controller;

import com.socialmedia.fakemedium.dto.CreateArticleRequest;
import com.socialmedia.fakemedium.entity.Article;
import com.socialmedia.fakemedium.repository.ArticleRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public ResponseEntity<Article> create(
            @Valid @RequestBody CreateArticleRequest req,
            Authentication auth
    ) {
        Article a = new Article();
        a.setTitle(req.title);
        a.setContent(req.content);
        a.setAuthorEmail(auth.getName()); // email from JWT / login

        Article saved = articleRepository.save(a);
        return ResponseEntity.ok(saved);
    }
}