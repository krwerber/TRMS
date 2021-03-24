package dev.werber.dao;

import dev.werber.beans.Employee;

public interface EmployeeDAO extends GenericDAO<Employee> {
	public Employee getByUsername(String username);
}
