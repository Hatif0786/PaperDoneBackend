package com.exam.ExamPortalBackend.service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.ExamPortalBackend.exception.CategoryAlreadyExistsException;
import com.exam.ExamPortalBackend.exception.CategoryDoesNotExistsException;
import com.exam.ExamPortalBackend.model.exam.Category;
import com.exam.ExamPortalBackend.repository.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repo;

	@Override
	public Category addCategory(Category c) throws CategoryAlreadyExistsException {
		// TODO Auto-generated method stub
		boolean result=this.repo.existsById(c.getcid());
		if(result) {
			throw new CategoryAlreadyExistsException("Category already Exists!!!!");
		}
		return this.repo.save(c);
	}
	
	@Override
	public Category updateCategory(Category c, Long id) throws CategoryDoesNotExistsException {
		// TODO Auto-generated method stub
		Optional<Category> result = this.repo.findById(id);
		if(result.get()==null) {
			throw new CategoryDoesNotExistsException("Category not found!!!");
		}
		else {
			Category temp =result.get();
			temp.setTitle(c.getTitle());
			temp.setDescription(c.getDescription());
			this.repo.save(temp);
			return temp;
		}
	}

	@Override
	public Category getCategory(Long cId) throws CategoryDoesNotExistsException {
		// TODO Auto-generated method stub
		
		Optional<Category> result = this.repo.findById(cId);
		if(result.get()==null) {
			throw new CategoryDoesNotExistsException("Category not found!!!");
		}
		else {
			return result.get();
		}
	}

	@Override
	public Set<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return new LinkedHashSet<>( this.repo.findAll());
	}

	@Override
	public void deleteCategory(Long cId) throws CategoryDoesNotExistsException {
		// TODO Auto-generated method stub
		Optional<Category> result = this.repo.findById(cId);
		if(result.get()==null) {
			throw new CategoryDoesNotExistsException("Category not found!!!");
		}
		else {
			this.repo.deleteById(cId);
		}
	}

}
