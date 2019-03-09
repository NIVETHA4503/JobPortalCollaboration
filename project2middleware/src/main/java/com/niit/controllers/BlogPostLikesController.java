package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogPostLikesDao;
import com.niit.models.BlogPost;
import com.niit.models.BlogPostLikes;
import com.niit.models.ErrrorClazz;

@RestController
public class BlogPostLikesController {
	@Autowired
private BlogPostLikesDao blogPostLikesDao;

//SELECT * from blogpost where blogpostid=?
//select * from blogpostlikes where blogpost_blogpostid=? and likedby_email=?
//read the blogpost,TO FIND THE COLOR OF GLYPHICON THUMBS UP
@RequestMapping(value="/hasuserlikedblogpost/{blogPostId}",method=RequestMethod.GET)
public ResponseEntity<?> hasUserLikedBlogPost(@PathVariable int blogPostId,HttpSession session){
	//Check for Authenticated- only logged user can post a blog 
		String email=(String)session.getAttribute("loginId");
		if(email==null){//if the user is not yet logged in,user is not an authenticated user
			ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
			return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		BlogPostLikes blogPostLikes=blogPostLikesDao.hasUserLikedBlogPost(blogPostId, email);
		return new ResponseEntity<BlogPostLikes>(blogPostLikes,HttpStatus.OK);//1 object or EMPTY BODY
}

//CLICKS THE GLYPHICON THUMBS UP(to insert and increment or Delete and decrement)
@RequestMapping(value="/updatelikes/{blogPostId}",method=RequestMethod.PUT)
public ResponseEntity<?> updateLikes(@PathVariable int blogPostId,HttpSession session){
	//Check for Authenticated- only logged user can post a blog 
			String email=(String)session.getAttribute("loginId");
			if(email==null){//if the user is not yet logged in,user is not an authenticated user
				ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
				return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			}
			BlogPost blogPost=blogPostLikesDao.updateLikes(blogPostId, email);
			return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
}
}
