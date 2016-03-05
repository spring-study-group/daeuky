package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
//	DAO가 늘어날때 마다 ConnectionMaker 구현 클래스의 오브젝트를 생성하는 코드가 메소드마다 반복
//	어떤 ConnectionMaker 구현 클래스를 사용하지 결정하는 기능이 중복
//	public AccountDao accountDao() {
//		return new AccountDao(new DConnectionMaker());
//	}
//	
//	public MessageDao messageDao() {
//		return new MessageDao(new DConnectionMaker());
//	}
	
	@Bean
	public UserDao userDao() {
		UserDao dao = new UserDao();
		dao.setConnectionMaker(connectionMaker());
		return dao;
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
