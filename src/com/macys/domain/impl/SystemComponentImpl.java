package com.macys.domain.impl;

import com.macys.dao.database.DBObject;
import com.macys.domain.SystemComponent;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.annotations.PersistentBusinessObject;
import com.macys.domain.business.common.annotations.PersistentMetadata;
import com.macys.utils.AppUtils;
import com.macys.valuesobjects.BaseDTO;
import com.macys.valuesobjects.SystemComponentVo;

@PersistentBusinessObject(type=BusinessObjectTypeEnum.SYSTEM_COMPONENT)
public class SystemComponentImpl extends BusinessObjectImpl implements SystemComponent{

	public SystemComponentImpl(DBObject dbObject) {
		super(dbObject);
	}
	
	@PersistentMetadata
	private String description;
	
	@PersistentMetadata
	private String displayName;

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public BaseDTO createDTO() {
		SystemComponentVo vo = new SystemComponentVo();
		vo.name = this.getName();
		vo.type = this.getType();
		vo.uuid = this.getUuid();
		vo.createdOnISO8601	= AppUtils.getDateISO8601(this.getCreatedOn());
		
		vo.description 	= this.getDescription();
		vo.displayName	= this.getName();
		
		return vo;
	}
	

}
