package com.example.blog.repository;

import com.example.blog.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Long> {
}
