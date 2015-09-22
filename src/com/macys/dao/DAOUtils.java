package com.macys.dao;

import java.util.ArrayList;
import java.util.List;

public class DAOUtils {

	@SafeVarargs
	public static <T> List<String> asListOfStrings(T... a) {
		
		if(a == null) {
			return null;
		}
		
		List<String> list = new ArrayList<String>(a.length);
		
		for (int i = 0; i < a.length; i++) {
			list.add(a[i].toString());
		}
		
        return list;
    }
	
}
