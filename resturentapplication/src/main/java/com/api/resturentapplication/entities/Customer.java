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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Component
public class Customer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonIgnore
	@ManyToOne
	private Resturant resturant;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	List<Previousdata> customers = new ArrayList<>();

	private String cname;

	private long cphone;
	
	private LocalDateTime localdatetime;
	
	private float totalbill;

	

	public Customer(long id, Resturant resturant, List<Previousdata> customers, String cname, long cphone,
			LocalDateTime localdatetime, float totalbill)
	{
		super();
		this.id = id;
		this.resturant = resturant;
		this.customers = customers;
		this.cname = cname;
		this.cphone = cphone;
		this.localdatetime = localdatetime;
		this.totalbill = totalbill;
	}



	public Customer()
	{
		super();
	}

	

	public float getTotalbill()
	{
		return totalbill;
	}



	public void setTotalbill(float totalbill)
	{
		this.totalbill = totalbill;
	}



	public long getId()
	{
		return id;
	}

	public void setId(long id)
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

	public List<Previousdata> getCustomers()
	{
		return customers;
	}

	public void setCustomers(List<Previousdata> customers)
	{
		this.customers = customers;
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

	public LocalDateTime getLocaldatetime()
	{
		return localdatetime;
	}

	public void setLocaldatetime(LocalDateTime localdatetime)
	{
		this.localdatetime = localdatetime;
	}
	
	
}
