package com.evowork.exception;

/**
 * @author tuannd
 * @date 12/03/2016
 * @since 1.0
 */
public class BusinessException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	public BusinessException() {

    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Exception e) {
        super(e);
    }
}
