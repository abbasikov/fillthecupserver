package com.macys.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.macys.exceptions.ErrorCodeEnum;
import com.macys.exceptions.ServiceException;
import com.macys.services.UserService;
import com.macys.utils.Constants;
import com.macys.valuesobjects.LabVo;
import com.macys.valuesobjects.UserVo;
import com.macys.valuesobjects.containers.BaseContainerVo;
import com.macys.valuesobjects.containers.LabContainerVo;
import com.macys.valuesobjects.containers.UserContainerVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "rest/public", description = "Public User Operations")
public class PublicRestController extends BaseRestController{

	protected final Logger log = Logger.getLogger(PublicRestController.class);
	
	private UserService userService;
	
	@Context
	private HttpHeaders httpHeaders;
	
	@Context
	private Request httpRequest;
	
	@Context
	private ContainerRequestContext requestCtx;
	
	@Context
    private transient HttpServletRequest servletRequest;
	
	@POST
	@Path("/labs")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Register Lab",response=UserContainerVo.class)
	public BaseContainerVo createLab(
			@ApiParam(value="Lab Name",		required=true) 	@FormParam("labName") 			String labName, 
			@ApiParam(value="Manager Name",	required=true)	@FormParam("managerName") 		String managerName, 
			@ApiParam(value="PDM Name",		required=true)	@FormParam("pdmName") 			String pdmName, 
			@ApiParam(value="UserName",		required=true)	@FormParam("userName") 			String userName,
			@ApiParam(value="Password",		required=true)	@FormParam("password") 			String password,
			@ApiParam(value="IsSuperAdmin",	required=true)	@FormParam("isSuperAdmin") 		String isSuperAdmin
									) {
		
		UserContainerVo userContainer = new UserContainerVo();
		
		try{
			
			UserVo userVo 				= userService.createLabAndUser(labName,managerName,pdmName,userName,password,isSuperAdmin);
			userContainer.meta.code 	= Constants.SUCCESS;
			userContainer.data 			= userVo;
			return userContainer;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			userContainer.meta.code 	= exc.getErrorCodeEnum().getCode();
			userContainer.meta.error 	= exc.getErrorCodeEnum().getMessage();
			userContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return userContainer;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			userContainer.meta.code 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			userContainer.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			userContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return userContainer;
		}
	}
	
	
	@POST
	@Path("/login")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Login",response=UserContainerVo.class)
	public BaseContainerVo login(
			@ApiParam(value="UserName",required=true) @FormParam("userName") String userName, 
			@ApiParam(value="Password",required=true) @FormParam("password") String password){

		UserContainerVo empContainer = new UserContainerVo();
		
		try{
			UserVo empvo 					= userService.login(userName,password);
			empContainer.meta.code 			= Constants.SUCCESS;
			empContainer.data 				= empvo;
			return empContainer;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			empContainer.meta.code 		= exc.getErrorCodeEnum().getCode();
			empContainer.meta.error 	= exc.getErrorCodeEnum().getMessage();
			empContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return empContainer;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			empContainer.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			empContainer.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			empContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return empContainer;
		}
	}
	
	@GET
	@Path("/labs")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Get All Labs",response=LabContainerVo.class)
	public BaseContainerVo getLabs(){

		LabContainerVo labContainer = new LabContainerVo();
		
		try{
			List<LabVo> labs 				= userService.getAllLabs();
			labContainer.meta.code 			= Constants.SUCCESS;
			labContainer.dataList 			= labs;
			return labContainer;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			labContainer.meta.code 		= exc.getErrorCodeEnum().getCode();
			labContainer.meta.error 	= exc.getErrorCodeEnum().getMessage();
			labContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return labContainer;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			labContainer.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			labContainer.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			labContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return labContainer;
		}
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
