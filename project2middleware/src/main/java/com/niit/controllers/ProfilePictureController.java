package com.niit.controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.niit.dao.ProfilePictureDao;
import com.niit.models.ErrrorClazz;
import com.niit.models.ProfilePicture;

@RestController
public class ProfilePictureController {
	@Autowired
private ProfilePictureDao profilePictureDao;
	
@RequestMapping(value="/uploadprofilepic",method=RequestMethod.POST)
public ResponseEntity<?> uploadProfilePicture(HttpSession session,@RequestParam MultipartFile image){
	//Check for Authenticated- only logged user can post a blog 
			String email=(String)session.getAttribute("loginId");
			if(email==null){//if the user is not yet logged in,user is not an authenticated user
				ErrrorClazz errorClazz=new ErrrorClazz(5,"Please login..");
				return new ResponseEntity<ErrrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
			}
			ProfilePicture profilePicture=new ProfilePicture();
			profilePicture.setEmail(email);
			try {
				profilePicture.setImage(image.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			profilePictureDao.saveOrUpdateProfilePicture(profilePicture);
			return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
}
	//This data will be used in img tag
    //url will be used in src attribute of an img tag, src needs sbinary data
//in client side <img src="http://..../getimage?email='james.s@niit.com'" alt="..">
    @RequestMapping(value="/getimage",method=RequestMethod.GET)
    public @ResponseBody byte[] getImage(@RequestParam String email,HttpSession session){
    	String authEmail=(String)session.getAttribute("loginId");
		if(authEmail==null){//if the user is not yet logged in,user is not an authenticated user
			return null;
		}
		ProfilePicture profilePicture=profilePictureDao.getProfilePicture(email);
		if(profilePicture==null)
			return null;
		else
			return profilePicture.getImage();
    }

}




