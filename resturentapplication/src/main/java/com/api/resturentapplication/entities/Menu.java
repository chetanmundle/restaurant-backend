package com.api.resturentapplication.entities;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Component
public class Menu
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JsonIgnore
	private Resturant resturant;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean isveg;

	private int discount;

	private String foodtype;

	private boolean ispopular;

	private int carbs;

	private int proteins;

	private int calories;
	
	@Column(nullable = false)
	private int price;

	private String fooddetails;

	@Column(nullable = false)
	private String foodimg;
 
	@OneToMany(mappedBy = "menus", cascade = CascadeType.ALL)
	List<Order_menus> order_menus = new ArrayList<>();
	
	@OneToMany(mappedBy = "menus",cascade = CascadeType.ALL)
	List<Previousdata> previousdatas =  new ArrayList<>();

	

	public Menu(int id, Resturant resturant, String name, boolean isveg, int discount, String foodtype,
			boolean ispopular, int carbs, int proteins, int calories, int price, String fooddetails, String foodimg,
			List<Order_menus> order_menus)
	{
		super();
		this.id = id;
		this.resturant = resturant;
		this.name = name;
		this.isveg = isveg;
		this.discount = discount;
		this.foodtype = foodtype;
		this.ispopular = ispopular;
		this.carbs = carbs;
		this.proteins = proteins;
		this.calories = calories;
		this.price = price;
		this.fooddetails = fooddetails;
		this.foodimg = foodimg;
		this.order_menus = order_menus;
	}

	public Menu()
	{
//		super();
	}

	
	
	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public List<Order_menus> getOrder_menus()
	{
		return order_menus;
	}

	public void setOrder_menus(List<Order_menus> order_menus)
	{
		this.order_menus = order_menus;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	

	public boolean getIsveg()
	{
		return isveg;
	}

	public void setIsveg(boolean isveg)
	{
		this.isveg = isveg;
	}

	public int getDiscount()
	{
		return discount;
	}

	public void setDiscount(int discount)
	{
		this.discount = discount;
	}

	public String getFoodtype()
	{
		return foodtype;
	}

	public void setFoodtype(String foodtype)
	{
		this.foodtype = foodtype;
	}

	public boolean getIspopular()
	{
		return ispopular;
	}

	public void setIspopular(boolean ispopular)
	{
		this.ispopular = ispopular;
	}

	public int getCarbs()
	{
		return carbs;
	}

	public void setCarbs(int carbs)
	{
		this.carbs = carbs;
	}

	public int getProteins()
	{
		return proteins;
	}

	public void setProteins(int proteins)
	{
		this.proteins = proteins;
	}

	public int getCalories()
	{
		return calories;
	}

	public void setCalories(int calories)
	{
		this.calories = calories;
	}

	public String getFooddetails()
	{
		return fooddetails;
	}

	public void setFooddetails(String fooddetails)
	{
		this.fooddetails = fooddetails;
	}

	

	public String getFoodimg()
	{
		return foodimg;
	}

	public void setFoodimg(String foodimg)
	{
		this.foodimg = foodimg;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Resturant getResturant()
	{
		return resturant;
	}

	public void setResturant(Resturant resturant)
	{
		this.resturant = resturant;
	}

//	@ManyToOne    // not required
//	@JsonIgnore
//	private Order_menus order_menus;

//
//	public int getId()
//	{
//		return id;
//	}
//
//	public void setId(int id)
//	{
//		this.id = id;
//	}
//
//	public Resturant getResturant()
//	{
//		return resturant;
//	}
//
//	public void setResturant(Resturant resturant)
//	{
//		this.resturant = resturant;
//	}
//
//	public Menu(int id, Resturant resturant, String menu_name)
//	{
//		super();
//		this.id = id;
//		this.resturant = resturant;
//		this.menu_name = menu_name;
//	}
//
//	public Menu()
//	{
//	}
//
//	public String getMenu_name()
//	{
//		return menu_name;
//	}
//
//	public void setMenu_name(String menu_name)
//	{
//		this.menu_name = menu_name;
//	}
//
//	// @Override
//	// @JsonIgnore
//	// public String toString() {
//	// return "Menu [id=" + id + ", rest=" + rest + ", menu_name=" + menu_name +
//	// "]";
//	// }

}
