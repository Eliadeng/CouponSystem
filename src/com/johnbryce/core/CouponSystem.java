package com.johnbryce.core;

import java.sql.SQLException;

import com.johnbryce.beans.Coupon;
import com.johnbryce.dao.CouponsDAO;
import com.johnbryce.db.ConnectionPool;
import com.johnbryce.db.DatabaseManager;
import com.johnbryce.dbdao.CouponsDBDAO;
import com.johnbryce.schedule.CouponExpirationDailyJob;

public class CouponSystem {
	private static CouponSystem instance = null;
	private Thread daily = null;

	private CouponSystem() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		setupDatabase();
		ConnectionPool.getInstance();
		startDailyThread();
	}

	private void startDailyThread() {
		System.out.println("start daily thread");
		daily = new Thread(new CouponExpirationDailyJob());
		daily.start();
	}

	public void stopDailyThread() {
		System.out.println("stop daily thread");
		daily.stop();
	}

	private void setupDatabase() throws SQLException {
		DatabaseManager.DropAllTables();
		DatabaseManager.CreateAllTables();
	}

	public static CouponSystem getInstance() throws SQLException {
		if (instance == null) {
			synchronized (CouponSystem.class) {
				if (instance == null) {
					try {
						instance = new CouponSystem();
					} catch (ClassNotFoundException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		return instance;
	}
}
