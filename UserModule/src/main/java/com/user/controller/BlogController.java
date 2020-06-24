package com.user.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.dao.BlogPostDAO;
import com.user.model.BlogPost;
import com.user.model.UniversalLoginStg;
import com.user.service.BlogPostService;
@RestController
public class BlogController {
	
	@Autowired
	BlogPostService blogPostService;
	
	@Autowired
	BlogPostDAO blogPostDAO;
	
	@PostMapping(path = "/saveBlog")
	public String saveBlog(@RequestBody BlogPost blogPost) throws AddressException, MessagingException, IOException {
		BlogPost TempBlogPost = new BlogPost(blogPost);
		blogPostService.save(blogPost);
		return "success";
	}
	
	@GetMapping(path = "/blogSelectAll")
	public ArrayList<BlogPost> selectAll() {
		
		return blogPostService.selectAll();
	}
}
