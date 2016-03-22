package springbook.user.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserDao {
	private DataSource dataSource;
	private JdbcContext jdbcContext;

	public void setDataSource(DataSource dataSource) { // 수정자 메소드이면서
														// jdbcContext에 대한 생성,
														// DI 작업을 동시에 수행
		this.jdbcContext = new JdbcContext(); // JdbcContext 생성(IoC)
		this.jdbcContext.setDataSource(dataSource); // DI
		this.dataSource = dataSource; // jdbcContext를 적용하지 않은 메소드를 위해 저장
	}

	public void add(final User user) throws ClassNotFoundException,
			SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
				PreparedStatement ps = c
						.prepareStatement("insert into users(id, name, password, level, login, recommend)"
								+ "values(?,?,?,?,?,?);");

				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				ps.setInt(4, user.getLevel().intValue());
				ps.setInt(5, user.getLogin());
				ps.setInt(6, user.getRecommend());

				return ps;
			}
		});
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = this.dataSource.getConnection();

		PreparedStatement ps = c
				.prepareStatement("select * from users where id = ?");

		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
		}

		rs.close();
		ps.close();
		c.close();

		if (user == null)
			throw new EmptyResultDataAccessException(1);

		return user;
	}

	public void deleteAll() throws SQLException {
		executeSql("delete from users");
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
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();

			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
