package com.macys.valuesobjects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class MetaVo {

	@JsonProperty("code")
	public int code;
	
	@JsonProperty("error")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String error;
	
	@JsonProperty("details")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String details;
}
