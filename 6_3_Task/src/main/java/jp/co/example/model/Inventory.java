package jp.co.example.model;

import java.time.LocalDate;

public class Inventory {

	private int itemId;
	private int userId;
	private String itemName;
	private int quantity;
	private LocalDate expiryDate;
	private String formattedExpiryDate;
	private boolean isExpiringSoon;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getFormattedExpiryDate() {
		return formattedExpiryDate;
	}

	public void setFormattedExpiryDate(String formattedExpiryDate) {
		this.formattedExpiryDate = formattedExpiryDate;
	}

	public boolean isExpiringSoon() {
		return isExpiringSoon;
	}

	public void setIsExpiringSoon(boolean isExpiringSoon) {
		this.isExpiringSoon = isExpiringSoon;
	}
}
