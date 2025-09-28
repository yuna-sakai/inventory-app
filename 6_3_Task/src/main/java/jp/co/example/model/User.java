package jp.co.example.model;

public class User {
	private int userId;
	private String loginId;
	private String userName;
	private String password;
	private int roleId;
	private String roleName;

	// デフォルトコンストラクタ
	public User() {
	}

	// コンストラクタ
	public User(int userId, String loginId, String userName, String password, int roleId, String roleName) {
		this.userId = userId;
		this.loginId = loginId;
		this.userName = userName;
		this.password = password;
		this.roleId = roleId;
		this.roleName = roleName;
	}

	// ゲッターとセッター
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
