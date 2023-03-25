package com.example.demo.service;

import com.example.demo.entity.EmployeeEntity;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeV2ServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public List<Employee> getAllEmployee() {
        List<EmployeeEntity> list = employeeRepository.findAll();

        List<Employee> employeeList
                = list.stream().map(employeeEntity -> {
                    Employee employee = new Employee();
                    BeanUtils.copyProperties(employeeEntity, employee);
                    return employee;
                 }).collect(Collectors.toList());
        return employeeList;
    }

    @Override
    public Employee getEmployee(String id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeEntity, employee);
        return employee;
    }

    @Override
    public Employee saveEmployee(Employee e) {
        if(e.getId() == null || e.getId().equals("")) e.setId(String.valueOf(UUID.randomUUID()));
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(e,employeeEntity);
        employeeRepository.save(employeeEntity);
        return e;
    }

    @Override
    public String deleteEmployee(String id) {
       employeeRepository.deleteById(id);

       return "Employee data for id: {id} deleted successfully";
    }
}
