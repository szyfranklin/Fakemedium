package com.socialmedia.fakemedium.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "article_likes",
        uniqueConstraints = @UniqueConstraint(name = "uk_article_user", columnNames = {"article_id", "user_email"}),
        indexes = {
                @Index(name = "idx_article_id", columnList = "article_id"),
                @Index(name = "idx_user_email", columnList = "user_email")
        }
)
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="article_id", nullable=false)
    private Long articleId;

    @Column(name="user_email", nullable=false, length=200)
    private String userEmail;

    @Column(name="created_at", nullable=false)
    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public Long getArticleId() { return articleId; }
    public String getUserEmail() { return userEmail; }
    public Instant getCreatedAt() { return createdAt; }

    public void setArticleId(Long articleId) { this.articleId = articleId; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}