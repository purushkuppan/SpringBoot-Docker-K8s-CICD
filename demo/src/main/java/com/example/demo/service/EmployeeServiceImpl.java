package com.example.demo.service;

import com.example.demo.error.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    List<Employee> list = new ArrayList<>();

    @Override
    public List<Employee> getAllEmployee() {
        return list;
    }

    @Override
    public Employee getEmployee(String id) {
        return list.stream().filter(employee -> employee.getId().equalsIgnoreCase(id)).
                findFirst().
                orElseThrow(() -> new EmployeeNotFoundException("Employee not found for id: "+id));
    }

    @Override
    public Employee saveEmployee(Employee e) {
        if(e.getId() == null || e.getId().equals("")) e.setId(String.valueOf(UUID.randomUUID()));
        list.add(e);
        return e;
    }

    @Override
    public String deleteEmployee(String id) {
        Employee e = list.stream().filter(employee -> employee.getId().equalsIgnoreCase(id)).findFirst().get();
        list.remove(e);
        return "Employee left Company and Employee Id is :"+ e.getId();
    }
}
