package com.api.resturentapplication.controllers;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.resturentapplication.dao.OtpRepos;
import com.api.resturentapplication.dao.RestRepos;
import com.api.resturentapplication.entities.Otp;
import com.api.resturentapplication.entities.Resturant;

@RequestMapping("email")
@RestController
@CrossOrigin(origins = "*")
public class EmailController
{

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	RestRepos restRepos;

	@Autowired
	OtpRepos otpRepos;

	private int generateOTP()
	{

		Random random = new Random();
		int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
		return otp;
	}

	@PostMapping("/sendotp")
	public ResponseEntity<String> sendEmail(@RequestBody Map<String, Object> requestMap)
	{
		try
		{
			String email = (String) requestMap.get("email");
			String roll = (String) requestMap.get("roll");

			Optional<Resturant> findByEmail = null;

			if (roll.equals("admin"))
			{
				findByEmail = restRepos.findByAdminemail(email);
			} else if (roll.equals("manager"))
			{
				findByEmail = restRepos.findByManageremail(email);
			} else
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body Data not found");
			}

			if (findByEmail.isPresent())
			{
				Resturant resturant = findByEmail.get();
				Otp otp = new Otp();
				EmailController ec = new EmailController();

				int OTP = ec.generateOTP();

				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(email);
				message.setSubject("OTP Details");
				message.setText("Your OTP for Forgot your Password is : " + OTP);
				emailSender.send(message);

				otp.setResturant(resturant);
				otp.setRoll(roll);
				otp.setOtp(OTP);
				otp.setOtptime(LocalDateTime.now());

				otpRepos.save(otp);

//				resturant.setOtp(OTP);
//				restRepos.save(resturant);

				return ResponseEntity.status(HttpStatus.OK).body("OTP send Successfully");

			} else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Email ID");
			}

		} catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

//	API to OTP Authentication
	@PostMapping("authenticate")
	public ResponseEntity<String> validateOTP(@RequestBody Map<String, Object> requestMap)
	{
		try
		{
			String email = (String) requestMap.get("email");
			int otpuser = (int) requestMap.get("otp");
			String roll = (String) requestMap.get("roll");

			Optional<Resturant> findByEmail = null;

			if (roll.equals("admin"))
			{
				findByEmail = restRepos.findByAdminemail(email);
			} else if (roll.equals("manager"))
			{
				findByEmail = restRepos.findByManageremail(email);
			} else
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body Data not found");
			}

			if (findByEmail.isPresent())
			{
				Resturant resturant = findByEmail.get();
				Optional<Otp> findotpofdb = otpRepos.findByResturant_idAndRollAndOtp(resturant.getId(), roll, otpuser);

				if (findotpofdb.isPresent())
				{
					Otp otp = findotpofdb.get();

					otpRepos.deleteById(otp.getId());
					return ResponseEntity.ok("OTP Successfully Varified");

				} else
				{
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect OTP");
				}

			} else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found");
			}

		} catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}
}
