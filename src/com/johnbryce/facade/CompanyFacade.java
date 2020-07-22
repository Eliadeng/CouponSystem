package com.johnbryce.facade;

import java.util.ArrayList;
import java.util.List;

import com.johnbryce.beans.Category;
import com.johnbryce.beans.Company;
import com.johnbryce.beans.Coupon;
import com.johnbryce.exceptions.NotAllowedException;

public class CompanyFacade extends ClientFacade {

	private int companyID;

	public CompanyFacade() {
		super();
	}

	@Override
	public boolean login(String email, String password) {
		return companiesDAO.isCompanyExists(email, password);
	}

	public void addCoupon(Coupon coupon) throws NotAllowedException {
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		companyID = coupon.getCompanyID();
		for (Coupon coup : coupons) {
			if (coup.getCompanyID() == companyID) {
				if (coupon.getTitle().equals(coup.getTitle())) {
					throw new NotAllowedException("the title is the same between the 2 companies");
				}
			}
		}
		couponsDAO.addCoupon(coupon);
	}

	public void updateCoupon(int couponID, Coupon coupon) throws NotAllowedException {
		Coupon toUpdate = couponsDAO.getOneCoupon(couponID);
		if (coupon.getCategory() != null) {
			toUpdate.setCategory(coupon.getCategory());
		}
		if (coupon.getTitle() != null) {
			toUpdate.setTitle(coupon.getTitle());
		}
		if (coupon.getDescription() != null) {
			toUpdate.setDescription(coupon.getDescription());
		}
		if (coupon.getStartDate() != null) {
			toUpdate.setStartDate(coupon.getStartDate());
		}
		if (coupon.getEndDate() != null) {
			toUpdate.setEndDate(coupon.getEndDate());
		}
		if (coupon.getAmount() > 0) {
			toUpdate.setAmount(coupon.getAmount());
		}
		if (coupon.getPrice() > 0) {
			toUpdate.setPrice(coupon.getPrice());
		}
		if (coupon.getImage() != null) {
			toUpdate.setImage(coupon.getImage());
		}
		couponsDAO.updateCoupon(coupon, couponID);
	}

	public void deleteCoupon(int couponID) {
		couponsDAO.deleteCouponCustomers(couponID);
		couponsDAO.deleteCoupon(couponID);
	}

	public List<Coupon> getCompanyCoupons() {
		List<Coupon> results = new ArrayList<Coupon>();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		setCompanyID(2);
		for (Coupon coupon : coupons) {
			if (coupon.getCompanyID() == companyID) {
				results.add(coupon);
			}
		}
		return results;
	}

	public List<Coupon> getCompanyCoupons(Category category, int companyID) {
		List<Coupon> results = new ArrayList<Coupon>();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		if (coupons != null) {
			for (Coupon coupon : coupons) {
				if (coupon.getCompanyID() == companyID && coupon.getCategory().equals(category)) {
					results.add(coupon);
				}
			}
		}
		return results;
	}

	public List<Coupon> getCompanyCoupons(double maxPrice, int companyID) {
		List<Coupon> results = new ArrayList<Coupon>();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		if (coupons != null) {
			for (Coupon coupon : coupons) {
				if (coupon.getCompanyID() == companyID && coupon.getPrice() <= maxPrice) {
					results.add(coupon);
				}
			}
		}
		return results;
	}

	public Company getCompanyDetailes() {
		setCompanyID(2);
		Company company = companiesDAO.getOneCompany(getCompanyID());
		List<Coupon> coupons = couponsDAO.getAllCouponsByCompany(getCompanyID());
		company.setCoupons(coupons);
		return company;
	}

	public int getCompanyID(String email, String password) {
		Company company = companiesDAO.getCompanyByEmailAndPassword(email, password);
		return company.getId();
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getCompanyID() {
		return companyID;
	}

	@Override
	public String toString() {
		return "CompanyFacade [companyID=" + companyID + "]";
	}

}
