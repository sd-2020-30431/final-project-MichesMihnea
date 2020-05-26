package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository <Vehicle, Long>{

}
