package jp.co.example.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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
	public String registerInventory(@Valid @ModelAttribute InventoryForm inventoryForm, BindingResult bindingResult,
			Model model, HttpSession session) {

		String redirect = authController.checkLogin();
		if (redirect != null)
			return redirect;

		if (bindingResult.hasErrors()) {
			return "registerInventory";
		}

		LocalDate expiryDate = null;
		if (!inventoryForm.getExpiryDate().isEmpty()) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				expiryDate = LocalDate.parse(inventoryForm.getExpiryDate(), formatter);
			} catch (Exception e) {
				model.addAttribute("errorMessage", "賞味期限の日付が無効です");
				return "registerInventory";
			}
		}

		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();

		int quantity = Integer.parseInt(inventoryForm.getQuantity());
		inventoryService.registerInventory(inventoryForm.getItemId(), userId, inventoryForm.getItemName(), quantity,
				expiryDate);

		return "redirect:/inventoryList";
	}

	   private Comparator<Inventory> comparatorFor(String col, String dir) {
	        Comparator<Inventory> c;
	        switch (col) {
	            case "item_name":
	                c = Comparator.comparing(Inventory::getItemName,
	                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
	                break;
	            case "quantity":
	                c = Comparator.comparingInt(Inventory::getQuantity);
	                break;
	            case "expiry_date":
	            default:
	                c = Comparator.comparing(Inventory::getExpiryDate,
	                        Comparator.nullsLast(Comparator.naturalOrder()));
	                break;
	        }
	        return "desc".equalsIgnoreCase(dir) ? c.reversed() : c;
	    }

	    private Comparator<Inventory> buildComparator(
	            String sort1, String dir1, String sort2, String dir2) {
	        return comparatorFor(sort1, dir1).thenComparing(comparatorFor(sort2, dir2));
	    }

	    @GetMapping("/inventoryList")
	    public String showInventoryList(
	            @RequestParam(required = false) String searchQuery,
	            @RequestParam(defaultValue = "expiry_date") String sort1,
	            @RequestParam(defaultValue = "asc")          String dir1,
	            @RequestParam(defaultValue = "item_name")    String sort2,
	            @RequestParam(defaultValue = "desc")         String dir2,
	            Model model) {

	        String redirect = authController.checkLogin();
	        if (redirect != null) return redirect;

	       
	        List<Inventory> list = (searchQuery == null || searchQuery.isBlank())
	                ? inventoryService.getAllInventories()
	                : inventoryService.searchInventory(searchQuery);

	        
	        LocalDate currentDate = LocalDate.now();
	        list.forEach(inv -> inv.setIsExpiringSoon(
	                inv.getExpiryDate() != null && inv.getExpiryDate().isBefore(currentDate.plusDays(4))
	        ));

	        
	        list = list.stream()
	                .sorted(buildComparator(sort1, dir1, sort2, dir2))
	                .collect(Collectors.toList());

	        model.addAttribute("inventoryList", list);
	        return "inventoryList";
	    }

	    // /searchResult は /inventoryList に寄せる（任意。残すなら同じ並べ替えを適用）
	    @GetMapping("/searchResult")
	    public String searchResult(
	            @RequestParam("searchQuery") String searchQuery,
	            @RequestParam(defaultValue = "expiry_date") String sort1,
	            @RequestParam(defaultValue = "asc")          String dir1,
	            @RequestParam(defaultValue = "item_name")    String sort2,
	            @RequestParam(defaultValue = "desc")         String dir2) {

	        String redirect = authController.checkLogin();
	        if (redirect != null) return redirect;

	        if (searchQuery == null || searchQuery.trim().isEmpty()) {
	            return "redirect:/inventoryList?errorMessage="
	                    + URLEncoder.encode("食材名を入力してください", StandardCharsets.UTF_8);
	        }
	        // そのまま inventoryList にリダイレクトし、同じロジックを使う
	        String q = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
	        return "redirect:/inventoryList?searchQuery=" + q
	                + "&sort1=" + sort1 + "&dir1=" + dir1
	                + "&sort2=" + sort2 + "&dir2=" + dir2;
	    }
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
