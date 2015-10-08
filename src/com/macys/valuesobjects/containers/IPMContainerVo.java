package com.macys.valuesobjects.containers;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import com.macys.valuesobjects.IPMVo;

public class IPMContainerVo extends BaseContainerVo {
	
	@JsonProperty("dataList")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public List<IPMVo> dataList;

}
