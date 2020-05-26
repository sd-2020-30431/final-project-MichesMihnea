package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Guest;

@Repository
public interface GuestRepository extends JpaRepository <Guest, Long>{

}
