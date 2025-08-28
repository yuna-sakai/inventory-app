package jp.co.example.service;

import java.util.List;

import jp.co.example.model.Role;

public interface RoleService {
	List<Role> findAllRoles();

	String getRoleNameById(int roleId); // このメソッドを追加
}
