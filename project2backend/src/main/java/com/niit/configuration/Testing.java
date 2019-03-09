package com.niit.configuration;

public class Testing {
	public static void main(String args[]) {
		DBConfiguration db=new DBConfiguration();
		db.getDataSource();
		db.sessionFactory();
		db.hibTransManagement();
		
	}

}
