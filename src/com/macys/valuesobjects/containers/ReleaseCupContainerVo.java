package com.macys.valuesobjects.containers;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import com.macys.valuesobjects.ReleaseCupVo;

public class ReleaseCupContainerVo extends BaseContainerVo{

	@JsonProperty("data")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public ReleaseCupVo data;
	
	@JsonProperty("dataList")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public List<ReleaseCupVo> dataList;
}
