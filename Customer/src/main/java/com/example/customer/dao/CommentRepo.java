package com.example.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.model.Comments;

public interface CommentRepo extends JpaRepository<Comments, Long> {

}
