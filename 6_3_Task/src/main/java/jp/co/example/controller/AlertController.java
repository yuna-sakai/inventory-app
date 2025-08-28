package jp.co.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.example.model.Inventory;
import jp.co.example.service.InventoryService;

@Controller
public class AlertController {

	@Autowired
	private InventoryService inventoryService;

	@GetMapping("/alertList")
	public String showAlertList(Model model) {
		LocalDate currentDate = LocalDate.now();
		LocalDate alertDate = currentDate.plusDays(3);

		List<Inventory> alertList = inventoryService.findItemsExpiringBefore(alertDate);
		model.addAttribute("alertList", alertList);

		return "alertList";
	}
}
