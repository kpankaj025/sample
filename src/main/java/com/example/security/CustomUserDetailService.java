package com.example.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.Employee;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	private final EmployeeRepo employeeRepo;
	
	public CustomUserDetailService(EmployeeRepo employeeRepo) {
		super();
		this.employeeRepo = employeeRepo;
	}




	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<Employee> emp=employeeRepo.findByEmail(username);
		if(!emp.isPresent()) {
			throw new ResourceNotFoundException("user not found");
		}
		CustomUserDetail user=new CustomUserDetail(emp.get());
		return user;
	}

}
