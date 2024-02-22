package com.api.resturentapplication.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.resturentapplication.dao.RestRepos;
import com.api.resturentapplication.entities.Resturant;

@RequestMapping("email")
@RestController
public class EmailController
{

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	RestRepos restRepos;

	private int generateOTP()
	{

		Random random = new Random();
		int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
		return otp;
	}

	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendEmail(@RequestBody Map<String, Object> requestMap)
	{
		try
		{
			String email = (String) requestMap.get("email");

			Optional<Resturant> findByEmail = restRepos.findByEmail(email);

			if (findByEmail.isPresent())
			{
				Resturant resturant = findByEmail.get();
				EmailController ec = new EmailController();
//				
				int OTP = ec.generateOTP();

				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(email);
				message.setSubject("OTP Details");
				message.setText("Your OTP for Forgot your Password is : " + OTP);
				emailSender.send(message);

				resturant.setOtp(OTP);
				restRepos.save(resturant);

				return ResponseEntity.status(HttpStatus.OK).body("OTP send Successfully");

			} else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email Not Found");
			}

		} catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

	@PostMapping("authenticate")
	public ResponseEntity<String> validateOTP(@RequestBody Map<String, Object> requestMap)
	{
		try
		{
			String email = (String) requestMap.get("email");
			int otpuser = (int) requestMap.get("otp");
			
			Optional<Resturant> findByEmail = restRepos.findByEmail(email);
			
			if(findByEmail.isPresent())
			{
				Resturant resturant = findByEmail.get();
				
				if(otpuser == resturant.getOtp())
				{
					resturant.setOtp(0);
					restRepos.save(resturant);
					return ResponseEntity.ok("OTP Successfully Varified");
				}else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect OTP");
				}
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found");
			}
			
		} catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}
}
