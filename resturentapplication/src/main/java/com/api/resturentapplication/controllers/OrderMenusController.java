package com.api.resturentapplication.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	private Order_menus order_menus;

	@PostMapping("/addtocart/{restid}/{tableid}/{menuid}")
	private ResponseEntity<Order_menus> addtocartitem(@PathVariable("restid") int restid,
			@PathVariable("tableid") int tableid, @PathVariable("menuid") int menuid)
	{

		Optional<Resturant> optionalResturant = restRepos.findById(restid);
		Optional<Menu> optionalfindByIdAndResturant_id = menuRepos.findByIdAndResturant_id(menuid, restid);
		Optional<TablesOfResturant> optionalfindByTableidAndResturant_id = tableofResturentRepository
				.findByTableidAndResturant_id(tableid, restid);

		if (optionalResturant.isPresent() && optionalfindByIdAndResturant_id.isPresent()
				&& optionalfindByTableidAndResturant_id.isPresent())
		{

			Resturant resturant = optionalResturant.get();
			Menu menu = optionalfindByIdAndResturant_id.get();
			TablesOfResturant tablesOfResturant = optionalfindByTableidAndResturant_id.get();
//			System.out.println(resturant.getId() + resturant.getRest_name());
			order_menus.setResturant(resturant);
			order_menus.setMenus(menu);
			order_menus.setTablesOfResturant(tablesOfResturant);
			order_menus.setMenu_name(menu.getName());

			orderMenusRepository.save(order_menus);
			return ResponseEntity.ok(order_menus);



		} else
		{
			return ResponseEntity.notFound().build();
		}

	}
}
