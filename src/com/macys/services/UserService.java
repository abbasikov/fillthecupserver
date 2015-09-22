package com.macys.services;

import java.util.List;

import com.macys.dao.DAO;
import com.macys.domain.User;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.exceptions.ErrorCodeEnum;
import com.macys.exceptions.ServiceException;
import com.macys.utils.Constants;
import com.macys.utils.EncryptionUtils;
import com.macys.utils.ServiceUtils;
import com.macys.valuesobjects.UserVo;

public class UserService {
	
	private DAO dao;
	
	public UserVo fetchUser(String email,String password) throws ServiceException{
		try{
			User employee =  (User)dao.findBusinessObjectByMetadata(BusinessObjectTypeEnum.USER, "email", email);
			
			if(employee == null)
				throw new ServiceException("User not found by email:"+email,ErrorCodeEnum.USER_NOT_FOUND);
			
			if(EncryptionUtils.checkPassword(password, employee.getPassword())){
				return (UserVo)employee.createDTO();
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
			
			List<String> employeeUuids = dao.findUuidsByMetadata(BusinessObjectTypeEnum.USER, "email", email);
			if(employeeUuids == null || employeeUuids.size() < 1) {
				throw new ServiceException("User with email:"+email+" not found.",ErrorCodeEnum.USER_NOT_FOUND);
			}
			
			User employee = (User) dao.getBusinessObjectByUuid(employeeUuids.get(0));
			
			if(!EncryptionUtils.checkPassword(password, employee.getPassword())){
				throw new ServiceException("Invalid credentials.", ErrorCodeEnum.INVALID_CREDENTIALS);
			}
			
			UserVo employeeVo = (UserVo)employee.createDTO();
			
			return employeeVo;
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
			
			//if a employee with this email already exists, then dont create a new one
			if(userUuids.size() > 0) {
				throw new ServiceException("User email:"+userVo.email+" already exists.",ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
			}

			
			
			User user = (User)dao.emptyBusinessObject(BusinessObjectTypeEnum.USER, userVo.firstName+" "+userVo.lastName, Constants.SYSTEM_USER);
			user.setFirstName(userVo.firstName);
			user.setLastName(userVo.lastName);
			user.setEmail(userVo.email);
			user.setPassword(EncryptionUtils.encryptPassword( userVo.password ));
			
			//Saving Employee
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
	
	public void deleteEmployeeByUuid(String uuid) throws ServiceException{
		try{
			dao.deleteBusinessObjectByUuid(uuid);}
		catch(ServiceException exc){
			throw exc;
		}
		catch (Exception e) {
			throw new ServiceException(e.getMessage(), e, ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
	}
	
	public UserVo registerAndSendEmail(UserVo employeeVo)throws ServiceException {
		UserVo vo = createUser(employeeVo);
		
		//Send Email
//		boolean sendEmail = Boolean.parseBoolean(PropertiesUtils.getInstance().getProperty(Constants.KEY_REGISTRATION_ALERT));
//		if(sendEmail){
//			EmailUtils.sendEmail(employeeVo.firstName+" "+employeeVo.lastName, employeeVo.email, employeeVo.password, EmailTemplateEnum.NEW_REGISTRATION);	
//		}
//		
		return vo;
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
		
}

