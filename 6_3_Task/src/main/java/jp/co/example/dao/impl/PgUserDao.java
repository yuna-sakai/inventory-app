package jp.co.example.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.example.dao.UserDao;
import jp.co.example.model.User;

@Repository
public class PgUserDao implements UserDao {

	private static final String FIND_ALL = "SELECT u.*, r.role_name " +
			"FROM users u " +
			"LEFT JOIN roles r ON u.role_id = r.role_id";

	private static final String INSERT_USER = "INSERT INTO users (login_id, user_name, role_id, password) " +
			"VALUES (:loginId, :userName, :roleId, :password)";

	private static final String COUNT_BY_LOGIN_ID = "SELECT COUNT(*) FROM users WHERE login_id = :loginId";

	private static final String FIND_BY_LOGIN_ID_AND_PASSWORD = "SELECT u.*, r.role_name " +
			"FROM users u " +
			"LEFT JOIN roles r ON u.role_id = r.role_id " +
			"WHERE u.login_id = :loginId AND u.password = :password";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query(FIND_ALL, new UserRowMapper());
	}

	@Override
	public boolean isLoginIdDuplicate(String loginId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("loginId", loginId, java.sql.Types.VARCHAR);
		return jdbcTemplate.queryForObject(COUNT_BY_LOGIN_ID, params, Integer.class) > 0;
	}

	@Override
	public void save(User user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("loginId", user.getLoginId(), java.sql.Types.VARCHAR);
		params.addValue("userName", user.getUserName(), java.sql.Types.VARCHAR);
		params.addValue("roleId", user.getRoleId(), java.sql.Types.INTEGER);
		params.addValue("password", user.getPassword(), java.sql.Types.VARCHAR);
		jdbcTemplate.update(INSERT_USER, params);
	}

	private static class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getInt("user_id"));
			user.setLoginId(rs.getString("login_id"));
			user.setUserName(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			user.setRoleId(rs.getInt("role_id"));
			user.setRoleName(rs.getString("role_name"));
			return user;
		}
	}

	@Override
	public User findByLoginIdAndPassword(String loginId, String password) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("loginId", loginId, java.sql.Types.VARCHAR);
		params.addValue("password", password, java.sql.Types.VARCHAR);

		try {
			return jdbcTemplate.queryForObject(FIND_BY_LOGIN_ID_AND_PASSWORD, params, new UserRowMapper());
		} catch (Exception e) {
			return null;
		}
	}
}
