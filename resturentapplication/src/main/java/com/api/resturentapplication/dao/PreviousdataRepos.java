package com.api.resturentapplication.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.Previousdata;

public interface PreviousdataRepos extends JpaRepository<Previousdata, Long>
{
	List<Previousdata> findByDate(LocalDate data);
}
