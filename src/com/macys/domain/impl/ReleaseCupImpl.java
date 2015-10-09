package com.macys.domain.impl;

import com.macys.dao.database.DBObject;
import com.macys.domain.ReleaseCup;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.annotations.PersistentBusinessObject;
import com.macys.domain.business.common.annotations.PersistentMetadata;
import com.macys.utils.AppUtils;
import com.macys.valuesobjects.BaseDTO;
import com.macys.valuesobjects.ReleaseCupVo;

@PersistentBusinessObject(type=BusinessObjectTypeEnum.RELEASECUP)
public class ReleaseCupImpl extends BusinessObjectImpl implements ReleaseCup{

	public ReleaseCupImpl(DBObject dbObject) {
		super(dbObject);
		// TODO Auto-generated constructor stub
	}
	
	@PersistentMetadata
	private String releaseUuid;
	
	@PersistentMetadata
	private String labUuid;
	
	@PersistentMetadata
	private String availableDevDays;
	
	@PersistentMetadata
	private String devDays;
	
	@PersistentMetadata
	private String regressionDays;
	
	@PersistentMetadata
	private String sysComponents;
	
	@PersistentMetadata
	private String matrixJson;
	
	@PersistentMetadata
	private String ipmTree;
	
	@PersistentMetadata
	private String lastClicked;
	
	@Override
	public String getReleaseUuid() {
		return releaseUuid;
	}

	@Override
	public void setReleaseUuid(String releaseUuid) {
		this.releaseUuid = releaseUuid;
	}

	@Override
	public String getLabUuid() {
		return labUuid;
	}

	@Override
	public void setLabUuid(String labUuid) {
		this.labUuid = labUuid;
	}

	@Override
	public String getAvailableDevDays() {
		return availableDevDays;
	}

	@Override
	public void setAvailableDevDays(String availableDevDays) {
		this.availableDevDays = availableDevDays;
	}

	@Override
	public String getDevDays() {
		return devDays;
	}

	@Override
	public void setDevDays(String devDays) {
		this.devDays = devDays;
	}

	@Override
	public String getRegressionDays() {
		return regressionDays;
	}

	@Override
	public void setRegressionDays(String regressionDays) {
		this.regressionDays = regressionDays;
	}
	
	@Override
	public String getSysComponents() {
		return sysComponents;
	}

	@Override
	public void setSysComponents(String sysComponents) {
		this.sysComponents = sysComponents;
	}
	
	@Override
	public String getMatrixJson() {
		return matrixJson;
	}

	@Override
	public void setMatrixJson(String matrixJson) {
		this.matrixJson = matrixJson;
	}
	
	@Override
	public String getLastClicked() {
		return lastClicked;
	}

	@Override
	public void setLastClicked(String lastClicked) {
		this.lastClicked = lastClicked;
	}
	
	@Override
	public String getIpmTree() {
		return ipmTree;
	}

	@Override
	public void setIpmTree(String ipmTree) {
		this.ipmTree = ipmTree;
	}

	@Override
	public BaseDTO createDTO() {
		ReleaseCupVo vo 	= new ReleaseCupVo();
		vo.name 			= this.getName();
		vo.type 			= this.getType();
		vo.uuid 			= this.getUuid();
		vo.createdOnISO8601	= AppUtils.getDateISO8601(this.getCreatedOn());
		
		vo.availableDevDays = this.getAvailableDevDays();
		vo.devDays			= this.getDevDays();
		vo.regressionDays	= this.getRegressionDays();
		vo.lastClicked		= this.getLastClicked();
		return vo;
	}
	
	

}
