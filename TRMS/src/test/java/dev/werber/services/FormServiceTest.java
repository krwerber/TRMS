package dev.werber.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.*;

import dev.werber.beans.Employee;
import dev.werber.beans.Form;
import dev.werber.dao.DAOFactory;
import dev.werber.dao.FormDAO;
import dev.werber.exceptions.NonUniqueUsernameException;

public class FormServiceTest {
	private static FormDAO formDao = DAOFactory.getFormDAO();
	private static FormService formServ = new FormServiceImp();
	private static EmployeeService empServ = new EmployeeServiceImp();
	private Form formSent;
	private Form formRec;
	private static Employee user;
	
	@BeforeAll
	public static void launch() {
		user = new Employee();
		user.setFirstName("test");
		user.setLastName("test");
		user.setUsername("test");
		user.setPasswd("test");
		try {
			empServ.addEmployee(user);
		} catch (NonUniqueUsernameException e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
	public static void destroy() {
		empServ.deleteEmployee(user);
	}
	
	@BeforeEach
	public void setup() {
		formSent = new Form();
		formSent.setAmount(100);
		formSent.setEmployee(user);
		formSent.setDescription("test");
	}
	
	@AfterEach
	public void clear() {
		formDao.delete(formSent);
		formRec = null;
	}
	
	@Test
	public void addFormTest() {
		formServ.addForm(formSent);
		formRec = formDao.getById(formSent.getId());
		
		assertTrue(formSent.equals(formRec));
	}
	
	@Test
	public void getFormByIdTest() {
		try {
			formDao.add(formSent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		formRec = formServ.getFormById(formSent.getId());
		assertTrue(formSent.equals(formRec));
		
	}
	
	@Test
	public void getFormsByEmployeeTest() {
		try {
			formDao.add(formSent);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		Set<Form> recForms = formServ.getAllFormsByEmployee(user);
		for (Form f : recForms) {
			assertTrue(f.getEmployee().getId().equals(user.getId()));
		}
	}
	
	@Test
	public void updateFormTest() {
		try {
			formDao.add(formSent);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		formSent.setDescription("update");
		formServ.updateForm(formSent);
		
		formRec = formDao.getById(formSent.getId());
		
		assertTrue(formRec.getDescription().equals("update"));
		
	}
	
	@Test
	public void deleteFormTest() {
		try {
			formDao.add(formSent);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		formServ.deleteForm(formSent);
		formRec = formServ.getFormById(formSent.getId());
		
		assertNull(formRec);
	}
}
