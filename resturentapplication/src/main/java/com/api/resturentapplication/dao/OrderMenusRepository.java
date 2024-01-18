package com.api.resturentapplication.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.Order_menus;

public interface OrderMenusRepository extends JpaRepository<Order_menus, Integer>
{
//	Optional<List<Order_menus>> findByTables_Of_Resturant_id(int tableid);
	Optional<List<Order_menus>> findByTables_id(int tableid);

	List<Order_menus> findByTables_IdAndResturant_Id(int tableid, int restid);
}
