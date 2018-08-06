package com.jda.test.utility;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	
	public  String convertJavaToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = "";
		try {
			jsonResult = mapper.writeValueAsString(object);
		}catch(JsonGenerationException e) {
			System.out.println("Exception thrown while converting Java Object to Json" + e.getMessage());
		}catch(JsonMappingException e) {
			System.out.println("Exception thrown while converting Java Object to Json" + e.getMessage());
		}catch(IOException e) {
			System.out.println("Exception thrown while converting Java Object to Json" + e.getMessage());
		}
				
		return jsonResult;
	}

}
