package jp.co.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.example.controller.form.LoginForm;
import jp.co.example.model.Role;
import jp.co.example.model.User;
import jp.co.example.service.RoleService;
import jp.co.example.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private HttpSession session;

	public String checkLogin() {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		return null;
	}

	@GetMapping("/login")
	public String showLogin(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@Valid LoginForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "login";
		}

		User user = userService.authenticate(form.getLoginId(), form.getPassword());
		if (user == null) {
			String errorMessage = "ログイン情報が正しくありません。再度お試しください。";
			model.addAttribute("errorMessage", errorMessage);
			return "login";
		}

		session.setAttribute("user", user);
		session.setAttribute("loggedInUserName", user.getUserName());

		List<Role> roles = roleService.findAllRoles();
		session.setAttribute("roles", roles);

		if (user.getRoleId() == 1) {
			return "redirect:/userManagement";
		} else if (user.getRoleId() == 2) {
			return "redirect:/inventoryList";
		}

		return "login";
	}

	@PostMapping("/logout")
	public String logout() {
		session.invalidate();
		return "logout";
	}
}
