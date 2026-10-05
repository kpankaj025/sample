package com.example.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.EmployeeDto;
import com.example.service.EmployeeService;
import org.springframework.data.web.PagedModel;

@RestController
@RequestMapping("/api/v1/emp")
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	
	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}


	@PostMapping
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto dto){

		
		EmployeeDto savedEmployee=employeeService.saveEmployee(dto);
		
		return new ResponseEntity<>(savedEmployee,HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/{EmpId}")
	public ResponseEntity<EmployeeDto> getById(@PathVariable("EmpId") String id ){
		
		EmployeeDto dto=employeeService.getById(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	@GetMapping("/email/{emailId}")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EmployeeDto> getEmployeeByEmail(@PathVariable("emailId") String email){
		
		EmployeeDto dto= employeeService.getByEmail(email);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	
	@GetMapping("/sorted")
	public ResponseEntity<PagedModel<EmployeeDto>> findSortedEmployee(
	        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
	        @RequestParam(name = "size", required = false, defaultValue = "10") int size,
	        @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
	        @RequestParam(name = "dir", required = false, defaultValue = "asc") String dir) {
	    
	    Sort sort = dir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	    PageRequest pageable = PageRequest.of(page, size, sort);
	    
	    Page<EmployeeDto> pages = employeeService.getEmployeeInPages(pageable);
	    
	    // Wrap 'pages' inside PagedModel to ensure a stable JSON structure
	    PagedModel<EmployeeDto> pagedModel = new PagedModel<>(pages);
	    
	    return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
}
