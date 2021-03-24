package dev.werber.services;

import java.util.Set;

import dev.werber.beans.Employee;
import dev.werber.beans.Form;

public interface FormService {
	void addForm(Form t);
	Set<Form> getAllForms();
	Form getFormById(Integer id);
	Set<Form> getAllFormsByEmployee(Employee e);
	void updateForm(Form t);
	void deleteForm(Form t);

}
