package dev.werber.delegates;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.werber.beans.Employee;
import dev.werber.beans.Form;
import dev.werber.services.EmployeeService;
import dev.werber.services.EmployeeServiceImp;
import dev.werber.services.FormService;
import dev.werber.services.FormServiceImp;

public class FormDelegate implements FrontControllerDelegate {
	private EmployeeService empServ = new EmployeeServiceImp();
	private FormService formServ = new FormServiceImp();
	private ObjectMapper om = new ObjectMapper();
	/*
	 * Endpoints:
	 * 	/form GET -- get all a users forms
	 *        POST -- add submit a new form
	 */
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = (String) req.getAttribute("path");
		String un = (String) req.getSession().getAttribute("username");
		Employee e = empServ.getEmployeeByUsername(un);
		Employee user = empServ.getEmployeeByUsername(e.getUsername());
		
		if (path == null || path.equals("")) {
			switch (req.getMethod()) {
				case "GET":
					Set<Form> forms = formServ.getAllFormsByEmployee(user);
					resp.getWriter().write(om.writeValueAsString(forms));
					break;
				case "POST":
					Form f = om.readValue(req.getInputStream(), Form.class);
					f.setEmployee(user);
					f.setApproved(false);
					formServ.addForm(f);
					resp.setStatus(HttpServletResponse.SC_CREATED);
					break;
				default:
					break;
			}
		}

	}

}
