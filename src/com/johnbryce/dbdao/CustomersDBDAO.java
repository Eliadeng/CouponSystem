package com.johnbryce.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.johnbryce.beans.Customer;
import com.johnbryce.dao.CustomersDAO;
import com.johnbryce.db.ConnectionPool;

public class CustomersDBDAO implements CustomersDAO {
	// private ConnectionPool connectionPool;
	private Connection connection;

	@Override
	public boolean isCustomerExists(String email, String password) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.customers WHERE email = ? AND password = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				flag = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return flag;
	}

	@Override
	public void addCustomer(Customer customer) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO couponsystem.customers (`first_name`, `last_name`, `email`, `password`)"
					+ " VALUES (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public void updateCustomer(Customer customer, int customer_id) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "UPDATE couponsystem.customers SET "
					+ "`first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`Id` = ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, customer_id);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public void deleteCustomer(int customerID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.customers where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.customers";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String email = resultSet.getString(4);
				String password = resultSet.getString(5);
				customers.add(new Customer(id, firstName, lastName, email, password));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return customers;
	}

	@Override
	public Customer getOneCustomer(int customerID) {
		Customer c1 = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.customers WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String email = resultSet.getString(4);
				String password = resultSet.getString(5);
				c1 = new Customer(id, firstName, lastName, email, password);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return c1;
	}

	@Override
	public int getCustomerIDByEmailAndPassword(String email, String password) {
		int id = 0;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.customers WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			id = resultSet.getInt(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return id;
	}

	@Override
	public String toString() {
		return "CustomersDBDAO [connection=" + connection + "]";
	}

}
