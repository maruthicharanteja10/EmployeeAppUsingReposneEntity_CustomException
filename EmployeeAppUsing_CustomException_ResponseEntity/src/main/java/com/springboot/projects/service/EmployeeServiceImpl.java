package com.springboot.projects.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.projects.customException.BusinessException;
import com.springboot.projects.entity.Employee;
import com.springboot.projects.repo.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public Employee addEmployee(Employee employee) {
    	if(employee.getName().isEmpty() || employee.getName().length() == 0 ) {
			throw new BusinessException("601","Please send proper name, It blank");
		}
		try {
			Employee savedEmployee = employeeRepo.save(employee);
			return savedEmployee;
		}catch (IllegalArgumentException e) {
			throw new BusinessException("602","given employee is null" + e.getMessage());
		}catch (Exception e) {
			throw new BusinessException("603","Something went wrong in Service layer while saving the employee" + e.getMessage());
		}
    }

    @Override
    public List<Employee> getAllEmployees() {
    	List<Employee> empList = null;
		try {
			empList = employeeRepo.findAll();
		}
		catch (Exception e) {
			throw new BusinessException("605","Something went wrong in Service layer while fetching all employees" + e.getMessage());
		}
		if(empList.isEmpty())
			throw new BusinessException("604", "Hey list completely empty, we have nothing to return");
		return empList;
    }

    @Override
    public Employee getEmpById(Integer empidL) {
    	try {
			return employeeRepo.findById(empidL).get();
			
		}catch (IllegalArgumentException e) {
			throw new BusinessException("606","given employee id is null, please send some id to be searched" + e.getMessage());
		}
		catch (java.util.NoSuchElementException e) {
			throw new BusinessException("607","given employee id doesnot exist in DB" + e.getMessage());
		}catch (Exception e) {
			throw new BusinessException("609","Something went wrong in Service layer while fetching all employees" + e.getMessage());
		}
    }

    @Override
    @Transactional
    public Employee updateEmployee(Integer id, Employee newEmployeeData) {
        Employee existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existingEmployee.setName(newEmployeeData.getName());

        return employeeRepo.save(existingEmployee);
    }

    @Override
    @Transactional
    public void deleteEmpById(Integer empidL) {
    	try {
			employeeRepo.deleteById(empidL);
		}catch (IllegalArgumentException e) {
			throw new BusinessException("608","given employee id is null, please send some id to be deleted" + e.getMessage());
		}catch (Exception e) {
			throw new BusinessException("610","Something went wrong in Service layer while fetching all employees" + e.getMessage());
		}
    }
}
