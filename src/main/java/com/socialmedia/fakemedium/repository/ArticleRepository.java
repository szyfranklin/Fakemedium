package com.socialmedia.fakemedium.repository;

import com.socialmedia.fakemedium.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}