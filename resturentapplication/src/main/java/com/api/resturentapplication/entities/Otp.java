package com.api.resturentapplication.entities;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Component
public class Otp
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int otp;

	private LocalDateTime otptime;

	private String roll;

	@ManyToOne
	@JsonIgnore
	Resturant resturant;

	public Otp()
	{
		super();
	}

	public Otp(Long id, int otp, LocalDateTime otptime, String roll, Resturant resturant)
	{
		super();
		this.id = id;
		this.otp = otp;
		this.otptime = otptime;
		this.roll = roll;
		this.resturant = resturant;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public int getOtp()
	{
		return otp;
	}

	public void setOtp(int otp)
	{
		this.otp = otp;
	}

	public LocalDateTime getOtptime()
	{
		return otptime;
	}

	public void setOtptime(LocalDateTime otptime)
	{
		this.otptime = otptime;
	}

	public String getRoll()
	{
		return roll;
	}

	public void setRoll(String roll)
	{
		this.roll = roll;
	}

	public Resturant getResturant()
	{
		return resturant;
	}

	public void setResturant(Resturant resturant)
	{
		this.resturant = resturant;
	}

}
