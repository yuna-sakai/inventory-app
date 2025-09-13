package jp.co.example.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jp.co.example.model.Inventory;

public interface InventoryDao {
    List<Inventory> findByItemNameContaining(String itemName);

    void deleteById(int id);

    Optional<Inventory> findById(int id);

    void updateInventory(Inventory inventory);

    List<Inventory> findAll();

    void save(Inventory inventory);

    List<Inventory> findItemsExpiringBefore(LocalDate date);

    // ==============================
    // 追加メソッド（改修用）
    // ==============================
    // 賞味期限順＋商品名順
    List<Inventory> findAllOrderByExpiryDateAndName();

    List<Inventory> findByItemNameContainingOrderByExpiryDateAndName(String itemName);

    // 商品名順
    List<Inventory> findAllOrderByName();

    List<Inventory> findByItemNameContainingOrderByName(String itemName);
}
