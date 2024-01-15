package com.api.resturentapplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.Resturant;

public interface RestRepos extends JpaRepository<Resturant,Integer>{

    Object getById(Resturant rest);
    
}
