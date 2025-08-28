package jp.co.example.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class InventoryForm {

	private int itemId;

	private int userId;

	@NotBlank(message = "食材名を入力してください")
	@Size(max = 30, message = "食材名は30文字以内で入力してください")
	private String itemName;

	@NotBlank(message = "個数を入力してください")
	@Pattern(regexp = "\\d+", message = "個数は数値で入力してください")
	private String quantity;

	private String expiryDate;

	// Getters and Setters

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}
