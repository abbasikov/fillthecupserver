package com.macys.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.macys.dao.database.DBObject;
import com.macys.dao.database.DBObjectMetadata;
import com.macys.dao.database.DBObjectRelationship;
import com.macys.dao.database.pk.DBObjectRelationshipPK;
import com.macys.domain.business.BusinessObject;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.Relationship;
import com.macys.domain.business.common.RelationshipTypeEnum;
import com.macys.exceptions.ErrorCodeEnum;
import com.macys.exceptions.ServiceException;

public class DAOImpl extends BaseDAOImpl implements DAO {
	
	private static Log log = LogFactory.getLog(DAOImpl.class);
	
	@Override
	public BusinessObject emptyBusinessObject(BusinessObjectTypeEnum type, String name, String createdBy) throws ServiceException {
		BusinessObject businessObject = ReflectionUtils.constructBusinessObject(type);
		businessObject.setName(name);
		businessObject.setCreatedBy(createdBy);
		log.info("new BusinessObject created:" + businessObject.getUuid());
		return businessObject;
	}
	
	@Override
	@Transactional
	public BusinessObject getBusinessObjectByUuid(String uuid) throws ServiceException {
		
		return super.getBusinessObjectByUuid(uuid);
	}
	
	@Override
	public void deleteBusinessObjectByUuid(String uuid) throws ServiceException {
		super.deleteBusinessObjectByUuid(uuid);
	}
	
	@Override
	@Transactional
	public void deleteRelationShip(String pUuid, String cUuid, RelationshipTypeEnum relationshipType) throws ServiceException
	{
		DBObjectRelationshipPK pk = new DBObjectRelationshipPK(pUuid, cUuid, relationshipType.getRelationshipName());
		this.dbObjectRelationshipRepository.delete(pk);
	}
	
	@Override
	@Transactional
	public void deleteRelationShipByChildUuid(String cUuid, RelationshipTypeEnum relationshipType) throws ServiceException
	{
		this.dbObjectRelationshipRepository.deleteRelationshipByChildUuid(cUuid, relationshipType.getRelationshipName());
	}
	
	@Override
	@Transactional
	public List<BusinessObject> getBusinessObjectByName(BusinessObjectTypeEnum type,String nameValue) throws ServiceException{
		return super.getBusinessObjectByName(type,nameValue);
	}
	
	
	
	@Override
	public List<? extends BusinessObject> getBusinessObjectByUuids(String[] uuids) {
		return null;
	}

	@Override
	public BusinessObject saveBusinessObject(BusinessObject businessObject) throws ServiceException {

		Assert.notNull(businessObject.getType());
		Assert.notNull(businessObject.getName());
		Assert.notNull(businessObject.getCreatedBy());
		
		DBObject dbObject = businessObject.getDbObject();
		
		boolean newObject = dbObject.getCounter() == null;
		
		List<DBObjectMetadata> metadatas = ReflectionUtils.annotatedMetadata(businessObject);
		
		dbObject = dbObjectRepository.save(dbObject);
		
		if(newObject) {
			dbObjectMetadataRepository.save(metadatas);
			
			DBObject dbObjectFromDB = jdbcTemplateRepostiory.findByUuid(dbObject.getUuid());
			((BusinessObjectImpl)businessObject).setDbObject(dbObjectFromDB);
			
			
		} else {
			
			List<DBObjectMetadata> metadataExisting = dbObjectMetadataRepository.findMetadatasByUuid(dbObject.getUuid());
			
			Set<DBObjectMetadata> metadataExisitngClone = new HashSet<DBObjectMetadata>(metadataExisting);
			
			metadataExisitngClone.removeAll(metadatas);
			
			Set<DBObjectMetadata> mdToDelete = metadataExisitngClone;
			
			if(mdToDelete.size() > 0) {
				dbObjectMetadataRepository.delete(mdToDelete);
			}
			
			Set<DBObjectMetadata> metadataNewOrChanged = new HashSet<DBObjectMetadata>();
			
			for(DBObjectMetadata md:metadatas) {
				if(metadataExisting.contains(md) == false) {
					metadataNewOrChanged.add(md);
				}
			}
			
			if(metadataNewOrChanged.size() >0) {
				dbObjectMetadataRepository.save(metadataNewOrChanged);
			}
			
		}
		
		return businessObject;
	}
	
	@Override
	/**
	 * Does not return metadata with the business object.
	 * @param type
	 * @param name
	 * @return
	 */
	public List<BusinessObject> findByTypeAndNameLite(BusinessObjectTypeEnum type, String name) throws ServiceException {
		
		List<DBObject> dbObjects = this.dbObjectRepository.findByTypeAndName(type.getType(), name);
		
		List<BusinessObject> businessObjects = new ArrayList<BusinessObject>(dbObjects.size());
		
		for(DBObject dbObject:dbObjects) {
			BusinessObjectTypeEnum enumType = BusinessObjectTypeEnum.enumFromType(dbObject.getType());
			if(enumType != null) {
				BusinessObject businessObject = ReflectionUtils.constructBusinessObject(type, dbObject);
				businessObjects.add(businessObject);
			}
		}
		
		return businessObjects;
	}
	
	public BusinessObject findByTypeAndNameLiteSingle(BusinessObjectTypeEnum type, String name) throws ServiceException {
		
		List<BusinessObject> objects = this.findByTypeAndNameLite(type, name);
		
		if(objects != null && objects.size() > 0) {
			return objects.get(0);
		}
		
		return null;
	}

	@Override
	public Relationship saveRelationship(String parentUuid, String childUuid, String type, String createdBy) {
		
		DBObjectRelationship dbRelationship = new DBObjectRelationship(new DBObjectRelationshipPK(parentUuid, childUuid, type), createdBy);
		
		dbRelationship = dbObjectRelationshipRepository.save(dbRelationship);
		
		return new Relationship(dbRelationship);
	}
	
	public List<String> findUuidsByMetadata(BusinessObjectTypeEnum type, String name, String value) {
		return this.jdbcTemplateRepostiory.findUuidsByMetadata(type, name, value);
	}
	
	
	@Override
	public BusinessObject findBusinessObjectByMetadata(BusinessObjectTypeEnum type, String metadataName, String metadataValue) throws ServiceException {
		
		List<String> uuids = this.findUuidsByMetadata(type, metadataName, metadataValue);
		
		if(uuids.size() > 0) {
			String uuid = uuids.get(0);
			BusinessObject businessObject = this.getBusinessObjectByUuid(uuid);
			return businessObject;
		}
		
		
		return null;
		
	}
	
	public List<BusinessObject> findBusinessObjectsByMetadata(BusinessObjectTypeEnum type, String metadataName, String metadataValue) throws ServiceException {
		
		List<String> uuids = this.findUuidsByMetadata(type, metadataName, metadataValue);
		List<BusinessObject> listToReturn = new ArrayList<BusinessObject>();
		
		for(String uuid : uuids){
			listToReturn.add(this.getBusinessObjectByUuid(uuid));
		}
		return listToReturn;
	}
	
	@Override
	public List<BusinessObject> findBusinessObjectsByType(BusinessObjectTypeEnum type) throws ServiceException {
		
		List<BusinessObject> listToReturn = new ArrayList<BusinessObject>();
		List<String> uuids = this.jdbcTemplateRepostiory.findUuidsByType(type);
		
		if(uuids.size() > 0) {
			for(String uuid : uuids){
				BusinessObject businessObject = this.getBusinessObjectByUuid(uuid);
				listToReturn.add(businessObject);
			}
			return listToReturn;
		}
		return null;
	}
	
	public BusinessObject updatedBusinessObjectByMetadata(String uuid, String[] names, String[] values) throws ServiceException{
		
		BusinessObject businessObj =  getBusinessObjectByUuid(uuid);
		
		if(names.length != values.length)
			throw new ServiceException("Metadatas and values length should be equal", ErrorCodeEnum.LENGTH_NOT_EQUAL);
		
		//Return empty list
		if(businessObj == null){
			return null;
		}
		
		for (int i = 0; i < names.length; i++) {
			jdbcTemplateRepostiory.updateMetadataByUuid(uuid, businessObj.getType(), names[i], values[i]);
		}
		
		return getBusinessObjectByUuid(uuid);
		
	}

	@Override
	public List<Relationship> findChildren(String parentUuid) {
		
		List<DBObjectRelationship> children = this.dbObjectRelationshipRepository.findChildren(parentUuid);
		
		List<Relationship> relationships = new ArrayList<Relationship>(children.size());
		
		for(DBObjectRelationship dbRelationship:children) {
			relationships.add(new Relationship(dbRelationship));
		}
		
		return relationships;
	}
	
	@Override
	public List<Relationship> findRelationshipByChildUuidAndType(String childUuid,String type){
		List<DBObjectRelationship> relations = this.dbObjectRelationshipRepository.findRelationshipByChildUuidAndType(childUuid,type);
		
		List<Relationship> relationships = new ArrayList<Relationship>(relations.size());
		
		for(DBObjectRelationship dbRelationship:relations) {
			relationships.add(new Relationship(dbRelationship));
		}
		
		return relationships;
	}
	
	@Override
	public List<Relationship> findChildrenWithRelationshipType(String parentUuid, RelationshipTypeEnum relationshipTypeEnum) {
		
		List<DBObjectRelationship> children = this.dbObjectRelationshipRepository.findChildrenWithRelationshipType(parentUuid, relationshipTypeEnum.getRelationshipName());
		
		List<Relationship> relationships = new ArrayList<Relationship>(children.size());
		
		for(DBObjectRelationship dbRelationship:children) {
			relationships.add(new Relationship(dbRelationship));
		}
		
		return relationships;
	}
	
	@Override
	@Transactional
	public List<BusinessObject> findChildBusinessObjects(String parentUuid, BusinessObjectTypeEnum[] objectTypeEnums) throws ServiceException {
		
		
		List<String> childUuids = this.jdbcTemplateRepostiory.findChildUuids(parentUuid, objectTypeEnums);
		
		if(childUuids.size() == 0) {
			return Collections.emptyList();
		}
		
		List<DBObject> dbobjects = this.dbObjectRepository.findUsingUuids(childUuids);
		
		List<BusinessObject> businessObjects = new ArrayList<BusinessObject>(dbobjects.size());
		
		for(DBObject dbObject:dbobjects) {
			BusinessObject businessObject = constructBusinessObjectUsingDBObject(dbObject);
			businessObjects.add(businessObject);
		}
		
		
		return businessObjects;
		
		
	}
	
	
	
}
