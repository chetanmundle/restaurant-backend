package com.api.resturentapplication.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.Customer;

public interface CustomerRepos extends JpaRepository<Customer, Long>
{
//	List<Customer> findByLocalDateTimeDate(LocalDate data);
}
