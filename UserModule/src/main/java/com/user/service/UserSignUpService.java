package com.user.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.controller.UserSignUpController;
import com.user.dao.UserSignUpDAO;
import com.user.model.Mail;
import com.user.model.UniversalLoginStg;
import com.user.model.UserSignUp;

@Service 
public class UserSignUpService {
	
    @Autowired
    UserSignUpDAO dao;
	
    @Autowired
    private EmailSenderService emailSenderService;
    
	public static final Logger log = LoggerFactory.getLogger(UserSignUpController.class);

	public UniversalLoginStg save(UniversalLoginStg user) throws AddressException, MessagingException, IOException {
		
		String URLString = "";
		
		String  password= user.getPassword();

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	  
		String hashedPassword = bCryptPasswordEncoder.encode(password); 
		  
		log.info("Result of matching: {}" ,bCryptPasswordEncoder.matches(password,
				hashedPassword)); log.info("Result of matching: {}"
		  ,bCryptPasswordEncoder.matches(password, hashedPassword));
		  
		user.setPassword(hashedPassword);

		dao.save(user);
		
		int token = user.getTokenNo();
		
		URLString = "http://localhost:8080/confirm-account?token="+token;
		String mailTo= user.getPrimaryEmail();

		
		   Mail mail = new Mail();
	        mail.setFrom("tsrahul1996@gmail.com");//replace with your desired email
	        mail.setTo(user.getPrimaryEmail());//replace with your desired email
	        mail.setSubject("Account Verification");
	        Map<String, Object> model = new HashMap<String, Object>();
	        model.put("name", user.getFirstName());
	        model.put("url", URLString);
	        model.put("token", token);
	        mail.setProps(model);
	        emailSenderService.sendEmail(mail);
	        
	        log.info("END... Email sent success");
		
		/*
		 * SimpleMailMessage mailMessage = new SimpleMailMessage();
		 * mailMessage.setTo(user.getPrimaryEmail());
		 * mailMessage.setSubject("Complete Registration!");
		 * mailMessage.setFrom("tsrahul1996@gmail.com");
		 * mailMessage.setText("To confirm your account, please click here : "
		 * +"http://localhost:8082/confirm-account?token="+token);
		 * 
		 * emailSenderService.sendEmail(mailMessage);
		 */
	        return dao.save(user);
			
	}

	private void sendmail(String mailTo,String URLString) throws AddressException, MessagingException, IOException {
		
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("tsrahul1996@gmail.com", "thirdoct1996");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("tsrahul1996@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Please click the link to continue verification \n "+URLString, "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart mimebodypart = new MimeBodyPart();


			String attachFile = "";
			FileDataSource filedatasource	= new FileDataSource(attachFile);
			mimebodypart.setDataHandler(new DataHandler(filedatasource));
			mimebodypart.setFileName(attachFile);
			multipart.addBodyPart(mimebodypart);
		   
			mimebodypart.attachFile("D:/logo1.png");
			multipart.addBodyPart(mimebodypart);
			msg.setContent(multipart);
			Transport.send(msg);   
		}

}
