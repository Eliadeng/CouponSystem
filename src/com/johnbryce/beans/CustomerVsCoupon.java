package com.johnbryce.beans;

public class CustomerVsCoupon {
	private int couponID;
	private int customerID;

	public CustomerVsCoupon(int customerID, int couponID) {
		this.customerID = customerID;
		this.couponID = couponID;
	}

	public CustomerVsCoupon() {
	}

	public int getCouponID() {
		return couponID;
	}

	public int getCustomerID() {
		return customerID;
	}

	@Override
	public String toString() {
		return "CustomerVsCoupon [couponID=" + couponID + ", customerID=" + customerID + "]";
	}

}
