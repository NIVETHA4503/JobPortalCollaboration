package com.niit.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogCommentDao;
import com.niit.dao.UserDao;
import com.niit.models.BlogComment;
import com.niit.models.ErrrorClazz;
import com.niit.models.User;

@RestController
public class BlogCommentController {
	@Autowired
private BlogCommentDao blogCommentDao;
	@Autowired
private UserDao userDao;
    @RequestMapping(value="/addblogcomment",method=RequestMethod.POST)
    //blogComment : {commentId:AUTO,commentTxt: entered by user,blogPost:set it in client side ,commentedOn:middleware ,commentedBy:middleware }
	public ResponseEntity<?> addBlogComment(HttpSession session,@RequestBody BlogComment blogComment){
    	//Check for Authenticated- only logged user can post a blog 
		String email=(String)session.getAttribute("loginId");
		if(email==null){//if the user is not yet logged in,user is not an authenticated user
			ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User commentedBy=userDao.getUser(email);
		blogComment.setCommentedBy(commentedBy);
		blogComment.setCommentedOn(new Date());
		blogCommentDao.addBlogComment(blogComment);
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
	}
    @RequestMapping(value="/getblogcomments",method=RequestMethod.GET)
    public ResponseEntity<?> getAllBlogComments(HttpSession session,@PathVariable int blogPostId){
    	//Check for Authenticated- only logged user can post a blog 
    			String email=(String)session.getAttribute("loginId");
    			if(email==null){//if the user is not yet logged in,user is not an authenticated user
    				ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
    				return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
    			}
    		List<BlogComment> blogComments=	blogCommentDao.getAllBlogComments(blogPostId);
    		return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);
    }
}
