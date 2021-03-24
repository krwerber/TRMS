package dev.werber.dao.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dev.werber.beans.Employee;
import dev.werber.dao.EmployeeDAO;
import dev.werber.exceptions.NonUniqueUsernameException;
import dev.werber.util.HibernateUtil;

public class EmployeeHibernate implements EmployeeDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	private Logger log = Logger.getLogger(EmployeeHibernate.class);

	@Override
	public Employee add(Employee t) throws NonUniqueUsernameException {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(t);
			tx.commit();
			
			log.info(t.toString() + " added to Employee");
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) tx.rollback();
			log.debug(t.getUsername() + " NOT added successfully");
			throw new NonUniqueUsernameException();
		} finally {
			s.close();
		}
		return t;
	}

	@Override
	public Employee getById(Integer id) {
		Session s = hu.getSession();
		Employee e = s.get(Employee.class, id);
		s.close();
		return e;
	}

	@Override
	public Set<Employee> getAll() {
		Session s = hu.getSession();
		String from = "FROM employee";
		Query<Employee> query = s.createQuery(from, Employee.class);
		List<Employee> list = query.getResultList();
		Set<Employee> employees = new HashSet<Employee>();
		employees.addAll(list);
		s.close();
		return employees;
	}

	@Override
	public void update(Employee t) {
		Session s = hu.getSession();
		Transaction tx = null;
		
		log.info("Attempting to update " + t.toString());
		try {
			tx = s.beginTransaction();
			s.update(t);
			tx.commit();
			
			log.info("Update successful");
		} catch (Exception e) {
			if (tx != null) tx.rollback();
		} finally {
			s.close();
		}

	}

	@Override
	public void delete(Employee t) {
		Session s = hu.getSession();
		Transaction tx = null;
		
		log.info("Deleting " + t.toString());
		try {
			tx = s.beginTransaction();
			s.delete(t);
			tx.commit();
			
			log.info("No longer in database: " + t.toString());
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			log.debug("NOT removed: " + t.toString());
		} finally {
			s.close();
		}


	}

	@Override
	public Employee getByUsername(String username) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		
		Predicate predicate = cb.equal(root.get("username"), username);
		
		query.select(root).where(predicate);
		
		Employee e = s.createQuery(query).getSingleResult();
		return e;
	}


}
