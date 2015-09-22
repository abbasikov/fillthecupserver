package com.macys.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;

import com.macys.dao.database.DBObject;
import com.macys.dao.repository.DBObjectMetadataRepository;
import com.macys.dao.repository.DBObjectRelationshipRepository;
import com.macys.dao.repository.DBObjectRepository;
import com.macys.dao.repository.JdbcTemplateRepostiory;
import com.macys.domain.business.BusinessObject;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.exceptions.ErrorCodeEnum;
import com.macys.exceptions.ServiceException;

public class BaseDAOImpl {
	
	@Resource
	protected DBObjectRepository dbObjectRepository;
	
	@Resource
	protected DBObjectMetadataRepository dbObjectMetadataRepository;
	
	@Resource
	protected DBObjectRelationshipRepository dbObjectRelationshipRepository;
	
	
	protected JdbcTemplateRepostiory jdbcTemplateRepostiory;
	
	@Transactional
	public BusinessObject constructBusinessObjectUsingDBObject(DBObject dbObject) throws ServiceException {
		
		BusinessObjectTypeEnum type = BusinessObjectTypeEnum.enumFromType(dbObject.getType());
		
		if(type == null) {
			throw new ServiceException("Invalid businessObjectType:" + dbObject.getType(),ErrorCodeEnum.INVALID_BUSINESS_OBJECT_TYPE);
		}
		
		BusinessObject businessObject = ReflectionUtils.constructBusinessObject(type, dbObject);
		
		ReflectionUtils.setMetadataToBusinessObject(dbObject.getMetadatas(), businessObject, true);
		
		return businessObject;
		
	}
	
	@Transactional
	public BusinessObject getBusinessObjectByUuid(String uuid) throws ServiceException  {
		
		DBObject dbObject = dbObjectRepository.findByUuid(uuid);
		
		if(dbObject == null) {
			return null;
		}
		
		return constructBusinessObjectUsingDBObject(dbObject);
	}
	
	@Transactional
	public void deleteBusinessObjectByUuid(String uuid) throws ServiceException {
		try{
			dbObjectRepository.delete(uuid);
			dbObjectMetadataRepository.deleteMetadatasByUuid(uuid);
		}
		catch(EmptyResultDataAccessException exc){
			//Occurs when no record found. No need to re-throw
		}
	}
	
	@Transactional
	public List<BusinessObject> getBusinessObjectByName(BusinessObjectTypeEnum type,String nameValue) throws ServiceException{
		
		List<BusinessObject> list = new ArrayList<BusinessObject>();
		
		List<DBObject> dbObjectList = dbObjectRepository.findByTypeAndName(type.getType(),nameValue);
		
		if(dbObjectList != null && dbObjectList.size()>0){
			for(DBObject dbObj:dbObjectList){
				list.add(constructBusinessObjectUsingDBObject(dbObj));
			}			
		}
		
		return list;
	}

	public void setDbObjectRepository(DBObjectRepository dbObjectRepository) {
		this.dbObjectRepository = dbObjectRepository;
	}

	public void setDbObjectMetadataRepository(
			DBObjectMetadataRepository dbObjectMetadataRepository) {
		this.dbObjectMetadataRepository = dbObjectMetadataRepository;
	}

	public void setDbObjectRelationshipRepository(
			DBObjectRelationshipRepository dbObjectRelationshipRepository) {
		this.dbObjectRelationshipRepository = dbObjectRelationshipRepository;
	}

	public void setJdbcTemplateRepostiory(
			JdbcTemplateRepostiory jdbcTemplateRepostiory) {
		this.jdbcTemplateRepostiory = jdbcTemplateRepostiory;
	}

	
	
	
	

}
