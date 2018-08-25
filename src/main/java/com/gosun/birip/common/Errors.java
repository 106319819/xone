package com.gosun.birip.common;

import java.util.EnumMap;

public enum Errors
{
	SYS_UNKNOW("未知命令"),
	SYS_ERROR("系统错误"),
	SYS_RUNTIME_ERROR("运行时异常"),
	GET_LOGINUSER( "取平台用户信息错误"),
	PARAM_MISSING( "缺少参数"),
	DB_DS_CLOSE( "关闭数据源错误"),
	DB_STMT_CLOSE( "关闭statement错误"),
	DB_RS_CLOSE( "关闭ResultSet错误"),
	DB_DS_GET( "获取数据源错误"),
	DB_EXE_SQL( "执行SQL错误"),
	DB_DS_NULL( "数据库连接对象为空"),
	PARAM_JSON( "解析JSON参数时错误"),
	CACHE_UPDATE( "更新缓存错误"),
	SESSION_TIMEOUT( "会话过期，请重新登录系统"),
	NO_PRIVILEGE( "权限不足"),
	HTTP_ERROR( "http错误"),
	FILE_DOWNLOAD( "文件下载错误"),
	FILE_PARSE("文件解析错误"),
	FILE_EXPORT("导出文件错误"),
	UNKNOW_CMDTYPE("未知命令类型"),
	FILE_UPLOAD("文件上传失败"),
	STREAM_CLOSE("关闭stream错误"),
	VALIDATE_ERROR("校验失败"),
	SOA_INVOKE("服务调用失败");
	private static EnumMap<Errors,String> enumMap = new EnumMap<Errors,String>(Errors.class);
	static {
		
		Errors[] enums = Errors.values();
		for(Errors e : enums){
			enumMap.put(e, e.value);
		}
	}
	private String value;
	Errors(String value){
		this.value = value;
	}
	public static String getError(Errors value, String message) {
		String err = Errors.enumMap.get(value);
		//return String.format("%04d ", value.ordinal()) + err + " - " + message;
		return err + " - " + message;
	}
	
}
