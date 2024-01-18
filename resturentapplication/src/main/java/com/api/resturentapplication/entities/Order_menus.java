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
import jakarta.persistence.JoinColumn;
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

	private int status;

	@ManyToOne
	@JsonIgnore
	private TablesOfResturant tables;

	@ManyToOne
	@JsonIgnore
	private Menu menus;

	public Order_menus(int id, Resturant resturant, String menu_name, int status, TablesOfResturant tablesOfResturant,
			Menu menus)
	{
		super();
		this.id = id;
		this.resturant = resturant;

		this.status = status;
		this.tables = tablesOfResturant;
		this.menus = menus;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
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

	public TablesOfResturant getTables()
	{
		return tables;
	}

	public void setTables(TablesOfResturant tables)
	{
		this.tables = tables;
	}

//	@OneToMany(mappedBy = "order_menus", cascade = CascadeType.ALL)
//	List<Menu> menus = new ArrayList<>();

}
