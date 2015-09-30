package com.macys.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.macys.dao.DAO;
import com.macys.domain.Lab;
import com.macys.domain.Release;
import com.macys.domain.ReleaseCup;
import com.macys.domain.SystemComponent;
import com.macys.domain.User;
import com.macys.domain.business.BusinessObject;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.Relationship;
import com.macys.domain.business.common.RelationshipTypeEnum;
import com.macys.exceptions.ErrorCodeEnum;
import com.macys.exceptions.ServiceException;
import com.macys.utils.Constants;
import com.macys.utils.EncryptionUtils;
import com.macys.utils.JsonUtils;
import com.macys.utils.ServiceUtils;
import com.macys.valuesobjects.LabVo;
import com.macys.valuesobjects.MatrixVo;
import com.macys.valuesobjects.ReleaseCupVo;
import com.macys.valuesobjects.ReleaseVo;
import com.macys.valuesobjects.SystemComponentVo;
import com.macys.valuesobjects.UserVo;

/**
 * @author Umair ABBASI
 *
 */
public class UserService {
	
	protected final Logger log = Logger.getLogger(UserService.class);
	
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
			
			//Get All Release Cups for Each lab of the user.
			for (LabVo labvo : userVo.labs) {
				labvo.releaseCups = getAllReleaseCupsByLabUuid(labvo.uuid);
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

	
	public LabVo createLabAndUser(String labName, String managerName, String pdmName,String userName,String password, String isSuperAdmin) throws ServiceException {
		
		//First Create The User
		UserVo userVo = createUser(userName, password, isSuperAdmin);
		
		//Second Create The Lab
		LabVo labVo   = createLab(labName, managerName, pdmName);
		
		//Save the relationship
		dao.saveRelationship(labVo.uuid, userVo.uuid, RelationshipTypeEnum.LAB_USER.toString(), Constants.SYSTEM_USER);
		
		labVo.users = new ArrayList<UserVo>();
		labVo.users.add(userVo);
		
		return labVo;
	}

	public List<LabVo> getAllLabs() throws ServiceException{
		// TODO Auto-generated method stub
		List<LabVo> listToReturn = new ArrayList<LabVo>();
		List<BusinessObject> businessObjects = dao.findBusinessObjectsByType(BusinessObjectTypeEnum.LAB);
		
		if(businessObjects != null){
			for (BusinessObject businessObject : businessObjects) {
				Lab lab = (Lab)businessObject;
				LabVo labVo = (LabVo)lab.createDTO();
				
				//Get Users of lab
				labVo.users = new ArrayList<UserVo>();
				List<Relationship> relations =  dao.findChildren(lab.getUuid());
				for (Relationship relationship : relations) {
					String userUuid =  relationship.getChildUuid();
					User user = (User)dao.getBusinessObjectByUuid(userUuid);
					labVo.users.add((UserVo)user.createDTO());
					labVo.releaseCups = getAllReleaseCupsByLabUuid(lab.getUuid());
				}
				
				listToReturn.add(labVo);
			}
		}
		
		return listToReturn;
	}
	
	public void deleteLab(String labUuid)throws ServiceException
	{	
		dao.deleteBusinessObjectByUuid(labUuid);
		List<Relationship> relations = dao.findChildren(labUuid);
		if(relations != null){
			for (Relationship relationship : relations) {
				try{
					dao.deleteRelationShip(relationship.getParentUuid(), relationship.getChildUuid(), RelationshipTypeEnum.enumFromName( relationship.getType()));
					dao.deleteBusinessObjectByUuid(relationship.getChildUuid());
				}
				catch(Exception exc){
					
				}
			}
		}
	}
	
	public BusinessObject updateBusinessObject(String uuid, String names, String values) throws ServiceException {
		try{
			String[] namesArray = names.split(",");
			String[] valuesArray= values.split(",");
			
			BusinessObject businessObject = dao.updatedBusinessObjectByMetadata(uuid, namesArray, valuesArray);
			if(businessObject == null)
				throw new ServiceException("Business object not found", ErrorCodeEnum.BUSINESS_OBJECT_NOT_FOUND);
			
			return businessObject;
		
		}
		catch(ServiceException exc){
			throw exc;
		}
		catch (Exception e) {
			throw new ServiceException(e.getMessage(), e, ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
	}
	
	public SystemComponentVo createSystemComponent(String name) throws ServiceException {
		ServiceUtils.verifyNotBlank(name, 		"name");
		SystemComponent systemComponent = (SystemComponent)dao.emptyBusinessObject(BusinessObjectTypeEnum.SYSTEM_COMPONENT, name,Constants.SYSTEM_USER);
		systemComponent.setDescription("");
		systemComponent = (SystemComponent) dao.saveBusinessObject(systemComponent);
		return (SystemComponentVo)systemComponent.createDTO();
	}
	
	public List<SystemComponentVo> getAllSystemComponents()throws ServiceException {
		List<SystemComponentVo> listToReturn = new ArrayList<SystemComponentVo>();
		List<BusinessObject> components = dao.findBusinessObjectsByType(BusinessObjectTypeEnum.SYSTEM_COMPONENT);
		if(components!=null){
			for (BusinessObject businessObject : components) {
				SystemComponent sysComp = (SystemComponent)businessObject;
				listToReturn.add((SystemComponentVo)sysComp.createDTO());
			}
		}
		return listToReturn;
	}
	
	public void deleteSystemComponent(String uuid) throws ServiceException{
		dao.deleteBusinessObjectByUuid(uuid);
		dao.deleteRelationShipByChildUuid(uuid, RelationshipTypeEnum.LAB_SYSTEMCOMPONENT);
	}
	
	public void deleteBusinessObject(String uuid) throws ServiceException{
		dao.deleteBusinessObjectByUuid(uuid);
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}

	public ReleaseVo createRelease(String name, String branchCutDate,String hardLockDate, String mcomDate, String bcomDate) throws ServiceException {
		ServiceUtils.verifyNotBlank(name, 			"name");
		ServiceUtils.verifyNotBlank(branchCutDate, 	"branchCutDate");
		ServiceUtils.verifyNotBlank(hardLockDate, 	"hardLockDate");
		ServiceUtils.verifyNotBlank(mcomDate, 		"mcomDate");
		ServiceUtils.verifyNotBlank(bcomDate, 		"bcomDate");
		
		Release release = (Release)dao.emptyBusinessObject(BusinessObjectTypeEnum.RELEASE, name, Constants.SYSTEM_USER);
		release.setBranchCutDate(branchCutDate);
		release.setBranchHardLockDate(hardLockDate);
		release.setMcomDate(mcomDate);
		release.setBcomDate(bcomDate);
		
		release = (Release)dao.saveBusinessObject(release);
		
		return (ReleaseVo)release.createDTO();
		
	}

	public List<ReleaseVo> getAllReleases() throws ServiceException {
		// TODO Auto-generated method stub
		List<ReleaseVo> listToReturn = new ArrayList<ReleaseVo>();
		List<BusinessObject> businessObjects = dao.findBusinessObjectsByType(BusinessObjectTypeEnum.RELEASE);
				
		if(businessObjects != null){
			for (BusinessObject businessObject : businessObjects) {
				Release release 	= (Release)businessObject;
				ReleaseVo releaseVo = (ReleaseVo)release.createDTO();
				listToReturn.add(releaseVo);
			}
		}
		return listToReturn;
	}

	public ReleaseCupVo createReleaseCup(String releaseUuid, String labUuid, String availableDevDays, String devDays, String regressionDays,String sysComponents) throws ServiceException {
		
		ServiceUtils.verifyNotBlank(releaseUuid, 		"releaseUuid");
		ServiceUtils.verifyNotBlank(labUuid, 			"labUuid");
		ServiceUtils.verifyNotBlank(availableDevDays, 	"availableDevDays");
		ServiceUtils.verifyNotBlank(devDays, 			"devDays");
		ServiceUtils.verifyNotBlank(regressionDays, 	"regressionDays");
		ServiceUtils.verifyNotBlank(sysComponents, 		"sysComponents");
		
		Release release = (Release)dao.getBusinessObjectByUuid(releaseUuid);
		if(release == null){
			throw new ServiceException("Invalid Release Uuid", ErrorCodeEnum.RELEASE_NOT_FOUND);
		}
		
		Lab lab = (Lab)dao.getBusinessObjectByUuid(labUuid);
		if(lab == null){
			throw new ServiceException("Invalid Lab Uuid", ErrorCodeEnum.LAB_NOT_FOUND);
		}
		
		//Now make the json
		List<SystemComponentVo> systemComponents = getSystemComponentsList(sysComponents);
		MatrixVo matrix 	= new MatrixVo();
		matrix.columns		= systemComponents;
		String matrixJson 	= JsonUtils.toJson(matrix);
		
		ReleaseCup releaseCup = (ReleaseCup)dao.emptyBusinessObject(BusinessObjectTypeEnum.RELEASECUP, release.getName(), Constants.SYSTEM_USER);
		releaseCup.setAvailableDevDays(availableDevDays);
		releaseCup.setDevDays(devDays);
		releaseCup.setRegressionDays(regressionDays);
		releaseCup.setReleaseUuid(releaseUuid);
		releaseCup.setLabUuid(labUuid);
		releaseCup.setSysComponents(sysComponents);
		releaseCup.setMatrixJson(matrixJson);
		
		//Saving the cup
		releaseCup = (ReleaseCup)dao.saveBusinessObject(releaseCup);
		
		ReleaseCupVo releaseCupVo 	= (ReleaseCupVo)releaseCup.createDTO();
		releaseCupVo.sysComponents	= systemComponents;
		releaseCupVo.release		= (ReleaseVo)release.createDTO();
		releaseCupVo.lab			= (LabVo)lab.createDTO();
		releaseCupVo.matrix			= JsonUtils.fromJson(releaseCup.getMatrixJson(), MatrixVo.class);
		
		return releaseCupVo;
	}
	
	private List<SystemComponentVo> getSystemComponentsList(String sysComponents) {
		List<SystemComponentVo> list = new ArrayList<SystemComponentVo>();
		List<SystemComponentVo> sortedList = new ArrayList<SystemComponentVo>();
		try{
			String[] uuids = sysComponents.split(";");
			for (String uuid : uuids) {
				SystemComponent sysComp = (SystemComponent)dao.getBusinessObjectByUuid(uuid);
				list.add((SystemComponentVo)sysComp.createDTO());
			}
			
			for (int i=0;i<list.size();i++) {
				if(list.get(i).name.equals("MVPs")){
					sortedList.add(list.remove(i));
					break;
				}
			}
			
			sortedList.addAll(list);
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
		}
		
		return sortedList;
	}

	public List<ReleaseCupVo> getAllReleaseCupsByLabUuid(String labUuid) throws ServiceException{
		
		ServiceUtils.verifyNotBlank(labUuid, 		"labUuid");
		
		Lab lab = (Lab)dao.getBusinessObjectByUuid(labUuid);
		if(lab == null){
			throw new ServiceException("Invalid Lab Uuid", ErrorCodeEnum.LAB_NOT_FOUND);
		}
		List<ReleaseCupVo> listToReturn = new ArrayList<ReleaseCupVo>();
		
		List<BusinessObject> businessObjects =  dao.findBusinessObjectsByMetadata(BusinessObjectTypeEnum.RELEASECUP, "labUuid", labUuid);
		if(businessObjects != null){
		
			for (BusinessObject businessObject : businessObjects) {
				ReleaseCup releaseCup 		= (ReleaseCup)businessObject;
				ReleaseCupVo releaseCupVo	= (ReleaseCupVo)releaseCup.createDTO();
				
				Release release				= (Release)dao.getBusinessObjectByUuid(releaseCup.getReleaseUuid());
				ReleaseVo releaseVo			= (ReleaseVo) release.createDTO();
				
				releaseCupVo.release		= releaseVo;
				releaseCupVo.lab			= (LabVo)lab.createDTO();
				releaseCupVo.sysComponents	= getSystemComponentsList(releaseCup.getSysComponents());
				releaseCupVo.matrix			= JsonUtils.fromJson(releaseCup.getMatrixJson(), MatrixVo.class);
				
				listToReturn.add(releaseCupVo);
			}
		}

		return listToReturn;
	}

			
}

