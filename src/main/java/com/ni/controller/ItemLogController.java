package com.ni.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ni.dto.ItemLogDTO;
import com.ni.model.ItemLog;
import com.ni.service.ItemLogService;

@Controller
public class ItemLogController {

	@Autowired
	private ItemLogService itemLogService;
	
	@GetMapping("/gameitem/allItemLog")
	public String getAllItemLog(Model m) {
		List<ItemLogDTO> logs = itemLogService.findAll();
		m.addAttribute("logs", logs);
		return "ni/itemLogDataTable";
	}
	
	@GetMapping("/gameitem/newItemLog")
	public String insertPage() {
		return "ni/itemLogInsert";
	}
	
//	-------------- gameItemMarket -----------------------------
	@ResponseBody
	@PostMapping("/market/newItemLog")
	public ItemLog insert(@RequestBody ItemLogDTO itemLog) {
		ItemLog log = itemLogService.findTotalById(itemLog.getMemberId(), itemLog.getItemId());
		if(log != null) {
			itemLog.setTotal(log.getTotal() + itemLog.getQuantity()); 
		} else {
			itemLog.setTotal(itemLog.getQuantity());
		}
		return itemLogService.insert(itemLog);
	}
	
	
	
//	-------------- profiles Inventory -----------------------------
	@ResponseBody
	@GetMapping("/profiles/inventory/{memberId}")
	public List<ItemLogDTO> findByMemberId(@PathVariable Integer memberId) {
		return itemLogService.findByMemberId(memberId);
	}
}
