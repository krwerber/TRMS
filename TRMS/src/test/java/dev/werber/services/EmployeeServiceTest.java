package dev.werber.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import dev.werber.beans.Employee;
import dev.werber.dao.DAOFactory;
import dev.werber.dao.EmployeeDAO;
import dev.werber.exceptions.NonUniqueUsernameException;

public class EmployeeServiceTest {
	private static EmployeeDAO empDao = DAOFactory.getEmployeeDAO();
	private static EmployeeService empServ = new EmployeeServiceImp();
	private Employee empSent;
	private Employee empRec;
	
	@BeforeEach
	public void setup() {
		empSent = new Employee();
		empSent.setFirstName("test");
		empSent.setLastName("test");
		empSent.setUsername("test");
		empSent.setPasswd("test");
	}
	
	@AfterEach
	public void clear() {
		empDao.delete(empSent);
		empRec = null;
	}
	
	@Test
	public void addEmployeeTest() {
		try {
			empServ.addEmployee(empSent);
		} catch (NonUniqueUsernameException e) {
			e.printStackTrace();
		}
		empRec = empDao.getById(empSent.getId());
		
		assertTrue(empSent.equals(empRec));
	}
	
	@Test
	public void getEmployeeByIdTest() {
		try {
			empDao.add(empSent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		empRec = empServ.getEmployeeById(empSent.getId());
		assertTrue(empRec.equals(empSent));
	}
	
	@Test
	public void getEmployeeByUsernameTest() {
		try {
			empDao.add(empSent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		empRec = empServ.getEmployeeByUsername(empSent.getUsername());
		assertTrue(empRec.equals(empSent));
	}
	
	@Test
	public void updateEmployeeTest() {
		try {
			empRec = empDao.add(empSent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		empSent.setFirstName("update");
		empServ.updateEmployee(empSent);
		
		empRec = empDao.getByUsername("test");
		assertTrue(empRec.getFirstName().equals("update"));
	}
	
	@Test
	public void deleteEmployeeTest() {
		try {
			empDao.add(empSent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		empServ.deleteEmployee(empSent);
		empRec = empDao.getById(empSent.getId());
		assertNull(empRec);
	}
}
