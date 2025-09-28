package jp.co.example.controller.form;

import javax.validation.constraints.NotBlank;

public class InsertForm {

	@NotBlank(message = "ログインIDを入力してください")
	private String loginId;

	@NotBlank(message = "名前を入力してください")
	private String userName;

	private int roleId;

	@NotBlank(message = "パスワードを入力してください")
	private String password;

	// Getters and Setters
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
