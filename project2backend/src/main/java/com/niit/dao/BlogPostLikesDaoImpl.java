package com.niit.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.models.BlogPost;
import com.niit.models.BlogPostLikes;
import com.niit.models.User;
@Repository
@Transactional
public class BlogPostLikesDaoImpl implements BlogPostLikesDao {
	@Autowired
private SessionFactory sessionFactory;
	public BlogPostLikes hasUserLikedBlogPost(int blogPostId, String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery(
			"from BlogPostLikes where blogPost.blogPostId=:blogId and likedBy.email=:email");
		query.setInteger("blogId", blogPostId);
		query.setString("email", email);
		BlogPostLikes blogPostLikes=(BlogPostLikes)query.uniqueResult();
		return blogPostLikes;
	}
   //when the client clicks the glyphicon thumbs up
	public BlogPost updateLikes(int blogPostId, String email) {
		//inserting a record in blogpostlikes and increment number of likes
		 //OR
		// deleting a record from blogpostlikes and decrement number of likes
		Session session=sessionFactory.getCurrentSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class,blogPostId);
		User likedBy=(User)session.get(User.class, email);
		BlogPostLikes blogPostLikes=hasUserLikedBlogPost(blogPostId, email);
		//INSERT INTO BLOGPOSTLIKES , UPDATE BLOGPOST SET LIKES=LIKES+1 where blogpostid=?
		if(blogPostLikes==null){//insert a record in blogpostlikes table,increment the number of likes
		   blogPostLikes=new BlogPostLikes();
		   blogPostLikes.setBlogPost(blogPost);
		   blogPostLikes.setLikedBy(likedBy);
		   session.save(blogPostLikes);//insert a record in blogpostlikes
		   //blogPost.likes=blogPost.likes+1
		   blogPost.setLikes(blogPost.getLikes()+1);//increment the number of likes, likes is a property in BlogPost
		   	session.update(blogPost);
		}else{//delete a record , decrement the number of likes
   			session.delete(blogPostLikes);
   			blogPost.setLikes(blogPost.getLikes() - 1);
   			session.update(blogPost);
		}
		
		return blogPost;//blogpost has updated  likes
	}

	
}

