package com.macys.valuesobjects;

import java.util.ArrayList;
import java.util.List;

import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.utils.AppUtils;
import com.macys.valuesobjects.MatrixVo.MatrixRow;

import flexjson.JSON;

public class IPMTreeVo {

	public final int INITIAL_ROWS = 6;
	
	public IPMTreeVo() {
		this.columns = new ArrayList<SystemComponentVo>();
		this.data	 = new ArrayList<MatrixRow>();
		
		
		this.columns.add(new SystemComponentVo(AppUtils.randomUuid(), "MVPs", BusinessObjectTypeEnum.SYSTEM_COMPONENT.toString()));
		this.columns.add(new SystemComponentVo(AppUtils.randomUuid(), "IPM", BusinessObjectTypeEnum.SYSTEM_COMPONENT.toString()));
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
}
