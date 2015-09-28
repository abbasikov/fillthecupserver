package com.macys.dao;

import java.util.List;

import com.macys.domain.business.BusinessObject;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.Relationship;
import com.macys.domain.business.common.RelationshipTypeEnum;
import com.macys.exceptions.ServiceException;

public interface DAO {
	
	public BusinessObject emptyBusinessObject(BusinessObjectTypeEnum type, String name, String createdBy) throws ServiceException;

	public BusinessObject getBusinessObjectByUuid(String uuid) throws ServiceException;
	
	public List<? extends BusinessObject> getBusinessObjectByUuids(String[] uuids);

	public BusinessObject saveBusinessObject(BusinessObject businessObject) throws ServiceException;
	
	public List<BusinessObject> findByTypeAndNameLite(BusinessObjectTypeEnum type, String name) throws ServiceException;
	
	public BusinessObject findByTypeAndNameLiteSingle(BusinessObjectTypeEnum type, String name) throws ServiceException;
	
	public Relationship saveRelationship(String parentUuid, String childUuid, String type, String createdBy);
	
	public List<String> findUuidsByMetadata(BusinessObjectTypeEnum type, String name, String value);
	
	public List<Relationship> findChildren(String parentUuid);
	
	public List<Relationship> findRelationshipByChildUuidAndType(String childUuid, String type);

	public BusinessObject findBusinessObjectByMetadata(BusinessObjectTypeEnum type,String metadataName, String metadataValue) throws ServiceException;
	
	public List<BusinessObject> findBusinessObjectsByMetadata(BusinessObjectTypeEnum type,String metadataName, String metadataValue) throws ServiceException;

	public List<Relationship> findChildrenWithRelationshipType(String parentUuid,RelationshipTypeEnum relationshipTypeEnum);
	
	public List<BusinessObject> findChildBusinessObjects(String parentUuid, BusinessObjectTypeEnum[] objectTypeEnums)throws ServiceException;

	public List<BusinessObject> getBusinessObjectByName(BusinessObjectTypeEnum type, String nameValue) throws ServiceException;

	public void deleteBusinessObjectByUuid(String uuid) throws ServiceException;
	
	public BusinessObject updatedBusinessObjectByMetadata(String uuid, String[] names, String[] values) throws ServiceException;
	
	public List<BusinessObject> findBusinessObjectsByType(BusinessObjectTypeEnum type) throws ServiceException;
	
	public void deleteRelationShip(String pUuid, String cUuid, RelationshipTypeEnum relationshipType) throws ServiceException;
	
	public void deleteRelationShipByChildUuid(String cUuid, RelationshipTypeEnum relationshipType) throws ServiceException;
}
