package dev.werber.dao;

import dev.werber.dao.hibernate.*;

public class DAOFactory {
	public static EmployeeDAO getEmployeeDAO() {
		return new EmployeeHibernate();
	}
	
	public static FormDAO getFormDAO() {
		return new FormHibernate();
	}
}
