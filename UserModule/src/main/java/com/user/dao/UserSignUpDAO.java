package com.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.user.model.UniversalLoginStg;

public interface UserSignUpDAO extends CrudRepository<UniversalLoginStg, Long>{
	
	UserSignUpDAO findByTokenNo(int confirmationToken);
	UserSignUpDAO findByPrimaryEmail(String primaryEmail);
	
}
