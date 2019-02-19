package com.gosun.xone.common;
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

	public BiripException()
	{
		super();
	}
	public BiripException(Error error,String message, Throwable cause)
	{
		super(message, cause);
		this.error = error;
		this.cause = cause;
	}
	public BiripException(Error error,String message)
	{
		super(message);
		this.error = error;
	}
	public BiripException(Error error,Integer message)
	{
		super(String.valueOf(message));
		this.error = error;
	}
	public BiripException(Error error,Throwable cause)
	{
		super(cause);
		this.error = error;
		this.cause = cause;
	}
	public BiripException(Error error)
	{
		super();
		this.error = error;
		
	}
	public BiripException(Throwable cause)
	{
		super(cause);
	}
	
	public static void throwing(Error error,Throwable cause) throws BiripException{
		throw new BiripException(error,cause);
	}
	public static void throwing(Error error,String message, Throwable cause) throws BiripException{
		BiripException e = new BiripException(error,message,cause);
		throw e;
	}
	public static void throwing(Throwable cause) throws BiripException{
		throw new BiripException(cause);
	}
	
	public static void throwing(Error error) throws BiripException{
		throw new BiripException(error);
	}
	public static void throwing(Error error,String message)throws BiripException{
		throw new BiripException(error,message);
	}
	
	public static BiripException instance(Error error, Exception e){
		BiripException inst = new BiripException(error,e);
		return inst;
	}
	public static BiripException instance(Error error){
		BiripException  inst = new BiripException(error);
		return inst;
	}
}
