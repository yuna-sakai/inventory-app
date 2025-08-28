package jp.co.example.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.controller.form.ManageUserForm;
import jp.co.example.model.User;
import jp.co.example.service.UserService;

@Controller
@RequestMapping("/deleteUser")
public class DeleteUserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String showDeleteForm(Model model) {
		model.addAttribute("manageUserForm", new ManageUserForm());
		return "deleteUser";
	}

	@PostMapping
	public String deleteUser(@Valid @ModelAttribute("manageUserForm") ManageUserForm manageForm,
			BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			return "deleteUser";
		}

		// ログイン中のユーザー情報を取得
		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			return "redirect:/login";
		}

		// 自分自身の退会を防止
		if (currentUser.getLoginId().equals(manageForm.getLoginId())) {
			model.addAttribute("message", "自分自身を退会することはできません");
			return "deleteUser";
		}

		boolean success = userService.deleteUser(manageForm.getLoginId(), manageForm.getPassword());
		if (success) {
			model.addAttribute("message", "退会が完了しました");
		} else {
			model.addAttribute("message", "IDまたはパスワードが間違っています");
		}

		return "deleteUser";
	}
}
