package com.johnbryce.dao;

import java.util.List;

import com.johnbryce.beans.Customer;

public interface CustomersDAO {
	boolean isCustomerExists(String email, String password);

	void addCustomer(Customer customer);

	void updateCustomer(Customer customer, int customerID);

	void deleteCustomer(int customerID);

	List<Customer> getAllCustomers();

	Customer getOneCustomer(int customerID);

	int getCustomerIDByEmailAndPassword(String email, String password);
}
