package com.macys.rest.filters;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

public class UserAuthRequestFilter implements ContainerRequestFilter {

	final static Logger log = Logger.getLogger(UserAuthRequestFilter.class);
	
    //private UserAuthenticator userAuthenticator;
    
    @Context
    private transient HttpServletRequest servletRequest;

	@Override
    public void filter( ContainerRequestContext requestCtx ) throws IOException {

        String path = requestCtx.getUriInfo().getPath();
        log.info( "Filtering request path: " + path );
        

        
        // For any other methods besides login and register, the session_id must be verified
//        if ( !path.contains( "login" )  && !path.contains( "register" ) && !path.contains( "logout" )) {
//            String sessionId = requestCtx.getHeaderString( HttpHeaderNames.SESSION_ID );
//
//            try{
////            	EmployeeSession session = userAuthenticator.isSessionIdValid(sessionId);
////            	if(session==null || session.isExpired == true)
////            		requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
////            	else
////            		servletRequest.setAttribute(HttpHeaderNames.EMPLOYEE_SESSION_OBJECT, session);
//            	
//            }
//            catch(Exception exc){
//            	exc.printStackTrace(System.out);
//            	requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
//            }
//            
//        }
    }
        
//	public void setServletRequest(HttpServletRequest servletRequest) {
//		this.servletRequest = servletRequest;
//	}
//
//	public void setUserAuthenticator(UserAuthenticator userAuthenticator) {
//		this.userAuthenticator = userAuthenticator;
//	}
//	
	
}


