package com.macys.valuesobjects;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class UserVo extends BaseDTO{
	
	@JsonSerialize(include=Inclusion.NON_NULL)
	@JsonProperty("firstName")
	public String firstName;
	
	@JsonSerialize(include=Inclusion.NON_NULL)
	@JsonProperty("lastName")
	public String lastName;
	
	@JsonSerialize(include=Inclusion.NON_NULL)
	@JsonProperty("userEmail")
	public String userEmail;
	
	@JsonSerialize(include=Inclusion.NON_NULL)
	@JsonProperty("username")
	public String username;
	
	@JsonProperty("isSuperAdmin")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String isSuperAdmin;
	
	@JsonProperty("isLabManager")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String isLabManager;
	
	@JsonProperty("isLabUser")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String isLabUser;
	
	@JsonProperty("isPasswordReset")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String isPasswordReset;
	
	@JsonProperty("labs")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public List<LabVo> labs;
	
}
