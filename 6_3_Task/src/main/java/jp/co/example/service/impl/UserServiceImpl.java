package jp.co.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import jp.co.example.controller.form.InsertForm;
import jp.co.example.controller.form.UpdateForm;
import jp.co.example.model.Role;
import jp.co.example.model.User;
import jp.co.example.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> userRowMapper = (rs, rowNum) -> {
	    User user = new User();
	    user.setUserId(rs.getInt("user_id"));
	    user.setLoginId(rs.getString("login_id"));
	    user.setUserName(rs.getString("user_name"));
	    user.setPassword(rs.getString("password"));
	    user.setRoleId(rs.getInt("role_id"));
	    return user;
	};

	private RowMapper<Role> roleRowMapper = (rs, rowNum) -> {
		Role role = new Role();
		role.setRoleId(rs.getInt("role_id"));
		role.setRoleName(rs.getString("role_name"));
		return role;
	};

	@Override
	public User authenticate(String loginId, String password) {
		String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";
		List<User> users = jdbcTemplate.query(sql, userRowMapper, loginId, password);
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public boolean deleteUser(String loginId, String password) {
		String sql = "DELETE FROM users WHERE login_id = ? AND password = ?";
		int rows = jdbcTemplate.update(sql, loginId, password);
		return rows > 0;
	}

	@Override
	public boolean resetPassword(String loginId, String newPassword) {
		String sql = "UPDATE users SET password = ? WHERE login_id = ?";
		int rows = jdbcTemplate.update(sql, newPassword, loginId);
		return rows > 0;
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM users";
		return jdbcTemplate.query(sql, userRowMapper);
	}

	@Override
	public boolean isLoginIdDuplicate(String loginId) {
		String sql = "SELECT COUNT(*) FROM users WHERE login_id = ?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] { loginId }, Integer.class);
		return count > 0;
	}

	@Override
	public void save(User user) {
		String sql = "INSERT INTO users (login_id, user_name, password, role_id) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getLoginId(), user.getUserName(), user.getPassword(), user.getRoleId());
	}

	@Override
	public List<Role> getAllRoles() {
		String sql = "SELECT * FROM roles";
		return jdbcTemplate.query(sql, roleRowMapper);
	}

	@Override
	public boolean insertUser(InsertForm form) {
		String sql = "INSERT INTO users (login_id, user_name, password, role_id) VALUES (?, ?, ?, ?)";
		int rows = jdbcTemplate.update(sql, form.getLoginId(), form.getUserName(), form.getPassword(),
				form.getRoleId());
		return rows > 0;
	}

	@Override
	public boolean updateUser(UpdateForm form) {
		String sql = "UPDATE users SET user_name = ?, role_id = ?, password = ? WHERE login_id = ?";
		int rows = jdbcTemplate.update(sql, form.getUserName(), form.getRoleId(), form.getPassword(),
				form.getLoginId());
		return rows > 0;
	}

	@Override
	public User findUserByLoginId(String loginId) {
		String sql = "SELECT * FROM users WHERE login_id = ?";
		List<User> users = jdbcTemplate.query(sql, userRowMapper, loginId);
		return users.isEmpty() ? null : users.get(0);
	}
}
