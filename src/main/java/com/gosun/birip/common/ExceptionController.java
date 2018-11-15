package com.gosun.birip.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ExceptionController
{
	@ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e)
	{
		e.printStackTrace();
		
		BiripException exception = null;
		if( (e instanceof BiripException) ){
			exception = (BiripException)e;
		}else{
			exception = BiripException.instance(Error.SYS_ERROR, e);
		}
		
		return Result.error(exception);
	}
	@ExceptionHandler(RuntimeException.class)
	public Result<?> handleRuntimeException(RuntimeException e)
	{
		e.printStackTrace();
		
		BiripException exception = BiripException.instance(Error.SYS_RUNTIME_ERROR, e);
		
		return Result.error(exception);
	}
}
