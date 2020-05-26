package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Admin;

@Repository
public interface AdminRepository extends JpaRepository <Admin, Long>{

}
