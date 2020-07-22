package com.johnbryce.facade;

import java.util.List;

import com.johnbryce.beans.Company;
import com.johnbryce.beans.Coupon;
import com.johnbryce.beans.Customer;
import com.johnbryce.exceptions.AlreadyExistException;
import com.johnbryce.exceptions.NotAllowedException;

public class AdminFacade extends ClientFacade {

	public AdminFacade() {
		super();
	}

	@Override
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		}
		return false;
	}

	public void addCompany(Company company) throws AlreadyExistException {
		List<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getName().equals(company.getName())) {
				throw new AlreadyExistException();
			}
			if (comp.getEmail().equals(company.getEmail())) {
				throw new AlreadyExistException();
			}
		}
		companiesDAO.addCompany(company);
	}

	public void updateCompany(int companyID, Company company) throws NotAllowedException {
		List<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getId() == company.getId()) {
				throw new NotAllowedException("Company ID exits");
			}
			if (comp.getName().equals(company.getName())) {
				throw new NotAllowedException("Company Name exits");
			}
		}
		companiesDAO.updateCompany(company, companyID);
	}

	public void deleteCompany(int companyID) {
		List<Coupon> originalCoupons = couponsDAO.getAllCoupons();
		for (Coupon coupon : originalCoupons) {
			if (coupon.getCompanyID() == companyID) {
				// 1- remove from table customer_vs_coupon
				couponsDAO.deleteCustomerVsCoupon(1, coupon.getId());
				// 2- remove from table coupon
				couponsDAO.deleteCoupon(coupon.getId());
				// 3- remove from the method couponPurchase
				couponsDAO.deleteCouponPurchase(coupon.getId(), companyID);
			}
		}
		companiesDAO.deleteCompany(companyID);
	}

	public List<Company> getAllCompanies() {
		return companiesDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyID) {
		return companiesDAO.getOneCompany(companyID);
	}

	public void addCustomer(Customer customer) throws AlreadyExistException {
		List<Customer> customerAdmin = customersDAO.getAllCustomers();
		for (Customer customer2 : customerAdmin) {
			if (customer2.getEmail().equals(customer.getEmail())) {
				throw new AlreadyExistException("the email is already exits");
			}
		}
		customersDAO.addCustomer(customer);
	}

	// need to fix this method
	public void updateCustomer(Customer customer) throws NotAllowedException {
		customer.setId(4);
		boolean flag = false;
		List<Customer> customerAdmin = customersDAO.getAllCustomers();
		for (Customer customer2 : customerAdmin) {
			if (customer2.getId() == customer.getId()) {
				flag = true;
			}
		}
		if (flag = false) {
			throw new NotAllowedException("the id doesn't exits in the system");
		} else {
			customersDAO.updateCustomer(customer, 4);
		}
	}

	public void deleteCustomer(int customerID) {
		couponsDAO.deleteCustomerCoupons(customerID);
		customersDAO.deleteCustomer(customerID);
	}

	public List<Customer> getAllCustomers() {
		return customersDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerID) {
		return customersDAO.getOneCustomer(customerID);
	}

	@Override
	public String toString() {
		return "AdminFacade [companiesDAO=" + companiesDAO + ", customersDAO=" + customersDAO + ", couponsDAO="
				+ couponsDAO + "]";
	}

}
