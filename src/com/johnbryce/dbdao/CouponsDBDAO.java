package com.johnbryce.dbdao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.johnbryce.beans.Category;
import com.johnbryce.beans.Coupon;
import com.johnbryce.beans.CustomerVsCoupon;
import com.johnbryce.dao.CouponsDAO;
import com.johnbryce.db.ConnectionPool;
import com.johnbryce.util.Utilities;

public class CouponsDBDAO implements CouponsDAO {
	// private ConnectionPool connectionPool;
	private Connection connection;

	public void addCategory(String category_name) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO `couponsystem`.`categories` (`name`) VALUES (?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, category_name);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public void addCoupon(Coupon coupon) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO couponsystem.coupons (company_id, category_id, title,description,start_date, end_date, amount, price, image) VALUES (?, ?, ?, ?, ?, ?,\r\n"
					+ " ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon.getCompanyID());
			statement.setInt(2, coupon.getCategory().ordinal() + 1);
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, Utilities.convertDataToSql(coupon.getStartDate()));
			statement.setDate(6, Utilities.convertDataToSql(coupon.getEndDate()));
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());

			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public void updateCoupon(Coupon coupon, int couponID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "UPDATE `couponsystem`.`coupons` SET " + "`title` = ?, `description` = ?, `start_date` = ?, "
					+ "`end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, coupon.getTitle());
			statement.setString(2, coupon.getDescription());
			statement.setDate(3, Utilities.convertDataToSql(coupon.getStartDate()));
			statement.setDate(4, Utilities.convertDataToSql(coupon.getEndDate()));
			statement.setInt(5, coupon.getAmount());
			statement.setDouble(6, coupon.getPrice());
			statement.setString(7, coupon.getImage());
			statement.setInt(8, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	public void deleteCustomerVsCoupon(int customerID, int couponID) {
		String delete = "DELETE FROM customers_vs_coupons where customer_id = ? AND coupon_id = ?";
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = delete;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public void deleteCoupon(int couponID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.coupons WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	@Override
	public List<Coupon> getAllCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.coupons";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyID = resultSet.getInt(2);
				Category category = CategoryDBDAO.getCategoryName(resultSet.getInt(3));
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupons.add(new Coupon(id, companyID, category, title, description, startDate, endDate, amount,
						price, image));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return coupons;
	}

	@Override
	public Coupon getOneCoupon(int couponID) {
		Coupon c1 = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.coupons WHERE id = " + couponID;

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyID = resultSet.getInt(2);
				Category category = CategoryDBDAO.getCategoryName(resultSet.getInt(3));
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				c1 = new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price,
						image);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return c1;
	}

	public boolean isCouponExists(int couponID) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.coupons where id= ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				flag = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return flag;
	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) {
		if (!isCouponExists(couponID)) {
			throw new NullPointerException();
		}
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "INSERT INTO couponsystem.customers_vs_coupons(customer_id,coupon_id) values (?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.customers_vs_coupons WHERE customer_id = ? AND coupon_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("delete coupon purchase successfully complete!!!");
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public void deleteCouponCustomers(int couponID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.customers_vs_coupons WHERE coupon_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	public void deleteCustomerCoupons(int customerID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "DELETE FROM couponsystem.customers_vs_coupons WHERE customer_id = ?";
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
	public List<Coupon> getAllCouponsByCompany(int companyID) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.coupons WHERE company_id = " + companyID;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyId = resultSet.getInt(2);
				Category category = CategoryDBDAO.getCategoryName(resultSet.getInt(3));
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupons.add(new Coupon(id, companyId, category, title, description, startDate, endDate, amount,
						price, image));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return coupons;
	}

	@Override
	public List<CustomerVsCoupon> getAllCustomersVsCoupons() {
		List<CustomerVsCoupon> customerVsCoupons = new ArrayList<CustomerVsCoupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM couponsystem.customers_vs_coupons";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int customerID = resultSet.getInt(1);
				int couponID = resultSet.getInt(2);
				customerVsCoupons.add(new CustomerVsCoupon(customerID, couponID));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return customerVsCoupons;

	}

	@Override
	public String toString() {
		return "CouponsDBDAO [connection=" + connection + "]";
	}

}
