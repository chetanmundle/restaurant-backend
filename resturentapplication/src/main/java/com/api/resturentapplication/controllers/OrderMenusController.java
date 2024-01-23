package com.api.resturentapplication.controllers;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.resturentapplication.dao.MenuRepos;
import com.api.resturentapplication.dao.OrderMenusRepository;
import com.api.resturentapplication.dao.RestRepos;
import com.api.resturentapplication.dao.TableofResturentRepository;
import com.api.resturentapplication.entities.Menu;
import com.api.resturentapplication.entities.Order_menus;
import com.api.resturentapplication.entities.Resturant;
import com.api.resturentapplication.entities.TablesOfResturant;

@RestController
@RequestMapping("/ordermenus")
@CrossOrigin
public class OrderMenusController
{
	@Autowired
	private OrderMenusRepository orderMenusRepository;

	@Autowired
	private TableofResturentRepository tableofResturentRepository;

	@Autowired
	private RestRepos restRepos;

	@Autowired
	private MenuRepos menuRepos;

//	@Autowired
//	private Order_menus order_menus;

	@PostMapping("/addtocart/{restid}/{tableid}/{menuid}")
	private ResponseEntity<Order_menus> addtocartitem(@PathVariable("restid") int restid,
			@PathVariable("tableid") int tableid, @PathVariable("menuid") int menuid)
	{

		Optional<Resturant> optionalResturant = restRepos.findById(restid);
		Optional<Menu> optionalfindByIdAndResturant_id = menuRepos.findByIdAndResturant_id(menuid, restid);
		Optional<TablesOfResturant> optionalfindByTableidAndResturant_id = tableofResturentRepository
				.findByIdAndResturant_id(tableid, restid);

		if (optionalResturant.isPresent() && optionalfindByIdAndResturant_id.isPresent()
				&& optionalfindByTableidAndResturant_id.isPresent())
		{

			try
			{
				Order_menus order_menus = new Order_menus();
				Resturant resturant = optionalResturant.get();
				Menu menu = optionalfindByIdAndResturant_id.get();
				TablesOfResturant tablesOfResturant = optionalfindByTableidAndResturant_id.get();
//				System.out.println(resturant.getId() + resturant.getRest_name());
				order_menus.setResturant(resturant);
				order_menus.setMenus(menu);
				order_menus.setTables(tablesOfResturant);
				order_menus.setStatus(1);
				order_menus.setQuantity(1);
				int price =menu.getPrice();
				int discount = menu.getDiscount();
				order_menus.setTotalprice(price - (price * discount/100));
				

				orderMenusRepository.save(order_menus);
				return ResponseEntity.ok().build();
			} catch (Exception e)
			{
				return ResponseEntity.internalServerError().build();
			}

		} else
		{
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/findmenusoftable/{restid}/{tableid}/{status}")
	public ResponseEntity<List<Map<String, Object>>> findByTableAndRest(@PathVariable("tableid") int tableid,
			@PathVariable("restid") int restid,@PathVariable("status") int status)
	{
		List<Order_menus> orderMenusList = orderMenusRepository.findByTables_IdAndResturant_IdAndStatus(tableid, restid,status);

		if (!orderMenusList.isEmpty())
		{
			try
			{
				List<Map<String, Object>> responseList = new ArrayList<>();

				for (Order_menus orderMenus : orderMenusList)
				{
//					System.out.println("Ordermenulist .....:"+orderMenus);
					Map<String, Object> orderMap = new HashMap<>();
		                orderMap.put("ordermenu_id", orderMenus.getId());
		                orderMap.put("status", orderMenus.getStatus());
		                orderMap.put("quantity", orderMenus.getQuantity());
		                orderMap.put("totalprice", orderMenus.getTotalprice());

					// Include restaurant details
//		                Resturant restaurant = orderMenus.getResturant();
//		                orderMap.put("restaurant_id", restaurant.getId());
//		                orderMap.put("restaurant_name", restaurant.getRest_name());
					
//					include table details
					TablesOfResturant tablesOfResturant = orderMenus.getTables();
					orderMap.put("tableid", tablesOfResturant.getId());
					

					// Include menu details
					Menu menu = orderMenus.getMenus();
					orderMap.put("id", menu.getId());
					orderMap.put("name", menu.getName());
					orderMap.put("foodtype", menu.getFoodtype());
					orderMap.put("isveg", menu.getIsveg());
					orderMap.put("discount", menu.getDiscount());
					orderMap.put("ispopular", menu.getIspopular());
					orderMap.put("carbs", menu.getCarbs());
					orderMap.put("proteins", menu.getProteins());
					orderMap.put("calories", menu.getCalories());
					orderMap.put("fooddetails", menu.getFooddetails());
					
					orderMap.put("foodimg", menu.getFoodimg());
				
					responseList.add(orderMap);
				}

				return ResponseEntity.ok().body(responseList);
			} catch (Exception e)
			{
				return ResponseEntity.internalServerError().build();
			}
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	
//	API for increase Quantity of ordermenu
	@PutMapping("/increasequantity/{orderid}")
	public ResponseEntity<HttpStatus> inscreaseQuantityofItem(@PathVariable("orderid") int orderid)
	{
		Optional<Order_menus> findById = orderMenusRepository.findById(orderid);
		
		if(findById.isPresent())
		{
			try
			{
				Order_menus order_menus = findById.get();
				Menu menu = order_menus.getMenus();
				int Qt = order_menus.getQuantity() + 1;
				order_menus.setQuantity(Qt);
				int discountedPriceOnOneQT = menu.getPrice() - (menu.getPrice() * menu.getDiscount() / 100);
				order_menus.setTotalprice(Qt * discountedPriceOnOneQT);
				orderMenusRepository.save(order_menus);
				
				return ResponseEntity.ok().build();
			} catch (Exception e)
			{
				return ResponseEntity.internalServerError().build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
//	API for the Quantity decrease of ordermenu
	@PutMapping("/decreasequantity/{orderid}")
	public ResponseEntity<HttpStatus> decreaseQuantityOfItem(@PathVariable("orderid") int orderid)
	{
		Optional<Order_menus> findById = orderMenusRepository.findById(orderid);
		if(findById.isPresent())
		{
			try
			{
				Order_menus order_menus = findById.get();
				
				int quantity = order_menus.getQuantity();
				if(quantity == 1 ) {
					orderMenusRepository.deleteById(orderid);
					return ResponseEntity.ok().build();
				}else {
					Menu menu = order_menus.getMenus();
					int Qt = quantity - 1;
					order_menus.setQuantity(Qt);
					int discountedPriceOnOneQT = menu.getPrice() - (menu.getPrice() * menu.getDiscount() / 100);
					order_menus.setTotalprice(Qt * discountedPriceOnOneQT);
					orderMenusRepository.save(order_menus);
					
					return ResponseEntity.ok().build();
				}
			} catch (Exception e)
			{
				return ResponseEntity.internalServerError().build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
//	get the final price of the perticular table with status
	@GetMapping("/getfinalprice/{restid}/{tableid}/{status}")
	public ResponseEntity<Integer> getTotalPriceofTable(
	    @PathVariable("restid") int restid,
	    @PathVariable("tableid") int tableid,
	    @PathVariable("status") int status
	) {
	    List<Order_menus> orderMenusList = orderMenusRepository.findByTables_IdAndResturant_IdAndStatus(tableid, restid, status);

	    if (!orderMenusList.isEmpty()) {
	        try {
	            int finalbillprice = 0;

	            for (Order_menus orderMenus : orderMenusList) {
	                int price = orderMenus.getTotalprice();
	                finalbillprice += price;
	            }

	            return ResponseEntity.ok(finalbillprice);
	        } catch (Exception e) {
	        	
	            return ResponseEntity.internalServerError().build();
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	    
	}

}
