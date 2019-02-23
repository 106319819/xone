package com.gosun.common;
/**
 * 
 * @author Administrator
 *
 */
public class XoneException extends Exception
{
	private Throwable cause;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Error error;
	public String getError()
	{
		return String.format("%04d", error.ordinal());
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String getMessage()
	{
		//String tp = "\\t|\r|\n";
		String msg =  super.getMessage(); //Util.nvl(Util.clearSpecialChar(getMessage(), tp),"");
		if(!Util.isNvl(msg)) {
			msg = msg.replaceAll("\"", "'");
		}
		if(!Util.isNvl(this.cause)) {
			StackTraceElement[] elements = this.cause.getStackTrace();
			String call = String.format(" @ %s.%s(%s:%d)", elements[0].getClassName(),elements[0].getMethodName(),elements[0].getFileName(),elements[0].getLineNumber());
			msg += call;
		}
		return Error.getError(error, msg);
	}

	public XoneException()
	{
		super();
	}
	public XoneException(Error error,String message, Throwable cause)
	{
		super(message, cause);
		this.error = error;
		this.cause = cause;
	}
	public XoneException(Error error,String message)
	{
		super(message);
		this.error = error;
	}
	public XoneException(Error error,Integer message)
	{
		super(String.valueOf(message));
		this.error = error;
	}
	public XoneException(Error error,Throwable cause)
	{
		super(cause);
		this.error = error;
		this.cause = cause;
	}
	public XoneException(Error error)
	{
		super();
		this.error = error;
		
	}
	public XoneException(Throwable cause)
	{
		super(cause);
	}
	
	public static void throwing(Error error,Throwable cause) throws XoneException{
		throw new XoneException(error,cause);
	}
	public static void throwing(Error error,String message, Throwable cause) throws XoneException{
		XoneException e = new XoneException(error,message,cause);
		throw e;
	}
	public static void throwing(Throwable cause) throws XoneException{
		throw new XoneException(cause);
	}
	
	public static void throwing(Error error) throws XoneException{
		throw new XoneException(error);
	}
	public static void throwing(Error error,String message)throws XoneException{
		throw new XoneException(error,message);
	}
	
	public static XoneException instance(Error error, Exception e){
		XoneException inst = new XoneException(error,e);
		return inst;
	}
	public static XoneException instance(Error error){
		XoneException  inst = new XoneException(error);
		return inst;
	}
}
