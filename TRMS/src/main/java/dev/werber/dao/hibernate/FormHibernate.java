package dev.werber.dao.hibernate;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dev.werber.beans.Form;
import dev.werber.dao.FormDAO;
import dev.werber.util.HibernateUtil;

public class FormHibernate implements FormDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	private Logger log = Logger.getLogger(FormHibernate.class);
	
	@Override
	public Form add(Form t) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			t.setDate(Date.valueOf(java.time.LocalDate.now()));
			t.setTime(Time.valueOf(java.time.LocalTime.now()));
			s.save(t);
			tx.commit();
			
			log.info(t.toString() + " added to Form");
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			log.debug("Form #" + t.getId() + " NOT added successfully");
		} finally {
			s.close();
		}
		return t;
	}

	@Override
	public Form getById(Integer id) {
		Session s = hu.getSession();
		Form f = s.get(Form.class, id);
		return f;
	}

	@Override
	public Set<Form> getAll() {
		Session s = hu.getSession();
		String from = "FROM Form";
		Query<Form> query = s.createQuery(from, Form.class);
		List<Form> list = query.getResultList();
		Set<Form> forms = new HashSet<Form>();
		forms.addAll(list);
		return forms;
	}

	@Override
	public void update(Form t) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.update(t);
			tx.commit();
			
			log.info(t.toString() + " updated");
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			log.debug("Form #" + t.getId() + " NOT updated successfully");
		} finally {
			s.close();
		}

	}

	@Override
	public void delete(Form t) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.delete(t);
			tx.commit();
			
			log.info("DELETED " + t.toString());
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			log.debug("Form #" + t.getId() + " NOT DELETED successfully");
		} finally {
			s.close();
		}


	}

}
