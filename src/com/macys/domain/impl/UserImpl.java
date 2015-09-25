package com.macys.domain.impl;

import org.apache.commons.lang3.StringUtils;

import com.macys.dao.database.DBObject;
import com.macys.domain.User;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.annotations.PersistentBusinessObject;
import com.macys.domain.business.common.annotations.PersistentMetadata;
import com.macys.utils.AppUtils;
import com.macys.valuesobjects.BaseDTO;
import com.macys.valuesobjects.UserVo;

@PersistentBusinessObject(type=BusinessObjectTypeEnum.USER)
public class UserImpl extends BusinessObjectImpl implements User{

	public UserImpl(DBObject dbObject) {
		super(dbObject);
	}
	
	@PersistentMetadata
	private String userName;
	
	@PersistentMetadata
	private String password;
	
	@PersistentMetadata
	private String isSuperAdmin;
	
	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	@Override
	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	@Override
	public BaseDTO createDTO(){
		
		UserVo vo = new UserVo();
		vo.name = this.getName();
		vo.type = this.getType();
		vo.uuid = this.getUuid();
		vo.createdOnISO8601	= AppUtils.getDateISO8601(this.getCreatedOn());
		
		vo.username		= this.getUserName();
		vo.isSuperAdmin = StringUtils.isBlank(this.getIsSuperAdmin()) ? "false":this.getIsSuperAdmin();
		
		return vo;
	}
	
	
	
}
