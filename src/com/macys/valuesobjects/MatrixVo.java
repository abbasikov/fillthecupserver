package com.macys.valuesobjects;

import java.util.ArrayList;
import java.util.List;

import flexjson.JSON;

public class MatrixVo {
	
	public MatrixVo(){
		//this.data = new String[1][1];
		//this.columns = new SystemComponentVo[2];
		//this.columns[0].name = "hello";
		//this.columns[1].name = "world";
		this.columns = new ArrayList<SystemComponentVo>();
		SystemComponentVo c1 = new SystemComponentVo();
		c1.name = "Hello";
		this.columns.add(c1);
	}
	
	public SettingVo 				settings;
	public List<SystemComponentVo> 		columns;
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
