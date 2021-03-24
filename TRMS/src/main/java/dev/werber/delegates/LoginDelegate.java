package dev.werber.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;

import dev.werber.beans.Department;
import dev.werber.beans.Employee;
import dev.werber.beans.Role;
import dev.werber.exceptions.NonUniqueUsernameException;
import dev.werber.services.EmployeeService;
import dev.werber.services.EmployeeServiceImp;

public class LoginDelegate implements FrontControllerDelegate {
	
	/*
	 * Endpoints:
	 *  /user/login - (POST) log in a user
	 *  			- (DELETE) log out a user
	 *  /user - (POST) register a user
	 * 	/user/:id - (GET) get user by id
	 *            - (PUT) update a user
	 *            - (DELETE) deletes a user
	 */
	private EmployeeService empServ = new EmployeeServiceImp();
	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = (String) req.getAttribute("path");
		
		if (path == null || path.equals("")) {
			if ("POST".equals(req.getMethod())) {
				// REGISTER /user POST
				Employee e = (Employee) om.readValue(req.getInputStream(), Employee.class);
				Department d = new ObjectMapper().readValue(e.getDepartment().getName(), Department.class);
				Department dep = empServ.getDepartmentByName(d.getName());
				e.setDepartment(dep);
				Role r = empServ.getRoleByName("Employee");
				try {
					e.setRole(r);
					String hashPass = BCrypt.hashpw(e.getPasswd(), BCrypt.gensalt());
					e.setPasswd(hashPass);
					empServ.addEmployee(e);
				} catch (NonUniqueUsernameException ex) {
					resp.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists");
				}
				if (e.getId() == null) {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				} else {
					req.getSession().setAttribute("employee", e);
					resp.getWriter().write(om.writeValueAsString(e));
					resp.setStatus(HttpServletResponse.SC_CREATED);
				}
			} else if ("GET".equals(req.getMethod())) {
				//Employee emp = (Employee) req.getSession().getAttribute("employee");
				//Employee empl = empServ.getEmployeeByUsername(emp.getUsername());
				//Employee emp = (Employee) om.readValue(req.getInputStream(), Employee.class);
				String un = (String) req.getSession().getAttribute("username");
				System.out.println(un);
				Employee emp = empServ.getEmployeeByUsername(un);
				resp.getWriter().write(om.writeValueAsString(emp));
				resp.setStatus(200);
			} else {
				resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
		} else if (path.contains("login")) {
			if ("POST".equals(req.getMethod()))
				login(req, resp);
			else if ("DELETE".equals(req.getMethod()))
				req.getSession().invalidate();
			else
				resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		} else {
			userWithId(req, resp, Integer.valueOf(path));
		}


	}
	
	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = req.getParameter("user");
		String password = req.getParameter("pass");
		
		Employee e = empServ.getEmployeeByUsername(username);
		if (e != null) {
			if (BCrypt.checkpw(password, e.getPasswd())) {
				req.getSession().setAttribute("employee", e);
				req.getSession().setAttribute("username", e.getUsername());
				resp.getWriter().write(om.writeValueAsString(e));
			} else {
				resp.sendError(404, "Incorrect password.");
			}
		} else {
			resp.sendError(404, "No user found with that username.");
		}
	}

	private void userWithId(HttpServletRequest req, HttpServletResponse resp, Integer id) throws IOException {
		Employee e = empServ.getEmployeeById(id);
		switch (req.getMethod()) {
			case "GET":
				if (e != null) {
					resp.getWriter().write(om.writeValueAsString(e));
				} else {
					resp.sendError(404, "User not found.");
				}
				break;
			case "PUT":
				String password = req.getParameter("pass");
				String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());
				if (e != null) {
					e.setPasswd(hashPass);
					empServ.updateEmployee(e);
				} else
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				break;
			case "DELETE":
				if (e != null)
				empServ.deleteEmployee(e);
				break;
			default:
				resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				break;
		}
	}


}
