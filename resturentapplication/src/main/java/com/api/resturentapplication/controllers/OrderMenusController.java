package com.api.resturentapplication.controllers;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/findmenusoftable/{restid}/{tableid}")
	public ResponseEntity<List<Map<String, Object>>> findByTableAndRest(@PathVariable("tableid") int tableid,
			@PathVariable("restid") int restid)
	{
		List<Order_menus> orderMenusList = orderMenusRepository.findByTables_IdAndResturant_Id(tableid, restid);

		if (!orderMenusList.isEmpty())
		{
			List<Map<String, Object>> responseList = new ArrayList<>();

			for (Order_menus orderMenus : orderMenusList)
			{
				Map<String, Object> orderMap = new HashMap<>();
//	                orderMap.put("id", orderMenus.getId());
//	                orderMap.put("status", orderMenus.getStatus());

				// Include restaurant details
//	                Resturant restaurant = orderMenus.getResturant();
//	                orderMap.put("restaurant_id", restaurant.getId());
//	                orderMap.put("restaurant_name", restaurant.getRest_name());
				
//				include table details
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
				responseList.add(orderMap);
			}

			return ResponseEntity.ok().body(responseList);
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

}
