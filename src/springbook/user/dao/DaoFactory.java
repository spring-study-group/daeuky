package springbook.user.dao;

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
	
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}
	
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
