package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Car;

@Repository
public interface CarRepository extends JpaRepository <Car, Long>{

}
