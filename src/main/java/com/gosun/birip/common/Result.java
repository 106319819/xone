package com.gosun.birip.common;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T>
{
	/**
	 * 调用状态 返回 success 表示成功  fail 表示失败
	 */
	private String status;
	/**
	 * 错误代码，当status为fail时，返回此字段
	 */
	private String error;
	/**
	 * 错误消息 当status为fail时，返回此字段
	 */
	private String message;
	/**
	 * 签名
	 */
	private String sign = "";
	private T data;
	
	public static class PageResult<T>{
		private Integer page;
		private Integer size;
		private Integer total;
		List<T> conent;
		public PageResult(Integer page,Integer size,Integer total,List<T> list){
			this.page = page;
			this.size = size;
			this.total = total;
			this.conent = list;
		}
		public Integer getPage()
		{
			return page;
		}
		public void setPage(Integer page)
		{
			this.page = page;
		}
		public Integer getSize()
		{
			return size;
		}
		public void setSize(Integer size)
		{
			this.size = size;
		}
		public Integer getTotal()
		{
			return total;
		}
		public void setTotal(Integer total)
		{
			this.total = total;
		}
		public List<T> getConent()
		{
			return conent;
		}
		public void setConent(List<T> conent)
		{
			this.conent = conent;
		}
		
		
	}
	
	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

	public static <T> Result< T > success(T data){
		
		Result<T> result = new Result<T>();
		result.data = data;
		result.status = "success";
		return result;
	}

	public static <T> Result<?>  success(Page<T> page,List<T> list){
		
		Result < PageResult<T> > result = new Result< PageResult<T> >();
		result.data = new PageResult<T>(page.getPageable().getPageNumber()+1, page.getPageable().getPageSize(), page.getTotalPages(), list);
		result.status = "success";
		return result;
	}
	
	public static <T> Result<?>  success(List<T> list){
		
		Result < List<T> > result = new Result< List<T> >();
		result.data = list;
		result.status = "success";
		return result;
	}

	public static Result< BiripException > error(BiripException e){
		Result<BiripException> result = new Result<BiripException>();
		result.status = "fail";
		result.error = e.getError();
		result.message = e.getMessage();
		
		return result;
	}
}
