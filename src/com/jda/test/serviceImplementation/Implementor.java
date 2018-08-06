package com.jda.test.serviceImplementation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import com.jda.test.model.Address;
import com.jda.test.model.PersonData;
import com.jda.test.service.Service;
import com.jda.test.utility.JsonUtil;
import com.jda.test.utility.Utility;

public class Implementor implements Service{
	static Utility utility;
	static ObjectMapper mapper;
	static{
		utility = new Utility();
		mapper = new ObjectMapper();
	}
	@Override
	public void createAddressBook() throws IOException {
		
		System.out.println("Enter name of AddressBook file : ");
		String addressBookName = utility.inputString();
		File file = new File("/home/bridgelabz/git/AddressBook/AddressBooks/" + addressBookName + ".json");
		file.createNewFile();
		if(file.createNewFile()){
			System.out.println("New AddressBook created.");
		}else{
			System.out.println("AddressBook with this name already exists.");
		}
	}

	@Override
	public void openAddressBook() throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Available address books : ");
		File file = new File("/home/bridgelabz/git/AddressBook/AddressBooks");
		for(File f:file.listFiles()){
			if(f.isFile()){
				System.out.println(f.getName());
			}
		}
		System.out.println("Enter name of address book to operate on :");
		String fileName = utility.inputString();
		int correctName = 0;
		for(File f:file.listFiles()){
			if(f.isFile() && f.getName()==fileName){
				correctName = 1;
				break;
			}
		}
		if(correctName!=1){
			System.out.println("No such file.");
			return;
		}
		
		ArrayList<PersonData> addressBookData = new ArrayList<PersonData>();
		File addressBookFile = new File("/home/bridgelabz/git/AddressBook/AddressBooks" + fileName);
		addressBookData = mapper.readValue(addressBookFile, new TypeReference<ArrayList<PersonData>>(){});
		
		System.out.println("1.Add 2.Edit 3. Delete 4.Sort By Name 5. Sort By Zipcode 6. Print 7.Save 8.SaveAs");
		Integer choice = utility.inputPositiveInteger();
		if(choice<1 && choice>8){
			System.out.println("Invalid choice");
			return;
			}
		switch(choice){
		case 1:
			add(addressBookData);
			break;
		case 2:
			edit(addressBookData);
			break;
		case 3:
			delete(addressBookData);
			break;
		case 4:
			sortByName(addressBookData);
			break;
		case 5:
			sortByZipcode(addressBookData);
			break;
		case 6:
			print(addressBookData);
			break;
		case 7:
			save(fileName, addressBookData);
			break;
		case 8:
			saveAs(addressBookData);
			break;
		}
		
	}

	private void saveAs(ArrayList<PersonData> addressBookData) throws IOException {
		System.out.print("Enter new of file to save as : ");
		String fileName = utility.inputString();
		File file = new File("/home/bridgelabz/git/AddressBook/AddressBooks/" + fileName + ".json");
		file.createNewFile();
		if(file.createNewFile()){
			System.out.println("File created");
			JsonUtil util = new JsonUtil();
			String output = util.convertJavaToJson(addressBookData);
			PrintWriter pw = new PrintWriter("/home/bridgelabz/git/AddressBook/AddressBooks/" + fileName  + ".json");
			pw.write(output);
			pw.flush();
			pw.close();
			System.out.println("File saved");
		}
		else{
			System.out.println("File saved");
		}
		return;
		
	}

	private void save(String fileName, ArrayList<PersonData> addressBookData) throws FileNotFoundException {
		JsonUtil util = new JsonUtil();
		String output = util.convertJavaToJson(addressBookData);
		PrintWriter pw = new PrintWriter("/home/bridgelabz/git/AddressBook/AddressBooks/" + fileName  + ".json");
		pw.write(output);
		pw.flush();
		pw.close();
		System.out.println("File saved");
		return;
	}

	private void print(ArrayList<PersonData> addressBookData) {
		for(int i=0;i<addressBookData.size();i++){
			PersonData person = addressBookData.get(i);
			System.out.println("First name : " + person.getFirstName());
			System.out.println("Mob no : " + person.getNumber());
			Address address = person.getAddress();
			System.out.println("City" + address.getCity());
		}
		
		
	}

	private void sortByZipcode(ArrayList<PersonData> addressBookData) {
		Collections.sort(addressBookData, new CustomComparatorZip());
		
	}

	private void sortByName(ArrayList<PersonData> addressBookData) {
		Collections.sort(addressBookData, new CustomComparatorName());
	}

	private void delete(ArrayList<PersonData> addressBookData) {
		System.out.println("Enter first name of person to delete from records.");
		String firstName = utility.inputString();
		boolean personExist = false;
		for(int i=0;i<addressBookData.size();i++){
			PersonData person = addressBookData.get(i);
			if(person.getFirstName()==firstName){
				addressBookData.remove(i);
				personExist = true;
				System.out.println(firstName + " removed");
				break;
			}
		}
		if(!personExist){
			System.out.println("No such person");
			return;
		}
		
	}

	private void edit(ArrayList<PersonData> addressBookData) {
		
		System.out.println("Enter person's first name : ");
		String firstName = utility.inputString();
		boolean personExist = false;
		PersonData person = null;
		for(int i=0;i<addressBookData.size();i++){
			person = addressBookData.get(i);
			if(person.getFirstName()==firstName){
				personExist = true;
				break;
			}
		}
		if(!personExist){
			System.out.println("No such person in address book");
			return;
		}
		System.out.println("1. Edit person detail 2.Edit address details");
		int choice = utility.inputPositiveInteger();
		if(choice<1 || choice >2){
			System.out.println("Invalid choice.");
			return;
		}
		switch(choice){
		case 1:
			System.out.print("1.Edit first name "
					+ "2.Edit last name "
					+ "3. Edit mobile number");
			Integer choice1 = utility.inputPositiveInteger();
			if(choice1<1 || choice1>3){
				System.out.println("Invalid choice");
				return;
			}
			switch(choice1){
			case 1:
				System.out.println("Enter new first name : ");
				String newFirstName = utility.inputString();
				person.setFirstName(newFirstName);
				break;
			case 2:
				System.out.println("Enter new last name : ");
				String newLastName = utility.inputString();
				person.setLastName(newLastName);
				break;
			case 3:
				System.out.println("Enter new mobile number");
				String newMobNumber = utility.inputMobNo();
				person.setNumber(newMobNumber);
				break;
			}
			
		case 2:
			Address address = person.getAddress();
			System.out.println("1.Edit zipcode "
					+ "2. Edit city "
					+ "3.Edit state");
			int choice2 = utility.inputPositiveInteger();
			if(choice2<1 || choice2>3){
				System.out.println("Invalid choice.");
				return;
			}
			switch(choice2){
			case 1:
				System.out.println("Entet new zipcode");
				int newZipcode = utility.inputZipcode();
				address.setZipcode(newZipcode);
				break;
			case 2:
				System.out.println("Enter new city");
				String newCity = utility.inputString();
				address.setCity(newCity);
				break;
			case 3:
				System.out.println("Enter new state");
				String newState = utility.inputString();
				address.setState(newState);
				break;
			}
			}
		return;
		
	}

	private void add(ArrayList<PersonData> addressBookData) {
	PersonData  person = new PersonData();
		System.out.println("First Name :");
	String firstName = utility.inputString();
	System.out.println("Last name : ");
	String lastName = utility.inputString();
	System.out.println("Number :");
	String mobNumber = utility.inputMobNo();
	person.setFirstName(firstName);
	person.setLastName(lastName);
	person.setNumber(mobNumber);
	
	Address address = new Address();
	int zipcode = utility.inputZipcode();
	String city = utility.inputString();
	String state = utility.inputString();
	address.setZipcode(zipcode);
	address.setCity(city);
	address.setState(state);
	
	person.setAddress(address);
	addressBookData.add(person);
		
	}

}
