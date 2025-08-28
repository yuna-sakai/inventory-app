package jp.co.example.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.dao.InventoryDao;
import jp.co.example.model.Inventory;
import jp.co.example.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryDao inventoryDao;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public List<Inventory> getAllInventories() {
		return inventoryDao.findAll().stream()
				.map(this::formatInventory)
				.collect(Collectors.toList());
	}

	@Override
	public List<Inventory> searchInventory(String query) {
		return inventoryDao.findByItemNameContaining(query).stream()
				.map(this::formatInventory)
				.collect(Collectors.toList());
	}

	@Override
	public Inventory getInventoryById(int id) {
		return inventoryDao.findById(id)
				.map(this::formatInventory)
				.orElse(null);
	}

	@Override
	public void deleteInventory(int id) {
		inventoryDao.deleteById(id);
	}

	@Override
	public void updateInventory(Inventory inventory) {
		inventoryDao.updateInventory(inventory);
	}

	@Override
	public void saveInventory(Inventory inventory) {
		inventoryDao.save(inventory);
	}

	@Override
	public void registerInventory(int itemId, int userId, String itemName, Integer quantity, LocalDate expiryDate) {
		Inventory inventory = new Inventory();
		inventory.setItemId(itemId);
		inventory.setUserId(userId);
		inventory.setItemName(itemName);
		inventory.setQuantity(quantity);
		inventory.setExpiryDate(expiryDate);
		inventoryDao.save(inventory);
	}

	@Override
	public List<Inventory> findItemsExpiringBefore(LocalDate date) {
		return inventoryDao.findItemsExpiringBefore(date).stream()
				.map(this::formatInventory)
				.collect(Collectors.toList());
	}

	private Inventory formatInventory(Inventory inventory) {
		if (inventory.getExpiryDate() != null) {
			inventory.setFormattedExpiryDate(inventory.getExpiryDate().format(formatter));
		}
		return inventory;
	}
}
