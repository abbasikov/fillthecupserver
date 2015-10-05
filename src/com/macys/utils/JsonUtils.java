package com.macys.utils;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class JsonUtils {
	
	public static String toJson(Object object) {
		String json = new JSONSerializer().serialize(object);
		return json;
	}
	
	public static Map<String, Object> mapFromJson(String json) {
		Map<String, Object> jsonMap = new JSONDeserializer<Map<String, Object>>().use(null, new HashMap<String, Object>().getClass()).deserialize(json);
		return jsonMap;
	}
	
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		try{
			Gson gson = new Gson();
	        T object = gson.fromJson(jsonString, clazz);
	        return object;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			return null;
		}
        
    }
	
}