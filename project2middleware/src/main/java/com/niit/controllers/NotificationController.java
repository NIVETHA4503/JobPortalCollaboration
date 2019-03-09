package com.niit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.NotificationDao;
import com.niit.models.ErrrorClazz;
import com.niit.models.Notification;

@RestController
public class NotificationController {
	@Autowired
private NotificationDao notificationDao;
	@RequestMapping(value="/getnotificationsnotviewed",method=RequestMethod.GET)
	public ResponseEntity<?> getNotificationsNotViewed(HttpSession session){
		//Check for Authenticated- only logged user can post a blog 
				String email=(String)session.getAttribute("loginId");
				if(email==null){//if the user is not yet logged in,user is not an authenticated user
					ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
					return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
				}
				List<Notification> notificationsNotViewed=notificationDao.getAllNotificationsNotViewed(email);
				return new ResponseEntity<List<Notification>>(notificationsNotViewed,HttpStatus.OK);
	}
	@RequestMapping(value="/getnotification/{notificationId}",method=RequestMethod.GET)
	public ResponseEntity<?> getNotification(HttpSession session,@PathVariable int notificationId){
		String email=(String)session.getAttribute("loginId");
		if(email==null){//if the user is not yet logged in,user is not an authenticated user
			ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		Notification notification=notificationDao.getNotification(notificationId);
		return new ResponseEntity<Notification>(notification,HttpStatus.OK);
	}
	@RequestMapping(value="/updatenotification/{notificationId}",method=RequestMethod.PUT)
	public ResponseEntity<?> updateNotificationViewedStatus(HttpSession session,@PathVariable int notificationId){
		String email=(String)session.getAttribute("loginId");
		if(email==null){//if the user is not yet logged in,user is not an authenticated user
			ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		notificationDao.updateNotificactionViewedStatus(notificationId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}




