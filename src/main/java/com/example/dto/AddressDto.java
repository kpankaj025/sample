package com.example.dto;

import com.example.entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AddressDto {
	
	private String city;
	private String locality;
	private String state;
	private String country;
	private String mobileNo;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String employeeId;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public AddressDto( String city, String locality, String state, String country, String mobileNo) {
		super();
		
		this.city = city;
		this.locality = locality;
		this.state = state;
		this.country = country;
		this.mobileNo = mobileNo;
		
	}

	public AddressDto() {
		super();
	}
	
	

}
