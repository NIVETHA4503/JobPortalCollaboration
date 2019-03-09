package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UserDao;
import com.niit.models.ErrrorClazz;
import com.niit.models.User;
@RestController 
public class UserController {
	@Autowired
private UserDao userDao;

	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public  ResponseEntity<?>    userRegistration(@RequestBody User user ){
		
		
		if(!userDao.isEmailUnique(user.getEmail())){
			ErrrorClazz errorClazz=new ErrrorClazz(2,"Email already exists.. pls choose different email id");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!userDao.isPhonenumberUnique(user.getPhonenumber())){
			ErrrorClazz errorClazz=new ErrrorClazz(1,"Phone number already exists.. pls enter another phonenumber");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(user.getRole()=="" || user.getRole()==null){
			ErrrorClazz errorClazz=new ErrrorClazz(4,"Role cannot be null..");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try{
		userDao.userRegistration(user);
		}catch(Exception e){
			ErrrorClazz errorClazz=new ErrrorClazz(3,"Unable to register user datails.."+e.getMessage());
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	 @RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session){
		User validUser= userDao.login(user);
		if(validUser==null){
			ErrrorClazz errorClazz=new ErrrorClazz(5,"Email/password is incorrect..Please enter valid credentials...");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		else{
			System.out.println("Session Id is " +session.getId());
			System.out.println("Session createdTime " + session.getCreationTime());
			validUser.setOnline(true);
			
			userDao.updateUser(validUser);
			session.setAttribute("loginId",user.getEmail());
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
	}
	 @RequestMapping(value="/logout",method=RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session){
 
		 String email=(String)session.getAttribute("loginId");
		 if(email==null){
			 ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		 }
		User user= userDao.getUser(email);
		 user.setOnline(false);
		 userDao.updateUser(user);
		 
		 session.removeAttribute("loginId");
		 session.invalidate();
		 return new ResponseEntity<Void>(HttpStatus.OK);
	}
	 @RequestMapping(value="/getuser",method=RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session){
		String email=(String)session.getAttribute("loginId");
		 if(email==null){
			 ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
			 return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		 }
     
		 User user=userDao.getUser(email);
		 return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	@RequestMapping(value="/updateuser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user,HttpSession session){
		String email=(String)session.getAttribute("loginId");
		
		 if(email==null){
			 ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
			 return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		 }
		 if(!userDao.isUpdatedPhonenumberUnique(user.getPhonenumber(),email)){
				ErrrorClazz errorClazz=new ErrrorClazz(1,"Phone number already exists.. pls enter another phonenumber");
				return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 if(user.getRole()=="" || user.getRole()==null){
				ErrrorClazz errorClazz=new ErrrorClazz(4,"Role cannot be null..");
				return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
			}
try {
	userDao.updateUser(user);
}catch(Exception e) {
	 ErrrorClazz errorClazz=new ErrrorClazz(6,"Unable to update user profile"+e.getMessage());
	 return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		 
}
return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
