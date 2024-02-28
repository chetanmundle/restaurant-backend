package com.api.resturentapplication.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;
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

	private String adminuid;

	private String adminpass;

	private String manageruid;

	private String managerpass;
	
	private String adminemail;
	
	private String manageremail;
	

	@OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL)
	List<Menu> menus = new ArrayList<>();

	@OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL)
	List<TablesOfResturant> tablesOfResturants = new ArrayList<>();

	@OneToMany(mappedBy = "resturant")
	List<Order_menus> order_menus = new ArrayList<>();

	@OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL)
	List<Customer> customers = new ArrayList<>();
	
	@OneToMany(mappedBy = "resturant",cascade = CascadeType.ALL)
	List<Otp> otp = new ArrayList<>();

	

	
	public Resturant(int id, String rest_name, int additionaldiscount, String adminuid, String adminpass,
			String manageruid, String managerpass, String adminemail, String manageremail, List<Menu> menus,
			List<TablesOfResturant> tablesOfResturants, List<Order_menus> order_menus, List<Customer> customers)
	{
		super();
		this.id = id;
		this.rest_name = rest_name;
		this.additionaldiscount = additionaldiscount;
		this.adminuid = adminuid;
		this.adminpass = adminpass;
		this.manageruid = manageruid;
		this.managerpass = managerpass;
		this.adminemail = adminemail;
		this.manageremail = manageremail;

		this.menus = menus;
		this.tablesOfResturants = tablesOfResturants;
		this.order_menus = order_menus;
		this.customers = customers;
	}

	public Resturant(int id, String adminuid, String adminpass)
	{
		super();
		this.id = id;
		this.adminuid = adminuid;
		this.adminpass = adminpass;
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

	public String getAdminuid()
	{
		return adminuid;
	}

	public void setAdminuid(String adminuid)
	{
		this.adminuid = adminuid;
	}

	public String getManageruid()
	{
		return manageruid;
	}

	public void setManageruid(String manageruid)
	{
		this.manageruid = manageruid;
	}

	public String getAdminpass()
	{
		return adminpass;
	}

	public void setAdminpass(String adminpass)
	{
		this.adminpass = adminpass;
	}

	public String getManagerpass()
	{
		return managerpass;
	}

	public void setManagerpass(String managerpass)
	{
		this.managerpass = managerpass;
	}

	public List<Customer> getCustomers()
	{
		return customers;
	}

	public void setCustomers(List<Customer> customers)
	{
		this.customers = customers;
	}
	
	

	
	
	

	public String getAdminemail()
	{
		return adminemail;
	}

	public void setAdminemail(String adminemail)
	{
		this.adminemail = adminemail;
	}

	public String getManageremail()
	{
		return manageremail;
	}

	public void setManageremail(String manageremail)
	{
		this.manageremail = manageremail;
	}


	@Override
	public String toString()
	{
		return "Resturant [id=" + id + ", rest_name=" + rest_name + ", additionaldiscount=" + additionaldiscount
				+ ", adminuid=" + adminuid + ", adminpass=" + adminpass + ", manageruid=" + manageruid
				+ ", managerpass=" + managerpass + ", adminemail=" + adminemail + ", menus=" + menus + ", tablesOfResturants="
				+ tablesOfResturants + ", order_menus=" + order_menus + ", customers=" + customers + "]";
	}

	

}
