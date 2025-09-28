package jp.co.example.dao;

import java.util.List;

import jp.co.example.model.Role;

public interface RoleDao {
	List<Role> findAll();

	Role findById(int roleId);
}
