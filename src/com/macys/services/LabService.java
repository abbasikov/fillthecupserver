package com.macys.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
import com.macys.utils.JsonUtils;
import com.macys.utils.ServiceUtils;
import com.macys.valuesobjects.IPMTreeVo;
import com.macys.valuesobjects.LabVo;
import com.macys.valuesobjects.MatrixVo;
import com.macys.valuesobjects.ReleaseCupVo;
import com.macys.valuesobjects.ReleaseVo;
import com.macys.valuesobjects.SystemComponentVo;
import com.macys.valuesobjects.UserVo;

public class LabService extends BaseService{

	
	public LabVo createLab(String labName, String managerName,String pdmName, String createdBy)throws ServiceException {
		ServiceUtils.verifyNotBlank(labName, 		"labName");
		ServiceUtils.verifyNotBlank(managerName, 	"managerName");
		
		Lab lab = (Lab)dao.emptyBusinessObject(BusinessObjectTypeEnum.LAB, labName, Constants.SYSTEM_USER);
		lab.setManagerName(managerName);
		lab.setPdmName(pdmName);
		lab.setDescription("");
		lab.setLocation("");
		lab.setLastClicked("");
		lab.setCreatedBy(createdBy);
		lab.setIsActivated("true");
		
		lab = (Lab)dao.saveBusinessObject(lab);
		
		return (LabVo)lab.createDTO();
		
	}
	
	
	
	public LabVo getLab(String labUuid, boolean throwExpNotFound)throws ServiceException {
		
		ServiceUtils.verifyNotBlank(labUuid, 			"labUuid");
		
		Lab lab = (Lab)dao.getBusinessObjectByUuid(labUuid);
		
		if(lab == null){
			if(throwExpNotFound){
				throw new ServiceException("Invalid Lab Uuid", ErrorCodeEnum.LAB_NOT_FOUND);
			}
			return null;
		}
		
		return (LabVo)lab.createDTO();
		
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
					if(user != null){
						labVo.users.add((UserVo)user.createDTO());
					}
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
//		List<Relationship> relations = dao.findChildren(labUuid);
//		if(relations != null){
//			for (Relationship relationship : relations) {
//				try{
//					dao.deleteBusinessObjectByUuid(relationship.getChildUuid());
//					dao.deleteRelationShip(relationship.getParentUuid(), relationship.getChildUuid(), RelationshipTypeEnum.enumFromName( relationship.getType()));
//				}
//				catch(Exception exc){
//					
//				}
//			}
//		}
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
	
	public List<SystemComponentVo> getSystemComponentsListByDelimiter(String sysComponents, String delimeter) {
		List<SystemComponentVo> list = new ArrayList<SystemComponentVo>();
		List<SystemComponentVo> sortedList = new ArrayList<SystemComponentVo>();
		try{
			String[] uuids = sysComponents.split(delimeter);
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
	

	public void deleteSystemComponent(String uuid) throws ServiceException{
		dao.deleteBusinessObjectByUuid(uuid);
		dao.deleteRelationShipByChildUuid(uuid, RelationshipTypeEnum.LAB_SYSTEMCOMPONENT);
	}
	
	public void deleteBusinessObject(String uuid) throws ServiceException{
		dao.deleteBusinessObjectByUuid(uuid);
	}
	
	public ReleaseVo createRelease(String name, String branchCutDate,String branchFreezeDate,String hardLockDate,String branchProductionDate, String mcomDate, String bcomDate,String isActivated) throws ServiceException {
		ServiceUtils.verifyNotBlank(name, 			"name");
		ServiceUtils.verifyNotBlank(branchCutDate, 	"branchCutDate");
		ServiceUtils.verifyNotBlank(hardLockDate, 	"hardLockDate");
		ServiceUtils.verifyNotBlank(mcomDate, 		"mcomDate");
		ServiceUtils.verifyNotBlank(bcomDate, 		"bcomDate");
		
		Release release = (Release)dao.emptyBusinessObject(BusinessObjectTypeEnum.RELEASE, name, Constants.SYSTEM_USER);
		release.setBranchCutDate(branchCutDate);
		release.setBranchFreezeDate(branchFreezeDate);
		release.setBranchHardLockDate(hardLockDate);
		release.setBranchProductionDate(branchProductionDate);
		release.setMcomDate(mcomDate);
		release.setBcomDate(bcomDate);
		release.setIsActivated(isActivated);
		release.setCreatedBy("Super Admin");
		
		
		release = (Release)dao.saveBusinessObject(release);
		
		return (ReleaseVo)release.createDTO();
		
	}
	
	public ReleaseVo getRelease(String releaseUuid, boolean throwExpNotFound) throws ServiceException {
		ServiceUtils.verifyNotBlank(releaseUuid, 			"releaseUuid");
		
		Release release = (Release)dao.getBusinessObjectByUuid(releaseUuid);
		
		if(release == null){
			if(throwExpNotFound){
				throw new ServiceException("Invalid Release Uuid", ErrorCodeEnum.RELEASE_NOT_FOUND);
			}
			return null;
		}
		
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
		List<SystemComponentVo> systemComponents = getSystemComponentsListByDelimiter(sysComponents, ";");
		MatrixVo matrix 	= new MatrixVo();
		matrix.updateColums(systemComponents);
		
		IPMTreeVo tree = new IPMTreeVo();
		
		
		ReleaseCup releaseCup = (ReleaseCup)dao.emptyBusinessObject(BusinessObjectTypeEnum.RELEASECUP, release.getName(), Constants.SYSTEM_USER);
		releaseCup.setAvailableDevDays(availableDevDays);
		releaseCup.setDevDays(devDays);
		releaseCup.setRegressionDays(regressionDays);
		releaseCup.setReleaseUuid(releaseUuid);
		releaseCup.setLabUuid(labUuid);
		releaseCup.setSysComponents(sysComponents);
		releaseCup.setMatrixJson(JsonUtils.toJson(matrix));
		releaseCup.setIpmTree(JsonUtils.toJson(tree));
		releaseCup.setLastClicked("");
		
		//Saving the cup
		releaseCup = (ReleaseCup)dao.saveBusinessObject(releaseCup);
		
		ReleaseCupVo releaseCupVo 	= (ReleaseCupVo)releaseCup.createDTO();
		releaseCupVo.sysComponents	= systemComponents;
		releaseCupVo.release		= (ReleaseVo)release.createDTO();
		releaseCupVo.lab			= (LabVo)lab.createDTO();
		releaseCupVo.matrix			= releaseCup.getMatrixJson();
		releaseCupVo.ipmTree		= releaseCup.getIpmTree();
		
		return releaseCupVo;
	}

	public ReleaseCupVo getReleaseCupByUuid(String releaseCupUuid) throws ServiceException {
		ServiceUtils.verifyNotBlank(releaseCupUuid, 		"releaseCupUuid");
		
		ReleaseCup releaseCup = (ReleaseCup) dao.getBusinessObjectByUuid(releaseCupUuid);
		if(releaseCup == null){
			throw new ServiceException("Invalid Release Cup Uuid", ErrorCodeEnum.RELEASE_CUP_NOT_FOUND);
		}
		
		ReleaseCupVo releaseCupVo 	= (ReleaseCupVo)releaseCup.createDTO();
		releaseCupVo.sysComponents	=  getSystemComponentsListByDelimiter(releaseCup.getSysComponents() , ";");
		releaseCupVo.release		= getRelease(releaseCup.getReleaseUuid(), false);
		releaseCupVo.lab			= getLab(releaseCup.getLabUuid(), false);
		releaseCupVo.matrix			= releaseCup.getMatrixJson();
		releaseCupVo.ipmTree		= releaseCup.getIpmTree();
		
		return releaseCupVo;
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
				releaseCupVo.sysComponents	= getSystemComponentsListByDelimiter(releaseCup.getSysComponents(), ";");
				releaseCupVo.matrix			= releaseCup.getMatrixJson();
				releaseCupVo.ipmTree		= releaseCup.getIpmTree();
				
				listToReturn.add(releaseCupVo);
			}
		}
	
		return listToReturn;
	}
	
	public List<LabVo> getAllLabsByUserUuid(String userUuid) throws ServiceException{
		
		List<LabVo> labList = new ArrayList<LabVo>();
		
		//Get Labs of user
		List<Relationship> relationsLabUsers =  dao.findRelationshipByChildUuidAndType(userUuid,RelationshipTypeEnum.LAB_USER.toString());
		if(relationsLabUsers != null){
			for (Relationship relationship : relationsLabUsers) {
				Lab lab 	= (Lab)dao.getBusinessObjectByUuid(  relationship.getParentUuid() );
				if(lab != null && lab.getIsActivated().toLowerCase().equals("true")){
					LabVo labVo = (LabVo)lab.createDTO();
					labList.add(labVo);
				}
				
			}
		}
		
		return labList;
	}

	public void assignLabsToUser(String userUuid, String labUuids, String delimeter, String createdBy) throws ServiceException {
		
		try{
			//First Delete the Previous Relationships
			List<Relationship> previousRelations =  dao.findRelationshipByChildUuidAndType(userUuid, RelationshipTypeEnum.LAB_USER.toString());
			if(previousRelations != null){
				for(Relationship previousRelation : previousRelations){
					dao.deleteRelationShip(previousRelation.getParentUuid(), previousRelation.getChildUuid(),RelationshipTypeEnum.LAB_USER);
				}
			}
			
			
			if(!StringUtils.isBlank(labUuids)){
				String[] labsUuidsArray = labUuids.split(delimeter);
				if(labsUuidsArray.length > 0){
					for(String labUuid : labsUuidsArray){
						dao.saveRelationship(labUuid, userUuid, RelationshipTypeEnum.LAB_USER.toString(), createdBy);
					}
				}
			}
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
		}
	}
	
}
