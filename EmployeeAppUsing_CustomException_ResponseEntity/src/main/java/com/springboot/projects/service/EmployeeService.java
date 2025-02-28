package com.springboot.projects.service;

import java.util.List;

import com.springboot.projects.entity.Employee;

public interface EmployeeService {

	public Employee addEmployee(Employee employee);
	public List<Employee> getAllEmployees();
	public Employee getEmpById(Integer empidL);

	Employee updateEmployee(Integer id, Employee newEmployeeData);
	void deleteEmpById(Integer empidL);
	
	

}
