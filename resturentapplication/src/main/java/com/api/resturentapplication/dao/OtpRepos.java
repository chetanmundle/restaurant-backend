package com.api.resturentapplication.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.resturentapplication.entities.Otp;

public interface OtpRepos extends JpaRepository<Otp, Long>
{
	Optional<Otp> findByResturant_idAndRollAndOtp(int restid,String roll,int otp);
	
	
}
