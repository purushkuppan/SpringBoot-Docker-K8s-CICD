package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/employee")
public class EmployeeControllerV2 {

    @Qualifier("employeeV2ServiceImpl")
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public Employee saveEmploye(@RequestBody Employee e){
       return employeeService.saveEmployee(e);
    }

    @GetMapping
    public List<Employee> getAllEmployeeList(){

        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    Employee getEmployee(@PathVariable String id){

        return employeeService.getEmployee(id);
    }

    @DeleteMapping("/{id}")
    String deleteEmployee(@PathVariable String id){

        return employeeService.deleteEmployee(id);
    }
}
