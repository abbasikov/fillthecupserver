package com.macys.valuesobjects;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class BaseDTO {
	
	public String uuid;
	
	public String type;
	
	public String name;
	
	public String createdOnISO8601;
	
	public String createdBy;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
