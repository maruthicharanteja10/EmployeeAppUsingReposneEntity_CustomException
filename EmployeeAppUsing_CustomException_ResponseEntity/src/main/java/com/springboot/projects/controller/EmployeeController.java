package com.springboot.projects.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.projects.customException.BusinessException;
import com.springboot.projects.customException.ControllerException;
import com.springboot.projects.entity.Employee;
import com.springboot.projects.service.EmployeeService;

@RestController
@RequestMapping("/code")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
    	try {
			Employee employeeSaved = employeeService.addEmployee(employee);
			return new ResponseEntity<Employee>(employeeSaved, HttpStatus.CREATED);
		}catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			ControllerException ce = new ControllerException("611","Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/emp/{empid}")
    public ResponseEntity<?> getEmpById(@PathVariable("empid") Integer empidL) {
    	try {
			Employee empRetrieved = employeeService.getEmpById(empidL);
			return new ResponseEntity<Employee>(empRetrieved, HttpStatus.OK);
		}catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			ControllerException ce = new ControllerException("612","Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
    }

    @DeleteMapping("/delete/{empid}")
    public ResponseEntity<?> deleteEmpById(@PathVariable("empid") Integer empidL) {
        try {
            employeeService.deleteEmpById(empidL);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{empid}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable("empid") Integer id) {
    	Employee employeeSaved = employeeService.updateEmployee(id,employee);
		return new ResponseEntity<Employee>(employeeSaved, HttpStatus.CREATED);
    }
}
