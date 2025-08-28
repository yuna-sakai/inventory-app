package jp.co.example.service;

import java.util.List;

import jp.co.example.controller.form.InsertForm;
import jp.co.example.controller.form.UpdateForm;
import jp.co.example.model.Role;
import jp.co.example.model.User;

public interface UserService {
	User authenticate(String loginId, String password);

	boolean deleteUser(String loginId, String password);

	boolean resetPassword(String loginId, String newPassword);

	List<User> findAll();

	boolean isLoginIdDuplicate(String loginId);

	void save(User user);

	List<Role> getAllRoles();

	boolean insertUser(InsertForm form);

	boolean updateUser(UpdateForm form);

	User findUserByLoginId(String loginId);
}
