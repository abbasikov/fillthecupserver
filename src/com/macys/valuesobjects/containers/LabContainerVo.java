package com.macys.valuesobjects.containers;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.macys.valuesobjects.LabVo;

public class LabContainerVo extends BaseContainerVo{

	@JsonProperty("data")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public LabVo data;
	
	@JsonProperty("dataList")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public List<LabVo> dataList;
}
