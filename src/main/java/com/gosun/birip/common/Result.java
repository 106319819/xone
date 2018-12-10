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
	private String code;
	/**
	 * 错误消息 当status为fail时，返回此字段
	 */
	private String message;
	/**
	 * 签名
	 */
	private String sign = "";
	private T data;
	/**
	 * 分页结果返回对象
	 * @author yao
	 *
	 * @param <T>
	 */
	public static class PageResult<T>{
		private Integer page; //当前页号，从1开始
		private Integer size;//每页行数
		private Integer pages;//总页数
		private Long rows;//总记录数
		private List<T> content; //当前页的数据列表
		public PageResult(Integer page,Integer size,Integer pages,Long rows,List<T> list){
			this.page = page;
			this.size = size;
			this.pages = pages;
			this.rows = rows;
			this.content = list;
		}
		
		public PageResult(List<T> list){
			this.content = list;
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
		
		
		public Integer getPages() {
			return pages;
		}

		public void setPages(Integer pages) {
			this.pages = pages;
		}

		public Long getRows() {
			return rows;
		}

		public void setRows(Long rows) {
			this.rows = rows;
		}

		public List<T> getContent()
		{
			return content;
		}
		public void setContent(List<T> content)
		{
			this.content = content;
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

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	/**
	 * 不返回数据，仅返回成功状态时调用
	 * @return
	 */
	public static <T> Result< T > success(){
		Result<T> result = new Result<T>();
		result.data = null;
		result.status = "success";
		return result;
	}

	/**
	 * 返回单个对象时调用
	 * @param data
	 * @return
	 */
	public static <T> Result< T > success(T data){
		
		Result<T> result = new Result<T>();
		result.data = data;
		result.status = "success";
		return result;
	}

	/**
	 * 失败时调用
	 * @param data
	 * @return
	 */
	public static <T> Result< T > fail(T data){

		Result<T> result = new Result<T>();
		result.data = data;
		result.status = "fail";
		return result;
	}

	/**
	 * 当返回分页记录时调用
	 * @param page
	 * @param list
	 * @return
	 */
	public static <T> Result<?>  success(Page<T> page,List<T> list){
		
		Result < PageResult<T> > result = new Result< PageResult<T> >();
		result.data = new PageResult<T>(page.getPageable().getPageNumber()+1, page.getPageable().getPageSize(), page.getTotalPages(), page.getTotalElements(), list);
		result.status = "success";
		return result;
	}
	/**
	 * 当返回列表时调用
	 * @param list
	 * @return
	 */
	public static <T> Result<?>  success(List<T> list){
		Result < List<T> > result = new Result< List<T> >();
		result.data = list;
		result.status = "success";
		return result;
	}

	/**
	 * 异常时调用
	 * @param e
	 * @return
	 */
	public static Result< BiripException > error(BiripException e){
		Result<BiripException> result = new Result<BiripException>();
		result.status = "fail";
		result.code = e.getError();
		result.message = e.getMessage();
		
		return result;
	}

	/**
	 * 状态处理中时调用
	 * @param data
	 * @return
	 */
	public static <T> Result< T > pending(T data){
		Result<T> result = new Result<T>();
		result.data = data;
		result.status = "pending";
		return result;
	}

}
