package com.macys.domain.impl;

import org.apache.commons.lang3.StringUtils;

import com.macys.dao.database.DBObject;
import com.macys.domain.IPM;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.annotations.PersistentBusinessObject;
import com.macys.domain.business.common.annotations.PersistentMetadata;
import com.macys.utils.AppUtils;
import com.macys.utils.JsonUtils;
import com.macys.valuesobjects.BaseDTO;
import com.macys.valuesobjects.IPMVo;

@PersistentBusinessObject(type=BusinessObjectTypeEnum.IPM)
public class IPMImpl extends BusinessObjectImpl implements IPM{

	public IPMImpl(DBObject dbObject) {
		super(dbObject);
		// TODO Auto-generated constructor stub
	}
	
	@PersistentMetadata
	private String mvpMatrixJson;

	@Override
	public String getMvpMatrixJson() {
		return mvpMatrixJson;
	}

	@Override
	public void setMvpMatrixJson(String mvpMatrixJson) {
		this.mvpMatrixJson = mvpMatrixJson;
	}
	
	@Override
	public BaseDTO createDTO() {
		IPMVo vo = new IPMVo();
		vo.name = this.getName();
		vo.type = this.getType();
		vo.uuid = this.getUuid();
		vo.createdOnISO8601	= AppUtils.getDateISO8601(this.getCreatedOn());
		
		vo.mvpMatrixJson	= StringUtils.isBlank(this.getMvpMatrixJson()) ? null : JsonUtils.mapFromJson(this.getMvpMatrixJson());
		
		return vo;
	}
	

}
