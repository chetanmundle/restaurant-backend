package com.api.resturentapplication.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Component
public class TablesOfResturant
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JsonIgnore
	private Resturant resturant;

	private String cname;

	private long cphone;

	private int status;

	@OneToMany(mappedBy = "tables", cascade = CascadeType.ALL)
	List<Order_menus> order_menus = new ArrayList<>();

	public TablesOfResturant(int id, Resturant resturant, String cname, long cphone, int status,
			List<Order_menus> order_menus)
	{
		super();
		this.id = id;
		this.resturant = resturant;
		this.cname = cname;
		this.cphone = cphone;
		this.status = status;
		this.order_menus = order_menus;
	}

	public TablesOfResturant()
	{
		super();
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

	public List<Order_menus> getOrder_menus()
	{
		return order_menus;
	}

	public void setOrder_menus(List<Order_menus> order_menus)
	{
		this.order_menus = order_menus;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public long getCphone()
	{
		return cphone;
	}

	public void setCphone(long cphone)
	{
		this.cphone = cphone;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return "TablesOfResturant [id=" + id + ", resturant=" + resturant + ", order_menus=" + order_menus + "]";
	}

}
