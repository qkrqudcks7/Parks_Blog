package com.example.blog.repository;

import com.example.blog.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    Page<Board> findAll(Pageable pageable);

    List<Board> findByCategoryContainingOrTextContainingOrSubjectContainingOrShortDescriptionContaining(String category, String text, String subject, String shortDescription);
}
