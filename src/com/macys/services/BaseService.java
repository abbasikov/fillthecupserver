package com.macys.services;

import org.apache.commons.lang3.StringUtils;

import com.macys.dao.DAO;
import com.macys.domain.business.BusinessObject;
import com.macys.exceptions.ErrorCodeEnum;
import com.macys.exceptions.ServiceException;
import com.macys.utils.ServiceUtils;

public class BaseService {
	
	protected DAO dao;

	public BusinessObject updateBusinessObject(String uuid, String names, String values,String delimiter) throws ServiceException {
		try{
			ServiceUtils.verifyNotBlank(uuid, 		"uuid");
			ServiceUtils.verifyNotBlank(names, 		"names");
			ServiceUtils.verifyNotBlank(values, 	"values");
			
			if(StringUtils.isBlank(delimiter)){
				delimiter = ";";
			}
			
			String[] namesArray = names.split(delimiter);
			String[] valuesArray= values.split(delimiter);
			
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

	public void setDao(DAO dao) {
		this.dao = dao;
	}
}
