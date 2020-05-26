package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Booking;

@Repository
public interface BookingRepository extends JpaRepository <Booking, Long>{

}

