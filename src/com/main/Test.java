package com.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.macys.utils.JsonUtils;
import com.macys.valuesobjects.MatrixVo;

public class Test {
	
	private int i;
	
	public static void main(String[] args) {
		
		String path = "null";
		
		if(StringUtils.isBlank(path))
			System.out.println("Path is Blank");
		else
			System.out.println("Path is not Blank");
		
		//long time = 1426550971000L;
		
		Date date = new Date();
		
		System.err.println(date);
		
		//System.out.println("ENum: "+(MessageStatusEnum.enumFromName("RdEAD")  == null));
		
//		TimeZone tz = TimeZone.getTimeZone("Asia/Dubai");
//		
//		SimpleDateFormat sdf = new SimpleDateFormat();
//		
//		sdf.setTimeZone(tz);
//		
//		System.err.println(sdf.format(date));
		
		String iso8601 = "yyyy-MM-dd'T'HH:mm:ss.SSZZ";
		SimpleDateFormat sdf1 = new SimpleDateFormat(iso8601);
		//sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println(sdf1.format(date)); 
		//cUaugEsXTYojW/7YZxN/LrH6c6Vy7uA3hYjfwk/qvyxP8p1AI0Mu9pVo/ZqyhCDx
		
		//System.out.println(AppUtils.getCreateOnByTimeZone(date, Constants.TIME_ZONE_DUBAI));
		//String json = "[{\"areaType\":\"Offices\",\"description\":\"Front Door\",\"serviceTypes\":[{\"name\":\"Facade Cleaning\",\"serviceAreas\":[{\"name\":\"Car Parking Canopy\",\"length\":\"12\",\"width\":\"12\",\"quantity\":\"\",\"cleaningItems\":[]}]}]}]";
		
//		SurveyEntryVo[] vo = JsonUtils.fromJson(json, SurveyEntryVo[].class);
//		int h = 9;
		//System.out.println(EncryptionUtils.encryptPassword("a"));
		String random = UUID.randomUUID().toString();
		
		System.out.println(random.substring(0,8));
		MatrixVo mat = new MatrixVo();
		//mat.settings = new SettingVo();
		Map<String, Object> map = new HashMap<String,Object>();
		String s = "{ \"SmartPrompt\" : [{\"Controller\": \"3\",\"QE\":\"3\", \"SDP\":\"5\"} , {\"Controller\": \"3\",\"QE\":\"3\", \"SDP\":\"5\"}] }";
		Map<String, Object> m =JsonUtils.mapFromJson(s); 
		List<Object> fff = (List<Object>)m.get("SmartPrompt");
		Map<String, String> hh = (Map<String, String>)fff.get(0);
		System.out.println(hh);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (i != other.i)
			return false;
		return true;
	}

}
