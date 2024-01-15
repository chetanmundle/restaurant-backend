package com.api.resturentapplication.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.resturentapplication.dao.RestRepos;
import com.api.resturentapplication.dao.TableofResturentRepository;
import com.api.resturentapplication.entities.Resturant;
import com.api.resturentapplication.entities.TablesOfResturant;

@RestController
@RequestMapping("/tablesofrestaurant")
public class TableController
{
	@Autowired
	private TableofResturentRepository tableofResturentRepository;
	
	@Autowired
	private RestRepos restRepos;
	
	@PostMapping("/savetable/{restid}")
	public ResponseEntity<TablesOfResturant> saveTable(@RequestBody TablesOfResturant tablesOfResturant, @PathVariable("restid") int restid){
		Optional<Resturant> optionalResturant = restRepos.findById(restid);
		
		if(optionalResturant.isPresent()) {
			Resturant resturant = optionalResturant.get();
			tablesOfResturant.setResturant(resturant);
			tableofResturentRepository.save(tablesOfResturant);
			return ResponseEntity.ok(tablesOfResturant);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
