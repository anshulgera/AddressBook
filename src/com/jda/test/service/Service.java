package com.jda.test.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public interface Service {
	public void createAddressBook() throws IOException, ClassNotFoundException, SQLException, Exception;
	public void openAddressBook() throws FileNotFoundException, JsonParseException, JsonMappingException, IOException, SQLException;
}
