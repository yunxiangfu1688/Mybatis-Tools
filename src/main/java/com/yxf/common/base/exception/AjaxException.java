package com.yxf.common.base.exception;

/*
 * ajax异常
 */
public class AjaxException extends BaseException
{

    /**
     * 
     */
    private static final long serialVersionUID = 5399606837124936992L;

    public AjaxException(String message, Throwable cause, String code, String[] values)
    {
        super(message, cause, code, values);
    }
    
}
