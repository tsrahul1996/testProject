package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xerces.internal.util.URI;
import com.user.dao.UserSignUpDAO;
import com.user.model.BlogPost;
import com.user.model.UniversalLoginStg;
import com.user.model.UserSignUp;
import com.user.service.UserSignUpService;

@RestController
@CrossOrigin(origins = {"http://150.1.18.138:4200","http://localhost:4200"},	methods = RequestMethod.POST)
public class UserSignUpController {

	@Autowired
	UserSignUpService userSignUpService;
	
	@Autowired
	UserSignUpDAO userSignUpDAO;

	public static final Logger log = LoggerFactory.getLogger(UserSignUpController.class);

	@GetMapping("/index")
	public String populateLogin() {

		return "login";
	}
	
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public String confirmUserAccount(@RequestParam("token")String confirmationToken) {
		int tokenNo = Integer.parseInt(confirmationToken);
		UniversalLoginStg token =  (UniversalLoginStg) userSignUpDAO.findByTokenNo(tokenNo);
		
		//System.out.println(request.getRemoteAddr());
        if(token != null)
        {
        	UniversalLoginStg user =  (UniversalLoginStg) userSignUpDAO.findByPrimaryEmail(token.getPrimaryEmail());
        	user.setStatus("A");
        	userSignUpDAO.save(user);
           // modelAndView.setViewName("accountVerified");
        }
        else
        {
          //  modelAndView.addObject("message","The link is invalid or broken!");
          //  modelAndView.setViewName("error");
        }
		return 	"success";
		
	}
	
	@PostMapping(path = "/members") public String
	  userSignUpSelectTest(@RequestBody UserSignUp user) {
	 
	//userSignUpService.save(user);
	 
	  return "success"; 	  
	  }
	

	@PostMapping(path = "/userSignUpSave")
	public ResponseEntity<UniversalLoginStg> userSignUpSelect(@RequestBody UniversalLoginStg universalLoginStg,HttpServletRequest request) throws AddressException, MessagingException, IOException {
		log.info("Result of matching: {}" ,request.getRemoteAddr());
		UniversalLoginStg confirmationToken = new UniversalLoginStg(universalLoginStg);
       
		UniversalLoginStg result = userSignUpService.save(universalLoginStg);

	    if (result == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	    	
		java.net.URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(universalLoginStg.getStgUserId()).toUri();
	
		return ResponseEntity.status(HttpStatus.OK).build();			
	    }
	}
	
}
