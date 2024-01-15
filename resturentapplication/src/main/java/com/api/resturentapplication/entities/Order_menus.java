package com.api.resturentapplication.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Component
public class Order_menus
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JsonIgnore
	private Resturant resturant;

	private String menu_name;

	@ManyToOne
	@JsonIgnore
	private TablesOfResturant tablesOfResturant;

	@ManyToOne
	@JsonIgnore
	private Menu menus;

	public Order_menus(int id, Resturant resturant, String menu_name, TablesOfResturant tablesOfResturant, Menu menues)
	{
		super();
		this.id = id;
		this.resturant = resturant;
		this.menu_name = menu_name;
		this.tablesOfResturant = tablesOfResturant;
		this.menus = menues;
	}

	public Menu getMenus()
	{
		return menus;
	}

	public void setMenus(Menu menus)
	{
		this.menus = menus;
	}

	public Order_menus()
	{
//		super();
	}

	public String getMenu_name()
	{
		return menu_name;
	}

	public void setMenu_name(String menu_name)
	{
		this.menu_name = menu_name;
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

	public TablesOfResturant getTablesOfResturant()
	{
		return tablesOfResturant;
	}

	public void setTablesOfResturant(TablesOfResturant tablesOfResturant)
	{
		this.tablesOfResturant = tablesOfResturant;
	}

//	@OneToMany(mappedBy = "order_menus", cascade = CascadeType.ALL)
//	List<Menu> menus = new ArrayList<>();

}
