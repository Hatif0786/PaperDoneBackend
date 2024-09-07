package com.exam.ExamPortalBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.ExamPortalBackend.model.exam.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
