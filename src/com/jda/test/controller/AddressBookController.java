package com.jda.test.controller;

import com.jda.test.service.Service;
import com.jda.test.serviceImplementation.Implementor;
import com.jda.test.utility.Utility;

public class AddressBookController {
	static public void main(String[] args) throws Exception{
		Utility utility = new Utility();
		Service service = new Implementor();
		System.out.println("Enter 1 for JDBC.");
		int temp = utility.inputPositiveInteger();
		if(temp==1) {
			service.addressBookJDBC();
		}else {
		while(true){
			System.out.println("1.Creat new AddressBook"
					+ " 2. Open Existing AddressBook");
			Integer choice = utility.inputPositiveInteger();
			if(choice>0 && choice<3){
				switch(choice){
				case 1:
					service.createAddressBook();
					break;
				case 2:
					service.openAddressBook();
					break;
				}
				
			}else{
				System.out.println("Invalid Input");
			}
		}
		}
	}
}
