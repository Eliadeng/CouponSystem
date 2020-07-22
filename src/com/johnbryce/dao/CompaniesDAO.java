package com.johnbryce.dao;

import java.util.List;

import com.johnbryce.beans.Company;

public interface CompaniesDAO {
	boolean isCompanyExists(String email, String password);

	void addCompany(Company company);

	void updateCompany(Company company, int id);

	void deleteCompany(int companyID);

	List<Company> getAllCompanies();

	Company getOneCompany(int companyID);

	Company getCompanyByEmailAndPassword(String email, String password);
}
