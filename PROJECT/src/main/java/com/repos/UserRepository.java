package com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.srccodes.beans.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{

}
