package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.domain.User;

public class UserDaoConnectionCountingTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = new User();
		user.setId("singun");
		user.setName("Daewook Shin");
		user.setPassword("123456");
		
		dao.add(user);
		
		System.out.println(user.getId() + " register success");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + " search success");
		
		CountingConnectionMaker ccm = 
				context.getBean("connectionMaker", CountingConnectionMaker.class);
		
		System.out.println("Connection counter : " + ccm.getCounter());
	}

}
