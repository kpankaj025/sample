package com.example.controller;

import java.time.LocalDate;

public class EmployeeReport {

private String name;
private String department;
private String password;
private Double salary;
private String email;
private LocalDate dob;

public EmployeeReport() {
}

public EmployeeReport(
        String name,
        String department,
        String password,
        Double salary,
        String email,
        LocalDate dob) {

    this.name = name;
    this.department = department;
    this.password = password;
    this.salary = salary;
    this.email = email;
    this.dob = dob;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getDepartment() {
    return department;
}

public void setDepartment(String department) {
    this.department = department;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public Double getSalary() {
    return salary;
}

public void setSalary(Double salary) {
    this.salary = salary;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public LocalDate getDob() {
    return dob;
}

public void setDob(LocalDate dob) {
    this.dob = dob;
}


}