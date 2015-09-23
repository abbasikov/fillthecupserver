package com.macys.valuesobjects;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class UserVo extends BaseDTO{
	
	@JsonProperty("firstName")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String firstName;
	
	@JsonProperty("lastName")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String lastName;
	
	@JsonProperty("email")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String email;
	
	@JsonSerialize(include=Inclusion.NON_NULL)
	@JsonProperty("password")
	public String password;
	
	@JsonProperty("isSuperAdmin")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String isSuperAdmin;
	
	@JsonProperty("labs")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public List<LabVo> labs;

}
