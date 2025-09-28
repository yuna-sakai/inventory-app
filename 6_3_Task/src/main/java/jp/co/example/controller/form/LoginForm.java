package jp.co.example.controller.form;

import javax.validation.constraints.NotBlank;

public class LoginForm {

	@NotBlank(message = "ログインIDを入力してください")
	private String loginId;

	@NotBlank(message = "パスワードを入力してください")
	private String password;

	// ゲッターとセッター
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
