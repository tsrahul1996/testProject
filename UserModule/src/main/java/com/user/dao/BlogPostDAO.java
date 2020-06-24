package com.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.user.model.BlogPost;

public interface BlogPostDAO extends CrudRepository<BlogPost, Long> {

	

}
