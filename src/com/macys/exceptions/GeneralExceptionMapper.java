package com.macys.exceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GeneralExceptionMapper implements ExceptionMapper<ClientErrorException>{

	@Override
	public Response toResponse(ClientErrorException exception) {
		String jsonString = "{meta:{"+
							"code:"+ErrorCodeEnum.INVALID_REQUEST.getCode()+","+
							"error:"+ErrorCodeEnum.INVALID_REQUEST.getMessage()+","+
							"details:"+exception.getMessage()+
							"}"+
							"}";
		
        Response response = Response.status(Response.Status.BAD_REQUEST) .entity(jsonString) .build();
        return response;
	}

}
