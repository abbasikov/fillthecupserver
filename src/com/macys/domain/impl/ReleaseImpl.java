package com.macys.domain.impl;

import org.apache.commons.lang3.StringUtils;

import com.macys.dao.database.DBObject;
import com.macys.domain.Release;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.annotations.PersistentBusinessObject;
import com.macys.domain.business.common.annotations.PersistentMetadata;
import com.macys.utils.AppUtils;
import com.macys.valuesobjects.BaseDTO;
import com.macys.valuesobjects.ReleaseVo;

@PersistentBusinessObject(type=BusinessObjectTypeEnum.RELEASE)
public class ReleaseImpl extends BusinessObjectImpl implements Release{

	public ReleaseImpl(DBObject dbObject) {
		super(dbObject);
		// TODO Auto-generated constructor stub
	}
	
	@PersistentMetadata
	private String branchCutDate;
	
	@PersistentMetadata
	private String branchHardLockDate;
	
	@PersistentMetadata
	private String branchFreezeDate;
	
	@PersistentMetadata
	private String branchProductionDate;
	
	@PersistentMetadata
	private String mcomDate;
	
	@PersistentMetadata
	private String bcomDate;
	
	@PersistentMetadata
	private String isActivated;

	@Override
	public String getBranchCutDate() {
		return branchCutDate;
	}

	@Override
	public void setBranchCutDate(String branchCutDate) {
		this.branchCutDate = branchCutDate;
	}

	@Override
	public String getBranchHardLockDate() {
		return branchHardLockDate;
	}

	@Override
	public void setBranchHardLockDate(String branchHardLockDate) {
		this.branchHardLockDate = branchHardLockDate;
	}

	@Override
	public String getMcomDate() {
		return mcomDate;
	}

	@Override
	public void setMcomDate(String mcomDate) {
		this.mcomDate = mcomDate;
	}

	@Override
	public String getBcomDate() {
		return bcomDate;
	}

	@Override
	public void setBcomDate(String bcomDate) {
		this.bcomDate = bcomDate;
	}
	
	@Override
	public String getBranchFreezeDate() {
		return branchFreezeDate;
	}

	@Override
	public void setBranchFreezeDate(String branchFreezeDate) {
		this.branchFreezeDate = branchFreezeDate;
	}

	@Override
	public String getBranchProductionDate() {
		return branchProductionDate;
	}

	@Override
	public void setBranchProductionDate(String branchProductionDate) {
		this.branchProductionDate = branchProductionDate;
	}
	
	@Override
	public String getIsActivated() {
		return isActivated;
	}

	@Override
	public void setIsActivated(String isActivated) {
		this.isActivated = isActivated;
	}

	@Override
	public BaseDTO createDTO() {
		
		ReleaseVo vo 		= new ReleaseVo();
		vo.name 			= this.getName();
		vo.type 			= this.getType();
		vo.uuid 			= this.getUuid();
		vo.createdOnISO8601	= AppUtils.getDateISO8601(this.getCreatedOn());
		
		vo.branchCutDate		= this.getBranchCutDate();
		vo.branchHardLockDate 	= this.getBranchHardLockDate();
		vo.mcomDate				= this.getMcomDate();
		vo.bcomDate				= this.getBcomDate();
		vo.branchFreezeDate		= this.getBranchFreezeDate();
		vo.branchProductionDate = this.getBranchProductionDate();
		vo.isActivated			= StringUtils.isBlank(this.getIsActivated()) ? "false" : this.getIsActivated() ;
		
		return vo;
	}
	
	
	

}
