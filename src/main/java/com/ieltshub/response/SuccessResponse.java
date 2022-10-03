package com.ieltshub.response;

import com.ieltshub.common.ErrorCode;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public class SuccessResponse extends ApiResponse {
    private Object data;

    public SuccessResponse() {

    }

    public SuccessResponse(Object data) {
        this.data = data;
        this.errorCode = ErrorCode.NO_ERROR;
    }

    public SuccessResponse(ErrorCode errorCode, Object data) {
        this.data = data;
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
