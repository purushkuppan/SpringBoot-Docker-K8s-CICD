package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employee")
public class EmployeeControllerV1 {

    @Qualifier("employeeServiceImpl")
    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployeeList(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    Employee getEmployee(@PathVariable String id){
        return employeeService.getEmployee(id);
    }

    @PostMapping
    Employee savEmployee(@RequestBody Employee e){
        return employeeService.saveEmployee(e);
    }

    @DeleteMapping("/{id}")
    String deleteEmployee(@PathVariable String id){
        return employeeService.deleteEmployee(id);
    }

}
