package com.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billing.entity.ElectricityUser;

public interface ElectricityUserRepository extends JpaRepository<ElectricityUser, Long>{
	
	ElectricityUser findByEmail(String email);

}
