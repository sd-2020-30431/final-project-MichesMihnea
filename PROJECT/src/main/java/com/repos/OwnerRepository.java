package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Owner;

@Repository
public interface OwnerRepository extends JpaRepository <Owner, Long>{

}
