package com.api.resturentapplication.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.api.resturentapplication.dao.MenuRepos;
import com.api.resturentapplication.dao.RestRepos;
import com.api.resturentapplication.entities.Menu;
import com.api.resturentapplication.entities.Resturant;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
public class ResturantController
{

	@Autowired
	private RestRepos restRepos;

	@Autowired
	private MenuRepos menuRepos;

	@PostMapping("/saverestaurant")
	public ResponseEntity<Resturant> saveRestaurant(@RequestBody Resturant resturant)
	{

		try
		{
			restRepos.save(resturant);
			return ResponseEntity.ok(resturant);
		} catch (Exception e)
		{
			// return
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	

	@GetMapping("/getall")
	public ResponseEntity<List<Resturant>> getAll()
	{

		List<Resturant> list = this.restRepos.findAll();

		// System.out.println(list.size());
		if (list.size() > 0)
		{
			return ResponseEntity.ok(list);
		} else
		{
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/restaurant/{restId}")
	public ResponseEntity<Resturant> getResturantWithMenus(@PathVariable("restId") int restId)
	{
		Optional<Resturant> optionalResturant = restRepos.findById(restId);

		if (optionalResturant.isPresent())
		{
			Resturant resturant = optionalResturant.get();
			return ResponseEntity.ok(resturant);
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}
}
