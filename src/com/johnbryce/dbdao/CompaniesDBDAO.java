package com.johnbryce.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.johnbryce.beans.Company;
import com.johnbryce.beans.Coupon;
import com.johnbryce.dao.CompaniesDAO;
import com.johnbryce.db.ConnectionPool;

public class CompaniesDBDAO implements CompaniesDAO {
	// private ConnectionPool connectionPool;
	private Connection connection;

	@Override
	public boolean isCompanyExists(String email, String password) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.companies WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return false;
	}

	@Override
	public void addCompany(Company company) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO `couponsystem`.`companies`" + " (`name`, `email`, `password`) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			// this is how we add the parameters
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public void updateCompany(Company company, int id) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "UPDATE `couponsystem`.`companies` SET `email` = ?, `password` = ? WHERE (`Id` = ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getEmail());
			statement.setString(2, company.getPassword());
			statement.setInt(3, id);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public void deleteCompany(int companyID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.companies WHERE Id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	@Override
	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<Company>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.companies";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);
				companies.add(new Company(id, name, email, password));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return companies;
	}

	@Override
	public Company getOneCompany(int companyID) {
		Company c1 = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.companies WHERE Id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);
				c1 = new Company(id, name, email, password);
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
	public Company getCompanyByEmailAndPassword(String email, String password) {
		Company c1 = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.companies WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				email = resultSet.getString(3);
				password = resultSet.getNString(4);
				c1 = new Company(id, name, email, password);
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
	public String toString() {
		return "CompaniesDBDAO [connection=" + connection + "]";
	}

}
