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
	private String firstName;
	
	@PersistentMetadata
	private String lastName;
	
	@PersistentMetadata
	private String userEmail;
	
	@PersistentMetadata
	private String userName;
	
	@PersistentMetadata
	private String password;
	
	@PersistentMetadata
	private String isPasswordReset;
	
	@PersistentMetadata
	private String isSuperAdmin;
	
	@PersistentMetadata
	private String isLabManager;
	
	@PersistentMetadata
	private String isLabUser;
	
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
	public String getUserEmail() {
		return userEmail;
	}

	@Override
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	@Override
	public String getIsLabManager() {
		return isLabManager;
	}

	@Override
	public void setIsLabManager(String isLabManager) {
		this.isLabManager = isLabManager;
	}

	@Override
	public String getIsLabUser() {
		return isLabUser;
	}

	@Override
	public void setIsLabUser(String isLabUser) {
		this.isLabUser = isLabUser;
	}
	
	@Override
	public String getIsPasswordReset() {
		return isPasswordReset;
	}

	@Override
	public void setIsPasswordReset(String isPasswordReset) {
		this.isPasswordReset = isPasswordReset;
	}

	@Override
	public BaseDTO createDTO(){
		UserVo vo = new UserVo();
		
		vo.name = this.getName();
		vo.type = this.getType();
		vo.uuid = this.getUuid();
		vo.createdOnISO8601	= AppUtils.getDateISO8601(this.getCreatedOn());
		vo.createdBy		= this.getCreatedBy();
		
		vo.username			= this.getUserName();
		vo.isSuperAdmin 	= StringUtils.isBlank(this.getIsSuperAdmin()) 	? "false":this.getIsSuperAdmin();
		vo.isLabManager		= StringUtils.isBlank(this.getIsLabManager()) 	? "false":this.getIsLabManager();
		vo.isLabUser		= StringUtils.isBlank(this.getIsLabUser()) 		? "false":this.getIsLabUser();
		vo.isPasswordReset 	= StringUtils.isBlank(this.getIsPasswordReset())? "false":this.getIsPasswordReset();
		
		vo.firstName	= this.getFirstName();
		vo.lastName		= this.getLastName();
		vo.userEmail	= this.getUserEmail();
		
		return vo;
	}
	
	
	
}
