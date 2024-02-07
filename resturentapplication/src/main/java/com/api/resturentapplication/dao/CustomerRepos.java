package com.api.resturentapplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.Customer;

public interface CustomerRepos extends JpaRepository<Customer, Long>
{

}
