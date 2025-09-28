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

import jp.co.example.controller.form.UpdateForm;
import jp.co.example.service.UserService;

@Controller
@RequestMapping("/updateUser")
public class UpdateUserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String showUpdateForm(Model model) {
		model.addAttribute("updateForm", new UpdateForm());
		model.addAttribute("roles", userService.getAllRoles());
		return "updateUser";
	}

	@PostMapping
	public String updateUser(@Valid @ModelAttribute("updateForm") UpdateForm updateForm,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("roles", userService.getAllRoles());
			return "updateUser";
		}

		boolean success = userService.updateUser(updateForm);
		if (success) {
			model.addAttribute("message", "変更が完了しました");
		} else {
			model.addAttribute("updateError", "一致するログインIDがありませんでした");
			model.addAttribute("roles", userService.getAllRoles());
			return "updateUser";
		}

		return "redirect:/userManagement";
	}
}
