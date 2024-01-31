package com.api.resturentapplication.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.TablesOfResturant;

public interface TableofResturentRepository extends JpaRepository<TablesOfResturant, Integer>
{
	Optional<TablesOfResturant> findByIdAndResturant_id(int tableid,int resturant_id);
	
	Optional<List<TablesOfResturant>> findByResturant_id(int restid);
}
