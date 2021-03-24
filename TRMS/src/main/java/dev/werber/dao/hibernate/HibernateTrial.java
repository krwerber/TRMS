package dev.werber.dao.hibernate;

import dev.werber.beans.Employee;
import dev.werber.dao.DAOFactory;
import dev.werber.dao.EmployeeDAO;

public class HibernateTrial {

	public static void main(String[] args) {
		EmployeeDAO empDao = DAOFactory.getEmployeeDAO();
		
		Employee empSent = new Employee();
		empSent.setFirstName("test");
		empSent.setLastName("test");
		empSent.setUsername("test");
		empSent.setPasswd("test");
		
		try {
			empDao.add(empSent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println(empSent);
		
		empSent.setUsername("update");
		
		empDao.update(empSent);
		
		Employee empRec = empDao.getByUsername("update");
		
		System.out.println(empRec);
	}

}
