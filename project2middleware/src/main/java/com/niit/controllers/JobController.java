package com.niit.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.JobDao;
import com.niit.dao.UserDao;
import com.niit.models.ErrrorClazz;
import com.niit.models.Job;
import com.niit.models.User;

@RestController
public class JobController {
@Autowired
private JobDao jobDao;
@Autowired
private UserDao userDao;
@RequestMapping(value="/addjob",method=RequestMethod.POST)
public ResponseEntity<?> addJob(@RequestBody Job job,HttpSession session){
	String email=(String)session.getAttribute("loginId");
	if(email==null){
		ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
		return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}

	User user=userDao.getUser(email);
	if(!user.getRole().equals("ADMIN")){
		ErrrorClazz errorClazz=new ErrrorClazz(6,"Access Denied..You are not authorized to post any job details..");
		return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
	
	
	try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
		job.setPostedon(sdf.format(new Date()));
		jobDao.addJob(job);
		System.out.println("Session Id is " +session.getId());
		System.out.println("Session createdTime " + session.getCreationTime());
		System.out.println("Session Attribute loginId value is "+ session.getAttribute("loginId"));
	}catch(Exception e) {
		ErrrorClazz errorClazz=new ErrrorClazz(1,"Job Details not inserted..something went wrong..\" +e.getMessage()");
		return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<Job>(job,HttpStatus.OK);
}

@RequestMapping(value="/getalljobs",method=RequestMethod.GET)
public ResponseEntity<?> getAllJobs(HttpSession session){
	System.out.println("Session Id is " +session.getId());
	System.out.println("Session createdTime " + session.getCreationTime());
	System.out.println("Session Attribute loginId value is "+ session.getAttribute("loginId"));
	String email=(String)session.getAttribute("loginId");
	if(email==null){
		ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
		return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
	List<Job> jobs=jobDao.getAllJobs();
	return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}
}