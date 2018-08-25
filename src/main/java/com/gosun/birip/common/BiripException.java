package com.gosun.birip.common;
/**
 * 
 * @author Administrator
 *
 */
public class BiripException extends Exception
{
	private Throwable cause;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Errors error;
	public String getError()
	{
		return String.format("%04d", error.ordinal());
	}

	public void setError(Errors error) {
		this.error = error;
	}

	@Override
	public String getMessage()
	{
		//String tp = "\\t|\r|\n";
		String msg =  super.getMessage(); //Util.nvl(Util.clearSpecialChar(getMessage(), tp),"");
		msg = msg.replaceAll("\"", "'");
		StackTraceElement[] elements = this.cause.getStackTrace();
		String call = String.format(" @ %s.%s(%s:%d)", elements[0].getClassName(),elements[0].getMethodName(),elements[0].getFileName(),elements[0].getLineNumber());
		msg += call;
		return Errors.getError(error, msg);
	}

	public BiripException()
	{
		super();
	}
	public BiripException(Errors error,String message, Throwable cause)
	{
		super(message, cause);
		this.error = error;
		this.cause = cause;
	}
	public BiripException(Errors error,String message)
	{
		super(message);
		this.error = error;
	}
	public BiripException(Errors error,Integer message)
	{
		super(String.valueOf(message));
		this.error = error;
	}
	public BiripException(Errors error,Throwable cause)
	{
		super(cause);
		this.error = error;
		this.cause = cause;
	}
	public BiripException(Errors error)
	{
		super();
		this.error = error;
		
	}
	public BiripException(Throwable cause)
	{
		super(cause);
	}
	
	public static void throwing(Errors error,Throwable cause) throws BiripException{
		throw new BiripException(error,cause);
	}
	public static void throwing(Errors error,String message, Throwable cause) throws BiripException{
		BiripException e = new BiripException(error,message,cause);
		throw e;
	}
	public static void throwing(Throwable cause) throws BiripException{
		throw new BiripException(cause);
	}
	
	public static void throwing(Errors error) throws BiripException{
		throw new BiripException(error);
	}
	public static void throwing(Errors error,String message)throws BiripException{
		throw new BiripException(error,message);
	}
	
	public static BiripException instance(Errors error, Exception e){
		BiripException ae = new BiripException(error,e);
		return ae;
	}
}
