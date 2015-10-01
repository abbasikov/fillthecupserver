package com.macys.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.macys.domain.Lab;
import com.macys.domain.User;
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

/**
 * @author Umair ABBASI
 *
 */
public class UserService extends BaseService{
	
	protected final Logger log = Logger.getLogger(UserService.class);
	
	private LabService labService;
	
	public UserVo login(String userName, String password) throws ServiceException{
		try{
			ServiceUtils.verifyNotBlank(userName, "UserName");
			ServiceUtils.verifyNotBlank(password, "Password");
			
			List<String> userUuids = dao.findUuidsByMetadata(BusinessObjectTypeEnum.USER, "userName", userName);
			if(userUuids == null || userUuids.size() < 1) {
				throw new ServiceException("User with userName:"+userName+" not found.",ErrorCodeEnum.USER_NOT_FOUND);
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
	
	public UserVo createUser(String userName, String password, String isSuperAdmin) throws ServiceException{
		ServiceUtils.verifyNotBlank(userName, 		"userName");
		ServiceUtils.verifyNotBlank(password, 		"password");
		
		//check if a user with this username already exists
		List<String> userUuids = dao.findUuidsByMetadata(BusinessObjectTypeEnum.USER, "userName", userName);
		
		//if a user with this username already exists, then dont create a new one
		if(userUuids.size() > 0) {
			throw new ServiceException("Username:"+userName+" already exists.",ErrorCodeEnum.USERNAME_ALREADY_EXISTS);
		}
		
		User user = (User)dao.emptyBusinessObject(BusinessObjectTypeEnum.USER, userName, Constants.SYSTEM_USER);
		user.setUserName(userName);
		user.setPassword(EncryptionUtils.encryptPassword( password ));
		user.setIsSuperAdmin(isSuperAdmin);
		
		user = (User)dao.saveBusinessObject(user);
		
		return (UserVo)user.createDTO();
	}

	
	public LabVo createLabAndUser(String labName, String managerName, String pdmName,String userName,String password, String isSuperAdmin) throws ServiceException {
		
		//First Create The User
		UserVo userVo = createUser(userName, password, isSuperAdmin);
		
		//Second Create The Lab
		LabVo labVo   = labService.createLab(labName, managerName, pdmName);
		
		//Save the relationship
		dao.saveRelationship(labVo.uuid, userVo.uuid, RelationshipTypeEnum.LAB_USER.toString(), Constants.SYSTEM_USER);
		
		labVo.users = new ArrayList<UserVo>();
		labVo.users.add(userVo);
		
		return labVo;
	}
		
	public void setLabService(LabService labService) {
		this.labService = labService;
	}
	
			
}

