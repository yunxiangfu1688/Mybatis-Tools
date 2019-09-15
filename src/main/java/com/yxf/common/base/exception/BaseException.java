package com.yxf.common.base.exception;



/** 
 * 异常基类，各个模块的运行期异常均继承于类 
 */  
public class BaseException extends RuntimeException {
  
    /**
     * 
     */
    private static final long serialVersionUID = -2830168424662011419L;

    /** 
     * message key 
     */  
    private String code;
  
    /** 
     * message params 
     */  
    private String[] values;
  
    /** 
     * @return the code 
     */  
    public String getCode() {
        return code;  
    }  
  
    /** 
     * @param code the code to set 
     */  
    public void setCode(String code) {
        this.code = code;  
    }  
  
    /** 
     * @return the values 
     */  
    public Object[] getValues() {
        return values;  
    }  
  
    /** 
     * @param values the values to set 
     */  
    public void setValues(String[] values) {
        this.values = values;  
    }  
  
    /**
     * 异常类的基本模板，其他异常继承于它
     * <br>利用了国际化配置的一些特征，可以抛出下面这样定义的一个错误消息:
     * <br>第{0}个{1}参数错误 
     * @param message
     * @param cause
     * @param code
     * @param values
     */
    public BaseException(String message, Throwable cause, String code, String[] values) {
        super(message, cause);  
        this.code = code;  
        this.values = values;  
    }  
    
}  