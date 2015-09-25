package com.macys.services;

import java.util.ArrayList;
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

/**
 * @author Umair ABBASI
 *
 */
public class UserService {
	
	private DAO dao;
	
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
	
	private LabVo createLab(String labName, String managerName,String pdmName)throws ServiceException {
		ServiceUtils.verifyNotBlank(labName, 		"labName");
		ServiceUtils.verifyNotBlank(managerName, 	"managerName");
		
		//check if a user with this labname already exists
		List<String> userUuids = dao.findUuidsByMetadata(BusinessObjectTypeEnum.LAB, "labName", labName);
				
		if(userUuids.size() > 0) {
			throw new ServiceException("LabName:"+labName+" already exists.",ErrorCodeEnum.LABNAME_ALREADY_EXISTS);
		}
		
		Lab lab = (Lab)dao.emptyBusinessObject(BusinessObjectTypeEnum.LAB, labName, Constants.SYSTEM_USER);
		lab.setManagerName(managerName);
		lab.setPdmName(pdmName);
		lab.setDescription("");
		lab.setLocation("");
		
		lab = (Lab)dao.saveBusinessObject(lab);
		
		return (LabVo)lab.createDTO();
		
	}
	
	private UserVo createUser(String userName, String password, String isSuperAdmin) throws ServiceException{
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

	
	public UserVo createLabAndUser(String labName, String managerName, String pdmName,String userName,String password, String isSuperAdmin) throws ServiceException {
		
		//First Create The User
		UserVo userVo = createUser(userName, password, isSuperAdmin);
		
		//Second Create The Lab
		LabVo labVo   = createLab(labName, managerName, pdmName);
		
		//Save the relationship
		dao.saveRelationship(labVo.uuid, userVo.uuid, RelationshipTypeEnum.LAB_USER.toString(), Constants.SYSTEM_USER);
		
		userVo.labs = new ArrayList<LabVo>();
		userVo.labs.add(labVo);
		
		return userVo;
	}

	public List<LabVo> getAllLabs() throws ServiceException{
		// TODO Auto-generated method stub
		List<LabVo> listToReturn = new ArrayList<LabVo>();
		List<BusinessObject> businessObjects = dao.findBusinessObjectsByType(BusinessObjectTypeEnum.LAB);
		
		if(businessObjects != null){
			for (BusinessObject businessObject : businessObjects) {
				Lab lab = (Lab)businessObject;
				listToReturn.add((LabVo)lab.createDTO());
			}
		}
		
		return listToReturn;
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
	
		
}

