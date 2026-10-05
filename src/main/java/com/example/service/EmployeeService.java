package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.dto.EmployeeDto;

public interface EmployeeService {
	
	EmployeeDto saveEmployee(EmployeeDto employeeDto);
	Page<EmployeeDto> getEmployeeInPages(Pageable pageable);
	EmployeeDto getById(String id);
	EmployeeDto getByEmail(String email);

}
