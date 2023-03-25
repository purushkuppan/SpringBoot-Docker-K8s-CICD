package com.example.demo.service;

import com.example.demo.model.Employee;

import java.util.List;

public interface EmployeeService {

     List<Employee> getAllEmployee();
     Employee getEmployee(String id);
     Employee saveEmployee(Employee e);
     String deleteEmployee(String id);
}
