package com.macys.valuesobjects;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class UserVo extends BaseDTO{
	
	@JsonSerialize(include=Inclusion.NON_NULL)
	@JsonProperty("username")
	public String username;
	
	@JsonProperty("isSuperAdmin")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String isSuperAdmin;
	
	@JsonProperty("labs")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public List<LabVo> labs;

}
