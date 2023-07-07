package com.ni.model;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<ItemOrder, Integer> {

//	================ 查詢所有賣單 ================
	@Query("FROM ItemOrder o JOIN GameItem i ON o.itemId = i.itemId "
			+ "JOIN Game g ON i.gameId = g.gameId WHERE i.gameId = :id AND itemName = :name "
			+ "AND buyer IS NULL AND seller IS NOT NULL AND o.status = 1 ORDER BY o.price")
	public List<ItemOrder> findSellItemList(@Param("id") Integer id, @Param("name") String name, Pageable page);
	
//	================ 查詢每個道具最低價的賣單 ================
	@Query("SELECT o.itemId, i.itemName, i.itemImgName, g.gameId, MIN(o.price) price FROM ItemOrder o "
			+ "JOIN GameItem i ON o.itemId = i.itemId JOIN Game g ON i.gameId = g.gameId "
			+ "WHERE o.status = 1 AND buyer IS NULL AND seller IS NOT NULL "
			+ "GROUP BY o.itemId, i.itemName, i.itemImgName, g.gameId")
	public List<Object[]> countOrderList();
	
//	================ 查詢每個道具最低價的賣單 ================
	@Query(value = "SELECT * FROM gameItem i JOIN (SELECT itemId, MIN(price) minPrice FROM itemOrder "
			+ "WHERE seller IS NOT NULL AND buyer IS NULL AND status = 1 GROUP BY itemId) p ON i.itemId = p.itemId", 
			nativeQuery = true)
	public List<ItemOrder> finditemAndMinPrice();
	
//	================ 查詢每個道具最低價的賣單 ================
	@Query(value = "SELECT itemId, MIN(price) minPrice FROM itemOrder "
			+ "WHERE seller IS NOT NULL AND buyer IS NULL AND status = 1 GROUP BY itemId", 
			nativeQuery = true)
	public List<Object[]> findMinPrice();
	
//	================ 查詢會員個人所有已成交的訂單 ================
	@Query("FROM ItemOrder WHERE itemId = :id AND buyer IS NOT NULL AND seller IS NOT NULL AND status = 2")
	public List<ItemOrder> findByItemIdAndStatus(@Param("id") Integer id);
	
//	================ 查詢單個道具最低價的買單 ================
	@Query(value = "SELECT TOP 1 * FROM itemOrder WHERE itemId = :id AND buyer IS NOT NULL "
			+ "AND seller IS NULL AND status = 1 ORDER BY price DESC, ordId", nativeQuery = true)
	public List<ItemOrder> findBuysByIdAndStatus(@Param("id") Integer id);
	
//	================ 查詢單個道具的賣單 ================
	@Query("FROM ItemOrder WHERE itemId = :id AND buyer IS NULL AND seller IS NOT NULL AND status = 1 ORDER BY price, ordId")
	public List<ItemOrder> findSalesByIdAndStatus(@Param("id") Integer id);
	
//	================ 查詢會員所有掛單中的訂單 ================
	@Query(value = "SELECT * FROM itemOrder WHERE [status] = 1 AND (seller = :id OR buyer = :id) ORDER BY ordId DESC", nativeQuery = true)
	public List<ItemOrder> findActiveList(@Param("id") Integer id);
}
