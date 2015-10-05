package com.macys.valuesobjects;

public class SystemComponentVo extends BaseDTO{
	
	public SystemComponentVo(){
		
	}
	
	public SystemComponentVo(String uuid,String name,String type){
		super.uuid	= uuid;
		super.name 	= name;
		super.type	= type;
		this.displayName = name;
	}
	
	public String displayName;
	public String description;
	
}
