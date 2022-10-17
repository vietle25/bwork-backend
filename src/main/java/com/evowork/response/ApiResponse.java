package com.evowork.response;

import com.evowork.common.ErrorCode;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public abstract class ApiResponse {
	
    protected ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
