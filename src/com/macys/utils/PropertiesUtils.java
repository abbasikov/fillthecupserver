package com.macys.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
	
	
	private Properties props;
	private static PropertiesUtils instance = new PropertiesUtils();
	
	
	private PropertiesUtils(){
		InputStream is = null;
		try {
			props = new Properties();
			is = this.getClass().getResourceAsStream("/application.properties"); 
		    props.load(is);
		    System.err.println("===Properties File loaded===");
		    System.out.println(props);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	public static PropertiesUtils getInstance(){
		return instance;
	}
	
	public String getProperty(String key){
		return props.getProperty(key);
	}

}
