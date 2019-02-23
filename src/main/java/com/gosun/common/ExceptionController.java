package com.gosun.common;

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
		
		XoneException exception = null;
		if( (e instanceof XoneException) ){
			exception = (XoneException)e;
		}else{
			exception = XoneException.instance(Error.SYS_ERROR, e);
		}
		
		return Result.error(exception);
	}
	@ExceptionHandler(RuntimeException.class)
	public Result<?> handleRuntimeException(RuntimeException e)
	{
		e.printStackTrace();
		
		XoneException exception = XoneException.instance(Error.SYS_RUNTIME_ERROR, e);
		
		return Result.error(exception);
	}
}
