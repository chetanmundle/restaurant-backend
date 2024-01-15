package com.api.resturentapplication.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.Menu;



public interface MenuRepos extends JpaRepository<Menu, Integer> {
    // public Restaurant findByRest_name(int id);
	Optional<Menu> findByIdAndResturant_id(int id,int resturant_id);
	
	Optional<List<Menu>> findByResturant_id(int restid);
	
	Optional<List<Menu>> findByIsveg(boolean isveg);
}
