package com.example.dto;


public class LoginResponseDto {
	
	private EmployeeDto employeeDto;
	private String accessToken;
	private String refreshToken;
	
	
	
	
	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public LoginResponseDto(EmployeeDto employee, String accessToken, String refreshToken) {
		super();
		this.employeeDto = employee;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	public LoginResponseDto() {
		super();
	}
	
	
	

}
