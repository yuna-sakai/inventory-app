package jp.co.example.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.example.controller.form.InventoryForm;
import jp.co.example.model.Inventory;
import jp.co.example.model.User;
import jp.co.example.service.InventoryService;

@Controller
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private AuthController authController;

    @GetMapping("/registerInventory")
    public String showRegisterInventoryPage(Model model) {
        String redirect = authController.checkLogin();
        if (redirect != null)
            return redirect;

        model.addAttribute("inventoryForm", new InventoryForm());
        return "registerInventory";
    }

    @PostMapping("/registerInventory")
    public String registerInventory(
            @Valid @ModelAttribute InventoryForm inventoryForm,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        String redirect = authController.checkLogin();
        if (redirect != null) return redirect;

        // バリデーションエラーがある場合は入力画面へ戻す
        if (bindingResult.hasErrors()) {
            return "registerInventory";
        }

        // 賞味期限チェック
        LocalDate expiryDate = null;
        if (inventoryForm.getExpiryDate() != null && !inventoryForm.getExpiryDate().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                expiryDate = LocalDate.parse(inventoryForm.getExpiryDate(), formatter);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "賞味期限の日付が無効です");
                return "registerInventory";
            }
        }

        // ログインユーザー取得
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();

        // quantity は Integer なのでそのまま利用
        int quantity = inventoryForm.getQuantity();

        // 在庫登録処理
        inventoryService.registerInventory(
                inventoryForm.getItemId(),
                userId,
                inventoryForm.getItemName(),
                quantity,
                expiryDate);

        return "redirect:/inventoryList";
    }

    // =========================
    // 在庫一覧・検索：ソート対応
    // =========================
    @GetMapping("/inventoryList")
    public String showInventoryList(@RequestParam(name = "sortKey", required = false, defaultValue = "item_name") String sortKey,
                                    Model model) {
        String redirect = authController.checkLogin();
        if (redirect != null)
            return redirect;

        LocalDate currentDate = LocalDate.now();
        List<Inventory> inventoryList = inventoryService.getSortedInventories(sortKey).stream()
                .peek(inventory -> {
                    if (inventory.getExpiryDate() != null) {
                        inventory.setIsExpiringSoon(inventory.getExpiryDate().isBefore(currentDate.plusDays(4)));
                    } else {
                        inventory.setIsExpiringSoon(false);
                    }
                })
                .collect(Collectors.toList());

        model.addAttribute("inventoryList", inventoryList);
        model.addAttribute("sortKey", sortKey);
        return "inventoryList";
    }

    @GetMapping("/searchResult")
    public String searchResult(@RequestParam("searchQuery") String searchQuery,
                               @RequestParam(name = "sortKey", required = false, defaultValue = "item_name") String sortKey,
                               Model model) {
        String redirect = authController.checkLogin();
        if (redirect != null)
            return redirect;

        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            model.addAttribute("errorMessage", "食材名を入力してください");
            model.addAttribute("inventoryList", inventoryService.getSortedInventories(sortKey));
            return "inventoryList";
        }

        List<Inventory> inventoryList = inventoryService.searchInventory(searchQuery, sortKey);
        if (inventoryList.isEmpty()) {
            model.addAttribute("errorMessage", "登録されていない食材です");
            model.addAttribute("inventoryList", inventoryService.getSortedInventories(sortKey));
            return "inventoryList";
        } else {
            model.addAttribute("inventoryList", inventoryList);
            model.addAttribute("sortKey", sortKey);
            return "searchResult";
        }
    }

    // =========================
    // 詳細・数量変更・削除は既存通り
    // =========================
    @GetMapping("/inventoryDetail")
    public String showInventoryDetail(@RequestParam("id") int id, Model model) {
        String redirect = authController.checkLogin();
        if (redirect != null)
            return redirect;

        Inventory inventory = inventoryService.getInventoryById(id);
        if (inventory == null) {
            return "redirect:/inventoryList";
        }

        model.addAttribute("inventory", inventory);
        return "inventoryDetail";
    }

    @PostMapping("/increaseQuantity")
    public String increaseQuantity(@RequestParam("id") int id, @RequestParam("quantity") int quantity) {
        String redirect = authController.checkLogin();
        if (redirect != null)
            return redirect;

        Inventory inventory = inventoryService.getInventoryById(id);
        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() + quantity);
            inventoryService.updateInventory(inventory);
        }
        return "redirect:/inventoryDetail?id=" + id;
    }

    @PostMapping("/decreaseQuantity")
    public String decreaseQuantity(@RequestParam("id") int id, @RequestParam("quantity") int quantity, Model model) {
        String redirect = authController.checkLogin();
        if (redirect != null)
            return redirect;

        Inventory inventory = inventoryService.getInventoryById(id);
        if (inventory != null) {
            if (inventory.getQuantity() > quantity) {
                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryService.updateInventory(inventory);
                return "redirect:/inventoryDetail?id=" + id;
            } else {
                model.addAttribute("inventory", inventory);
                model.addAttribute("errorMessage", "削除を押してください。");
                return "inventoryDetail";
            }
        }
        return "redirect:/inventoryList";
    }

    @PostMapping("/deleteInventory")
    public String deleteInventory(@RequestParam("id") int id) {
        String redirect = authController.checkLogin();
        if (redirect != null)
            return redirect;

        inventoryService.deleteInventory(id);
        return "redirect:/inventoryList";
    }
}
