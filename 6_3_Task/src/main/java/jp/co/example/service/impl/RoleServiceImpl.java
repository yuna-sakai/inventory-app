package jp.co.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.dao.RoleDao;
import jp.co.example.model.Role;
import jp.co.example.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> findAllRoles() {
		return roleDao.findAll();
	}

	@Override
	public String getRoleNameById(int roleId) {
		Role role = roleDao.findById(roleId);
		return role != null ? role.getRoleName() : null;
	}
}
