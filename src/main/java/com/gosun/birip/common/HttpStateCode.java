package com.gosun.birip.common;

import java.util.HashMap;
import java.util.Map;


public class HttpStateCode
{
	
	
	private static Map<Integer, String> httpStateMap = new HashMap<Integer, String>();
	static
	{

		httpStateMap.put(100,"Continue");
		httpStateMap.put(101,"Switching Protocols");
		httpStateMap.put(102,"Processing");
		
		httpStateMap.put(200,"OK");
		httpStateMap.put(201,"Created");
		httpStateMap.put(202,"Accepted");
		httpStateMap.put(203,"Non-Authoritative Information");
		httpStateMap.put(204,"No Content");
		httpStateMap.put(205,"Reset Content");
		httpStateMap.put(206,"Partial Content");
		
		httpStateMap.put(300,"Multiple Choices");
		httpStateMap.put(301,"Moved Permanently");
		httpStateMap.put(302,"Move temporarily");
		
		httpStateMap.put(303,"See Other");
		httpStateMap.put(304,"Not Modified");
		httpStateMap.put(305,"Use Proxy");
		httpStateMap.put(306,"Switch Proxy");
		httpStateMap.put(307,"Temporary Redirect");
		
		httpStateMap.put(400,"Bad Request");
		httpStateMap.put(401,"Unauthorized");
		httpStateMap.put(403,"Forbidden");
		httpStateMap.put(404,"Not Found");
		httpStateMap.put(405,"Method Not Allowed");
		httpStateMap.put(406,"Not Acceptable");
		httpStateMap.put(407,"Proxy Authentication Required");
		httpStateMap.put(408,"Request Timeout");
		httpStateMap.put(409,"Conflict");
		httpStateMap.put(410,"Gone");
		httpStateMap.put(411,"Length Required");
		
		httpStateMap.put(412,"Precondition Failed");
		httpStateMap.put(413,"Request Entity Too Large");
		httpStateMap.put(414,"Request-URI Too Long");
		httpStateMap.put(415,"Unsupported Media Type");
		httpStateMap.put(416,"Requested Range Not Satisfiable");
		httpStateMap.put(417,"Expectation Failed");
		httpStateMap.put(422,"Unprocessable Entity");
		httpStateMap.put(423,"Locked");
		httpStateMap.put(424,"Failed Dependency");
		httpStateMap.put(425,"Unordered Collection");
		httpStateMap.put(426,"Upgrade Required");
		httpStateMap.put(449,"Retry With");
		
		httpStateMap.put(500,"Internal Server Error");
		httpStateMap.put(501,"Not Implemented");
		httpStateMap.put(502,"Bad Gateway");
		httpStateMap.put(503,"Service Unavailable");
		httpStateMap.put(504,"Gateway Timeout");
		httpStateMap.put(505,"HTTP Version Not Supported");
		httpStateMap.put(506,"Variant Also Negotiates");
		httpStateMap.put(507,"Insufficient Storage");
		httpStateMap.put(509,"Bandwidth Limit Exceeded");
		httpStateMap.put(510,"Not Extended");
		httpStateMap.put(600,"Unparseable Response Headers");
	}

	public static String getDescription(Integer state) {
		return " state: " + state + " " + httpStateMap.get(state);
	}
}
