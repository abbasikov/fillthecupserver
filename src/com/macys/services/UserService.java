package com.macys.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.macys.dao.DAO;
import com.macys.domain.Lab;
import com.macys.domain.User;
import com.macys.domain.business.BusinessObject;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.Relationship;
import com.macys.domain.business.common.RelationshipTypeEnum;
import com.macys.exceptions.ErrorCodeEnum;
import com.macys.exceptions.ServiceException;
import com.macys.utils.Constants;
import com.macys.utils.EncryptionUtils;
import com.macys.utils.ServiceUtils;
import com.macys.valuesobjects.LabVo;
import com.macys.valuesobjects.UserVo;

public class UserService {
	
	private DAO dao;
	
	public UserVo fetchUser(String email,String password) throws ServiceException{
		try{
			User user =  (User)dao.findBusinessObjectByMetadata(BusinessObjectTypeEnum.USER, "email", email);
			
			if(user == null)
				throw new ServiceException("User not found by email:"+email,ErrorCodeEnum.USER_NOT_FOUND);
			
			if(EncryptionUtils.checkPassword(password, user.getPassword())){
				return (UserVo)user.createDTO();
			}
			else{
				throw new ServiceException("Invalid Credentials",ErrorCodeEnum.INVALID_CREDENTIALS);
			}
		}
		catch(ServiceException exc){
			throw exc;
		}
		catch(Exception exc){
			throw new ServiceException(exc.getMessage(),exc,ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}		
	}
	
	public UserVo login(String email, String password) throws ServiceException{
		try{
			ServiceUtils.verifyNotBlank(email, "Email");
			ServiceUtils.verifyNotBlank(password, "Password");
			
			List<String> userUuids = dao.findUuidsByMetadata(BusinessObjectTypeEnum.USER, "email", email);
			if(userUuids == null || userUuids.size() < 1) {
				throw new ServiceException("User with email:"+email+" not found.",ErrorCodeEnum.USER_NOT_FOUND);
			}
			
			User user = (User) dao.getBusinessObjectByUuid(userUuids.get(0));
			
			if(!EncryptionUtils.checkPassword(password, user.getPassword())){
				throw new ServiceException("Invalid credentials.", ErrorCodeEnum.INVALID_CREDENTIALS);
			}
			
			UserVo userVo = (UserVo)user.createDTO();
			userVo.labs = new ArrayList<LabVo>();
			
			//Get Labs of user
			List<Relationship> relations =  dao.findRelationshipByChildUuidAndType(user.getUuid(),RelationshipTypeEnum.LAB_USER.toString());
			for (Relationship relationship : relations) {
				Lab lab = (Lab)dao.getBusinessObjectByUuid(  relationship.getParentUuid() );
				userVo.labs.add((LabVo)lab.createDTO());
			}
			
			return userVo;
		}
		catch(ServiceException exc){
			throw exc;
		}
		catch(Exception exc){
			throw new ServiceException(exc.getMessage(), exc, ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
	}
	
	public UserVo createUser(UserVo userVo) throws ServiceException{
		
		try{
			ServiceUtils.verifyNotBlank(userVo.firstName, "FirstName");
			ServiceUtils.verifyNotBlank(userVo.email, "Email");
			ServiceUtils.verifyNotBlank(userVo.password, "Password");
			
			//check if a user with this email already exists
			List<String> userUuids = dao.findUuidsByMetadata(BusinessObjectTypeEnum.USER, "email", userVo.email);
			
			//if a user with this email already exists, then dont create a new one
			if(userUuids.size() > 0) {
				throw new ServiceException("User email:"+userVo.email+" already exists.",ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
			}

			
			
			User user = (User)dao.emptyBusinessObject(BusinessObjectTypeEnum.USER, userVo.firstName+" "+userVo.lastName, Constants.SYSTEM_USER);
			user.setFirstName(userVo.firstName);
			user.setLastName(userVo.lastName);
			user.setEmail(userVo.email);
			user.setPassword(EncryptionUtils.encryptPassword( userVo.password ));
			
			//Saving User
			user = (User)dao.saveBusinessObject(user);
			
			
			return (UserVo)user.createDTO();
		}	
		catch(ServiceException exc){
			throw exc;
		}
		catch(Exception exc){
			throw new ServiceException(exc.getMessage(), exc, ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void deleteUserByUuid(String uuid) throws ServiceException{
		try{
			dao.deleteBusinessObjectByUuid(uuid);}
		catch(ServiceException exc){
			throw exc;
		}
		catch (Exception e) {
			throw new ServiceException(e.getMessage(), e, ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
	}
	
	public UserVo registerAndSendEmail(UserVo userVo)throws ServiceException {
		UserVo vo = createUser(userVo);
		return vo;
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}

	public LabVo createLab(String name, String description, String location) throws ServiceException {
		ServiceUtils.verifyNotBlank("name", "name");
		
		Lab lab = (Lab)dao.emptyBusinessObject(BusinessObjectTypeEnum.LAB, name, Constants.SYSTEM_USER);
		lab.setDescription(description);
		lab.setLocation(location);
		
		lab = (Lab)dao.saveBusinessObject(lab);
		LabVo labVo = (LabVo)lab.createDTO();
		
		return labVo;
	}

	public List<LabVo> getAllLabs() throws ServiceException{
		// TODO Auto-generated method stub
		List<LabVo> listToReturn = new ArrayList<LabVo>();
		List<BusinessObject> businessObjects = dao.findBusinessObjectsByType(BusinessObjectTypeEnum.LAB);
		
		for (BusinessObject businessObject : businessObjects) {
			Lab lab = (Lab)businessObject;
			listToReturn.add((LabVo)lab.createDTO());
		}
		
		return listToReturn;
	}

	public void assignLabToUser(String labuuid, String useruuid) throws ServiceException {
		
		Lab lab = (Lab) dao.getBusinessObjectByUuid(labuuid);
		if(lab == null)
			throw new ServiceException("Invalid labuuid.", ErrorCodeEnum.INVALID_BUSINESS_OBJECT_ID);
		
		User user = (User)dao.getBusinessObjectByUuid(useruuid);
		if(user == null)
			throw new ServiceException("Invalid useruuid.", ErrorCodeEnum.INVALID_BUSINESS_OBJECT_ID);
		
		dao.saveRelationship(labuuid, useruuid, RelationshipTypeEnum.LAB_USER.toString(), Constants.SYSTEM_USER);
		
	}
		
}

