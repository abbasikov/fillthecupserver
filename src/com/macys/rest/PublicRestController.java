package com.macys.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import com.macys.valuesobjects.MetaVo;
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
	@Path("/register")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Register",response=UserContainerVo.class)
	public BaseContainerVo register(
			@ApiParam(value="First Name",required=true) @FormParam("firstName") String firstName, 
			@ApiParam(value="Last Name",required=true)	@FormParam("lastName") String lastName, 
			@ApiParam(value="Email",required=true)	@FormParam("email") String email, 
			@ApiParam(value="Password",required=true)	@FormParam("password") String password
									) {
		
		UserContainerVo empContainer = new UserContainerVo();
		
		try{
			UserVo empvo 		= new UserVo();
			empvo.firstName		= firstName;
			empvo.lastName		= lastName;
			empvo.email			= email;
			empvo.password		= password;
			
			empvo = userService.registerAndSendEmail(empvo);
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
	
	
	@POST
	@Path("/login")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Login",response=UserContainerVo.class)
	public BaseContainerVo login(
			@ApiParam(value="Email",required=true) @FormParam("email") String email, 
			@ApiParam(value="Password",required=true) @FormParam("password") String password){

		UserContainerVo empContainer = new UserContainerVo();
		
		try{
			UserVo empvo 					= userService.login(email,password);
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
	
	@POST
	@Path("/labs")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Create Lab",response=LabContainerVo.class)
	public BaseContainerVo createLab(
			@ApiParam(value="Name",required=true) 			@FormParam("name") String name, 
			@ApiParam(value="Description",required=true) 	@FormParam("description") String description,
			@ApiParam(value="Location",required=true) 		@FormParam("location") String location){

		LabContainerVo labContainer = new LabContainerVo();
		
		try{
			LabVo labvo 					= userService.createLab(name,description,location);
			labContainer.meta.code 			= Constants.SUCCESS;
			labContainer.data 				= labvo;
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
	
	@PUT
	@Path("/labs/{labuuid}/users/{useruuid}")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Assign Lab To User",response=MetaVo.class)
	public MetaVo assignLabToUser(
			@ApiParam(value="LabUuid",required=true) @PathParam("labuuid") String labuuid,
			@ApiParam(value="UserUuid",required=true) @PathParam("useruuid") String useruuid){
		
		MetaVo meta = new MetaVo();
		
		try{
			userService.assignLabToUser(labuuid,useruuid);
			meta.code 			= Constants.SUCCESS;
			return meta;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			meta.code 		= exc.getErrorCodeEnum().getCode();
			meta.error 		= exc.getErrorCodeEnum().getMessage();
			meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return meta;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return meta;
		}
	}
	

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
