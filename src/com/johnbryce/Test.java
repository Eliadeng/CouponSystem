package com.johnbryce;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.johnbryce.beans.Category;
import com.johnbryce.beans.Company;
import com.johnbryce.beans.Coupon;
import com.johnbryce.beans.Customer;
import com.johnbryce.beans.CustomerVsCoupon;
import com.johnbryce.core.CouponSystem;
import com.johnbryce.dao.CouponsDAO;
import com.johnbryce.db.ConnectionPool;
import com.johnbryce.db.DatabaseManager;
import com.johnbryce.dbdao.CompaniesDBDAO;
import com.johnbryce.dbdao.CouponsDBDAO;
import com.johnbryce.dbdao.CustomersDBDAO;
import com.johnbryce.exceptions.AlreadyExistException;
import com.johnbryce.exceptions.NotAllowedException;
import com.johnbryce.exceptions.PurchaseCouponException;
import com.johnbryce.facade.AdminFacade;
import com.johnbryce.facade.CompanyFacade;
import com.johnbryce.facade.CustomerFacade;
import com.johnbryce.security.ClientType;
import com.johnbryce.security.LoginManager;
import com.johnbryce.util.Utilities;

public class Test {
	public static CompaniesDBDAO c2 = new CompaniesDBDAO();
	public static CustomersDBDAO c4 = new CustomersDBDAO();
	public static CouponsDBDAO c3 = new CouponsDBDAO();

	public static AdminFacade adminFacade = new AdminFacade();
	public static CompanyFacade companyFacade = new CompanyFacade();
	public static CustomerFacade customerFacade = new CustomerFacade();

	public static LoginManager loginManager = LoginManager.getInstance();

	public static void PrintCompanies() {
		List<Company> printCompanies = new ArrayList<Company>();
		printCompanies = c2.getAllCompanies();
		System.out.println("The companies are: ");
		for (Company company : printCompanies) {
			System.out.println(company);
		}
	}

	public static void AddCompanies() {
		c2.addCompany(new Company("Kima'at Hinam", "hinam@gmail.com", "1234"));
		c2.addCompany(new Company("Haviv", "haviv@gmail.com", "haviv123"));
		c2.addCompany(new Company("Mahsanei Chashmal", "mahsanei@gmail.com", "mahsan123"));
		c2.addCompany(new Company("Anak Hachasmal", "anak@gmail.com", "anak321"));
		c2.addCompany(new Company("Burger King", "king@gmail.com", "king123"));
		PrintCompanies();
	}

	public static void PrintCompanyCoupons() {
		System.out.println("Full company details are:");
		System.out.println(Arrays.asList(c2.getOneCompany(1), c3.getAllCouponsByCompany(1)));
		System.out.println(Arrays.asList(c2.getOneCompany(2), c3.getAllCouponsByCompany(2)));
		System.out.println(Arrays.asList(c2.getOneCompany(3), c3.getAllCouponsByCompany(3)));
		System.out.println(Arrays.asList(c2.getOneCompany(4), c3.getAllCouponsByCompany(4)));
		System.out.println(Arrays.asList(c2.getOneCompany(5), c3.getAllCouponsByCompany(5)));
	}

	public static void PrintCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		customers = c4.getAllCustomers();
		System.out.println("The customers are: ");
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

	public static void AddCustomers() {
		c4.addCustomer(new Customer("Eliad", "Engelstein", "eliadengel@gamil.com", "4444"));
		c4.addCustomer(new Customer("Moshe", "Leibovitch", "moshe@gmail.com", "5555"));
		c4.addCustomer(new Customer("Matan", "Becker", "matanbeck@gmail.com", "6666"));
		c4.addCustomer(new Customer("Kobi", "Aizler", "kobiaiz@gmail.com", "7777"));
		PrintCustomers();
	}

	public static void PrintCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();
		coupons = c3.getAllCoupons();
		System.out.println("the coupons are: ");
		for (Coupon coupon : coupons) {
			System.out.println(coupon);
		}
	}

	public static void AddCoupons() {
		c3.addCoupon(new Coupon(1, Category.FOOD, "Sale", "Buy 1 and get 1 for free", Utilities.realDate(2020, 5, 11),
				Utilities.realDate(2021, 5, 26), 50, 7, "kima'at hinam"));
		c3.addCoupon(new Coupon(2, Category.FOOD, "Sale", "50% precent on second product of the same kind",
				Utilities.realDate(2020, 5, 11), Utilities.realDate(2021, 5, 26), 60, 3, "haviv"));
		c3.addCoupon(new Coupon(3, Category.ELECTRICITY, "20% sale", "for all the loations in the store",
				Utilities.realDate(2020, 5, 11), Utilities.realDate(2021, 5, 26), 80, 30, "mahsanei Chashmal"));
		c3.addCoupon(new Coupon(4, Category.ELECTRICITY, "ALL in 10", "All the productes in the store in 10 shekeles",
				Utilities.realDate(2020, 8, 10), Utilities.realDate(2020, 11, 11), 400, 10, "Anak Hachasmal"));
		c3.addCoupon(new Coupon(5, Category.RESTAURANT, "Free Dessert", "Buy a meal and get a free dessert from the dessert menu",
				Utilities.realDate(2020, 3, 12), Utilities.realDate(2020, 12, 23), 500, 0, "burger king"));
		PrintCoupons();
	}

	public static void AddCategories() {
		c3.addCategory("FOOD");
		c3.addCategory("ELECTRICITY");
		c3.addCategory("RESTAURANT");
		c3.addCategory("VACATION");
	}

	public static void AddCouponPurchase() {
		c3.addCouponPurchase(1, 2);
		c3.addCouponPurchase(1, 3);
		c3.addCouponPurchase(3, 2);
		c3.addCouponPurchase(4, 3);
	}

	public static void PrintCustomerCoupons() {
		System.out.println("Full customer details:");
		System.out.println(Arrays.asList(c4.getOneCustomer(1), c3.getOneCoupon(2)));
		System.out.println(Arrays.asList(c4.getOneCustomer(1), c3.getOneCoupon(3)));
		System.out.println(Arrays.asList(c4.getOneCustomer(3), c3.getOneCoupon(2)));
		System.out.println(Arrays.asList(c4.getOneCustomer(4), c3.getOneCoupon(3)));
	}

	public static void DeleteCouponPurchase() {
		c3.deleteCouponPurchase(1, 2);
	}

	public static void AddCompaniesCustomersAndCoupons() {
		System.out.println();
		AddCategories();
		AddCompanies();
		AddCoupons();
		PrintCompanyCoupons();
		AddCustomers();
		AddCouponPurchase();
		PrintCustomerCoupons();
		DeleteCouponPurchase();
	}

	public static void AdminFacade() throws AlreadyExistException, NotAllowedException {
		System.out.println("AdminFacade? " + loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR));
		System.out.println("adminFacade? " + adminFacade.login("admin@admin.com", "admin"));
		adminFacade.addCompany(new Company("Pelephone", "pelephone@gmail.com", "pele123"));
		PrintCompanies();
		adminFacade.updateCompany(1, new Company("hinami@gmail.com", "hinam12345"));
		PrintCompanies();
		adminFacade.deleteCompany(5);
		System.out.println("The Companies after delete are: " + adminFacade.getAllCompanies());
		System.out.println("The Chosen company is: " + adminFacade.getOneCompany(1));
		adminFacade.addCustomer(new Customer("Jonathan", "Cohen", "johnny@gmail.com", "johnny123"));
		PrintCustomers();
		adminFacade.updateCustomer(new Customer("Dan", "Glazer", "danglazer@gmail.com", "glazer1234"));
		PrintCustomers();
		adminFacade.deleteCustomer(1);
		PrintCustomers();
		System.out.println("The Chosen Customer is: " + adminFacade.getOneCustomer(3));
	}

	public static void CompanyFacade() throws NotAllowedException {
		System.out
				.println("CompanyFacade? " + loginManager.login("hinami@gmail.com", "hinam12345", ClientType.COMPANY));
		System.out.println("companyFacade? " + companyFacade.login("hinami@gmail.com", "hinam12345"));
		companyFacade.addCoupon(new Coupon(3,3, Category.ELECTRICITY, "3 in 100 Shekels", "3 products in 100 shekalim",
				Utilities.realDate(2020, 5, 11), Utilities.realDate(2021, 5, 26), 50, 100, "mahsanei chashmal"));
		PrintCoupons();
		companyFacade.updateCoupon(1, new Coupon("Buy 2 get 1 for free", "buy 2 bottles and gets the 3 for free",
				Utilities.realDate(2020, 5, 11), Utilities.realDate(2021, 5, 26), 30, 10, "kima'at hinam"));
		PrintCoupons();
		companyFacade.deleteCoupon(4);
		PrintCoupons();
		System.out.println();
		System.out.println(companyFacade.getCompanyCoupons());
		System.out.println(companyFacade.getCompanyCoupons(Category.ELECTRICITY, 3));
		System.out.println(companyFacade.getCompanyCoupons(30, 1));
		System.out.println(companyFacade.getCompanyDetailes());
	}

	public static void CustomerFacade() throws PurchaseCouponException {
		System.out.println("CustomerFacade? " + loginManager.login("moshe@gmail.com", "5555", ClientType.CUSTOMER));
		System.out.println("customerFacade? " + customerFacade.login("moshe@gmail.com", "5555"));
		customerFacade.purchaseCoupon(c3.getOneCoupon(3));
		System.out.println("the coupons that the customer purchased: " + customerFacade.getCustomerCoupons());
		System.out.println(
				"the coupons that the customer by category: " + customerFacade.getCustomerCoupons(Category.ELECTRICITY));
		System.out.println(
				"the coupons that the customer purchased by maxPrice: " + customerFacade.getCustomerCoupons(50));
		System.out.println("customer details: " + customerFacade.getCustomerDetails(3));
	}

	public static void testAll() throws ClassNotFoundException, SQLException, InterruptedException,
			AlreadyExistException, NotAllowedException, PurchaseCouponException {
		CouponSystem couponSystem = CouponSystem.getInstance();
		AddCompaniesCustomersAndCoupons();
		System.out.println();
		AdminFacade();
		System.out.println();
		CompanyFacade();
		System.out.println();
		CustomerFacade();
		System.out.println();
		couponSystem.stopDailyThread();
		ConnectionPool.getInstance().closeAllConnection();
	}

	public static void main(String[] args) throws ClassNotFoundException, InterruptedException, SQLException,
			AlreadyExistException, NotAllowedException, PurchaseCouponException {
		testAll();
	}

}
