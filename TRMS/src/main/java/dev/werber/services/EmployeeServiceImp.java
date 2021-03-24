package dev.werber.services;

import java.util.HashSet;
import java.util.Set;

import dev.werber.beans.Department;
import dev.werber.beans.Employee;
import dev.werber.beans.Role;
import dev.werber.dao.DAOFactory;
import dev.werber.dao.EmployeeDAO;
import dev.werber.dao.hibernate.EmplUtilHibernate;
import dev.werber.exceptions.NonUniqueUsernameException;

public class EmployeeServiceImp implements EmployeeService {
	private EmployeeDAO empDao;
	private EmplUtilHibernate depDao = new EmplUtilHibernate();
	
	public EmployeeServiceImp() {
		this.empDao = DAOFactory.getEmployeeDAO();
	}

	@Override
	public void addEmployee(Employee t) throws NonUniqueUsernameException{
		try {
			empDao.add(t);
		} catch (NonUniqueUsernameException e) {
			throw new NonUniqueUsernameException();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Employee getEmployeeById(Integer i) {
		return empDao.getById(i);
	}

	@Override
	public Employee getEmployeeByUsername(String username) {
		return empDao.getByUsername(username);
	}

	@Override
	public void updateEmployee(Employee t) {
		empDao.update(t);

	}

	@Override
	public void deleteEmployee(Employee t) {
		empDao.delete(t);

	}

	@Override
	public Set<Employee> getAllEmployees() {
		return empDao.getAll();
	}
	
	@Override
	public Set<Department> getAllDepartments() {
		Set<Employee> allEmployees = getAllEmployees();
		Set<Department> dpmts = new HashSet<Department>();
		for (Employee e : allEmployees) {
			if(!dpmts.contains(e.getDepartment())) dpmts.add(e.getDepartment());
		}
		return dpmts;
	}

	@Override
	public void saveDepartment(Department t) {
		depDao.saveDepartment(t);
		
	}
	
	@Override
	public void saveRole(Role t) {
		depDao.saveRole(t);
		
	}

	@Override
	public Role getRoleByName(String name) {
		return depDao.getRoleByName(name);
	}

	@Override
	public Department getDepartmentByName(String name) {
		return depDao.getDepartmentByName(name);
	}

}
