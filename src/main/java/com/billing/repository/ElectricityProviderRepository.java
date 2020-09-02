package com.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billing.entity.ElectricityProvider;

public interface ElectricityProviderRepository extends JpaRepository<ElectricityProvider, Long>{

}
