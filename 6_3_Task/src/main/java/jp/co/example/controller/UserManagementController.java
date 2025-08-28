package jp.co.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.model.User;
import jp.co.example.service.UserService;

@Controller
@RequestMapping("/userManagement")
public class UserManagementController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthController authController;

	@GetMapping
	public String showUserManagement(Model model) {
		String redirect = authController.checkLogin();
		if (redirect != null)
			return redirect;

		List<User> userList = userService.findAll();
		for (User user : userList) {
			user.setRoleName(getRoleName(user.getRoleId()));
		}
		model.addAttribute("userList", userList);
		return "userManagement";
	}

	private String getRoleName(int roleId) {
		if (roleId == 1) {
			return "管理者";
		} else {
			return "一般";
		}
	}
}
