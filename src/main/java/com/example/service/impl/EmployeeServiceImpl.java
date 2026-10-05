package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.AddressDto;
import com.example.dto.EmployeeDto;
import com.example.entity.Address;
import com.example.entity.Employee;
import com.example.entity.EmployeeType;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.EmployeeRepo;
import com.example.service.EmployeeService;
import com.example.util.Helper;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	Logger logger=LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	private final EmployeeRepo employeeRepo;
	private PasswordEncoder passwordEncoder;
	
	
	public EmployeeServiceImpl(EmployeeRepo employeeRepo,PasswordEncoder passwordEncoder) {
		super();
		this.employeeRepo = employeeRepo;
		this.passwordEncoder=passwordEncoder;
	}

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		
		logger.info(" request received in employee service");
		// Dto to employee 
		Employee emp=new Employee();
		emp.setId(Helper.generateId());
		emp.setName(employeeDto.getName());
		emp.setDepartment(employeeDto.getDepartment());
		emp.setDob(employeeDto.getDob());
		emp.setEmail(employeeDto.getEmail());
		emp.setSalary(employeeDto.getSalary());
		emp.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
		emp.setType(EmployeeType.ROLE_PRIVATE);
		emp.setActive(employeeDto.isActive());
		
		List<Address> list=employeeDto.getAddress().stream().map(k->{Address addr=new Address();BeanUtils.copyProperties(k, addr); addr.setEmployee(emp) ;return addr;}).toList();
		emp.setAddress(list);
		
		// saving employee
		
		Employee savedEmployee=employeeRepo.save(emp);
		
		EmployeeDto savedDto=new EmployeeDto();
		BeanUtils.copyProperties(savedEmployee,savedDto);
		savedDto.setAddress(savedEmployee.getAddress().stream().map(k->{AddressDto address=new AddressDto();BeanUtils.copyProperties(k, address); address.setEmployeeId(k.getEmployee().getId()) ; return address;}).toList());
		logger.info("employee save successful");
		
		return savedDto;
	}

	@Override
	public Page<EmployeeDto> getEmployeeInPages(Pageable pageable) {
		// TODO Auto-generated method stub
		
		Page<Employee> pages=employeeRepo.findAll(pageable);
		
		return pages.map(emp->{EmployeeDto dto= new EmployeeDto();
		
		dto.setId(emp.getId());
		dto.setName(emp.getName());
		dto.setDob(emp.getDob());
		dto.setDepartment(emp.getDepartment());
		dto.setEmail(emp.getEmail());
		dto.setSalary(emp.getSalary());
		dto.setActive(emp.isActive());
		dto.setId(emp.getId());
		dto.setAddress(emp.getAddress().stream().map(a->{AddressDto addr=new AddressDto();BeanUtils.copyProperties(a, addr); addr.setEmployeeId(a.getEmployee().getId()) ;return addr;}).toList());
		
		return dto;
		});
		
		
	}

	@Override
	public EmployeeDto getById(String id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Optional<Employee> getEmp=employeeRepo.findById(id);
		if(!getEmp.isPresent()) {
			
			throw new ResourceNotFoundException("Employee not found"+id);
		}
		EmployeeDto dto=new EmployeeDto();
		dto.setId(getEmp.get().getId());
		dto.setDepartment(getEmp.get().getDepartment());
		dto.setName(getEmp.get().getName());
		dto.setDob(getEmp.get().getDob());
		dto.setEmail(getEmp.get().getEmail());
		dto.setSalary(getEmp.get().getSalary());
		dto.setActive(getEmp.get().isActive());
		dto.setAddress(getEmp.get().getAddress().stream().map(a->{AddressDto addr=new AddressDto();BeanUtils.copyProperties(a, addr); addr.setEmployeeId(getEmp.get().getId()) ;return addr;}).toList());
		
		return dto;
	}

	@Override
	public EmployeeDto getByEmail(String email) throws ResourceNotFoundException {
		Optional<Employee> getEmp=employeeRepo.findByEmail(email);
		if(!getEmp.isPresent()) {
			logger.error("user not found");
			throw new ResourceNotFoundException("Employee not found"+email);
		}
		
		EmployeeDto dto=new EmployeeDto();
		dto.setId(getEmp.get().getId());
		dto.setDepartment(getEmp.get().getDepartment());
		dto.setName(getEmp.get().getName());
		dto.setDob(getEmp.get().getDob());
		dto.setEmail(getEmp.get().getEmail());
		dto.setSalary(getEmp.get().getSalary());
		dto.setActive(getEmp.get().isActive());
		dto.setAddress(getEmp.get().getAddress().stream().map(a->{AddressDto addr=new AddressDto();BeanUtils.copyProperties(a, addr);addr.setEmployeeId(getEmp.get().getId());return addr;}).toList());
		return dto;
	}

}
