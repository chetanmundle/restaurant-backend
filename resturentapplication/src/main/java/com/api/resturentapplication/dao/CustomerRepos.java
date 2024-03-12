package com.api.resturentapplication.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.resturentapplication.entities.Customer;

public interface CustomerRepos extends JpaRepository<Customer, Long>
{
//	List<Customer> findByLocalDateTimeDate(LocalDate data);

	@Query(value = "SELECT * FROM customer WHERE DATE(localdatetime) = ?1 and resturant_id = ?2", nativeQuery = true)
	List<Customer> finddatabydate(LocalDate date, int restid);

	@Query(value = "SELECT * FROM customer WHERE DATE(localdatetime) BETWEEN ?1 AND ?2 AND resturant_id = ?3", nativeQuery = true)
	List<Customer> getdatabetweentwodates(LocalDate startdate, LocalDate enddate, int restid);
}
