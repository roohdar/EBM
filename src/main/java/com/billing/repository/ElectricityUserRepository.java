package com.billing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.billing.entity.ElectricityUser;

public interface ElectricityUserRepository extends JpaRepository<ElectricityUser, Long>{
	
	ElectricityUser findByEmail(String email);
	@Query("SELECT e FROM ElectricityUser e where e.electricityProvider.id = :providorId")
	List<ElectricityUser> getAllElectricityUserByProviders(@Param("providorId") Long providorId);

}
