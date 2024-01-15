package com.api.resturentapplication.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<Menu> saveMenus(@PathVariable("restid") int restid, @RequestParam String name,
			@RequestParam("foodimg") MultipartFile foodimg, @RequestParam("isveg") boolean isveg,
			@RequestParam("discount") int discount, @RequestParam("foodtype") String foodtype,
			@RequestParam("ispopular") boolean ispopular, @RequestParam("carbs") int carbs,
			@RequestParam("proteins") int proteins, @RequestParam("calories") int calories,
			@RequestParam("fooddetails") String fooddetails,@RequestParam("price") int price)
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
				return ResponseEntity.ok(menu);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();

				return ResponseEntity.internalServerError().build();
			}

		} else
		{
			return ResponseEntity.notFound().build();
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

	@GetMapping("/getmenus/vegornonveg/{vegornonveg}")
	public ResponseEntity<Optional<List<Menu>>> getMenusWithVegorNonveg(@PathVariable("vegornonveg") String vegornonveg)
	{

		if (vegornonveg.equals("veg"))
		{
			Optional<List<Menu>> findByIsveg = menuRepos.findByIsveg(true);

			return ResponseEntity.ok(findByIsveg);

//			return ResponseEntity.status(200).build();
		} else if (vegornonveg.equals("nonveg"))
		{
			Optional<List<Menu>> findByIsveg = menuRepos.findByIsveg(false);

			return ResponseEntity.ok(findByIsveg);
		} else
		{
			System.out.println("Not found");
			return ResponseEntity.notFound().build();
		}
	}
	
//	 Get on menu by Id
	@GetMapping("/getmenu/{menuid}")
	public ResponseEntity<Optional<Menu>> getMenuById(@PathVariable("menuid") int menuid)
	{
		Optional<Menu> findById = menuRepos.findById(menuid);
		
		if(findById.isPresent())
		{
			return ResponseEntity.ok(findById);
		}else {
			return ResponseEntity.notFound().build();
		}
	}

}
