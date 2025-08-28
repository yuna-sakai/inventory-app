package jp.co.example.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.example.dao.RoleDao;
import jp.co.example.model.Role;

@Repository
public class PgRoleDao implements RoleDao {

	private static final String FIND_ALL = "SELECT * FROM roles";
	private static final String FIND_BY_ID = "SELECT * FROM roles WHERE role_id = :roleId"; // このクエリを追加

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Role> findAll() {
		return jdbcTemplate.query(FIND_ALL, new RoleRowMapper());
	}

	@Override
	public Role findById(int roleId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("roleId", roleId);
		return jdbcTemplate.queryForObject(FIND_BY_ID, params, new RoleRowMapper());
	}

	private static class RoleRowMapper implements RowMapper<Role> {
		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setRoleId(rs.getInt("role_id"));
			role.setRoleName(rs.getString("role_name"));
			return role;
		}
	}
}
