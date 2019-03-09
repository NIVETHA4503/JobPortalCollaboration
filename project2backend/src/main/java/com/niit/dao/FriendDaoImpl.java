package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.models.Friend;
import com.niit.models.User;
@Repository
@Transactional
public class FriendDaoImpl implements FriendDao {
	@Autowired
private SessionFactory sessionFactory;
	public List<User> getAllSuggestedUsers(String email) {
		Session session=sessionFactory.getCurrentSession();
		
		String queryString="select * from User_Table where email in "  //S
				+ "(select email from User_Table where email!=:e1 "  //A
				+ "    minus "
				+ "  (select toId_email from friend_Table where fromId_email=:e2 "  //B
				+ "       union "
				+ "   select fromId_email from friend_Table where toId_email=:e3"
				+ "  )" //C
				+ ")";
		
		SQLQuery query=session.createSQLQuery(queryString);
        query.setString("e1", email);
        query.setString("e2", email);
        query.setString("e3", email);
        query.addEntity(User.class);//to convert it into an object of type USER
        List<User> suggestedUsers=query.list();
        return suggestedUsers;
	}
	public void addFriend(Friend friend) {//toId,fromId,status as P
		Session session=sessionFactory.getCurrentSession();
		session.save(friend);
	}
	public List<Friend> getPendingRequests(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where toId.email=:email and status=:status");
		query.setString("email", email);
		query.setCharacter("status", 'P');
		List<Friend> pendingRequests=query.list();
		return pendingRequests;
	}
	public void acceptRequest(Friend pendingRequest) {
		Session session=sessionFactory.getCurrentSession();
		session.update(pendingRequest);//update friend set status='A' where friendId=?
	}
	public void deleteRequest(Friend pendingRequest) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(pendingRequest);//delete from friend where friendId=?
	}
	public List<User> listOfFriends(String email) {
		Session session=sessionFactory.getCurrentSession();
		//f is alias
		Query query1=session.createQuery("select f.fromId from Friend f where f.toId.email=:e1 and f.status='A'");
		query1.setString("e1", email);
		List<User> list1=query1.list();
		
		Query query2=session.createQuery("select f.toId from Friend f where f.fromId.email=:e2 and f.status='A'");
		query2.setString("e2", email);
		List<User> list2=query2.list();
		
		list1.addAll(list2);//UNION SET OPERATOR , list1=list1 U list2
		
		return list1;
	}

}



