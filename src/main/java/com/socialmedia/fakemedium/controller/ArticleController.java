package com.socialmedia.fakemedium.controller;

import com.socialmedia.fakemedium.dto.CreateArticleRequest;
import com.socialmedia.fakemedium.entity.Article;
import com.socialmedia.fakemedium.entity.ArticleLike;
import com.socialmedia.fakemedium.repository.ArticleLikeRepository;
import com.socialmedia.fakemedium.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;


    public ArticleController(ArticleRepository articleRepository, ArticleLikeRepository articleLikeRepository) {
        this.articleRepository = articleRepository;
        this.articleLikeRepository = articleLikeRepository;
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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication auth
    ) {
        String email = auth.getName();

        Article article = articleRepository
                .findByIdAndAuthorEmail(id, email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        articleRepository.delete(article);
        return ResponseEntity.noContent().build(); // 204
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Article> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateArticleRequest req,
            Authentication auth
    ) {
        String email = auth.getName();

        Article article = articleRepository
                .findByIdAndAuthorEmail(id, email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        article.setTitle(req.title);
        article.setContent(req.content);

        Article updated = articleRepository.save(article);
        return ResponseEntity.ok(updated);
    }


    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable Long id, Authentication auth) {
        // make sure article exists
        if (!articleRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        String email = auth.getName();

        // idempotent: if already liked, return 204
        if (articleLikeRepository.existsByArticleIdAndUserEmail(id, email)) {
            return ResponseEntity.noContent().build();
        }

        ArticleLike like = new ArticleLike();
        like.setArticleId(id);
        like.setUserEmail(email);
        articleLikeRepository.save(like);

        return ResponseEntity.noContent().build(); // 204
    }

    @DeleteMapping("/{id}/like")
    @Transactional
    public ResponseEntity<Void> unlike(@PathVariable Long id, Authentication auth) {
        if (!articleRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        String email = auth.getName();
        articleLikeRepository.deleteByArticleIdAndUserEmail(id, email);

        return ResponseEntity.noContent().build(); // 204 (even if it didn't exist)
    }

    @GetMapping("/{id}/likes/count")
    public ResponseEntity<Long> likeCount(@PathVariable Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return ResponseEntity.ok(articleLikeRepository.countByArticleId(id));
    }
}