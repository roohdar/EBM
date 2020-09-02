package com.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billing.entity.ElectricMeter;
import com.billing.entity.ElectricityUser;

public interface ElectricityMeterRepository extends JpaRepository<ElectricMeter, Long>{

}
