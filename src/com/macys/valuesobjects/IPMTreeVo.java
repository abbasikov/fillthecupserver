package com.macys.valuesobjects;

import java.util.HashMap;
import java.util.Map;

import flexjson.JSON;

public class IPMTreeVo {
	
	Map<String, Object> ipms = null;
	
	public IPMTreeVo() {
		ipms = new HashMap<String, Object>();
		ipms.put("IPM1", null);
		ipms.put("IPM2", null);
		ipms.put("IPM3", null);
		ipms.put("IPM4", null);
	}
	
	@JSON(include=true)
	public Map<String, Object> getIpms() {
		return ipms;
	}
	
}
