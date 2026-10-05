package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Employee;abstract
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
	
	Optional<Employee> findByEmail(String email);
	

}
