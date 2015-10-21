package com.macys.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.macys.services.LabService;
import com.macys.services.UserService;
import com.macys.utils.Constants;
import com.macys.valuesobjects.LabVo;
import com.macys.valuesobjects.ReleaseCupVo;
import com.macys.valuesobjects.ReleaseVo;
import com.macys.valuesobjects.SystemComponentVo;
import com.macys.valuesobjects.UserVo;
import com.macys.valuesobjects.containers.BaseContainerVo;
import com.macys.valuesobjects.containers.LabContainerVo;
import com.macys.valuesobjects.containers.ReleaseContainerVo;
import com.macys.valuesobjects.containers.ReleaseCupContainerVo;
import com.macys.valuesobjects.containers.SystemComponentContainerVo;
import com.macys.valuesobjects.containers.UserContainerVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "rest/public", description = "Public User Operations")
public class PublicRestController extends BaseRestController{

	protected final Logger log = Logger.getLogger(PublicRestController.class);
	
	private UserService userService;
	
	private LabService	labService;
	
	@Context
	private HttpHeaders httpHeaders;
	
	@Context
	private Request httpRequest;
	
	@Context
	private ContainerRequestContext requestCtx;
	
	@Context
    private transient HttpServletRequest servletRequest;
	
	@POST
	@Path("/login")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Login",response=UserContainerVo.class)
	public BaseContainerVo login(
			@ApiParam(value="UserName",required=true) @FormParam("userName") String userName, 
			@ApiParam(value="Password",required=true) @FormParam("password") String password){

		UserContainerVo container = new UserContainerVo();
		
		try{
			UserVo empvo 					= userService.login(userName,password);
			container.meta.code 			= Constants.SUCCESS;
			container.data 				= empvo;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= exc.getErrorCodeEnum().getCode();
			container.meta.error 	= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	
	
	@POST
	@Path("/deletebusinessobject")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Delete Business Object",response=BaseContainerVo.class)
	public BaseContainerVo deleteBusinessObject(@ApiParam(value="uuid",required=true) @FormParam("uuid") String uuid){
 
		BaseContainerVo baseContainer = new BaseContainerVo();
		try{
			labService.deleteBusinessObject(uuid);
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
											@FormParam("values") 	String values,
											@FormParam("delimiter") 	String delimiter) {
		BaseContainerVo baseContainer = new BaseContainerVo();
		try{
			userService.updateBusinessObject(uuid,names,values,delimiter);
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
			SystemComponentVo vo 	= labService.createSystemComponent(name);
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
			List<SystemComponentVo> voList 	= labService.getAllSystemComponents();
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
			labService.deleteSystemComponent(uuid);
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
	public BaseContainerVo createRelease(	@ApiParam(value="name",required=true) 					@FormParam("name") 					String name,
											@ApiParam(value="branchCutDate",required=true) 			@FormParam("branchCutDate") 		String branchCutDate,
											@ApiParam(value="branchFreezeDate",required=true) 		@FormParam("branchFreezeDate") 		String branchFreezeDate,
											@ApiParam(value="hardLockDate",required=true) 			@FormParam("branchHardLockDate") 	String branchHardLockDate,
											@ApiParam(value="branchProductionDate",required=true) 	@FormParam("branchProductionDate") 	String branchProductionDate,
											@ApiParam(value="mcomDate",required=true)				@FormParam("mcomDate")	 			String mcomDate,
											@ApiParam(value="bcomDate",required=true) 				@FormParam("bcomDate") 				String bcomDate,
											@ApiParam(value="isActivated",required=true) 			@FormParam("isActivated") 			String isActivated){
 
		ReleaseContainerVo container = new ReleaseContainerVo();
		
		try{
			ReleaseVo vo 		=  labService.createRelease(name, branchCutDate, branchFreezeDate, branchHardLockDate, branchProductionDate,  mcomDate,  bcomDate, isActivated);
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
			List<ReleaseVo> list 	= labService.getAllReleases();
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
	
	
	@POST
	@Path("/releasecups")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Create Release Cup",response=ReleaseCupContainerVo.class)
	public BaseContainerVo createReleaseCup(@ApiParam(value="releaseUuid",required=true) 		@FormParam("releaseUuid") 		String releaseUuid,
											@ApiParam(value="labUuid",required=true) 			@FormParam("labUuid") 			String labUuid,
											@ApiParam(value="availableDevDays",required=true) 	@FormParam("availableDevDays") 	String availableDevDays,
											@ApiParam(value="devDays",required=true)			@FormParam("devDays")	 		String devDays,
											@ApiParam(value="regressionDays",required=true) 	@FormParam("regressionDays")	String regressionDays,
											@ApiParam(value="sysComponents",required=true) 		@FormParam("sysComponents")		String sysComponents){
 
		ReleaseCupContainerVo container = new ReleaseCupContainerVo();
		try{
			ReleaseCupVo vo 		=  labService.createReleaseCup(releaseUuid,labUuid,availableDevDays,devDays,regressionDays,sysComponents);
			container.meta.code 	= Constants.SUCCESS;
			container.data			= vo;
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
	@Path("/releasecups/{releaseCupUuid}")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Create Release Cup",response=ReleaseCupContainerVo.class)
	public BaseContainerVo getReleaseCup(@ApiParam(value="releaseCupUuid",required=true) @PathParam("releaseCupUuid") String releaseCupUuid){
 
		ReleaseCupContainerVo container = new ReleaseCupContainerVo();
		try{
			ReleaseCupVo vo 		=  labService.getReleaseCupByUuid(releaseCupUuid);
			container.meta.code 	= Constants.SUCCESS;
			container.data			= vo;
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
	@Path("/labs/{labUuid}/releasecups")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Get All Release Cups By Lab Uuid",response=ReleaseCupContainerVo.class)
	public BaseContainerVo getAllReleaseCupsByLabUuid(@ApiParam(value="labUuid",required=true) @PathParam("labUuid") String labUuid){
 
		ReleaseCupContainerVo container = new ReleaseCupContainerVo();
		try{
			List<ReleaseCupVo> voList 	=  labService.getAllReleaseCupsByLabUuid(labUuid);
			container.meta.code 		= Constants.SUCCESS;
			container.dataList			= voList;
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
	@Path("/labs/{labUuid}/users")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Get All Users By Lab Uuid",response=UserContainerVo.class)
	public BaseContainerVo getAllUsersByLabUuid(@ApiParam(value="labUuid",required=true) @PathParam("labUuid") String labUuid){
 
		UserContainerVo container = new UserContainerVo();
		try{
			List<UserVo> voList 	=  userService.getAllUsersByLabUuid(labUuid);
			container.meta.code 		= Constants.SUCCESS;
			container.dataList			= voList;
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
	
	@POST
	@Path("/register")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Register Lab And User",response=UserContainerVo.class)
	public BaseContainerVo register(
			@ApiParam(value="First Name",	required=true) 	@FormParam("firstName") 		String firstName,
			@ApiParam(value="Last Name",	required=true) 	@FormParam("lastName") 			String lastName,
			@ApiParam(value="User Email",	required=true) 	@FormParam("userEmail") 		String userEmail,
			@ApiParam(value="Lab Name",		required=true) 	@FormParam("labName") 			String labName, 
			@ApiParam(value="Manager Name",	required=true)	@FormParam("managerName") 		String managerName, 
			@ApiParam(value="PDM Name",		required=true)	@FormParam("pdmName") 			String pdmName, 
			@ApiParam(value="UserName",		required=true)	@FormParam("userName") 			String userName,
			@ApiParam(value="Password",		required=true)	@FormParam("password") 			String password,
			@ApiParam(value="IsSuperAdmin",	required=true)	@FormParam("isSuperAdmin") 		String isSuperAdmin,
			@ApiParam(value="IsLabManager",	required=true)	@FormParam("isLabManager") 		String isLabManager,
			@ApiParam(value="IsLabUser",	required=true)	@FormParam("isLabUser") 		String isLabUser
									) {
		
		UserContainerVo container = new UserContainerVo();
		 
		try{
			
			UserVo userVo 			= userService.createLabAndUser(firstName,lastName,userEmail,labName,managerName,pdmName,userName,password,isSuperAdmin,isLabManager,isLabUser);
			container.meta.code 	= Constants.SUCCESS;
			container.data 			= userVo;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= exc.getErrorCodeEnum().getCode();
			container.meta.error 	= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	@GET
	@Path("/labs")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Get All Labs",response=LabContainerVo.class)
	public BaseContainerVo getLabs(){

		LabContainerVo labContainer = new LabContainerVo();
		
		try{
			List<LabVo> labs 				= labService.getAllLabs();
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
	@Path("/labs")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Create Lab",response=LabContainerVo.class)
	public BaseContainerVo createLab(
			@ApiParam(value="Lab Name",		required=true) 		@FormParam("labName") 			String labName, 
			@ApiParam(value="Manager Name",	required=true)		@FormParam("managerName") 		String managerName, 
			@ApiParam(value="PDM Name",		required=true)		@FormParam("pdmName") 			String pdmName,
			@ApiParam(value="Created By",		required=true)	@FormParam("createdBy") 		String createdBy) {
		
		LabContainerVo container = new LabContainerVo();
		 
		try{
			
			LabVo labVo 			= labService.createLab(labName, managerName, pdmName, createdBy);
			container.meta.code 	= Constants.SUCCESS;
			container.data 			= labVo;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= exc.getErrorCodeEnum().getCode();
			container.meta.error 	= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
		
	@POST
	@Path("/deletebusinessobject")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Delete Lab",response=BaseContainerVo.class)
	public BaseContainerVo deleteLab(@ApiParam(value="uuid",required=true) @FormParam("uuid") String uuid){
 
		BaseContainerVo baseContainer = new BaseContainerVo();
		try{
			labService.deleteLab(uuid);
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
	
	@GET
	@Path("/users/{userUuid}/labs")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Get All Labs By User Uuid",response=LabVo.class)
	public BaseContainerVo getAllLabsByUserUuid(@ApiParam(value="userUuid",required=true) @PathParam("userUuid") String userUuid){
 
		LabContainerVo container = new LabContainerVo();
		try{
			List<LabVo> voList	=  labService.getAllLabsByUserUuid(userUuid);
			container.meta.code = Constants.SUCCESS;
			container.dataList	= voList;
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
	
	@POST
	@Path("/users")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Create User",response=UserContainerVo.class)
	public BaseContainerVo createUser(
			@ApiParam(value="First Name",	required=true) 	@FormParam("firstName") 		String firstName,
			@ApiParam(value="Last Name",	required=true) 	@FormParam("lastName") 			String lastName,
			@ApiParam(value="User Email",	required=true) 	@FormParam("userEmail") 		String userEmail,
			@ApiParam(value="UserName",		required=true)	@FormParam("userName") 			String userName,
			@ApiParam(value="Password",		required=true)	@FormParam("password") 			String password,
			@ApiParam(value="IsSuperAdmin",	required=true)	@FormParam("isSuperAdmin") 		String isSuperAdmin,
			@ApiParam(value="IsLabManager",	required=true)	@FormParam("isLabManager") 		String isLabManager,
			@ApiParam(value="IsLabUser",	required=true)	@FormParam("isLabUser") 		String isLabUser,
			@ApiParam(value="IsPasswordReset",required=true)@FormParam("isPasswordReset") 		String isPasswordReset,
			@ApiParam(value="CreatedBy",	required=true)	@FormParam("createdBy") 		String createdBy,
			@ApiParam(value="LabUuids",		required=true)	@FormParam("labUuids") 			String labUuids
									) {
		
		UserContainerVo container = new UserContainerVo();
		 
		try{
			
			UserVo userVo 			= userService.createUser(firstName,lastName,userEmail,userName,password,isSuperAdmin,isLabManager,isLabUser,isPasswordReset,createdBy);
			
			//Asigning Lab
			labService.assignLabsToUser(userVo.uuid,labUuids,";",createdBy);
			
			container.meta.code 	= Constants.SUCCESS;
			container.data 			= userVo;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= exc.getErrorCodeEnum().getCode();
			container.meta.error 	= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	@POST
	@Path("/assignlabstouser")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Assign Lab To User",response=BaseContainerVo.class)
	public BaseContainerVo assignLabToUser(
			@ApiParam(value="UserUuid",		required=true)	@FormParam("userUuid") 			String userUuid,
			@ApiParam(value="LabUuids",		required=true)	@FormParam("labUuids") 			String labUuids,
			@ApiParam(value="CreatedBy",	required=true)	@FormParam("createdBy") 		String createdBy
									) {
		
		BaseContainerVo container = new BaseContainerVo();
		 
		try{
			labService.assignLabsToUser(userUuid,labUuids,";",createdBy);
			container.meta.code 	= Constants.SUCCESS;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= exc.getErrorCodeEnum().getCode();
			container.meta.error 	= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	@GET
	@Path("/users")
	@Produces( MediaType.APPLICATION_JSON )
	@ApiOperation(value = "Get All Users",response=UserContainerVo.class)
	public BaseContainerVo getAllUsers() {
		
		UserContainerVo container = new UserContainerVo();
		 
		try{
			
			List<UserVo> list 		= userService.getAllUsers();
			container.meta.code 	= Constants.SUCCESS;
			container.dataList 		= list;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= exc.getErrorCodeEnum().getCode();
			container.meta.error 	= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	@POST
	@Path("/passwordreset")
	@Produces( MediaType.APPLICATION_JSON )
	public BaseContainerVo passwordReset(@FormParam("username") String username){

		BaseContainerVo container = new BaseContainerVo();
		try{
			userService.passwordReset(username);
			container.meta.code 			= Constants.SUCCESS;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= exc.getErrorCodeEnum().getCode();
			container.meta.error 	= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	@POST
	@Path("/passwordchange")
	@Produces( MediaType.APPLICATION_JSON )
	public BaseContainerVo passwordChange(@FormParam("username") String username, @FormParam("newpassword") String newpassword) {
		
		UserContainerVo container = new UserContainerVo();
		
		try{
			userService.passwordChange(username,newpassword);
			UserVo vo 				= userService.login(username,newpassword);
			container.meta.code 	= Constants.SUCCESS;
			container.data 			= vo;
			return container;
		}
		catch(ServiceException exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= exc.getErrorCodeEnum().getCode();
			container.meta.error 	= exc.getErrorCodeEnum().getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
		catch(Exception exc){
			exc.printStackTrace(System.err);
			container.meta.code 		= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode();
			container.meta.error 	= ErrorCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
			container.meta.details	= ExceptionUtils.getRootCauseMessage(exc).toString();
			return container;
		}
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setLabService(LabService labService) {
		this.labService = labService;
	}
	
	
}
