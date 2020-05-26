package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.Review;

@Repository
public interface ReviewRepository extends JpaRepository <Review, Long>{

}
