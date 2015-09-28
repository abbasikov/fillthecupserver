package com.macys.valuesobjects.containers;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import com.macys.valuesobjects.SystemComponentVo;

public class SystemComponentContainerVo extends BaseContainerVo{

	@JsonProperty("data")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public SystemComponentVo data;
	
	@JsonProperty("dataList")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public List<SystemComponentVo> dataList;
}
