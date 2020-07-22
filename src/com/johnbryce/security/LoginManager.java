package com.johnbryce.security;

import com.johnbryce.facade.AdminFacade;
import com.johnbryce.facade.ClientFacade;
import com.johnbryce.facade.CompanyFacade;
import com.johnbryce.facade.CustomerFacade;

public class LoginManager {
	private static LoginManager instance = null;
	private ClientFacade clientFacade;
	
	private LoginManager() {
		System.out.println("Login Manager in action");
	}
	
	public static LoginManager getInstance() {
		if (instance == null) {
			synchronized (LoginManager.class) {
				if (instance == null) {
					instance = new LoginManager();
				}
			}
		}
		return instance;
	}
	
	public ClientFacade login(String email, String password, ClientType clientType) {
		switch (clientType) {
		case ADMINISTRATOR:
			clientFacade = (ClientFacade) new AdminFacade();
			if (clientFacade.login(email, password)) {
				return clientFacade;
			} else {
				return null;
			}
			
		case COMPANY:
			clientFacade = (ClientFacade) new CompanyFacade();
			if(clientFacade.login(email, password)) {
				int companyID = ((CompanyFacade) clientFacade).getCompanyID();
				System.out.println("" + companyID);
				((CompanyFacade) clientFacade).setCompanyID(companyID);
				return clientFacade;
			} else {
				return null;
			}
			
		case CUSTOMER:
			clientFacade = (ClientFacade) new CustomerFacade();
			if (clientFacade.login(email, password)) {
				int customerID = ((CustomerFacade) clientFacade).getCustomerID();
				((CustomerFacade) clientFacade).setCustomerID(customerID);
				return clientFacade;
			} else {
				return null;
			}
			
		default:
			clientFacade = null;
			break;
			
		}
		
		return null;
		
	}

}
