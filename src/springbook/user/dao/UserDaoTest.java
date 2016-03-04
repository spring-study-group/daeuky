package springbook.user.dao;

import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new DaoFactory().userDao();
		
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
	}
}
