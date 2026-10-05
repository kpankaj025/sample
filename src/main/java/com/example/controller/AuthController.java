package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.EmployeeDto;
import com.example.dto.LoginRequestDto;
import com.example.dto.LoginResponseDto;
import com.example.entity.Employee;
import com.example.security.JwtService;
import com.example.service.EmployeeService;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	private JwtService jwtService;
	private UserDetailsService userDetailsService;
	private AuthenticationManager authenticationManager;
	private EmployeeService employeeService;
	

	
	public AuthController(JwtService jwtService, UserDetailsService userDetailsService,
			AuthenticationManager authenticationManager, EmployeeService employeeService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.employeeService = employeeService;
	}
	
	Logger logger=LoggerFactory.getLogger(AuthController.class);


	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto ){
		
		
		logger.info("starting loginhandler");
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword());
		authenticationManager.authenticate(token);
		
		String bearer=jwtService.generateToken(loginDto.getEmail(),true);
		String refresh=jwtService.generateToken(loginDto.getEmail(), false);
		
		EmployeeDto emp= employeeService.getByEmail(loginDto.getEmail());
		
		LoginResponseDto response=new LoginResponseDto();
		response.setAccessToken(bearer);
		response.setRefreshToken(refresh);
		response.setEmployeeDto(emp);
		
		// logging for successful login
		logger.info("leaving from logging : {} : logging success",response);
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
		
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeDto dto){
		logger.info("registering user");
		EmployeeDto savcedEmp=employeeService.saveEmployee(dto);
		
		logger.info("registration successful for user: "+savcedEmp.getName());
		
		return new ResponseEntity<>(savcedEmp,HttpStatus.CREATED);
	}

}
