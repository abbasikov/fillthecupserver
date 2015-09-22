package com.macys.domain.business.common;

public enum RelationshipTypeEnum {
	
	COMPANY_EMPLOYEE("Company_Employee",0),
	COMPANY_PROJECT("Company_Project",1),
	EMPLOYEE_MESSAGE("Employee_Message",2);
	
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
