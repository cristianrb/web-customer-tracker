package com.cristianrb.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cristianrb.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		Session session = sessionFactory.getCurrentSession();
		Query<Customer> query = session.createQuery("from Customer ORDER BY lastName", Customer.class);
		
		List<Customer> customers = query.getResultList();
		
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int customerId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Customer.class, customerId);
	}

	@Override
	public void deleteCustomer(int customerId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from Customer WHERE id =: customerId");
		query.setParameter("customerId", customerId);
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String searchName) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		if (searchName != null && searchName.trim().length() > 0) {
			query = session.createQuery("from Customer WHERE lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
			query.setParameter("theName", "%" + searchName.toLowerCase() + "%");
		} else {
			query = session.createQuery("from Customer ORDER BY lastName", Customer.class);
		}
		
		
		List<Customer> customers = query.getResultList();
		return customers;
	}

}
