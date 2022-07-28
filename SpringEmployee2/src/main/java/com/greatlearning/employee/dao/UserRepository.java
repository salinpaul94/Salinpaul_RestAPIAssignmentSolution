package com.greatlearning.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greatlearning.employee.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.name = :name")
	public User getUserByName(@Param("name") String name);
}
