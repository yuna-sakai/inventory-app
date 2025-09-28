package jp.co.example.dao;

import java.util.List;

import jp.co.example.model.User;

// ユーザー情報を管理するDAOインターフェース
public interface UserDao {
	User findByLoginIdAndPassword(String loginId, String password); // ログインIDとパスワードでユーザーを検索

	List<User> findAll(); // 全ユーザー情報を取得

	boolean isLoginIdDuplicate(String loginId); // ログインIDが重複しているかを確認

	void save(User user); // ユーザー情報を保存
}
