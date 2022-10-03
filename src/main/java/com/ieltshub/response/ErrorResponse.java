package com.ieltshub.response;

import com.ieltshub.common.ErrorCode;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public class ErrorResponse extends ApiResponse {
	
    private Object error;

    public ErrorResponse() {

    }

    public ErrorResponse(Object error) {
        this.error = error;
        this.errorCode = ErrorCode.SYSTEM_ERROR;
    }
    
    public ErrorResponse(ErrorCode errorCode) {
    	this.errorCode = errorCode;
    }

    public ErrorResponse(Object error, ErrorCode errorCode) {
        this.error = error;
        this.errorCode = errorCode;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
