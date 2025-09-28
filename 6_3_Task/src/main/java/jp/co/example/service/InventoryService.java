package jp.co.example.service;

import java.time.LocalDate;
import java.util.List;

import jp.co.example.model.Inventory;

public interface InventoryService {
	List<Inventory> getAllInventories();

	List<Inventory> searchInventory(String query);

	Inventory getInventoryById(int id);

	void deleteInventory(int id);

	void updateInventory(Inventory inventory);

	void saveInventory(Inventory inventory);

	void registerInventory(int itemId, int userId, String itemName, Integer quantity, LocalDate expiryDate);

	List<Inventory> findItemsExpiringBefore(LocalDate date);
}
