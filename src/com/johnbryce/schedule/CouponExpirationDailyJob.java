package com.johnbryce.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.johnbryce.beans.Coupon;
import com.johnbryce.beans.Customer;
import com.johnbryce.dao.CouponsDAO;
import com.johnbryce.dao.CustomersDAO;
import com.johnbryce.dbdao.CouponsDBDAO;
import com.johnbryce.dbdao.CustomersDBDAO;

public class CouponExpirationDailyJob implements Runnable {
	private static final long SLEEP = 10000;

	private CouponsDAO couponsDAO = new CouponsDBDAO();
	private CustomersDAO customersDAO = new CustomersDBDAO();
	private boolean quit = false;

	public CouponExpirationDailyJob() {
	}

	@Override
	public void run() {
		while (!quit) {
			List<Coupon> coupons = couponsDAO.getAllCoupons();
			List<Integer> customersID = new ArrayList<Integer>();
			customersID.add(3);
			customersID.add(3);
			customersID.add(4);
			int count = 0;
			for (Coupon coupon : coupons) {
				if (coupon.getEndDate().before(new Date())) {
					// this coupon has been expired - now remove the coupons relationship
					couponsDAO.deleteCouponPurchase(customersID.get(count), coupon.getId());
					couponsDAO.deleteCoupon(coupon.getId());
					count++;
				}
			}
			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}

	}

	public void Stop() {
		this.quit = true;
	}

}
