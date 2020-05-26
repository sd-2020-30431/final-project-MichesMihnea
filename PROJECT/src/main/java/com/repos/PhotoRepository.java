package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Photo;

@Repository
public interface PhotoRepository extends JpaRepository <Photo, Long>{

}
