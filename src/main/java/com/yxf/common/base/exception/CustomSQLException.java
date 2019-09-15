package com.yxf.common.base.exception;

public class CustomSQLException extends BaseException
{

    /**
     * 
     */
    private static final long serialVersionUID = -4564244755137613965L;

    public CustomSQLException(String message, Throwable cause, String code, String[] values)
    {
        super(message, cause, code, values);
    }

}
