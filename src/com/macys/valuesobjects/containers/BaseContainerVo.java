package com.macys.valuesobjects.containers;

import org.codehaus.jackson.annotate.JsonProperty;

import com.macys.valuesobjects.MetaVo;

public class BaseContainerVo {
	
	@JsonProperty("meta")
	public MetaVo meta = new MetaVo();
	
}
