package com.exam.ExamPortalBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.ExamPortalBackend.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
