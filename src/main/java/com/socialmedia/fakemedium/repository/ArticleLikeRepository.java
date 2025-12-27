package com.socialmedia.fakemedium.repository;

import com.socialmedia.fakemedium.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    boolean existsByArticleIdAndUserEmail(Long articleId, String userEmail);
    long countByArticleId(Long articleId);
    void deleteByArticleIdAndUserEmail(Long articleId, String userEmail);
}
