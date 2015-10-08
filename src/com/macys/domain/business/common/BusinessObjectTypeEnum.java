package com.macys.domain.business.common;

import java.util.ArrayList;
import java.util.List;

import com.macys.utils.AppUtils;

public enum BusinessObjectTypeEnum {

	USER("USER1","User"),
	LAB("LLAB1","LLab"),
	SYSTEM_COMPONENT("SYSCO","SystemComponent"),
	RELEASE("RELSE","Release"),
	RELEASECUP("RELCP","ReleaseCup"),
	IPM("IPMVO","IPM");
	
	String type;
	String interfaceName;

	BusinessObjectTypeEnum(String type, String interfaceName) {
		this.type = type;
		this.interfaceName = interfaceName;
		
		if(type.length() > 5) {
			throw new IllegalArgumentException("invalid type size:" + type);
		}
	}
	
	public String getType() {
		return type;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	
	public String toString() {
		return type;
	}
	
	public String newRandomUuid() {
		String uuid = new StringBuilder(type).append("-").append(AppUtils.randomUuid().toUpperCase()).toString();
		return uuid;
	}
	
	public static BusinessObjectTypeEnum enumFromType(String typeName) {
		
		for(BusinessObjectTypeEnum en:BusinessObjectTypeEnum.values()) {
			if(en.getType().equals(typeName)) {
				return en;
			}
		}
		
		return null;
	}
	
	public static BusinessObjectTypeEnum enumFromUuid(String uuid) {
		
		if(uuid == null) {
			return null;
		}
		
		for(BusinessObjectTypeEnum en:BusinessObjectTypeEnum.values()) {
			if(uuid.startsWith(en.getType())) {
				return en;
			}
		}
		
		return null;
	}
	
	public static BusinessObjectTypeEnum[] enumsFromNames(List<String> names) {
		
		if(names == null || names.size() == 0) {
			return null;
		}
		
		List<BusinessObjectTypeEnum> types = new ArrayList<BusinessObjectTypeEnum>(names.size());
		
		for(String name : names) {
			BusinessObjectTypeEnum enum1 = enumFromString(name);
			
			if(enum1 != null) {
				types.add(enum1);
			}
		}
		
		return types.toArray(new BusinessObjectTypeEnum[]{});
	}
	
	public static BusinessObjectTypeEnum enumFromString(String name) {
		for(BusinessObjectTypeEnum enum1:BusinessObjectTypeEnum.values()) {
			if(enum1.type.equals(name)) {
				return enum1;
			}
		}
		
		return null;
	}
	
	public static List<String> listOfStrings(BusinessObjectTypeEnum[] enums) {
		
		//return OTUtils.asListOfStrings(enums);
		return null;
		
	}
	
	
}
