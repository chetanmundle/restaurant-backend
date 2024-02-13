package com.api.resturentapplication.entities;

import java.time.LocalDateTime;
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

	private int quantity;

	private int totalprice;

	private long cphone;
	
	private LocalDateTime datetime;

//	private 



	

	
	
	public Order_menus(int id, Resturant resturant, int status, TablesOfResturant tables, Menu menus, int quantity,
		int totalprice, long cphone, LocalDateTime dataTime)
{
	super();
	this.id = id;
	this.resturant = resturant;
	this.status = status;
	this.tables = tables;
	this.menus = menus;
	this.quantity = quantity;
	this.totalprice = totalprice;
	this.cphone = cphone;
	this.datetime = dataTime;
}

	public LocalDateTime getDataTime()
	{
		return datetime;
	}

	public void setDataTime(LocalDateTime dataTime)
	{
		this.datetime = dataTime;
	}
	
	public int getTotalprice()
	{
		return totalprice;
	}

	public void setTotalprice(int totalprice)
	{
		this.totalprice = totalprice;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
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

	public long getCphone()
	{
		return cphone;
	}

	public void setCphone(long cphone)
	{
		this.cphone = cphone;
	}
	
	

//	@OneToMany(mappedBy = "order_menus", cascade = CascadeType.ALL)
//	List<Menu> menus = new ArrayList<>();

}
