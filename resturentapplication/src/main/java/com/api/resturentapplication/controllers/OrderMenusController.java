package com.api.resturentapplication.controllers;

import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.resturentapplication.dao.CustomerRepos;
import com.api.resturentapplication.dao.MenuRepos;
import com.api.resturentapplication.dao.OrderMenusRepository;
import com.api.resturentapplication.dao.PreviousdataRepos;
import com.api.resturentapplication.dao.RestRepos;
import com.api.resturentapplication.dao.TableofResturentRepository;
import com.api.resturentapplication.entities.Customer;
import com.api.resturentapplication.entities.Menu;
import com.api.resturentapplication.entities.Order_menus;
import com.api.resturentapplication.entities.Previousdata;
import com.api.resturentapplication.entities.Resturant;
import com.api.resturentapplication.entities.TablesOfResturant;

import aj.org.objectweb.asm.Label;

@RestController
@RequestMapping("/ordermenus")
@CrossOrigin(origins = "*")
@EnableScheduling
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
	private CustomerRepos customerRepos;

	@Autowired
	private PreviousdataRepos previousdataRepos;

//	@Autowired
//	private Order_menus order_menus;

//	API for add to cart item
	@PostMapping("/addtocart")
	public ResponseEntity<String> testapi(@RequestBody Map<String, Object> requestbodyMap)
	{
		Object restidobj = requestbodyMap.get("restid");
		Object tableidobj = requestbodyMap.get("tableid");
		int menuid = (int) requestbodyMap.get("menuid");
		Object cphoneStringobj = requestbodyMap.get("cphone");

		int restid = Integer.parseInt((String) restidobj);
		int tableid = Integer.parseInt((String) tableidobj);
//		int menuid = Integer.parseInt((String) menuidobj);
		long cphone = Long.parseLong((String) cphoneStringobj);

		try
		{

			Optional<TablesOfResturant> optionalfindByTableidAndResturant_id = tableofResturentRepository
					.findByIdAndResturant_id(tableid, restid);

			if (optionalfindByTableidAndResturant_id.isPresent())
			{
				TablesOfResturant tablesOfResturant = optionalfindByTableidAndResturant_id.get();
				if (tablesOfResturant.getCphone() == 0 || tablesOfResturant.getCphone() == cphone)
				{
					Optional<Resturant> optionalResturant = restRepos.findById(restid);
					Optional<Menu> optionalfindByIdAndResturant_id = menuRepos.findByIdAndResturant_id(menuid, restid);

					if (optionalfindByIdAndResturant_id.isPresent() && optionalResturant.isPresent())
					{

						try
						{
							Order_menus order_menus = new Order_menus();
							Resturant resturant = optionalResturant.get();
							Menu menu = optionalfindByIdAndResturant_id.get();
							order_menus.setResturant(resturant);
							order_menus.setMenus(menu);
							order_menus.setTables(tablesOfResturant);
							order_menus.setStatus(1);
							order_menus.setQuantity(1);
							order_menus.setCphone(cphone);
							order_menus.setDataTime(LocalDateTime.now());
							int price = menu.getPrice();
							int discount = menu.getDiscount();
							order_menus.setTotalprice(price - (price * discount / 100));

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
				} else
				{
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Table is already booked...");
				}
			} else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant or table not found..");
			}
		} catch (NumberFormatException e)
		{
			// Handle the case where the string cannot be parsed to a long
			return ResponseEntity.badRequest().body("Invalid phone number format");
		}

	}

////	Get table menus by status
//	@GetMapping("/findmenusoftable/{restid}/{tableid}/{status}")
//	public ResponseEntity<List<Map<String, Object>>> findByTableAndRest(@PathVariable("tableid") int tableid,
//			@PathVariable("restid") int restid, @PathVariable("status") int status)
//	{
//		List<Order_menus> orderMenusList = orderMenusRepository.findByTables_IdAndResturant_IdAndStatus(tableid, restid,
//				status);
//
//		if (!orderMenusList.isEmpty())
//		{
//			try
//			{
//				List<Map<String, Object>> responseList = new ArrayList<>();
//
//				for (Order_menus orderMenus : orderMenusList)
//				{
////					System.out.println("Ordermenulist .....:"+orderMenus);
//					Map<String, Object> orderMap = new HashMap<>();
//					orderMap.put("ordermenu_id", orderMenus.getId());
//					orderMap.put("status", orderMenus.getStatus());
//					orderMap.put("quantity", orderMenus.getQuantity());
//					orderMap.put("totalprice", orderMenus.getTotalprice());
//
////					include table details
//					TablesOfResturant tablesOfResturant = orderMenus.getTables();
//					orderMap.put("tableid", tablesOfResturant.getId());
//
//					// Include menu details
//					Menu menu = orderMenus.getMenus();
//					orderMap.put("id", menu.getId());
//					orderMap.put("name", menu.getName());
//					orderMap.put("foodtype", menu.getFoodtype());
//					orderMap.put("isveg", menu.getIsveg());
//					orderMap.put("discount", menu.getDiscount());
//					orderMap.put("ispopular", menu.getIspopular());
//					orderMap.put("carbs", menu.getCarbs());
//					orderMap.put("proteins", menu.getProteins());
//					orderMap.put("calories", menu.getCalories());
//					orderMap.put("fooddetails", menu.getFooddetails());
//
//					orderMap.put("foodimg", menu.getFoodimg());
//
//					responseList.add(orderMap);
//				}
//
//				return ResponseEntity.ok().body(responseList);
//			} catch (Exception e)
//			{
//				return ResponseEntity.internalServerError().build();
//			}
//		} else
//		{
//			return ResponseEntity.notFound().build();
//		}
//	}

//	Get all menus of the table
	@PostMapping("/findmenusoftable")
	public ResponseEntity<List<Map<String, Object>>> findmenusoftable(@RequestBody Map<String, Object> requestbodyMap)
	{
		Object restidobj = requestbodyMap.get("restid");
		Object tableidobj = requestbodyMap.get("tableid");
		int status = (int) requestbodyMap.get("status");
		Object cphoneStringobj = requestbodyMap.get("cphone");

		int restid = Integer.parseInt((String) restidobj);
		int tableid = Integer.parseInt((String) tableidobj);
		long cphone = Long.parseLong((String) cphoneStringobj);

		List<Order_menus> orderMenusList = orderMenusRepository
				.findByTables_IdAndResturant_IdAndStatusAndCphone(tableid, restid, status, cphone);

		if (!orderMenusList.isEmpty())
		{
			try
			{
				List<Map<String, Object>> responseList = new ArrayList<>();

				for (Order_menus orderMenus : orderMenusList)
				{
//					System.out.println("Ordermenulist .....:"+orderMenus);
					Map<String, Object> orderMap = new HashMap<>();
					orderMap.put("ordermenu_id", orderMenus.getId());
					orderMap.put("status", orderMenus.getStatus());
					orderMap.put("quantity", orderMenus.getQuantity());
					orderMap.put("totalprice", orderMenus.getTotalprice());

//					include table details
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
					orderMap.put("foodimg", menu.getFoodimg());

					responseList.add(orderMap);
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

//	GET Ids of the cart
	@PostMapping("/findidsofcartitem")
	public ResponseEntity<List<Map<String, Object>>> getidsofCartitem(@RequestBody Map<String, Object> requestbodyMap)
	{
		Object restidobj = requestbodyMap.get("restid");
		Object tableidobj = requestbodyMap.get("tableid");
		int status = (int) requestbodyMap.get("status");
		Object cphoneStringobj = requestbodyMap.get("cphone");

		int restid = Integer.parseInt((String) restidobj);
		int tableid = Integer.parseInt((String) tableidobj);
		long cphone = Long.parseLong((String) cphoneStringobj);

		List<Order_menus> orderMenusList = orderMenusRepository
				.findByTables_IdAndResturant_IdAndStatusAndCphone(tableid, restid, status, cphone);

		if (!orderMenusList.isEmpty())
		{
			try
			{
				List<Map<String, Object>> responseList = new ArrayList<>();

				for (Order_menus orderMenus : orderMenusList)
				{

					Map<String, Object> orderMap = new HashMap<>();

					// Include menu details
					Menu menu = orderMenus.getMenus();
					orderMap.put("id", menu.getId());

					responseList.add(orderMap);
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

//	API for increase Quantity of ordermenu
	@PutMapping("/increasequantity/{orderid}")
	public ResponseEntity<HttpStatus> inscreaseQuantityofItem(@PathVariable("orderid") int orderid)
	{
		Optional<Order_menus> findById = orderMenusRepository.findById(orderid);

		if (findById.isPresent())
		{
			try
			{
				Order_menus order_menus = findById.get();
				Menu menu = order_menus.getMenus();
				int Qt = order_menus.getQuantity() + 1;
				order_menus.setQuantity(Qt);
				int discountedPriceOnOneQT = menu.getPrice() - (menu.getPrice() * menu.getDiscount() / 100);
				order_menus.setTotalprice(Qt * discountedPriceOnOneQT);
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

//	API for the Quantity decrease of ordermenu
	@PutMapping("/decreasequantity/{orderid}")
	public ResponseEntity<HttpStatus> decreaseQuantityOfItem(@PathVariable("orderid") int orderid)
	{
		Optional<Order_menus> findById = orderMenusRepository.findById(orderid);
		if (findById.isPresent())
		{
			try
			{
				Order_menus order_menus = findById.get();

				int quantity = order_menus.getQuantity();
				if (quantity == 1)
				{
					orderMenusRepository.deleteById(orderid);
					return ResponseEntity.ok().build();
				} else
				{
					Menu menu = order_menus.getMenus();
					int Qt = quantity - 1;
					order_menus.setQuantity(Qt);
					int discountedPriceOnOneQT = menu.getPrice() - (menu.getPrice() * menu.getDiscount() / 100);
					order_menus.setTotalprice(Qt * discountedPriceOnOneQT);
					orderMenusRepository.save(order_menus);

					return ResponseEntity.ok().build();
				}
			} catch (Exception e)
			{
				return ResponseEntity.internalServerError().build();
			}
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

//	get the final price of the particular table with status
	@GetMapping("/getfinalprice/{restid}/{tableid}/{status}")
	public ResponseEntity<Integer> getTotalPriceofTable(@PathVariable("restid") int restid,
			@PathVariable("tableid") int tableid, @PathVariable("status") int status)
	{
		List<Order_menus> orderMenusList = orderMenusRepository.findByTables_IdAndResturant_IdAndStatus(tableid, restid,
				status);

		if (!orderMenusList.isEmpty())
		{
			try
			{
				int finalbillprice = 0;

				for (Order_menus orderMenus : orderMenusList)
				{
					int price = orderMenus.getTotalprice();
					finalbillprice += price;
				}

				return ResponseEntity.ok(finalbillprice);
			} catch (Exception e)
			{

				return ResponseEntity.internalServerError().build();
			}
		} else
		{
			return ResponseEntity.notFound().build();
		}

	}

//	make the status one to two (1{add in cart} -> 2{order}) of menu and update table to booked
	@PutMapping("/status/changestatustotwo/{restid}/{tableid}")
	public ResponseEntity<String> changeStatusonetToTwo(@PathVariable("restid") int restid,
			@PathVariable("tableid") int tableid, @RequestBody TablesOfResturant tablesOfResturant1)
	{
		Optional<TablesOfResturant> findByIdAndResturant_id = tableofResturentRepository
				.findByIdAndResturant_id(tableid, restid);

		if (findByIdAndResturant_id.isPresent())
		{
			TablesOfResturant tablesOfResturant = findByIdAndResturant_id.get();
			int status = tablesOfResturant.getStatus();
			if (status == 0)
			{
				List<Order_menus> orderMenusList = orderMenusRepository.findByTables_IdAndResturant_IdAndStatus(tableid,
						restid, 1);

				if (!orderMenusList.isEmpty())
				{
					try
					{

						tablesOfResturant.setCname(tablesOfResturant1.getCname());
						tablesOfResturant.setCphone(tablesOfResturant1.getCphone());
						tablesOfResturant.setStatus(1);
						tableofResturentRepository.save(tablesOfResturant);

						for (Order_menus order_menus : orderMenusList)
						{
							order_menus.setStatus(2);
							orderMenusRepository.save(order_menus);
						}
						return ResponseEntity.ok().build();
					} catch (Exception e)
					{
						return ResponseEntity.internalServerError().build();
					}
				} else
				{
//					System.out.println();
					return ResponseEntity.notFound().build();
				}
			} else if (status == 1)
			{
				if (tablesOfResturant.getCphone() == tablesOfResturant1.getCphone())
				{
					System.out.println("Same number");
					List<Order_menus> orderMenusList = orderMenusRepository
							.findByTables_IdAndResturant_IdAndStatus(tableid, restid, 1);

					if (!orderMenusList.isEmpty())
					{
						try
						{
							for (Order_menus order_menus : orderMenusList)
							{
								order_menus.setStatus(2);
								orderMenusRepository.save(order_menus);
							}
							return ResponseEntity.ok().build();
						} catch (Exception e)
						{
							return ResponseEntity.internalServerError().build();
						}
					} else
					{
//						System.out.println();
						return ResponseEntity.notFound().build();
					}
				} else
				{
//					anather user is logged in means table is booked
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Table is already booked...");
				}
			} else
			{
				return ResponseEntity.status(409).build();
			}
		} else
		{
			System.out.println("Restaurant not found");
			return ResponseEntity.notFound().build();
		}

	}

//	test of up
	@PutMapping("/status/changestatustotwo")
	public ResponseEntity<String> changeStatustotwo(@RequestBody Map<String, Object> requestbodyMap)
	{
		Object restidobj = requestbodyMap.get("restid");
		Object tableidobj = requestbodyMap.get("tableid");
//		int status = (int) requestbodyMap.get("status");
		String cname = (String) requestbodyMap.get("cname");
		Object cphoneStringobj = requestbodyMap.get("cphone");

		int restid = Integer.parseInt((String) restidobj);
		int tableid = Integer.parseInt((String) tableidobj);

		long cphone = Long.parseLong((String) cphoneStringobj);

		Optional<TablesOfResturant> findByIdAndResturant_id = tableofResturentRepository
				.findByIdAndResturant_id(tableid, restid);

		if (findByIdAndResturant_id.isPresent())
		{
			TablesOfResturant tablesOfResturant = findByIdAndResturant_id.get();
			int status = tablesOfResturant.getStatus();
			if (status == 0)
			{
				List<Order_menus> orderMenusList = orderMenusRepository
						.findByTables_IdAndResturant_IdAndStatusAndCphone(tableid, restid, 1, cphone);

				if (!orderMenusList.isEmpty())
				{
					try
					{

						tablesOfResturant.setCname(cname);
						tablesOfResturant.setCphone(cphone);
						tablesOfResturant.setStatus(1);
						tableofResturentRepository.save(tablesOfResturant);

						for (Order_menus order_menus : orderMenusList)
						{
							order_menus.setStatus(2);
							orderMenusRepository.save(order_menus);
						}
						return ResponseEntity.ok().build();
					} catch (Exception e)
					{
						return ResponseEntity.internalServerError().build();
					}
				} else
				{
//					System.out.println();
					return ResponseEntity.notFound().build();
				}
			} else if (status == 1)
			{
				if (tablesOfResturant.getCphone() == cphone)
				{
					System.out.println("Same number");
					List<Order_menus> orderMenusList = orderMenusRepository
							.findByTables_IdAndResturant_IdAndStatus(tableid, restid, 1);

					if (!orderMenusList.isEmpty())
					{
						try
						{
							for (Order_menus order_menus : orderMenusList)
							{
								order_menus.setStatus(2);
								orderMenusRepository.save(order_menus);
							}
							return ResponseEntity.ok().build();
						} catch (Exception e)
						{
							return ResponseEntity.internalServerError().build();
						}
					} else
					{
//						System.out.println();
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found....");
					}
				} else
				{
//					anather user is logged in means table is booked
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Table is already booked...");
				}
			} else
			{
				return ResponseEntity.status(409).build();
			}
		} else
		{
			System.out.println("Restaurant not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant Not Found....");
		}
	}

//	make the status one to two (2 -> 3)
	@PutMapping("/status/changestatustothree/{restid}/{tableid}")
	public ResponseEntity<HttpStatus> changeStatusTwotoThree(@PathVariable("restid") int restid,
			@PathVariable("tableid") int tableid)
	{
		List<Order_menus> orderMenusList = orderMenusRepository.findByTables_IdAndResturant_IdAndStatus(tableid, restid,
				2);

		if (!orderMenusList.isEmpty())
		{
			try
			{
				for (Order_menus order_menus : orderMenusList)
				{
					order_menus.setStatus(3);
					orderMenusRepository.save(order_menus);
				}
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

//	get invoice menus
	@PostMapping("/getinvoicemenus")
	public ResponseEntity<Map<String, Object>> getInvoicemenus(@RequestBody Map<String, Object> requestbodyMap)
	{
		Object restidobj = requestbodyMap.get("restid");
		Object tableidobj = requestbodyMap.get("tableid");
		int status = (int) requestbodyMap.get("status");

		int restid = Integer.parseInt((String) restidobj);
		int tableid = Integer.parseInt((String) tableidobj);

		List<Order_menus> orderMenusList = orderMenusRepository.findByTables_IdAndResturant_IdAndStatus(tableid, restid,
				status);

		if (!orderMenusList.isEmpty())
		{
			float billwithoutdiscount = 0;
			float billwithdiscount = 0;
			try
			{
				Map<String, Object> responseMap = new HashMap<>();
				List<Map<String, Object>> orderMenusResponseList = new ArrayList<>();

				for (Order_menus orderMenus : orderMenusList)
				{

					// Include menu details
					Menu menu = orderMenus.getMenus();

					int orderId = menu.getId();
					boolean isIdPresent = orderMenusResponseList.stream()
							.anyMatch(order -> ((Integer) order.get("id")) == orderId);

					if (isIdPresent)
					{
						// ID is already present, update the quantity
						Map<String, Object> existingOrder = orderMenusResponseList.stream()
								.filter(order -> ((Integer) order.get("id")) == orderId).findFirst().orElse(null);

						if (existingOrder != null)
						{
//							int existingQuantity = (Integer) existingOrder.get("quantity");
//							existingOrder.put("quantity", existingQuantity + 1);
//
//							float discount = menu.getDiscount();
//							float price = menu.getPrice();
//							float discountedprice = (float) (price - (price * discount / 100.0));
//							billwithoutdiscount += discountedprice;
							int existingQuantity = (Integer) existingOrder.get("quantity");
							existingOrder.put("quantity", existingQuantity + orderMenus.getQuantity());
							

							float discount = menu.getDiscount();
							float price = menu.getPrice();
							float discountedprice = (float) (price - (price * discount / 100.0));
							billwithoutdiscount += discountedprice * orderMenus.getQuantity() ;
						}
					} else
					{
//						Map<String, Object> orderMap = new HashMap<>();
////						orderMap.put("ordermenu_id", orderMenus.getId());
//						orderMap.put("quantity", orderMenus.getQuantity());
//						orderMap.put("id", menu.getId());
//						orderMap.put("name", menu.getName());
//
//						float discount = menu.getDiscount();
//						orderMap.put("discount", discount);
//						float price = menu.getPrice();
//						orderMap.put("price", price);
//						float discountedprice = (float) (price - (price * discount / 100.0));
//						billwithoutdiscount += discountedprice;
//						orderMap.put("discountedprice", discountedprice);
//
//						orderMenusResponseList.add(orderMap);
						Map<String, Object> orderMap = new HashMap<>();
//						orderMap.put("ordermenu_id", orderMenus.getId());
						orderMap.put("quantity", orderMenus.getQuantity());
						orderMap.put("id", menu.getId());
						orderMap.put("name", menu.getName());

						float discount = menu.getDiscount();
						orderMap.put("discount", discount);
						float price = menu.getPrice();
						orderMap.put("price", price);
						float discountedprice = (float) (price - (price * discount / 100.0));
						billwithoutdiscount += discountedprice * orderMenus.getQuantity() ;
						orderMap.put("discountedprice", discountedprice);

						orderMenusResponseList.add(orderMap);
					}
				}

				Optional<Resturant> findById = restRepos.findById(restid);
				Resturant resturant = findById.get();
				float discountofRestaurnat = resturant.getAdditionaldiscount();

				billwithdiscount = billwithoutdiscount - (billwithoutdiscount * discountofRestaurnat / 100);

				Optional<TablesOfResturant> tabledetails = tableofResturentRepository.findByIdAndResturant_id(tableid,
						restid);
				TablesOfResturant tablesOfResturant = tabledetails.get();
				responseMap.put("cname", tablesOfResturant.getCname());
				responseMap.put("cphone", tablesOfResturant.getCphone());
				responseMap.put("restaurantname", resturant.getRest_name());
				responseMap.put("discountofRestaurnat", discountofRestaurnat);

				responseMap.put("billwithdiscount", billwithdiscount);
				responseMap.put("billwithoutdiscount", billwithoutdiscount);
				responseMap.put("ordermenus", orderMenusResponseList);

				return ResponseEntity.ok().body(responseMap);

			} catch (Exception e)
			{
				return ResponseEntity.internalServerError().build();
			}
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

//	get invoice menus
	@PostMapping("/getinvoicemenus/customer")
	public ResponseEntity<Map<String, Object>> getInvoicemenusforcustomer(
			@RequestBody Map<String, Object> requestbodyMap)
	{
		Object restidobj = requestbodyMap.get("restid");
		Object tableidobj = requestbodyMap.get("tableid");
		int status = (int) requestbodyMap.get("status");
		Object cphoneStringobj = requestbodyMap.get("cphone");

		long cphone = Long.parseLong((String) cphoneStringobj);
		int restid = Integer.parseInt((String) restidobj);
		int tableid = Integer.parseInt((String) tableidobj);

		List<Order_menus> orderMenusList = orderMenusRepository
				.findByTables_IdAndResturant_IdAndStatusAndCphone(tableid, restid, status, cphone);

		if (!orderMenusList.isEmpty())
		{
		
			float billwithoutdiscount = 0;
			float billwithdiscount = 0;
			try
			{
				Map<String, Object> responseMap = new HashMap<>();
				List<Map<String, Object>> orderMenusResponseList = new ArrayList<>();

				for (Order_menus orderMenus : orderMenusList)
				{

					// Include menu details
					Menu menu = orderMenus.getMenus();

					int orderId = menu.getId();
				
					boolean isIdPresent = orderMenusResponseList.stream()
							.anyMatch(order -> ((Integer) order.get("id")) == orderId);

					if (isIdPresent)
					{
						// ID is already present, update the quantity
						Map<String, Object> existingOrder = orderMenusResponseList.stream()
								.filter(order -> ((Integer) order.get("id")) == orderId).findFirst().orElse(null);

						if (existingOrder != null)
						{
							int existingQuantity = (Integer) existingOrder.get("quantity");
							existingOrder.put("quantity", existingQuantity + orderMenus.getQuantity());
							

							float discount = menu.getDiscount();
							float price = menu.getPrice();
							float discountedprice = (float) (price - (price * discount / 100.0));
							billwithoutdiscount += discountedprice * orderMenus.getQuantity() ;
						}
						
					} else
					{
						Map<String, Object> orderMap = new HashMap<>();
//						orderMap.put("ordermenu_id", orderMenus.getId());
						orderMap.put("quantity", orderMenus.getQuantity());
						orderMap.put("id", menu.getId());
						orderMap.put("name", menu.getName());

						float discount = menu.getDiscount();
						orderMap.put("discount", discount);
						float price = menu.getPrice();
						orderMap.put("price", price);
						float discountedprice = (float) (price - (price * discount / 100.0));
						billwithoutdiscount += discountedprice * orderMenus.getQuantity() ;
						orderMap.put("discountedprice", discountedprice);

						orderMenusResponseList.add(orderMap);
						
					}
			

				}

				Optional<Resturant> findById = restRepos.findById(restid);
				Resturant resturant = findById.get();
				float discountofRestaurnat = resturant.getAdditionaldiscount();

				billwithdiscount = billwithoutdiscount - (billwithoutdiscount * discountofRestaurnat / 100);

				Optional<TablesOfResturant> tabledetails = tableofResturentRepository.findByIdAndResturant_id(tableid,
						restid);
				TablesOfResturant tablesOfResturant = tabledetails.get();
				responseMap.put("cname", tablesOfResturant.getCname());
				responseMap.put("cphone", tablesOfResturant.getCphone());
				responseMap.put("restaurantname", resturant.getRest_name());
				responseMap.put("discountofRestaurnat", discountofRestaurnat);

				responseMap.put("billwithdiscount", billwithdiscount);
				responseMap.put("billwithoutdiscount", billwithoutdiscount);
				responseMap.put("ordermenus", orderMenusResponseList);

				return ResponseEntity.ok().body(responseMap);
			} catch (Exception e)
			{
				return ResponseEntity.internalServerError().build();
			}
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

//	add data after taking payment to anather table and delete from order menu table
	@DeleteMapping("/vacanttable/delete")
	public ResponseEntity<String> vacantable(@RequestBody Map<String, Object> requestbodyMap)
	{
		int status = 3;

		Object restidobj = requestbodyMap.get("restid");
		Object tableidobj = requestbodyMap.get("tableid");
//		Object cphoneStringobj = requestbodyMap.get("cphone");
//		String cname = (String) requestbodyMap.get("cname");

//		long cphone = Long.parseLong((String) cphoneStringobj);
		int restid = Integer.parseInt((String) restidobj);
		int tableid = Integer.parseInt((String) tableidobj);

		Optional<Resturant> findById = restRepos.findById(restid);
//		List<Order_menus> listoforder = orderMenusRepository.findByTables_IdAndResturant_IdAndStatusAndCphone(tableid, restid, status, cphone);
		Optional<TablesOfResturant> findtable = tableofResturentRepository.findByIdAndResturant_id(tableid, restid);

		if (findById.isPresent() && findtable.isPresent())
		{

			Resturant resturant = findById.get();
			TablesOfResturant tablesOfResturant = findtable.get();
			long cphone = tablesOfResturant.getCphone();
			String cname = tablesOfResturant.getCname();
			List<Order_menus> listoforder = orderMenusRepository
					.findByTables_IdAndResturant_IdAndStatusAndCphone(tableid, restid, status, cphone);

			if (!listoforder.isEmpty())
			{
				Customer customer = new Customer();
				customer.setCname(cname);
				customer.setCphone(cphone);
				customer.setResturant(resturant);
				customer.setLocaldatetime(LocalDateTime.now());

				customerRepos.save(customer);
				
				float totalbill = 0;

				for (Order_menus order_menus : listoforder)
				{
					totalbill += order_menus.getTotalprice();
					Previousdata previousdata = new Previousdata();
					previousdata.setCustomer(customer);
					previousdata.setMenus(order_menus.getMenus());
					previousdata.setQuantity(order_menus.getQuantity());
					previousdata.setDate(LocalDate.now());
					previousdataRepos.save(previousdata);

				}
				float discount = resturant.getAdditionaldiscount();
				totalbill = totalbill - (totalbill * discount /100);
				customer.setTotalbill(totalbill);
				customerRepos.save(customer);

//				set vacant table
				tablesOfResturant.setCname(null);
				tablesOfResturant.setCphone(0);
				tablesOfResturant.setStatus(0);
				tableofResturentRepository.save(tablesOfResturant);

//				delete all from order menu table
				orderMenusRepository.deleteAll(listoforder);

				return ResponseEntity.status(HttpStatus.OK).body("Data saved and delete successfully");

			} else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order list not found");
			}

		} else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rest and table  not found");
		}

	}

//	This is for the delete the unwanted entries of the added cart by others
	@Scheduled(fixedDelay = 600000) 
	public void deleteOldData()
	{
		LocalDateTime timetodelete = LocalDateTime.now().minusMinutes(20);
		List<Order_menus> entities = orderMenusRepository.findByDatetimeBeforeAndStatus(timetodelete,1);
		orderMenusRepository.deleteAll(entities);
		System.out.println("Deleted old data: " + entities.size() + " entries.");
	}
	
	


}
