package com.johnbryce.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.johnbryce.beans.Category;
import com.johnbryce.beans.Coupon;
import com.johnbryce.beans.Customer;
import com.johnbryce.beans.CustomerVsCoupon;
import com.johnbryce.exceptions.PurchaseCouponException;

public class CustomerFacade extends ClientFacade {
	private int customerID;

	public CustomerFacade() {
		super();
	}

	@Override
	public boolean login(String email, String password) {
		return customersDAO.isCustomerExists(email, password);
	}

	public void purchaseCoupon(Coupon coupon) throws PurchaseCouponException {
		List<CustomerVsCoupon> customerVsCoupons = couponsDAO.getAllCustomersVsCoupons();
		System.out.println(customerVsCoupons);
		setCustomerID(3);
		for (CustomerVsCoupon customersVsCoupons : customerVsCoupons) {
			if (customersVsCoupons.getCouponID() == coupon.getId()
					&& customersVsCoupons.getCustomerID() == this.customerID) {
				throw new PurchaseCouponException("you can not purchase more  than one coupon");
			}
		}
		// you can not purchase coupon when amount = 0
		Coupon couponDB = couponsDAO.getOneCoupon(coupon.getId());
		System.out.println(couponDB);
		if (couponDB.getAmount() <= 0) {
			throw new PurchaseCouponException("you can not purchase coupon when amount = 0");
		}
		// you can not purchase coupon when date is expired
		if (couponDB.getEndDate().before(new Date())) {
			throw new PurchaseCouponException("you can not purchase coupon when date is expired");
		}
		// after purchase decrease amount by 1
		couponDB.setAmount(couponDB.getAmount() - 1);
		// new amount
		System.out.println(couponDB.getAmount());
		couponsDAO.updateCoupon(couponDB, couponDB.getId());
		// add relationship update
		couponsDAO.addCouponPurchase(this.customerID, coupon.getId());
	}

	public List<Coupon> getCustomerCoupons() {
		List<Coupon> results = new ArrayList<Coupon>();
		setCustomerID(3);
		List<CustomerVsCoupon> customer_vs_Coupons = couponsDAO.getAllCustomersVsCoupons();
		for (CustomerVsCoupon customer_vs_Coupons2 : customer_vs_Coupons) {
			if (customer_vs_Coupons2.getCustomerID() == this.customerID) {
				Coupon tmp = couponsDAO.getOneCoupon(customer_vs_Coupons2.getCouponID());
				results.add(tmp);
			}
		}
		return results;
	}

	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> results = new ArrayList<Coupon>();
		setCustomerID(3);
		List<CustomerVsCoupon> customerVsCoupons = couponsDAO.getAllCustomersVsCoupons();
		for (CustomerVsCoupon customer_vs_Coupons2 : customerVsCoupons) {
			if (customer_vs_Coupons2.getCustomerID() == this.customerID) {
				Coupon tmp = couponsDAO.getOneCoupon(customer_vs_Coupons2.getCouponID());
				if (tmp.getCategory().equals(category)) {
					results.add(tmp);
				}
			}
		}
		return results;
	}

	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> results = new ArrayList<Coupon>();
		setCustomerID(3);
		List<CustomerVsCoupon> customer_vs_Coupons = couponsDAO.getAllCustomersVsCoupons();
		for (CustomerVsCoupon customer_vs_Coupons2 : customer_vs_Coupons) {
			if (customer_vs_Coupons2.getCustomerID() == this.customerID) {
				Coupon tmp = couponsDAO.getOneCoupon(customer_vs_Coupons2.getCouponID());
				if (tmp.getPrice() <= maxPrice) {
					results.add(tmp);
				}
			}
		}
		return results;
	}

	public Customer getCustomerDetails(int customerID) {
		Customer result = customersDAO.getOneCustomer(customerID);
		List<Coupon> coupons = new ArrayList<Coupon>();
		List<CustomerVsCoupon> customer_vs_Coupons = couponsDAO.getAllCustomersVsCoupons();
		for (CustomerVsCoupon customer_vs_Coupons2 : customer_vs_Coupons) {
			if (customer_vs_Coupons2.getCustomerID() == this.customerID) {
				coupons.add(couponsDAO.getOneCoupon(customer_vs_Coupons2.getCouponID()));
			}
		}
		result.setCoupons(coupons);
		return result;
	}

	public int getCustomerID(String email, String password) {
		int customer = customersDAO.getCustomerIDByEmailAndPassword(email, password);
		return customer;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getCustomerID() {
		return customerID;
	}

	@Override
	public String toString() {
		return "CustomerFacade [customerID=" + customerID + "]";
	}

}
