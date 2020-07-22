package com.johnbryce.dao;

import java.util.List;

import com.johnbryce.beans.Coupon;
import com.johnbryce.beans.CustomerVsCoupon;


public interface CouponsDAO {
	void addCoupon(Coupon coupon);

	void updateCoupon(Coupon coupon, int couponID);

	void deleteCustomerVsCoupon(int customerID, int couponID);

	void deleteCoupon(int couponID);

	List<Coupon> getAllCoupons();

	Coupon getOneCoupon(int couponID);

	void addCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchase(int customerID, int couponID);

	void deleteCustomerCoupons(int customerID);

	void deleteCouponCustomers(int couponID);

	List<Coupon> getAllCouponsByCompany(int companyID);
	
	List<CustomerVsCoupon> getAllCustomersVsCoupons();

	

}
