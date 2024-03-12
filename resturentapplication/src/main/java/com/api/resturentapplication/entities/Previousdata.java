package com.api.resturentapplication.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Component
public class Previousdata
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JsonIgnore
	private Customer customer;

	@ManyToOne
	@JsonIgnore
	private Menu menus;

	private int quantity;

	private LocalDate date;

	public Previousdata(long id, Customer customer, Menu menus, int quantity, LocalDate date)
	{
		super();
		this.id = id;
		this.customer = customer;
		this.menus = menus;
		this.quantity = quantity;
		this.date = date;
	}

	public Previousdata()
	{
		super();
	}

	public LocalDate getDate()
	{
		return date;
	}

	public void setDate(LocalDate date)
	{
		this.date = date;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public Menu getMenus()
	{
		return menus;
	}

	public void setMenus(Menu menus)
	{
		this.menus = menus;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

}
