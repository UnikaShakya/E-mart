package services;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Message;
import beans.User;

@Service
public class UserNameValidation {
	
	@Autowired
	Message errorMessage;
	SessionFactory factory;
	Session session;
	
	public UserNameValidation() {
		
		factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		session = factory.getCurrentSession();
	}
	
	public boolean unique(String name) {
		try {
		session.beginTransaction();
		String HQL="from User where userName=?";
		Query query=session.createQuery(HQL);
		query.setString(0, name);
		Object res = query.uniqueResult();
		if(res==null)
			return true;
		else
			return false;
		}
		catch(Exception er) {
			System.out.println("Error : "+er.getMessage());
			this.errorMessage = new Message(true,"Internal Error Please Try Again Later..!!");
			return false;
		}
	}

}
