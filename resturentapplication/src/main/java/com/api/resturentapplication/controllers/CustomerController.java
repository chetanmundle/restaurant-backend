package com.api.resturentapplication.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.resturentapplication.dao.CustomerRepos;
import com.api.resturentapplication.entities.Customer;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController
{
	@Autowired
	CustomerRepos customerRepos;

//	API for the get the todays data like new order/ total sell 
	@GetMapping("/sales/day/timeperiod/restaurant/{restid}/get")
	public ResponseEntity<Object> getDaysell(@PathVariable("restid") int restid,
			@RequestParam("startdate") LocalDate startdate, @RequestParam("enddate") LocalDate enddate)
	{

		try
		{
//			check the date is not after todays date
			if (startdate.isAfter(LocalDate.now()) || enddate.isAfter(LocalDate.now()) || startdate.isAfter(enddate))
			{
				return ResponseEntity.status(400).body("Date is not Valid");
			}

			List<Customer> getdatabydate = customerRepos.finddatabydate(LocalDate.now(), restid);
			List<Customer> getdatabetweentwodates = customerRepos.getdatabetweentwodates(startdate, enddate, restid);
			
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("todayssales", 0);
			responseMap.put("timeperiodsales", 0);
			
			if (!getdatabydate.isEmpty())
			{
				
				float totalSalesOfDay = 0;

				for (Customer customer : getdatabydate)
				{
					totalSalesOfDay += customer.getTotalbill();
				}

				responseMap.put("todayssales", totalSalesOfDay);  // this is day sales
				
			
			}
			 
			if(!getdatabetweentwodates.isEmpty())
			{
				float totalsales = 0;
				
				for(Customer customer:getdatabetweentwodates)
				{
					totalsales += customer.getTotalbill();
				}
				
				responseMap.put("timeperiodsales", totalsales);
			}
			
			
			return ResponseEntity.ok(responseMap);
		} catch (Exception e)
		{
			return ResponseEntity.internalServerError().body("Internal Server Error");
		}
	}
	
	
	
}
