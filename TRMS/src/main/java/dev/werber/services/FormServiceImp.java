package dev.werber.services;

import java.util.HashSet;
import java.util.Set;

import dev.werber.beans.Employee;
import dev.werber.beans.Form;
import dev.werber.dao.DAOFactory;
import dev.werber.dao.FormDAO;

public class FormServiceImp implements FormService {
	private FormDAO formDao; 
	
	public FormServiceImp() {
		this.formDao = DAOFactory.getFormDAO();
	}
	@Override
	public void addForm(Form t) {
		try {
			formDao.add(t);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Form getFormById(Integer id) {
		return formDao.getById(id);
	}

	@Override
	public Set<Form> getAllFormsByEmployee(Employee e) {
		Set<Form> allForms = getAllForms();
		Set<Form> userForms = new HashSet<Form>();
		System.out.println(e.toString());
		System.out.println(allForms);
		for (Form f : allForms) {
			if (f.getEmployee().getId().equals(e.getId()))
				userForms.add(f);
		}
		System.out.println(userForms);
		return userForms;
	}

	@Override
	public void updateForm(Form t) {
		formDao.update(t);

	}

	@Override
	public void deleteForm(Form t) {
		formDao.delete(t);

	}

	@Override
	public Set<Form> getAllForms() {
		return formDao.getAll();
	}

}
