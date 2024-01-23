package com.api.resturentapplication.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.resturentapplication.dao.MenuRepos;
import com.api.resturentapplication.dao.RestRepos;
import com.api.resturentapplication.entities.Menu;
import com.api.resturentapplication.entities.Resturant;

@RestController
@RequestMapping("/menu")
@CrossOrigin()
public class MenuController
{

	@Autowired
	private RestRepos restRepos;

	@Autowired
	private MenuRepos menuRepos;

//	Api for save particular menu
	@PostMapping("/savemenu/{restid}")
	public ResponseEntity<String> saveMenus(@PathVariable("restid") int restid, @RequestParam("name") String name,
			@RequestParam(name = "foodimg", required = false) MultipartFile foodimg,
			@RequestParam("isveg") boolean isveg,
			@RequestParam(name = "discount", required = false, defaultValue = "0") int discount,
			@RequestParam(name = "foodtype", required = false, defaultValue = "") String foodtype,
			@RequestParam(name = "ispopular", required = false, defaultValue = "false") boolean ispopular,
			@RequestParam(name = "carbs", required = false, defaultValue = "0") int carbs,
			@RequestParam(name = "proteins", required = false, defaultValue = "0") int proteins,
			@RequestParam(name = "calories", required = false, defaultValue = "0") int calories,
			@RequestParam("fooddetails") String fooddetails, @RequestParam("price") int price)
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
				menu.setFoodimg(foodimg.getBytes());
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
//				return ResponseEntity.status(HttpStatus.OK).build();
				return ResponseEntity.ok("Menu Saved");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();

				return ResponseEntity.internalServerError().body("Internal Server Error");
			}

		} else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
//			return ResponseEntity.notFound().build();
//			return ResponseEntity.status(404).build();
		}
	}

	@GetMapping("/getallmenus/{restid}")
	public ResponseEntity<Optional<List<Menu>>> getMenusOfRestaurant(@PathVariable("restid") int restid)
	{

//		List<Menu> findByResturant_id = menuRepos.findByResturant_id(restid);
//		return ResponseEntity.ok(findByResturant_id);

		Optional<List<Menu>> optionalfindByResturant_id = menuRepos.findByResturant_id(restid);
		if (optionalfindByResturant_id.isPresent())
		{
			return ResponseEntity.ok(optionalfindByResturant_id);
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/getmenus/vegornonveg/{restid}/{vegornonveg}")
	public ResponseEntity<Optional<List<Menu>>> getMenusWithVegorNonveg(@PathVariable("restid") int restid,
			@PathVariable("vegornonveg") String vegornonveg)
	{

		Optional<Resturant> findById = restRepos.findById(restid);

		if (findById.isPresent())
		{
			if (vegornonveg.equals("veg"))
			{
				Optional<List<Menu>> findByIsveg = menuRepos.findByIsveg(true);

				return ResponseEntity.ok(findByIsveg);

//				return ResponseEntity.status(200).build();
			} else if (vegornonveg.equals("nonveg"))
			{
				Optional<List<Menu>> findByIsveg = menuRepos.findByIsveg(false);

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
	@GetMapping("/getmenu/{menuid}")
	public ResponseEntity<Optional<Menu>> getMenuById(@PathVariable("menuid") int menuid)
	{
		Optional<Menu> findById = menuRepos.findById(menuid);

		if (findById.isPresent())
		{
			return ResponseEntity.ok(findById);
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

}
