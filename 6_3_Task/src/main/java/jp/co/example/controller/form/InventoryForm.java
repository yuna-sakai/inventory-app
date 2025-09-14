package jp.co.example.controller.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InventoryForm {

    private int itemId;

    private int userId;

    @NotBlank(message = "食材名を入力してください")
    @Size(max = 30, message = "食材名は30文字以内で入力してください")
    private String itemName;

    @NotNull(message = "個数を入力してください")
    @Min(value = 1, message = "個数は1以上で入力してください")
    @Max(value = 9999, message = "個数は9999以下で入力してください")
    private Integer quantity;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
