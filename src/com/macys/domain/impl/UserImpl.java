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
	public String firstName;
	
	@PersistentMetadata
	public String lastName;
	
	@PersistentMetadata
	public String email;
	
	@PersistentMetadata
	public String password;
	
	@PersistentMetadata
	public String isSuperAdmin;
	
	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
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
		
		vo.firstName 	= this.firstName;
		vo.lastName 	= this.lastName;
		vo.email  		= this.email;
		vo.isSuperAdmin = StringUtils.isBlank(this.isSuperAdmin) ? "false":this.isSuperAdmin;
		
		return vo;
	}
	
	
	
}
