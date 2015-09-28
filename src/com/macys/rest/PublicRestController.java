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
import com.macys.valuesobjects.ReleaseVo;
import com.macys.valuesobjects.SystemComponentVo;
import com.macys.valuesobjects.UserVo;
import com.macys.valuesobjects.containers.BaseContainerVo;
import com.macys.valuesobjects.containers.LabContainerVo;
import com.macys.valuesobjects.containers.ReleaseContainerVo;
import com.macys.valuesobjects.containers.SystemComponentContainerVo;
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
	@ApiOperation(value = "Register Lab",response=LabContainerVo.class)
	public BaseContainerVo createLab(
			@ApiParam(value="Lab Name",		required=true) 	@FormParam("labName") 			String labName, 
			@ApiParam(value="Manager Name",	required=true)	@FormParam("managerName") 		String managerName, 
			@ApiParam(value="PDM Name",		required=true)	@FormParam("pdmName") 			String pdmName, 
			@ApiParam(value="UserName",		required=true)	@FormParam("userName") 			String userName,
			@ApiParam(value="Password",		required=true)	@FormParam("password") 			String password,
			@ApiParam(value="IsSuperAdmin",	required=true)	@FormParam("isSuperAdmin") 		String isSuperAdmin
									) {
		
		LabContainerVo labContainer = new LabContainerVo();
		 
		try{
			
			LabVo labVo 				= userService.createLabAndUser(labName,managerName,pdmName,userName,password,isSuperAdmin);
			labContainer.meta.code 	= Constants.SUCCESS;
			labContainer.data 			= labVo;
			return labContainer;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			labContainer.meta.code 	= exc.getErrorCodeEnum().getCode();
			labContainer.meta.error 	= exc.getErrorCodeEnum().getMessage();
			labContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return labContainer;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			labContainer.meta.code 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			labContainer.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			labContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return labContainer;
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
	
	@POST
	@Path("/deletelab")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Delete Lab",response=BaseContainerVo.class)
	public BaseContainerVo deleteLab(@ApiParam(value="uuid",required=true) @FormParam("uuid") String uuid){
 
		BaseContainerVo baseContainer = new BaseContainerVo();
		try{
			userService.deleteLab(uuid);
			baseContainer.meta.code = Constants.SUCCESS;
			return baseContainer;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			baseContainer.meta.code 		= exc.getErrorCodeEnum().getCode();
			baseContainer.meta.error 		= exc.getErrorCodeEnum().getMessage();
			baseContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return baseContainer;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			baseContainer.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			baseContainer.meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			baseContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return baseContainer;
		}
	}
	
	@POST
	@Path("/deletebusinessobject")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Delete Business Object",response=BaseContainerVo.class)
	public BaseContainerVo deleteBusinessObject(@ApiParam(value="uuid",required=true) @FormParam("uuid") String uuid){
 
		BaseContainerVo baseContainer = new BaseContainerVo();
		try{
			userService.deleteBusinessObject(uuid);
			baseContainer.meta.code = Constants.SUCCESS;
			return baseContainer;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			baseContainer.meta.code 		= exc.getErrorCodeEnum().getCode();
			baseContainer.meta.error 		= exc.getErrorCodeEnum().getMessage();
			baseContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return baseContainer;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			baseContainer.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			baseContainer.meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			baseContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return baseContainer;
		}
	}
	
	@POST
	@Path("/updateobject")
	@Produces( MediaType.APPLICATION_JSON )
	public BaseContainerVo objectService(	@FormParam("uuid")		String uuid,
											@FormParam("names") 	String names, 
											@FormParam("values") 	String values) {
		BaseContainerVo baseContainer = new BaseContainerVo();
		try{
			userService.updateBusinessObject(uuid,names,values);
			baseContainer.meta.code = Constants.SUCCESS;
			return baseContainer;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			baseContainer.meta.code 		= exc.getErrorCodeEnum().getCode();
			baseContainer.meta.error 		= exc.getErrorCodeEnum().getMessage();
			baseContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return baseContainer;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			baseContainer.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			baseContainer.meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			baseContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return baseContainer;
		}
	}
	
	@POST
	@Path("/systemcomponents")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Create System Component",response=SystemComponentVo.class)
	public BaseContainerVo createSystemComponents(@ApiParam(value="name",required=true) @FormParam("name") String name){
		SystemComponentContainerVo container = new SystemComponentContainerVo();
		try{
			SystemComponentVo vo 	= userService.createSystemComponent(name);
			container.meta.code 	= Constants.SUCCESS;
			container.data 			= vo;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= exc.getErrorCodeEnum().getCode();
			container.meta.error 		= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	@GET
	@Path("/systemcomponents")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Get All System Components",response=SystemComponentVo.class)
	public BaseContainerVo getAllSystemComponents(){
		SystemComponentContainerVo container = new SystemComponentContainerVo();
		try{
			List<SystemComponentVo> voList 	= userService.getAllSystemComponents();
			container.meta.code 			= Constants.SUCCESS;
			container.dataList 				= voList;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= exc.getErrorCodeEnum().getCode();
			container.meta.error 		= exc.getErrorCodeEnum().getMessage();
			container.meta.details		= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details		= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	@POST
	@Path("/deletesystemcomponent")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Delete SystemComponent",response=BaseContainerVo.class)
	public BaseContainerVo deleteSystemComponenet(@ApiParam(value="uuid",required=true) @FormParam("uuid") String uuid){
 
		BaseContainerVo baseContainer = new BaseContainerVo();
		try{
			userService.deleteSystemComponent(uuid);
			baseContainer.meta.code = Constants.SUCCESS;
			return baseContainer;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			baseContainer.meta.code 		= exc.getErrorCodeEnum().getCode();
			baseContainer.meta.error 		= exc.getErrorCodeEnum().getMessage();
			baseContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return baseContainer;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			baseContainer.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			baseContainer.meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			baseContainer.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return baseContainer;
		}
	}
	
	@POST
	@Path("/releases")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Create Release",response=ReleaseContainerVo.class)
	public BaseContainerVo createRelease(	@ApiParam(value="name",required=true) 			@FormParam("name") 			String name,
											@ApiParam(value="branchCutDate",required=true) 	@FormParam("branchCutDate") String branchCutDate,
											@ApiParam(value="hardLockDate",required=true) 	@FormParam("hardLockDate") 	String hardLockDate,
											@ApiParam(value="mcomDate",required=true)		@FormParam("mcomDate")	 	String mcomDate,
											@ApiParam(value="bcomDate",required=true) 		@FormParam("bcomDate") 		String bcomDate){
 
		ReleaseContainerVo container = new ReleaseContainerVo();
		try{
			ReleaseVo vo 		=  userService.createRelease(name,branchCutDate,hardLockDate,mcomDate,bcomDate);
			container.meta.code = Constants.SUCCESS;
			container.data		= vo;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= exc.getErrorCodeEnum().getCode();
			container.meta.error 		= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	
	@GET
	@Path("/releases")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Get All Release",response=ReleaseContainerVo.class)
	public BaseContainerVo getAllReleases(){
 
		ReleaseContainerVo container = new ReleaseContainerVo();
		try{
			List<ReleaseVo> list 	= userService.getAllReleases();
			container.meta.code 	= Constants.SUCCESS;
			container.dataList		= list;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= exc.getErrorCodeEnum().getCode();
			container.meta.error 		= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
