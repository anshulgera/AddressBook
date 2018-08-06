package com.jda.test.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public interface Service {
	public void createAddressBook() throws IOException;
	public void openAddressBook() throws FileNotFoundException, JsonParseException, JsonMappingException, IOException;
}
