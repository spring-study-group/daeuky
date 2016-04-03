package springbook.user.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserDao {
	@Deprecated
	private DataSource dataSource;

	@Deprecated
	private JdbcContext jdbcContext;

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) { // 수정자 메소드이면서
														// jdbcContext에 대한 생성,
														// DI 작업을 동시에 수행
		this.jdbcContext = new JdbcContext(); // JdbcContext 생성(IoC)
		this.jdbcContext.setDataSource(dataSource); // DI
		this.dataSource = dataSource; // jdbcContext를 적용하지 않은 메소드를 위해 저장

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			return user;
		}
	};

	public void add(final User user) {
		this.jdbcTemplate.update(
				"insert into users(id, name, password, level, login, recommend) "
						+ "values(?,?,?,?,?,?);", user.getId(), user.getName(),
				user.getPassword(), user.getLevel().intValue(),
				user.getLogin(), user.getRecommend());

	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?;", new Object[] {id},
				this.userMapper);
	}

	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id;", 
				this.userMapper);
	}

	private void executeSql(final String query) throws SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
				return c.prepareStatement(query);
			}
		});
	}

	public int getCount() throws SQLException {
		return this.jdbcTemplate.queryForInt("select count(*) from users");
	}

	public void update(User user1) {
		this.jdbcTemplate.update(
				"update users set name = ?, password = ?, level = ?, login = ?, " +
		"recommend = ? where id = ?", user1.getName(), user1.getPassword(),
		user1.getLevel().intValue(), user1.getLogin(), user1.getRecommend(),
		user1.getId());
	}
}
