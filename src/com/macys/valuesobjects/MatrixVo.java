package com.macys.valuesobjects;

import java.util.List;

import flexjson.JSON;

public class MatrixVo {
	
	public List<SystemComponentVo> 	columns;
	public String[][]				data = {{}};	
	
	@JSON(include=true)
	public String[][] getData(){
		return data;
	}
	
	@JSON(include=true)
	public List<SystemComponentVo> getColumns(){
		return columns;
	}
}
