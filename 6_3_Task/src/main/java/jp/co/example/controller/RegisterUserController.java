package jp.co.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.controller.form.InsertForm;
import jp.co.example.service.UserService;

@Controller
@RequestMapping("/registerUser")
public class RegisterUserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String showRegisterForm(Model model) {
		model.addAttribute("registerForm", new InsertForm());
		model.addAttribute("roles", userService.getAllRoles());
		return "registerUser";
	}

	@PostMapping
	public String registerUser(@Valid @ModelAttribute("registerForm") InsertForm registerForm,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("roles", userService.getAllRoles());
			return "registerUser";
		}

		if (userService.isLoginIdDuplicate(registerForm.getLoginId())) {
			model.addAttribute("loginIdError", "このログインIDは既に使用されています");
			model.addAttribute("roles", userService.getAllRoles());
			return "registerUser";
		}

		boolean success = userService.insertUser(registerForm);
		if (success) {
			model.addAttribute("message", "登録が完了しました");
			return "redirect:/userManagement";
		} else {
			model.addAttribute("loginIdError", "登録に失敗しました");
			model.addAttribute("roles", userService.getAllRoles());
			return "registerUser";
		}
	}
}
