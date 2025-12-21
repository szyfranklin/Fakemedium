package com.socialmedia.fakemedium.repository;

import com.socialmedia.fakemedium.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    // List my articles (newest first)
    List<Article> findAllByAuthorEmailOrderByCreatedAtDesc(String authorEmail);
    // Find an article only if it belongs to me (for delete safety)
    Optional<Article> findByIdAndAuthorEmail(Long id, String authorEmail);
}