package com.niit.dao;

import java.util.List;

import com.niit.models.Friend;
import com.niit.models.User;

public interface FriendDao {
List<User>  getAllSuggestedUsers(String email);
void   addFriend(Friend friend);//toId-> client,fromId-> loggedIn user,status->P
List<Friend> getPendingRequests(String email);//email of the loggedin user
void acceptRequest(Friend pendingRequest);
void deleteRequest(Friend pendingRequest);
List<User> listOfFriends(String email);
}
