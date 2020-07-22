package com.johnbryce.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseManager {
	private static Connection connection = null;
	private static final String url = "jdbc:mysql://localhost:3306/couponsystem?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";
	private static final String username = "root";
	private static final String password = "Xehadark8013";

	private static String schema1 = " CREATE SCHEMA `couponsystem`";
	private static String company1 = "CREATE TABLE `couponsystem`.`companies` (\r\n"
			+ "  `Id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `name` VARCHAR(45) NOT NULL,\r\n"
			+ "  `email` VARCHAR(45) NOT NULL,\r\n" + "  `password` VARCHAR(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`Id`))";
	private static String customer1 = "CREATE TABLE `couponsystem`.`customers` (\r\n"
			+ "  `Id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `first_name` VARCHAR(45) NOT NULL,\r\n"
			+ "  `last_name` VARCHAR(45) NOT NULL,\r\n" + "  `email` VARCHAR(45) NOT NULL,\r\n"
			+ "  `password` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`Id`))";
	private static String category1 = "CREATE TABLE `couponsystem`.`categories` (\r\n"
			+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `name` VARCHAR(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`id`))";
	private static String coupon1 = "CREATE TABLE `couponsystem`.`coupons` (\r\n"
			+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + "  `company_id` INT NOT NULL,\r\n"
			+ "  `category_id` INT NOT NULL,\r\n" + "  `title` VARCHAR(45) NOT NULL,\r\n"
			+ "  `description` VARCHAR(45) NOT NULL,\r\n" + "  `start_date` DATE NOT NULL,\r\n"
			+ "  `end_date` DATE NOT NULL,\r\n" + "  `amount` INT NOT NULL,\r\n" + "  `price` DOUBLE NOT NULL,\r\n"
			+ "  `image` VARCHAR(45) NULL,\r\n" + "  PRIMARY KEY (`id`),\r\n"
			+ "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\r\n"
			+ "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\r\n" + "  CONSTRAINT `company_id`\r\n"
			+ "    FOREIGN KEY (`company_id`)\r\n" + "    REFERENCES `couponsystem`.`companies` (`Id`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `category_id`\r\n"
			+ "    FOREIGN KEY (`category_id`)\r\n" + "    REFERENCES `couponsystem`.`categories` (`id`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION)";
	private static String customer1_vs_coupon1 = "CREATE TABLE `couponsystem`.`customers_vs_coupons` (\r\n"
			+ "  `customer_id` INT NOT NULL,\r\n" + "  `coupon_id` INT NOT NULL,\r\n"
			+ "  PRIMARY KEY (`customer_id`, `coupon_id`),\r\n"
			+ "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\r\n" + "  CONSTRAINT `customer_id`\r\n"
			+ "    FOREIGN KEY (`customer_id`)\r\n" + "    REFERENCES `couponsystem`.`customers` (`Id`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `coupon_id`\r\n"
			+ "    FOREIGN KEY (`coupon_id`)\r\n" + "    REFERENCES `couponsystem`.`coupons` (`id`)\r\n"
			+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION)";

	public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static void CreateAllTables() throws SQLException {
		CreateTable(schema1);
		CreateTable(company1);
		CreateTable(customer1);
		CreateTable(category1);
		CreateTable(coupon1);
		CreateTable(customer1_vs_coupon1);
	}

	private static void CreateTable(String sql) throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	private static String company2 = "DROP TABLE companies";
	private static String customer2 = "DROP TABLE customers";
	private static String category2 = "DROP TABLE categories";
	private static String coupon2 = "DROP TABLE coupons";
	private static String customer2_vs_coupon2 = "DROP TABLE customers_vs_coupons";
	private static String schema = "DROP SCHEMA couponsystem";

	public static void DropAllTables() {
		DropTable(customer2_vs_coupon2);
		DropTable(customer2);
		DropTable(coupon2);
		DropTable(category2);
		DropTable(company2);
		DropTable(schema);
	}

	private static void DropTable(String sql) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	private static Scanner in = new Scanner(System.in);
	private static String companyData = "INSERT INTO companies (name,email,password) values";
	private static String customerData = "INSERT INTO customers (first_name,last_name,email,password) values";
	private static String categoryData = "insert into categories (name) values";
	private static String couponData = "INSERT INTO  coupons(company_id,category_id,title,description,"
			+ "start_date,end_date,amount,price,image) values";
	private static String customerData_vs_couponData = "INSERT INTO customers_vs_coupons (customer_id,coupon_id) values";

	public static void InsertData() {
		System.out.println("Enter company detailes");
		CreateDataToTables(companyData + "(" + in.next() + ")");
		System.out.println("Enter customer detailes");
		CreateDataToTables(customerData + "(" + in.next() + ")");
		System.out.println("Enter category detailes");
		CreateDataToTables(categoryData + "(" + in.next() + ")");
		System.out.println("Enter coupon detailes");
		CreateDataToTables(couponData + "(" + in.next() + ")");
		System.out.println("Enter customer_vs_coupon detailes");
		CreateDataToTables(customerData_vs_couponData + "(" + in.next() + ")");
	}

	private static void CreateDataToTables(String sql) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

}
