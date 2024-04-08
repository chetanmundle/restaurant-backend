package com.api.resturentapplication.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
			LocalDate todayDate = LocalDate.now();
			if (startdate.isAfter(todayDate) || enddate.isAfter(todayDate) || startdate.isAfter(enddate))
			{
				return ResponseEntity.status(400).body("Date is not Valid");
			}

//			List<Customer> getdatabydate = customerRepos.finddatabydate(todayDate, restid);
//			List<Customer> getdatabetweentwodates = customerRepos.getdatabetweentwodates(startdate, enddate, restid);

			List<Float> getdatabydate = customerRepos.finddatabydate(todayDate, restid);
			List<Float> getdatabetweentwodates = customerRepos.getdatabetweentwodates(startdate, enddate, restid);

			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("todayssales", 0);
			responseMap.put("timeperiodsales", 0);

			if (!getdatabydate.isEmpty())
			{

				float totalSalesOfDay = 0;

				for (Float bill : getdatabydate)
				{
					totalSalesOfDay += bill;
				}

				responseMap.put("todayssales", totalSalesOfDay); // this is day sales

			}

			if (!getdatabetweentwodates.isEmpty())
			{
				float totalsales = 0;

				for (Float bill : getdatabetweentwodates)
				{
					totalsales += bill;
				}

				responseMap.put("timeperiodsales", totalsales);
			}

			Map<String, Float> lastsevendaysMap = new LinkedHashMap<>();

			lastsevendaysMap.put("firstday", (float) 0);
			lastsevendaysMap.put("secondday", (float) 0);
			lastsevendaysMap.put("thirdday", (float) 0);
			lastsevendaysMap.put("fourthday", (float) 0);
			lastsevendaysMap.put("fifthday", (float) 0);
			lastsevendaysMap.put("sixthday", (float) 0);
			lastsevendaysMap.put("seventhday", (float) 0);

			List<Customer> getsevendayssales = customerRepos.getsevendayssales(todayDate.minusDays(7),
					todayDate.minusDays(1), restid);

			if (!getsevendayssales.isEmpty())
			{
				float sum = 0;
				for (Customer customer : getsevendayssales)
				{
					LocalDate localDate = customer.getLocaldatetime().toLocalDate();

					if (localDate.isEqual(todayDate.minusDays(7)))
					{
						lastsevendaysMap.put("firstday", lastsevendaysMap.get("firstday") + customer.getTotalbill());

					} else if (localDate.isEqual(todayDate.minusDays(6)))
					{
						lastsevendaysMap.put("secondday", lastsevendaysMap.get("secondday") + customer.getTotalbill());

					} else if (localDate.isEqual(todayDate.minusDays(5)))
					{
						lastsevendaysMap.put("thirdday", lastsevendaysMap.get("thirdday") + customer.getTotalbill());

					} else if (localDate.isEqual(todayDate.minusDays(4)))
					{
						lastsevendaysMap.put("fourthday", lastsevendaysMap.get("fourthday") + customer.getTotalbill());

					} else if (localDate.isEqual(todayDate.minusDays(3)))
					{
						lastsevendaysMap.put("fifthday", lastsevendaysMap.get("fifthday") + customer.getTotalbill());

					} else if (localDate.isEqual(todayDate.minusDays(2)))
					{
						lastsevendaysMap.put("sixthday", lastsevendaysMap.get("sixthday") + customer.getTotalbill());
					} else if (localDate.isEqual(todayDate.minusDays(1)))
					{
						lastsevendaysMap.put("seventhday",
								lastsevendaysMap.get("seventhday") + customer.getTotalbill());
					}
				}
			}

			responseMap.put("lastsevendayssales", lastsevendaysMap);

			return ResponseEntity.ok(responseMap);
		} catch (Exception e)
		{
			return ResponseEntity.internalServerError().body("Internal Server Error");
		}
	}

}
