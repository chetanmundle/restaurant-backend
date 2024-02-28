package com.api.resturentapplication.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.resturentapplication.entities.Resturant;

public interface RestRepos extends JpaRepository<Resturant, Integer>
{

	Object getById(Resturant rest);

	Optional<Resturant> findByIdAndAdminuidAndAdminpass(int id, String adminuid, String adminpass);

	Optional<Resturant> findByAdminemail(String adminemail);
	
	Optional<Resturant> findByManageremail(String manageremail);

//	@Query(value = "SELECT * FROM RESTURANT WHERE ID = ?1", nativeQuery = true)
//	Optional<Resturant> findrestbyid(int restid);

	@Query(value = "SELECT ID,ADMINUID,ADMINPASS FROM RESTURANT WHERE ID = ?1", nativeQuery = true)
	List<Map<String, Object>> adminauth(int restid);

	@Query(value = "SELECT ID,manageruid,managerpass FROM RESTURANT WHERE ID = ?1", nativeQuery = true)
	List<Map<String, Object>> managerAuth(int restid);
	

	@Query(value = "SELECT ID,ADMINUID,ADMINPASS FROM RESTURANT WHERE ADMINUID = ?1 AND ADMINPASS = ?2",nativeQuery = true)
	List<Map<String, Object>> adminlogin(String adminuid,String adminpass);
	
	@Query(value = "SELECT ID,MANAGERUID,MANAGERPASS FROM RESTURANT WHERE MANAGERUID = ?1 AND MANAGERPASS = ?2",nativeQuery = true)
	List<Map<String, Object>> managerlogin(String manageruid,String managerpass);
	

}
