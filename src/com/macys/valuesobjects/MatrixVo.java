package com.macys.valuesobjects;

import java.util.ArrayList;
import java.util.List;

import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.utils.AppUtils;

import flexjson.JSON;

public class MatrixVo {
	
	public MatrixVo() {
		this.columns = new ArrayList<SystemComponentVo>();
		this.data	 = new ArrayList<MatrixRow>();
		for(int i=0;i<9;i++){
			this.data.add(new MatrixRow());
		}
		
		this.columns.add(new SystemComponentVo(AppUtils.randomUuid(), "MVPs", BusinessObjectTypeEnum.SYSTEM_COMPONENT.toString()));
	}
	
	private List<SystemComponentVo> 	columns;
	private List<MatrixRow>				data;;	
	
	public void updateColums(List<SystemComponentVo> uColums){
		this.columns.addAll(uColums);
	}
	
	@JSON(include=true)
	public List<MatrixRow> getData(){
		return data;
	}
	
	@JSON(include=true)
	public List<SystemComponentVo> getColumns(){
		return columns;
	}
	
	public class MatrixRow {
		
	}
	
	public class MatrixCol{
		public String name;
	}
}


