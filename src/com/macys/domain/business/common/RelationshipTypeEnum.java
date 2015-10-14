package com.macys.domain.business.common;

public enum RelationshipTypeEnum {
	
	LAB_USER("Lab_User",0),
	LAB_SYSTEMCOMPONENT("Lab_SystemComponent",1),
	RELEASECUP_IPM("RELEASECUP_IPM",2),
	USER_ROLE("USER_ROLE",3);
	
	String relationshipName;
	int code;
	
	RelationshipTypeEnum(String relationshipName, int code) {
		this.relationshipName = relationshipName;
		this.code = code;
	}
	
	public String getRelationshipName() {
		return relationshipName;
	}
	
	public static RelationshipTypeEnum enumFromName(String name) {
		
		for(RelationshipTypeEnum enum1:RelationshipTypeEnum.values()) {
			if(enum1.relationshipName.equals(name)) {
				return enum1;
			}
		}
		
		return null;
	}
	
}
