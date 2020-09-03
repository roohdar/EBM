package com.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billing.entity.ElectricMeter;

public interface ElectricityMeterRepository extends JpaRepository<ElectricMeter, Long>{
	
	public ElectricMeter findByMeterNumber(String meterNumber);

}
