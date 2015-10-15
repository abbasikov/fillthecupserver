package com.macys.valuesobjects.containers;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.macys.valuesobjects.UserVo;

public class UserContainerVo extends BaseContainerVo{
	
	@JsonProperty("data")
	public UserVo data;
	
	@JsonProperty("dataList")
	public List<UserVo> dataList;
	
}
