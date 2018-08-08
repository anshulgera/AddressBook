package com.jda.test.serviceImplementation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jda.test.service.Service;
import com.jda.test.utility.Utility;
import com.mysql.cj.jdbc.DatabaseMetaData;

public class ServiceImplJDBC implements Service{
	static Utility utility;
	private static Connection connect = null;
    private static Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    static {
    	utility = new Utility();
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager
			        .getConnection("jdbc:mysql://localhost/addressbook?"
			                + "user=sqluser&password=sqluserpw");
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	@Override
	public void createAddressBook() throws Exception {
		System.out.println("JDBC - Create Address Book");
		//addressBookJDBC();
			System.out.println("Enter name of new addressbook to create.");
			String newAddressBook = utility.inputString();
			
			String createSQL = "CREATE TABLE  IF NOT EXISTS " + newAddressBook + "(FIRSTNAME VARCHAR(10),"
					+ " LASTNAME VARCHAR(10),"
					+ " MOBNUMBER BIGINT,"
					+ " CITY VARCHAR(10),"
					+ " STATE VARCHAR(10))";
			statement.executeUpdate(createSQL);
			System.out.println("New AddressBook Table created.");
		}
		   
	
	@Override
	public void openAddressBook() throws FileNotFoundException, JsonParseException, JsonMappingException, IOException, SQLException {
		System.out.println("JDBC - open Address Book");
		System.out.println("Address books available");
		
		//Get all addressbooks from 'addressbook' Database.
		DatabaseMetaData md = (DatabaseMetaData) connect.getMetaData();
		ResultSet rs = md.getTables("addressbook", null, "%", null);
		while (rs.next()) {
		  System.out.println(rs.getString("TABLE_NAME"));
		}
	}

	
	public void addressBookJDBC() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/feedback?"
                            + "user=sqluser&password=sqluserpw");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("insert into addressbook.addressdetails values ( ?, ?, ?, ?, ?)");
			
		System.out.println("'addressbook' database exists, inserting values in addressBook");
		System.out.println("Enter number of entries.");
		Integer n = utility.inputPositiveInteger();
		for(int i=0;i<n;i++) {
			System.out.println(i+1);
			System.out.println("Enter First name : ");
			String firstName = utility.inputString();
			System.out.println("Enter Last name : ");
			String lastName = utility.inputString();
			System.out.print("Enter mob no : ");
			long mobno = utility.inputLong();
			utility.emptyInput();
			System.out.println("Enter city : ");
			String city = utility.inputString();
			System.out.println("Enter state : ");
			String state = utility.inputString();
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setLong(3, mobno);
			preparedStatement.setString(4, city);
			preparedStatement.setString(5, state);
			preparedStatement.addBatch();
			preparedStatement.executeBatch();
			System.out.println("Record inserted.");
		}
		System.out.println("All records inserted.");
		
		System.out.println("Enter mobno to search by: ");
		long option = utility.inputLong();
		utility.emptyInput();
		String query = "SELECT * FROM addressbook.addressdetails WHERE MOBNUMBER='" + option + "';";
		statement = connect.createStatement();
		resultSet = statement.executeQuery(query);
		writeResultSet(resultSet);
		
	}
		catch (Exception e) {
            throw e;
        } finally {
            close();
        }
	}
	 private void writeResultSet(ResultSet resultSet) throws SQLException {
	        // ResultSet is initially before the first data set
	        while (resultSet.next()) {
	            // It is possible to get the columns via name
	            // also possible to get the columns via the column number
	            // which starts at 1
	            // e.g. resultSet.getSTring(2);
	            String firstname = resultSet.getString("FIRSTNAME");
	            String lastname = resultSet.getString("LASTNAME");
	            long mobno = resultSet.getLong("MOBNUMBER");
	            String city = resultSet.getString("CITY");
	            String state = resultSet.getString("STATE");
	            System.out.println("FirstName: " + firstname);
	            System.out.println("LastName: " + lastname);
	            System.out.println("MobNo : : " + mobno);
	            System.out.println("City: " + city);
	            System.out.println("State : " + state);
	        }
	    }
	private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
