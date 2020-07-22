package com.johnbryce.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.johnbryce.beans.Category;
import com.johnbryce.db.ConnectionPool;

public class CategoryDBDAO {
	public int getCategoryID(Category category) {
		Connection connection = null;
		int categoryId = 0;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT id FROM couponsystem.categories where name=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, category.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				categoryId = resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return categoryId;
	}

	public static Category getCategoryName(int ID) {
		Connection connection = null;
		String categoryName = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT name FROM couponsystem.categories where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, ID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				categoryName = resultSet.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return Category.valueOf(categoryName);
	}
}
