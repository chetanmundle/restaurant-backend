package com.api.resturentapplication.controllers;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.api.resturentapplication.dao.MenuRepos;
import com.api.resturentapplication.dao.RestRepos;
import com.api.resturentapplication.entities.Menu;
import com.api.resturentapplication.entities.Resturant;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuController
{

	@Autowired
	private RestRepos restRepos;

	@Autowired
	private MenuRepos menuRepos;

//	Api for save particular menu
	@PostMapping("/savemenu/{restid}")
	public ResponseEntity<String> saveMenus(@PathVariable("restid") int restid, @RequestParam("name") String name,
			@RequestParam(name = "foodimg", required = false, defaultValue = "") String foodimg,
			@RequestParam("isveg") boolean isveg,
			@RequestParam(name = "discount", required = false, defaultValue = "0") int discount,
			@RequestParam(name = "foodtype", required = false, defaultValue = "") String foodtype,
			@RequestParam(name = "ispopular", required = false, defaultValue = "false") boolean ispopular,
			@RequestParam(name = "carbs", required = false, defaultValue = "0") int carbs,
			@RequestParam(name = "proteins", required = false, defaultValue = "0") int proteins,
			@RequestParam(name = "calories", required = false, defaultValue = "0") int calories,
			@RequestParam("fooddetails") String fooddetails, @RequestParam("price") int price)
	{

		try
		{
			Menu menu = new Menu();

			Optional<Resturant> optionalResturant = restRepos.findById(restid);

			if (optionalResturant.isPresent())
			{
				Resturant resturant = optionalResturant.get();
				try
				{
					menu.setResturant(resturant); // Set the rest field in Menu entity

					menu.setName(name);
					menu.setFoodimg(foodimg);
					menu.setIsveg(isveg);
					menu.setDiscount(discount);
					menu.setPrice(price);
					menu.setFoodtype(foodtype);
					menu.setIspopular(ispopular);
					menu.setCarbs(carbs);
					menu.setProteins(proteins);
					menu.setCalories(calories);
					menu.setFooddetails(fooddetails);
					menuRepos.save(menu);
//					return ResponseEntity.status(HttpStatus.OK).build();
					return ResponseEntity.ok("Menu Saved");
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();

					return ResponseEntity.internalServerError().body("Internal Server Error");
				}

			} else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
			}
		} catch (Exception e)
		{
			System.out.println("Internal server");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

//	update menu api 
//	@PutMapping("/updatemenu/{restid}/{menuid}")
//	public ResponseEntity<HttpStatus> updatemenu(@PathVariable("restid") int restid,@PathVariable("menuid") int menuid, 
//			@RequestParam(name = "name",required = false) String name,
//			@RequestParam(name = "foodimg", required = false) MultipartFile foodimg,
//			@RequestParam(name = "isveg", required = false) boolean isveg,
//			@RequestParam(name = "discount", defaultValue = "0") int discount,
//			@RequestParam(name = "foodtype", required = false) String foodtype,
//			@RequestParam(name = "ispopular", required = false) boolean ispopular,
//			@RequestParam(name = "carbs", required = false, defaultValue = "0") int carbs,
//			@RequestParam(name = "proteins", required = false, defaultValue = "0") int proteins,
//			@RequestParam(name = "calories", required = false,defaultValue = "0") int calories,
//			@RequestParam(name = "fooddetails", required = false,defaultValue = "0") String fooddetails, 
//			@RequestParam(name = "price", required = false,defaultValue = "0") int price)
//	{
//		Optional<Menu> findByIdAndResturant_id = menuRepos.findByIdAndResturant_id(menuid, restid);
//		if(findByIdAndResturant_id.isPresent())
//		{
//			Menu menu = findByIdAndResturant_id.get();
//			
//			if (name != null)
//			{
//				menu.setName(name);
////				menu.setDiscount(33);
//				System.out.println("Name is not null");
//			}else {
//				System.out.println("Name is null");
//			}
//			 return ResponseEntity.ok().build();
//		}else {
//			return ResponseEntity.notFound().build();
//		}
//	}

//	get all menus of the particular restaurant with menu order
	@GetMapping("/getallmenus/{restid}")
	public ResponseEntity<Optional<List<Menu>>> getMenusOfRestaurant(@PathVariable("restid") int restid)
	{

		Optional<List<Menu>> optionalfindByResturant_id = menuRepos.findByResturant_id(restid);
		if (optionalfindByResturant_id.isPresent())
		{
			return ResponseEntity.ok(optionalfindByResturant_id);
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

//	get all menus of the restaurant by veg or nonveg
	@GetMapping("/getmenus/vegornonveg/{restid}/{vegornonveg}")
	public ResponseEntity<Optional<List<Menu>>> getMenusWithVegorNonveg(@PathVariable("restid") int restid,
			@PathVariable("vegornonveg") String vegornonveg)
	{

		Optional<Resturant> findById = restRepos.findById(restid);

		if (findById.isPresent())
		{
			if (vegornonveg.equals("veg"))
			{
				Optional<List<Menu>> findByIsveg = menuRepos.findByIsvegAndResturant_id(true, restid);

				return ResponseEntity.ok(findByIsveg);

//				return ResponseEntity.status(200).build();
			} else if (vegornonveg.equals("nonveg"))
			{
				Optional<List<Menu>> findByIsveg = menuRepos.findByIsvegAndResturant_id(false, restid);

				return ResponseEntity.ok(findByIsveg);
			} else
			{
				return ResponseEntity.notFound().build();
			}
		} else
		{
			return ResponseEntity.notFound().build();
		}

	}

//	 Get on menu by Id
	@GetMapping("/getmenu/{restid}/{menuid}")
	public ResponseEntity<Map<String, Object>> getMenuById(@PathVariable("restid") int restid,
			@PathVariable("menuid") int menuid)
	{
		Optional<Menu> findById = menuRepos.findByIdAndResturant_id(menuid, restid);

		if (findById.isPresent())
		{
			Menu menu = findById.get();
			Map<String, Object> menuMap = new HashMap<>();
			menuMap.put("id", menu.getId());
			menuMap.put("calories", menu.getCalories());
			menuMap.put("carbs", menu.getCarbs());
			menuMap.put("discount", menu.getDiscount());
			menuMap.put("fooddetails", menu.getFooddetails());
			menuMap.put("foodtype", menu.getFoodtype());
			menuMap.put("ispopular", menu.getIspopular());
			menuMap.put("isveg", menu.getIsveg());
			menuMap.put("price", menu.getPrice());
			menuMap.put("name", menu.getName());
			menuMap.put("proteins", menu.getProteins());
			menuMap.put("foodimg", menu.getFoodimg());
			return ResponseEntity.ok(menuMap);
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

//	get foods by there type eg. pizza, burger
	@GetMapping("/getmenu/byperticularfood/{restid}/{foodtype}")
	public ResponseEntity<Optional<List<Menu>>> getPerticularfoodMenus(@PathVariable("restid") int restid,
			@PathVariable("foodtype") String foodtype)
	{
		Optional<Resturant> findById = restRepos.findById(restid);

		if (findById.isPresent())
		{
//			Resturant resturant = findById.get();
//			Menu menu = new Menu();
//			
//			menu.setResturant(resturant);
			Optional<List<Menu>> findByResturantidAndFoodtype = menuRepos.findByResturant_idAndFoodtype(restid,
					foodtype);
			return ResponseEntity.ok(findByResturantidAndFoodtype);
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

//	get api with veg or nonveg menus type = isveg
	@GetMapping("/getmenu/byperticularfood/type/{restid}/{type}/{foodtype}")
	public ResponseEntity<Optional<List<Menu>>> getPerticularfoodMenuswithvegnonveg(@PathVariable("restid") int restid,
			@PathVariable("type") String type, @PathVariable("foodtype") String foodtype)
	{
		boolean isveg = false;
		Optional<Resturant> findById = restRepos.findById(restid);

		if (findById.isPresent())
		{
			if (type.equals("veg"))
			{
				isveg = true;
			}
			Optional<List<Menu>> findByResturant_idAndFoodtypeAndIsveg = menuRepos
					.findByResturant_idAndFoodtypeAndIsveg(restid, foodtype, isveg);
			return ResponseEntity.ok(findByResturant_idAndFoodtypeAndIsveg);
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

//	get all only menus of the particular  restaurant 
	@GetMapping("/getmenus/getallonlymenus/{restid}")
	public ResponseEntity<List<Map<String, Object>>> getallmenusofperticularRestaurant(
			@PathVariable("restid") int restid)
	{
		Optional<List<Menu>> findByResturant_id = menuRepos.findByResturant_id(restid);

		if (findByResturant_id.isPresent())
		{
			try
			{
				List<Menu> menuList = findByResturant_id.get();

				List<Map<String, Object>> responseList = new ArrayList<>();

				for (Menu menu : menuList)
				{
					Map<String, Object> menuMap = new HashMap<>();
					menuMap.put("id", menu.getId());
					menuMap.put("calories", menu.getCalories());
					menuMap.put("carbs", menu.getCarbs());
					menuMap.put("discount", menu.getDiscount());
					menuMap.put("fooddetails", menu.getFooddetails());
					menuMap.put("foodtype", menu.getFoodtype());
					menuMap.put("ispopular", menu.getIspopular());
					menuMap.put("isveg", menu.getIsveg());
					menuMap.put("price", menu.getPrice());
					menuMap.put("name", menu.getName());
					menuMap.put("proteins", menu.getProteins());
					menuMap.put("foodimg", menu.getFoodimg());
					responseList.add(menuMap);
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

//	delete perticular menu of the table by id 
	@DeleteMapping("/deletebyid/{restid}/{menuid}")
	@Transactional
	public ResponseEntity<HttpStatus> deletemenuById(@PathVariable("restid") int restid,
			@PathVariable("menuid") int menuid)
	{
		try
		{
			menuRepos.deleteByIdAndResturant_id(menuid, restid);
			return ResponseEntity.noContent().build();
		} catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

//	API for Edit Menu
	@PostMapping("/edit/menu/{restid}")
	public ResponseEntity<String> editMenus(@RequestBody Menu menu, @PathVariable("restid") int restid)
	{
		try
		{
			Optional<Resturant> findrestById = restRepos.findById(restid);

			if (findrestById.isPresent())
			{
				Optional<Menu> findmenubyid = menuRepos.findById(menu.getId());
				if (findmenubyid.isPresent())
				{
					Resturant resturant = findrestById.get();
					menu.setResturant(resturant);

					menuRepos.save(menu);
					return ResponseEntity.ok().body("Menu Edited Successfully");
				} else
				{
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu data not found");
				}
			} else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant Not found");
			}

		} catch (Exception e)
		{
			return ResponseEntity.status(500).build();
		}
	}
	

}
