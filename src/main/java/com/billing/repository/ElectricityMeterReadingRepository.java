package com.billing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.billing.entity.ElectricityMeterReading;

public interface ElectricityMeterReadingRepository extends JpaRepository<ElectricityMeterReading, Long>{
	
	
	@Query("SELECT er FROM ElectricityMeterReading er where er.electricMeter.meterNumber = :meterNumber")
	List<ElectricityMeterReading> findByMeterNumber(@Param("meterNumber") String meterNumber);
	
	@Query("SELECT er FROM ElectricityMeterReading er where date_part('year', er.startDate) = :year AND er.electricMeter.meterNumber = :meterNumber")
	List<ElectricityMeterReading> findByMeterNumberAndYear(@Param("meterNumber") String meterNumber, @Param("year") Double year);
	

}
