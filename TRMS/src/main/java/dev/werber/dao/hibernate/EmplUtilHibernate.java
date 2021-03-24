package dev.werber.dao.hibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dev.werber.beans.Department;
import dev.werber.beans.Role;
import dev.werber.util.HibernateUtil;

public class EmplUtilHibernate {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	public Department saveDepartment(Department t) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.saveOrUpdate(t);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		return t;
	}
	
	public Role saveRole(Role t) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.saveOrUpdate(t);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		return t;
	}
	
	public Department getDepartmentByName(String name) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Department> query = cb.createQuery(Department.class);
		Root<Department> root = query.from(Department.class);
		
		Predicate predicate = cb.equal(root.get("name"), name);
		
		query.select(root).where(predicate);
		
		Department e = s.createQuery(query).getSingleResult();
		return e;
	}
	
	public Role getRoleByName(String name) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Role> query = cb.createQuery(Role.class);
		Root<Role> root = query.from(Role.class);
		
		Predicate predicate = cb.equal(root.get("name"), name);
		
		query.select(root).where(predicate);
		
		Role e = s.createQuery(query).getSingleResult();
		return e;
	}
}
