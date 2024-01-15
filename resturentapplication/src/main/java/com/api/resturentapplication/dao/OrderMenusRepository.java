package com.api.resturentapplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.Order_menus;

public interface OrderMenusRepository extends JpaRepository<Order_menus, Integer>
{

}
