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

    // ========================
    // 在庫一覧（ソート対応）
    // ========================
    @Override
    public List<Inventory> getSortedInventories(String sortKey) {
        List<Inventory> list;
        if ("expiry_date".equals(sortKey)) {
            list = inventoryDao.findAllOrderByExpiryDateAndName();
        } else {
            list = inventoryDao.findAllOrderByName();
        }
        return list.stream()
                .map(this::formatInventory)
                .collect(Collectors.toList());
    }

    // ========================
    // 検索（ソート対応）
    // ========================
    @Override
    public List<Inventory> searchInventory(String query, String sortKey) {
        List<Inventory> list;
        if ("expiry_date".equals(sortKey)) {
            list = inventoryDao.findByItemNameContainingOrderByExpiryDateAndName(query);
        } else {
            list = inventoryDao.findByItemNameContainingOrderByName(query);
        }
        return list.stream()
                .map(this::formatInventory)
                .collect(Collectors.toList());
    }

    // ========================
    // 既存メソッド
    // ========================

 @Override
 public List<Inventory> getAllInventories() {
     // デフォルトは商品名順
     return getSortedInventories("item_name");
 }

 @Override
 public List<Inventory> searchInventory(String query) {
     // デフォルトは商品名順
     return searchInventory(query, "item_name");
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

    // ========================
    // 日付フォーマット共通処理
    // ========================
    private Inventory formatInventory(Inventory inventory) {
        if (inventory.getExpiryDate() != null) {
            inventory.setFormattedExpiryDate(inventory.getExpiryDate().format(formatter));
        }
        return inventory;
    }
}
