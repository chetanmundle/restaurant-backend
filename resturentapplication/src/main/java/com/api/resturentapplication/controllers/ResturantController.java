package com.api.resturentapplication.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

//	API for the admin loggin
	@PostMapping("/admin/authentication")
	public ResponseEntity<String> adminauthentication(@RequestBody Map<String, Object> requestMap)
	{
		try
		{
			int restid = (int) requestMap.get("restid");
			String adminuid = (String) requestMap.get("adminuid");
			String adminpass = (String) requestMap.get("adminpass");

			int restiddb = 0;
			String adminuiddb = null, adminpassdb = null;

			List<Map<String, Object>> findrestbyid = restRepos.adminauth(restid);

			for (Map<String, Object> row : findrestbyid)
			{
				restiddb = (int) row.get("ID");
				adminuiddb = (String) row.get("ADMINUID");
				adminpassdb = (String) row.get("ADMINPASS");
			}

			if (adminuid.equals(adminuiddb) && adminpass.equals(adminpassdb))
			{
				return ResponseEntity.status(HttpStatus.OK).body("User Successfully Logged In");
			} else if (!adminuid.equals(adminuiddb) && !adminpass.equals(adminpassdb))
			{
				return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Invalid Username and Password");
			} else if (!adminuid.equals(adminuiddb))
			{
				return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Invalid Username");
			} else if (!adminpass.equals(adminpassdb))
			{
				return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Invalid Password");
			} else
			{
				return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not Found");
			}

		} catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	
//		API for the admin loggin
	@PostMapping("/manager/authentication")
	public ResponseEntity<String> managerauthentication(@RequestBody Map<String, Object> requestMap)
	{
		try
		{
			int restid = (int) requestMap.get("restid");
			String manageruid = (String) requestMap.get("manageruid");
			String managerpass = (String) requestMap.get("managerpass");

			int restiddb = 0;
			String manageriddb = null, managerpassdb = null;

			List<Map<String, Object>> findrestbyid = restRepos.managerAuth(restid);

			for (Map<String, Object> row : findrestbyid)
			{
				restiddb = (int) row.get("ID");
				manageriddb = (String) row.get("MANAGERUID");
				managerpassdb = (String) row.get("MANAGERPASS");
			}
			System.out.println(manageriddb +" "+managerpassdb);

			if (manageruid.equals(manageriddb) && managerpass.equals(managerpassdb))
			{
				return ResponseEntity.status(HttpStatus.OK).body("User Successfully Logged In");
			} else if (!manageruid.equals(manageriddb) && !managerpass.equals(managerpassdb))
			{
				return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Invalid Username and Password");
			} else if (!manageruid.equals(manageriddb))
			{
				return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Invalid Username");
			} else if (!managerpass.equals(managerpassdb))
			{
				return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Invalid Password");
			} else
			{
				return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not Found");
			}

		} catch (Exception e)
		{
			System.out.println("Hello");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}
