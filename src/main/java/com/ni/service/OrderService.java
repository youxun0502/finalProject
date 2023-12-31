package com.ni.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ni.dto.ItemOrderDTO;
import com.ni.model.ItemOrder;
import com.ni.model.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	public List<ItemOrderDTO> findAll() {
		return convertToDTOList(orderRepo.findAll());
	}
	
	public ItemOrderDTO findById(Integer ordId) {
		Optional<ItemOrder> optional = orderRepo.findById(ordId);
		if(optional.isPresent()) {
			return convertToDTO(optional.get());
		}
		return null;
	}
	
	public ItemOrder insert(ItemOrderDTO itemOrder) {
		return orderRepo.save(convertToOrder(itemOrder));
	}
	
	@Transactional
	public ItemOrder updateStatusById(Integer ordId, Integer status) {
		Optional<ItemOrder> optional = orderRepo.findById(ordId);
		if(optional.isPresent()) {
			ItemOrder order = optional.get();
			order.setStatus(status);;
			return order;
		}
		System.out.println("no update data");
		return null;
	}
	
	public List<ItemOrderDTO> findSellItemList(Integer gameId, String itemName) {
		return convertToDTOList(orderRepo.findSellItemList(gameId, itemName));
	}
	
	public List<Map<String, Object>> findOrderList() {
		List<Object[]> results = orderRepo.countOrderList();
	    List<Map<String, Object>> orderList = new ArrayList<>();
	    for (Object[] result : results) {
	    	Map<String, Object> order = new HashMap<>();
	        order.put("itemId", result[0]);
	        order.put("itemName", result[1]);
	        order.put("itemImgName", result[2]);
	        order.put("gameId", result[3]);
	        order.put("price", result[4]);
	        orderList.add(order);
	    }
		return orderList;
	}
	
	public List<ItemOrderDTO> findByItemIdAndStatus(Integer itemId) {
		return convertToDTOList(orderRepo.findByItemIdAndStatus(itemId));
	}
	
	
//	======================= 轉換 DTO 和 Entity =======================
	public List<ItemOrderDTO> convertToDTOList(List<ItemOrder> orders) {
		List<ItemOrderDTO> orderDTOList = new ArrayList<>();
		for(ItemOrder order : orders) {
			ItemOrderDTO orderDTO = new ItemOrderDTO();
			if(order.getOrdId() != null) orderDTO.setOrdId(order.getOrdId());
			if(order.getItemId() != null) orderDTO.setItemId(order.getItemId());
			if(order.getBuyer() != null) orderDTO.setBuyer(order.getBuyer());
			if(order.getSeller() != null) orderDTO.setSeller(order.getSeller());
			if(order.getQuantity() != null) orderDTO.setQuantity(order.getQuantity());
			if(order.getPrice() != null) orderDTO.setPrice(order.getPrice());
			if(order.getStatus() != null) orderDTO.setStatus(order.getStatus());
			if(order.getCreateTime() != null) orderDTO.setCreateTime(order.getCreateTime());
			if(order.getUpdateTime() != null) orderDTO.setUpdateTime(order.getUpdateTime());
			if(order.getBuy() != null) orderDTO.setBuy(order.getBuy());
			if(order.getSell() != null) orderDTO.setSell(order.getSell());
			if(order.getGameItem() != null) orderDTO.setGameItem(order.getGameItem());
			orderDTOList.add(orderDTO);
		}
		return orderDTOList;
	}
	
	public ItemOrderDTO convertToDTO(ItemOrder order) {
		ItemOrderDTO orderDTO = new ItemOrderDTO();
		if(order.getOrdId() != null) orderDTO.setOrdId(order.getOrdId());
		if(order.getItemId() != null) orderDTO.setItemId(order.getItemId());
		if(order.getBuyer() != null) orderDTO.setBuyer(order.getBuyer());
		if(order.getSeller() != null) orderDTO.setSeller(order.getSeller());
		if(order.getQuantity() != null) orderDTO.setQuantity(order.getQuantity());
		if(order.getPrice() != null) orderDTO.setPrice(order.getPrice());
		if(order.getStatus() != null) orderDTO.setStatus(order.getStatus());
		if(order.getCreateTime() != null) orderDTO.setCreateTime(order.getCreateTime());
		if(order.getUpdateTime() != null) orderDTO.setUpdateTime(order.getUpdateTime());
		if(order.getBuy() != null) orderDTO.setBuy(order.getBuy());
		if(order.getSell() != null) orderDTO.setSell(order.getSell());
		if(order.getGameItem() != null) orderDTO.setGameItem(order.getGameItem());
		return orderDTO;
	}
	
	public ItemOrder convertToOrder(ItemOrderDTO orderDTO) {
		ItemOrder order = new ItemOrder();
		if(orderDTO.getOrdId() != null) order.setOrdId(orderDTO.getOrdId());
		if(orderDTO.getItemId() != null) order.setItemId(orderDTO.getItemId());
		if(orderDTO.getBuyer() != null) order.setBuyer(orderDTO.getBuyer());
		if(orderDTO.getSeller() != null) order.setSeller(orderDTO.getSeller());
		if(orderDTO.getQuantity() != null) order.setQuantity(orderDTO.getQuantity());
		if(orderDTO.getPrice() != null) order.setPrice(orderDTO.getPrice());
		if(orderDTO.getStatus() != null) order.setStatus(orderDTO.getStatus());
		if(orderDTO.getCreateTime() != null) order.setCreateTime(orderDTO.getCreateTime());
		if(orderDTO.getUpdateTime() != null) order.setUpdateTime(orderDTO.getUpdateTime());
		if(orderDTO.getBuy() != null) order.setBuy(orderDTO.getBuy());
		if(orderDTO.getSell() != null) order.setSell(orderDTO.getSell());
		if(orderDTO.getGameItem() != null) order.setGameItem(orderDTO.getGameItem());
		return order;
	}
}
