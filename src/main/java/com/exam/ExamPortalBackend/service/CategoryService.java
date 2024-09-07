package com.exam.ExamPortalBackend.service;

import java.util.Set;

import com.exam.ExamPortalBackend.exception.CategoryAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.CategoryDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Category;

public interface CategoryService {
	
	public Category addCategory(Category c) throws CategoryAlreadyExistsException;
	
	public Category getCategory(Long cId) throws CategoryDoesNotExistsException;
	
	public Set<Category> getAllCategories();
	
	public void deleteCategory(Long cId) throws CategoryDoesNotExistsException;

	public Category updateCategory(Category c, Long id) throws CategoryDoesNotExistsException;
	
}
