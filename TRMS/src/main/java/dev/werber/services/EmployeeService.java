package dev.werber.services;

import java.util.Set;

import dev.werber.beans.Department;
import dev.werber.beans.Employee;
import dev.werber.beans.Role;
import dev.werber.exceptions.NonUniqueUsernameException;

public interface EmployeeService {
	//create
	void addEmployee(Employee t) throws NonUniqueUsernameException;
	//read
	Set<Employee> getAllEmployees();
	Employee getEmployeeById(Integer i);
	Employee getEmployeeByUsername(String username);
	//update
	void updateEmployee(Employee t);
	//delete
	void deleteEmployee(Employee t);
	
	Set<Department> getAllDepartments();
	void saveDepartment(Department t);
	void saveRole(Role t);
	Role getRoleByName(String name);
	Department getDepartmentByName(String name);
}
