package com.api.resturentapplication.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Component
public class Resturant
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String rest_name;

	private int additionaldiscount;

	@OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL)
	List<Menu> menus = new ArrayList<>();

	@OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL)
	List<TablesOfResturant> tablesOfResturants = new ArrayList<>();

	@OneToMany(mappedBy = "resturant")
	List<Order_menus> order_menus = new ArrayList<>();
	
	@OneToMany(mappedBy = "resturant",cascade = CascadeType.ALL)
	List<Customer> customers = new ArrayList<>();

	public Resturant(int id, String rest_name, int additionaldiscount, List<Menu> menus,
			List<TablesOfResturant> tablesOfResturants, List<Order_menus> order_menus)
	{
		super();
		this.id = id;
		this.rest_name = rest_name;
		this.additionaldiscount = additionaldiscount;
		this.menus = menus;
		this.tablesOfResturants = tablesOfResturants;
		this.order_menus = order_menus;
	}

	public Resturant()
	{
//		super();
	}
	
	

	public int getAdditionaldiscount()
	{
		return additionaldiscount;
	}

	public void setAdditionaldiscount(int additionaldiscount)
	{
		this.additionaldiscount = additionaldiscount;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getRest_name()
	{
		return rest_name;
	}

	public void setRest_name(String rest_name)
	{
		this.rest_name = rest_name;
	}

	public List<Menu> getMenus()
	{
		return menus;
	}

	public void setMenus(List<Menu> menus)
	{
		this.menus = menus;
	}

	public List<TablesOfResturant> getTablesOfResturants()
	{
		return tablesOfResturants;
	}

	public void setTablesOfResturants(List<TablesOfResturant> tablesOfResturants)
	{
		this.tablesOfResturants = tablesOfResturants;
	}

	public List<Order_menus> getOrder_menus()
	{
		return order_menus;
	}

	public void setOrder_menus(List<Order_menus> order_menus)
	{
		this.order_menus = order_menus;
	}

	@Override
	public String toString()
	{
		return "Resturant [id=" + id + ", rest_name=" + rest_name + ", menus=" + menus + ", tablesOfResturants="
				+ tablesOfResturants + ", order_menus=" + order_menus + "]";
	}

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getRest_name() {
//        return rest_name;
//    }
//
//    public void setRest_name(String rest_name) {
//        this.rest_name = rest_name;
//    }
//
//    public List<Menu> getMenus() {
//        return menus;
//    }
//
//    public void setMenus(ArrayList<Menu> menus) {
//        this.menus = menus;
//    }
//
////    @JsonProperty("menus")
////    public List<String> getMenuNames() {
////        List<String> menuNames = new ArrayList<>();
////        for (Menu menu : menus) {
////        	menuNames.add(String.valueOf(menu.getId()));
////            menuNames.add(menu.getMenu_name());
////        }
////        return menuNames;
////    }
//
//    public Resturant() {
//    }
//
//    public Resturant(int id, String rest_name, ArrayList<Menu> menus) {
//        this.id = id;
//        this.rest_name = rest_name;
//        this.menus = menus;
//    }
//
//     @Override
//     public String toString() {
//     return "Resturant [id=" + id + ", rest_name=" + rest_name + ", menus=" +
//     menus + "]";
//     }

}
