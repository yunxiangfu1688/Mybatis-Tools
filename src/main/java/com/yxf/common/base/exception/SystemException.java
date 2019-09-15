package com.yxf.common.base.exception;

/**
 * 系统业务异常
 */
public class SystemException extends BaseException {

	/**
     * 
     */
    private static final long serialVersionUID = -8651773484303127774L;

    /** 
     * Constructors 
     *  
     * @param message 
     *            错误信息 <b>*无异常代码时抛出</b>
     */
    public SystemException(String message) {
        super(message, null, null, null);  
    }
    
    /** 
     * Constructors 
     *  
     * @param code 
     *            错误代码 
     * @param message 
     *            错误信息           
     */
	public SystemException(String code, String message) {
	    super(message, null, code, null);  
	}

	/** 
     * Constructors 
     *  
     * @param cause 
     *            异常接口 
     * @param code 
     *            错误代码 
     * @param message 
     *            错误信息            
     */ 
	public SystemException(Throwable cause, String code, String message) {
	    super(message, cause, code, null);  
	}

	/** 
     * Constructors 
     *  
     * @param code 
     *            错误代码 
     * @param values 
     *            一组异常信息待定参数
     * @param message 
     *            错误信息             
     */  
	public SystemException(String code, String[] values, String message) {
	    super(message, null, code, values);  
	}

	/** 
     * Constructors 
     *  
     * @param cause 
     *            异常接口 
     * @param code 
     *            错误代码 
     * @param values 
     *            一组异常信息待定参数 
     * @param message 
     *            错误信息            
     */  
	public SystemException(Throwable cause, String code, String[] values, String message) {
        super(message, cause, code, values);  
	}

}
