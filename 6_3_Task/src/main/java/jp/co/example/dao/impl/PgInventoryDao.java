package jp.co.example.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.co.example.dao.InventoryDao;
import jp.co.example.model.Inventory;

@Repository
public class PgInventoryDao implements InventoryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Inventory> findByItemNameContaining(String itemName) {
        String sql = "SELECT * FROM inventories WHERE item_name LIKE ?";
        return jdbcTemplate.query(sql, new Object[] { "%" + itemName + "%" }, new InventoryRowMapper());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM inventories WHERE item_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Inventory> findById(int id) {
        String sql = "SELECT * FROM inventories WHERE item_id = ?";
        List<Inventory> inventories = jdbcTemplate.query(sql, new Object[] { id }, new InventoryRowMapper());
        return inventories.stream().findFirst();
    }

    @Override
    public void updateInventory(Inventory inventory) {
        String sql = "UPDATE inventories SET item_name = ?, quantity = ?, expiration_date = ? WHERE item_id = ?";
        jdbcTemplate.update(sql, inventory.getItemName(), inventory.getQuantity(), inventory.getExpiryDate(),
                inventory.getItemId());
    }

    @Override
    public List<Inventory> findAll() {
        String sql = "SELECT * FROM inventories";
        return jdbcTemplate.query(sql, new InventoryRowMapper());
    }

    @Override
    public void save(Inventory inventory) {
        String sql = "INSERT INTO inventories (user_id, item_name, quantity, expiration_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, inventory.getUserId(), inventory.getItemName(), inventory.getQuantity(),
                inventory.getExpiryDate());
    }

    @Override
    public List<Inventory> findItemsExpiringBefore(LocalDate date) {
        String sql = "SELECT * FROM inventories WHERE expiration_date <= ?";
        return jdbcTemplate.query(sql, new Object[] { date }, new InventoryRowMapper());
    }

    // ==========================
    // 追加メソッド（改修済み）
    // ==========================
    @Override
    public List<Inventory> findAllOrderByExpiryDateAndName() {
        String sql = "SELECT * FROM inventories ORDER BY expiration_date ASC, item_name ASC";
        return jdbcTemplate.query(sql, new InventoryRowMapper());
    }

    @Override
    public List<Inventory> findByItemNameContainingOrderByExpiryDateAndName(String itemName) {
        String sql = "SELECT * FROM inventories WHERE item_name LIKE ? ORDER BY expiration_date ASC, item_name ASC";
        return jdbcTemplate.query(sql, new Object[] { "%" + itemName + "%" }, new InventoryRowMapper());
    }

    // ==========================
    // 共通 RowMapper
    // ==========================
    private static class InventoryRowMapper implements RowMapper<Inventory> {
        @Override
        public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {
            Inventory inventory = new Inventory();
            inventory.setItemId(rs.getInt("item_id"));
            inventory.setUserId(rs.getInt("user_id"));
            inventory.setItemName(rs.getString("item_name"));
            inventory.setQuantity(rs.getInt("quantity"));
            java.sql.Date expirationDate = rs.getDate("expiration_date");
            if (expirationDate != null) {
                inventory.setExpiryDate(expirationDate.toLocalDate());
            }
            return inventory;
        }
    }
 // ==========================
 // 商品名順（DAO実装）
 // ==========================
 @Override
 public List<Inventory> findAllOrderByName() {
     String sql = "SELECT * FROM inventories ORDER BY item_name ASC";
     return jdbcTemplate.query(sql, new InventoryRowMapper());
 }

 @Override
 public List<Inventory> findByItemNameContainingOrderByName(String itemName) {
     String sql = "SELECT * FROM inventories WHERE item_name LIKE ? ORDER BY item_name ASC";
     return jdbcTemplate.query(sql, new Object[] { "%" + itemName + "%" }, new InventoryRowMapper());
 }

}
