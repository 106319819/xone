package com.gosun.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.JpaSort.JpaOrder;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Util
{

	private static Logger logger = LoggerFactory.getLogger(Util.class);
	
	public static Pageable parse(String content) throws XoneException{
		ObjectMapper mapper = new ObjectMapper();
		Pageable pageable = null;
		JsonNode root = null;
		try {
			root = mapper.readTree(content);
			pageable = Util.parse(root);
		} catch (IOException e) {
			XoneException.throwing(Error.PARAM_JSON, e);
		}
		
		return pageable;
	}
	
	public static Pageable parse(JsonNode root) throws XoneException{
		Pageable pageable = null;
		String pk = "page";
		String sk = "size";
		if(!root.has(pk)) {
			pk = "pageSize"; //"\"pageSize\":10,\"pageNumber\":0"
		}
		if(!root.has(sk)) {
			sk = "pageNumber";
		}
		int page = root.get(pk).asInt() -1 ;
		int size = root.get(sk).asInt();
		if(root.has("sorts")){
			// {sorts : [{direction:"ASC",property:"field"}]}
			String dk = "direction";
			String ak = "property";
			List<Order> orders = new ArrayList<Order>();
			JsonNode sorts = root.get("sorts");
			for(JsonNode sort : sorts){				
				if(sort.has(dk) && sort.has(ak)) {
					String direction = sort.get(dk).asText();
					String property = sort.get(ak).asText();
					JpaOrder order = (JpaOrder)JpaSort.unsafe(Direction.valueOf(direction), property).getOrderFor(property);
					orders.add(order);
				}				
			}
			if(!orders.isEmpty()) {
				pageable = PageRequest.of(page, size, JpaSort.by(orders));
			}
		}else{
			pageable = PageRequest.of(page, size);
		}
		return pageable;
	}
	
	public static Pageable parseSorts(Pageable pageable, JsonNode sorts) throws XoneException {

		// {sorts : [{direction:"ASC",property:"field"}]}
		String dk = "direction";
		String ak = "property";
		List<Order> orders = new ArrayList<Order>();
		for (JsonNode sort : sorts) {		
			if (sort.has(dk) && sort.has(ak)) {
				String direction = sort.get(dk).asText();
				String property = sort.get(ak).asText();
				JpaOrder order = (JpaOrder)JpaSort.unsafe(Direction.valueOf(direction), property).getOrderFor(property);
				orders.add(order);
			}
		}
		if (!orders.isEmpty()) {
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), JpaSort.by(orders));
		}
		return pageable;
	}
	
	public static Date getDate(String dateStr, String fmt)
	{
		DateFormat format = new SimpleDateFormat(fmt);
		Date date = null;

		try
		{
			date = format.parse(dateStr);
		}
		catch (ParseException e)
		{
			logger.error(e.getMessage());
		}
		return date;
	}

	// 取年月
	public static Date getEndDateByMonth(Date startDate, int months)
	{
		Date ret = null;
		if (startDate == null)
		{
			return ret;
		}
		Calendar calen = Calendar.getInstance();
		calen.setTime(startDate);
		calen.set(Calendar.DAY_OF_MONTH, 1);
		calen.add(Calendar.MONTH, months);
		ret = calen.getTime();
		return ret;
	}

	public static String formatDate(Date date, String fmt)
	{
		DateFormat format = new SimpleDateFormat(fmt);
		String str = "";
		if (date != null)
		{
			str = format.format(date);
		}
		return str;
	}

	public static String formatDate(java.sql.Date date, String fmt)
	{
		return formatDate(date.getTime(),fmt);
	}
	public static String formatDate(java.sql.Timestamp date, String fmt)
	{
		return formatDate(date.getTime(),fmt);
	}
	public static String formatDate(long time, String fmt)
	{
		Calendar calen = Calendar.getInstance();
		calen.setTimeInMillis(time);
		DateFormat format = new SimpleDateFormat(fmt);
		return format.format(calen.getTime());
	}
	 public static String addDate(String day, int hour){
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
	        Date date = null;
	        try{
	            date = format.parse(day);
	        }catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        if (date == null){ return "";}
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.HOUR_OF_DAY, hour);//24小时制
	        date = cal.getTime();
	        cal = null;
	        return format.format(date);
	    }
	public static String formateTime(Date time)
	{
		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		String str = "";
		if (time != null)
		{
			str = format.format(time);
		}
		return str;
	}

	public static int parseInt(String val, int defValue)
	{
		int ret = defValue;
		if (val != null)
		{
			try
			{
				ret = Integer.parseInt(val);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
			}
		}
		return ret;
	}

	public static double parseDouble(String val, double defValue)
	{
		double ret = defValue;
		if (val != null)
		{
			try
			{
				ret = Double.parseDouble(val);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
			}
		}
		return ret;
	}

	/**
	 * 比较日期src是否大于dest
	 * 
	 * @author 张尧伟
	 * @param Date
	 *            src --源时间
	 * @param Date
	 *            dest 目标时间
	 * @param isClearTime
	 *            是否清除时间后再比较 true or false
	 * @return src > dest true
	 */
	public static boolean after(Date src, Date dest, boolean isClearTime)
	{
		Calendar srcCal = Calendar.getInstance();
		Calendar destCal = Calendar.getInstance();
		if (isClearTime)
		{
			srcCal.setTime(clearTime(src));
			destCal.setTime(clearTime(dest));
		}
		else
		{
			srcCal.setTime(src);
			destCal.setTime(dest);
		}
		return srcCal.after(destCal);
	}

	/**
	 * 比较日期src是否小于dest
	 * 
	 * @author 张尧伟
	 * @param Date
	 *            src --源时间
	 * @param Date
	 *            dest 目标时间
	 * @param isClearTime
	 *            是否清除时间后再比较 true or false
	 * @return src > dest true
	 */
	public static boolean before(Date src, Date dest, boolean isClearTime)
	{
		Calendar srcCal = Calendar.getInstance();
		Calendar destCal = Calendar.getInstance();
		if (isClearTime)
		{
			srcCal.setTime(clearTime(src));
			destCal.setTime(clearTime(dest));
		}
		else
		{
			srcCal.setTime(src);
			destCal.setTime(dest);
		}
		return srcCal.before(destCal);
	}

	/**
	 * 比较src日期是否在sartDate与endDate之间,注意比较时左右区间均包含
	 * 
	 * @author 张尧伟
	 * @param src
	 * @param startDate
	 * @param endDate
	 * @param isClearTime
	 * @return boolean
	 */
	public static boolean between(Date src, Date startDate, Date endDate, boolean isClearTime)
	{
		return ((Util.after(src, startDate, isClearTime) && Util.before(src, endDate, isClearTime)) || (Util.equals(src, startDate, isClearTime) || Util.equals(src, endDate, isClearTime)));
	}

	/**
	 * 比较日期src是否相等dest
	 * 
	 * @author 张尧伟
	 * @param Date
	 *            src --源时间
	 * @param Date
	 *            dest 目标时间
	 * @param isClearTime
	 *            是否清除时间后再比较 true or false
	 * @return src == dest true
	 */
	public static boolean equals(Date src, Date dest, boolean isClearTime)
	{
		Calendar srcCal = Calendar.getInstance();
		Calendar destCal = Calendar.getInstance();
		if (isClearTime)
		{
			srcCal.setTime(clearTime(src));
			destCal.setTime(clearTime(dest));
		}
		else
		{
			srcCal.setTime(src);
			destCal.setTime(dest);
		}
		return srcCal.equals(destCal);
	}

	/**
	 * 取当前年份
	 * 
	 * @return
	 */
	public static int getCurrentYear()
	{
		Calendar calen = Calendar.getInstance();
		int year = calen.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 取当前月份
	 * 
	 * @return
	 */
	public static int getCurrentMonth()
	{
		Calendar calen = Calendar.getInstance();
		int month = calen.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 取当前日期
	 * 
	 * @return Date
	 */
	public static Date getCurrentDate()
	{
		return new Date(System.currentTimeMillis());
	}

	public static String getCurrentDateTimeString()
	{
		return formatDate(getCurrentDate(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回当前时间 例如：
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDecimal(Double d)
	{

		DecimalFormat df = new DecimalFormat("###,###.00");

		String numberFormated = df.format(d.doubleValue());

		return numberFormated;
	}

	public static String getDefaultEndDate()
	{
		return formatDate(getCurrentDate(), "yyyy-MM-dd");
	}

	/**
	 * 将用“,”分隔的字符串转换成ArrayList
	 * 
	 * @param inStr
	 * @return
	 */
	public static  List<Long> parseListEx(String str,String split)
	{
		List<Long> ret = new ArrayList<Long>();
		if (str != null && str.length() > 0)
		{
			StringTokenizer st = new StringTokenizer(str, split);
			while (st.hasMoreElements())
			{
				ret.add(Long.valueOf(st.nextElement().toString()));
			}
		}
		return ret;
	}
	
	

	/**
	 * 根据开始日期和给定的间隔（天）取结束日期
	 * 
	 * @param startDate
	 * @param interval
	 * @return
	 */
	public static Date getEndDateByStartDate(Date startDate, int days)
	{
		Date ret = null;
		if (startDate == null)
		{
			return ret;
		}
		Calendar calen = Calendar.getInstance();
		calen.setTime(startDate);
		calen.add(Calendar.DAY_OF_YEAR, days);
		ret = calen.getTime();
		return ret;
	}

	public static String getEndDateByStartDate(String startDate, int days)
	{
		Date ret = getEndDateByStartDate(getDate(startDate, "yyyy-MM-dd"), days);
		return formatDate(ret, "yyyy-MM-dd");
	}

	/**
	 * Title:获得汉字的拼音首字母 Description: GB 2312-80 把收录的汉字分成两级。第一级汉字是常用汉字，计 3755 个，
	 * 置于 16～55 区，按汉语拼音字母／笔形顺序排列；第二级汉字是次常用汉字， 计 3008 个，置于 56～87
	 * 区，按部首／笔画顺序排列，所以本程序只能查到 对一级汉字的声母。同时对符合声母（zh，ch，sh）只能取首字母（z，c，s） Copyright:
	 * Copyright (c) 2004 Company:
	 * 
	 * @author not attributable
	 * @version 1.0
	 */

	// 国标码和区位码转换常量
	private static final int GB_SP_DIFF = 160;

	// 存放国标一级汉字不同读音的起始区位码
	private static final int[] secPosvalueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	// 存放国标一级汉字不同读音的起始区位码对应读音
	private static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z' };

	// 获取一个字符串的拼音码
	public static String getFirstLetter(String oriStr)
	{
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++)
		{ // 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0)
			{ // 非汉字,只处理字母及数字
				// 97 - 122 a .. z
				// 65 - 90 A..Z
				// 48 - 57 0 .. 9
				if ((uniCode[0] <= 122 && uniCode[0] >= 97) || (uniCode[0] <= 90 && uniCode[0] >= 65) || (uniCode[0] <= 57 && uniCode[0] >= 48))
				{
					buffer.append(temp);
				}
			}
			else
			{
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
	 * 例如汉字"你"的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n'
	 */

	private static char convert(byte[] bytes)
	{

		char result = '-';
		int secPosvalue = 0;
		int i;
		for (i = 0; i < bytes.length; i++)
		{
			bytes[i] -= GB_SP_DIFF;
		}
		secPosvalue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++)
		{
			if (secPosvalue >= secPosvalueList[i] && secPosvalue < secPosvalueList[i + 1])
			{
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	public static <T> List<T> createInitList(T o, int count)
	{
		List<T> lst = new ArrayList<T>();

		try
		{
			for (int i = 0; i < count; ++i)
			{
				T o1 = (T) o.getClass().newInstance();
				lst.add(o1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return lst;
	}

	/**
	 * 将src中的属性值赋给dest对象中对应的属性，浅复制，不会递归parent 
	 * @param <T>
	 * @param src
	 *            源对象
	 * @param dest
	 *            目标对象
	 * @param ignoreSetMethods
	 *            目标对象中要忽略的方法
	 * @return dest对象
	 */
	public static <T> T beanEvaluate(T src, T dest, String[] ignoreMethods)
	{

		Map<String, String> ignoreMap = new HashMap<String, String>();
		if (null != ignoreMethods)
		{
			for (int i = 0; i < ignoreMethods.length; ++i)
			{
				ignoreMap.put(ignoreMethods[i], ignoreMethods[i]);
			}
		}
		Map<String, Class[]> paramMap = new HashMap<String, Class[]>();
		Method[] destm = dest.getClass().getDeclaredMethods();
		for (int i = 0; i < destm.length; ++i)
		{
			String name = destm[i].getName();
			if (name.substring(0, 3).equals("set") && !ignoreMap.containsKey(name))
			{
				paramMap.put(destm[i].getName(), destm[i].getParameterTypes());
			}
		}

		Method[] m = src.getClass().getDeclaredMethods();
		try
		{
			for (int i = 0; i < m.length; ++i)
			{
				String name = m[i].getName();
				if (name.substring(0, 3).equals("get") && !ignoreMap.containsKey(name))
				{
					String setName = "set" + name.substring(3);
					if (ignoreMap.containsKey(setName))
					{// 忽略
						continue;
					}
					if(!paramMap.containsKey(setName)){
						logger.debug(setName+ "不存在 目标对象中，忽略...");
						//不存在目标方法
						continue;
					}
					
					// get方法，取源对象中的值，只有当值不为null时，才会被更新
					Object vo = m[i].invoke(src, null);
					if (null != vo)
					{
						Class[] ptypes = paramMap.get(setName);
						Method destMethod = dest.getClass().getMethod(setName, ptypes);
						destMethod.invoke(dest, vo);
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dest;
	}

	
	/**
	 * 将src列表中的属性值赋给dest列表对象中对应的属性
	 * 
	 * @param <T>
	 * @param srclst
	 *            源对象列表
	 * @param destlst
	 *            目标对象列表
	 * @param ignoreSetMethods
	 *            目标对象中要忽略的set方法
	 * @return destlst对象列表
	 */
	public static <T> List<T> beanBatchEvaluate(List<T> srclst, List<T> destlst, String[] ignoreSetMethods)
	{

		Map<String, String> ignoreMap = new HashMap<String, String>();
		if (null != ignoreSetMethods)
		{
			for (int i = 0; i < ignoreSetMethods.length; ++i)
			{
				ignoreMap.put(ignoreSetMethods[i], ignoreSetMethods[i]);
			}
		}

		Map<String, Class[]> paramMap = new HashMap<String, Class[]>();
		Iterator<T> it = destlst.iterator();
		T dest = it.next();
		Method[] destm = dest.getClass().getDeclaredMethods();
		for (int i = 0; i < destm.length; ++i)
		{
			String name = destm[i].getName();
			if (name.substring(0, 3).equals("set"))
			{
				paramMap.put(destm[i].getName(), destm[i].getParameterTypes());
			}
		}

		it = srclst.iterator();
		T src = it.next();
		Method[] m = src.getClass().getDeclaredMethods();
		try
		{

			Iterator<T> srcit = srclst.iterator();
			Iterator<T> destit = destlst.iterator();
			while (destit.hasNext() && srcit.hasNext())
			{
				dest = destit.next();
				src = srcit.next();
				for (int i = 0; i < m.length; ++i)
				{
					String name = m[i].getName();
					if (name.substring(0, 3).equals("get"))
					{// get方法，取源对象中的值，只有当值不为null时，才会被更新
						Object vo = m[i].invoke(src, null);
						if (null != vo)
						{
							String setName = "set" + name.substring(3);
							if (null != ignoreMap.get(setName))
							{// 忽略
								continue;
							}

							Class[] ptypes = paramMap.get(setName);
							Method destMethod = dest.getClass().getMethod(setName, ptypes);
							destMethod.invoke(dest, vo);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return destlst;
	}

	/**
	 * 字段对应属性的编码规则 去除下画线,以大小写分隔
	 * 
	 * @param colName
	 * @param encodeType
	 * @return
	 */
	public static String propertyEncode(String colName)
	{
		String prop = "";
		String tmp[] = colName.split("_");
		for (int i = 0; i < tmp.length; ++i)
		{
			String first = tmp[i].substring(0, 1);
			String second = null;
			if (tmp[i].length() > 1)
			{
				second = tmp[i].substring(1);
			}
			if (null == second)
			{
				tmp[i] = first.toUpperCase();
			}
			else
			{
				tmp[i] = first.toUpperCase() + second.toLowerCase();
			}
			prop += tmp[i];
		}
		return prop;
	}

	/**
	 * 字段对应属性的编码规则 去除下画线,以大小写分隔
	 * 
	 * @param colName
	 * @param encodeType
	 * @return
	 */
	public static String columnEncode(String colName)
	{

		String tmp[] = colName.split("_");
		tmp[0] = tmp[0].toLowerCase();
		String prop = tmp[0];
		for (int i = 1; i < tmp.length; ++i)
		{
			String first = tmp[i].substring(0, 1);
			String second = null;
			if (tmp[i].length() > 1)
			{
				second = tmp[i].substring(1);
			}
			if (null == second)
			{
				tmp[i] = first.toUpperCase();
			}
			else
			{
				tmp[i] = first.toUpperCase() + second.toLowerCase();
			}
			prop += tmp[i];
		}
		return prop;
	}

	public static Map<String, Integer> buildPropertyMap(ResultSet rs, Map<String, String> propMap, int encodeType, Set<String> excludeCol) throws Exception
	{
		// 解析字段
		Map<String, Integer> colTypeMap = new HashMap<String, Integer>();
		int cnt = rs.getMetaData().getColumnCount();
		for (int i = 1; i <= cnt; ++i)
		{
			String colName = rs.getMetaData().getColumnName(i).toUpperCase();
			// 检查是否是要排除的字段
			if (null != excludeCol && excludeCol.contains(colName))
			{
				continue;
			}
			// 记录字段类型
			colTypeMap.put(colName, rs.getMetaData().getColumnType(i));

			if (propMap.containsKey(colName))
			{// 已经存在propMap中
				continue;
			}

			// 不存在则加入
			String val = colName;
			if (0 == encodeType)
			{
				val = "set" + propertyEncode(colName);
			}
			propMap.put(colName, val);
		}

		
		return colTypeMap;
	}

	/**
	 * 创建数据库字段的元数据MAP
	 * 
	 * @author 张尧伟
	 * @param rs
	 * @param propMap
	 * @param encodeType
	 * @param excludeCol
	 * @return key字段名 -> Map(type -> 列类型,index -> 列号,scale -> 精度 ,precision -> 长度)
	 * @throws Exception
	 */
	public static Map<String, Map<String, Integer>> buildMetaMap(ResultSet rs, Map<String, String> propMap, int encodeType, Set<String> excludeCol) throws Exception
	{
		// 解析字段
		Map<String, Map<String, Integer>> colMetaMap = new HashMap<String, Map<String, Integer>>();
		int cnt = rs.getMetaData().getColumnCount();
		for (int i = 1; i <= cnt; ++i)
		{
			String colName = rs.getMetaData().getColumnName(i);
			// 检查是否是要排除的字段
			if (null != excludeCol && excludeCol.contains(colName))
			{
				continue;
			}

			if (propMap.containsKey(colName))
			{// 已经存在propMap中
				continue;
			}

			// 解析元数据信息
			Map<String, Integer> metaMap = new HashMap<String, Integer>();
			// 记录字段类型
			colMetaMap.put(colName, metaMap);

			metaMap.put("type", rs.getMetaData().getColumnType(i));
			metaMap.put("index", i);
			metaMap.put("scale", rs.getMetaData().getScale(i));
			metaMap.put("precision", rs.getMetaData().getPrecision(i));
		

			// 不存在则加入
			String val = colName;
			if (0 == encodeType)
			{
				val = columnEncode(colName);
			}
			propMap.put(colName, val);
		}

		return colMetaMap;
	}
	
	public static Date getDateByWeek(int year, int week, boolean start)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);

		if (start)
		{
			cal.set(Calendar.WEEK_OF_YEAR, week);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			// cal.setTimeInMillis(cal.getTimeInMillis()+7*24*60*60*1000);
		}
		else
		{
			cal.set(Calendar.WEEK_OF_YEAR, week + 1);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			// cal.setTimeInMillis(cal.getTimeInMillis()-2*24*60*60*1000);
		}
		return cal.getTime();
	}

	/**
	 * 根据年,周取此周对应的开始日期或结束日期
	 * 
	 * @param year
	 * @param week
	 * @param start
	 *            true 取开始日期 false取周结束日期
	 * @return String yyyy-mm-dd
	 */
	public static String getDayByWeek(int year, int week, boolean start)
	{
		Date dt = Util.getDateByWeek(year, week, start);
		return new SimpleDateFormat("yyyy-MM-dd").format(dt);
	}

	/**
	 * 取当前日期所属月的周次
	 * 
	 * @return
	 */
	public static int getWeekOfCurrentMonth()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		return cal.get(Calendar.WEEK_OF_MONTH);
	}
	

	/**
	 * 取当前周的开始日期字符串
	 * 
	 * @return String yyyy-mm-dd
	 */
	public static String getFirstDayOfCurrentWeek()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		return Util.getDayByWeek(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR), true);
	}

	/**
	 * 取当前周的结束日期字符串
	 * 
	 * @return String yyyy-mm-dd
	 */
	public static String getLastDayofCurrentWeek()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		return Util.getDayByWeek(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR), false);
	}

	/**
	 * 取当前周的开始日期
	 * 
	 * @return Date
	 */
	public static Date getFirstDateOfCurrentWeek()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		return Util.getDateByWeek(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR), true);
	}

	/**
	 * 取指定时间的年份所属的第一天
	 * 
	 * @author 张尧伟
	 * @param time
	 * @return Date
	 */
	public static Date getFirstDateOfYear(long time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(time));
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 取当前周的结束日期
	 * 
	 * @return Date
	 */
	public static Date getLastDateOfCurrentWeek()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		return Util.getDateByWeek(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR), false);
	}

	/**
	 * 取得src加days天后的日期
	 * 
	 * @param src
	 * @param days
	 * @return Date
	 */
	public static Date getAddDate(Date src, Integer days)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(src);
		cal.add(Calendar.DAY_OF_MONTH, days.intValue());

		return cal.getTime();
	}

	public static Date getAddDateForHours(Date src, Integer hours)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(src);
		cal.add(Calendar.HOUR_OF_DAY, hours.intValue());

		return cal.getTime();
	}

	/**
	 * 返回从src开始的days天后的所有日期列表
	 * 
	 * @param src
	 * @param days
	 * @return List<Date>
	 */
	public static List<Date> getDateList(Date src, Integer days)
	{
		List<Date> lst = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(src);
		lst.add(src);
		for (int i = 0; i < (days.intValue() - 1); ++i)
		{
			cal.add(Calendar.DAY_OF_MONTH, 1);
			lst.add(cal.getTime());
		}

		return lst;
	}

	/**
	 * 返回start开始到end结束的日期列表
	 * 
	 * @author 张尧伟
	 * @param start
	 * @param end
	 * @return static List<Date>
	 */
	public static List<Date> getDateList(Date start, Date end)
	{
		List<Date> lst = new ArrayList<Date>();
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(start);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);

		lst.add(start);
		while (startCal.get(Calendar.DAY_OF_YEAR) < endCal.get(Calendar.DAY_OF_YEAR))
		{
			startCal.add(Calendar.DAY_OF_YEAR, 1);
			lst.add(startCal.getTime());
		}

		return lst;
	}

	/**
	 * 返回start开始到end结束的日期列表
	 * 
	 * @author 张尧伟
	 * @param start
	 * @param end
	 * @param interval
	 *            小时间隔
	 * @return static List<Date>
	 */
	public static List<Date> getDateList(Date start, Date end, int interval)
	{
		List<Date> lst = new ArrayList<Date>();
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(start);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);

		lst.add(start);
		while (startCal.get(Calendar.HOUR_OF_DAY) < endCal.get(Calendar.HOUR_OF_DAY))
		{
			if ((startCal.get(Calendar.HOUR_OF_DAY) + interval) < endCal.get(Calendar.HOUR_OF_DAY))
			{
				startCal.add(Calendar.HOUR_OF_DAY, interval);
			}
			else
			{
				startCal.add(Calendar.HOUR_OF_DAY, endCal.get(Calendar.HOUR_OF_DAY) - startCal.get(Calendar.HOUR_OF_DAY));
			}
			lst.add(startCal.getTime());
		}

		return lst;
	}

	/**
	 * 返回start到end之间的小时间隔时间列表
	 * 
	 * @author 张尧伟
	 * @param start
	 *            　开始日期时间
	 * @param end
	 *            结束日期时间
	 * @param interval
	 *            小时间隔数
	 * @return List<Map<String,Date>>,map中取开始日期key:start，取结束日期key:end
	 */
	public static List<Map<String, Date>> getDateHourIntervalList(Date start, Date end, int interval)
	{
		List<Map<String, Date>> lst = new ArrayList<Map<String, Date>>();
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(start);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);

		Calendar tmpCal = Calendar.getInstance();
		while (startCal.get(Calendar.HOUR_OF_DAY) < endCal.get(Calendar.HOUR_OF_DAY))
		{
			Map<String, Date> map = new HashMap<String, Date>();
			if ((startCal.get(Calendar.HOUR_OF_DAY) + interval) < endCal.get(Calendar.HOUR_OF_DAY))
			{
				map.put("start", startCal.getTime());

				startCal.add(Calendar.HOUR_OF_DAY, interval);

				tmpCal.setTime(startCal.getTime());
				tmpCal.add(Calendar.HOUR_OF_DAY, -1);
				tmpCal.set(Calendar.MINUTE, endCal.get(Calendar.MINUTE));
				tmpCal.set(Calendar.SECOND, endCal.get(Calendar.SECOND));
				map.put("end", tmpCal.getTime());

			}
			else
			{
				map.put("start", startCal.getTime());

				startCal.add(Calendar.HOUR_OF_DAY, endCal.get(Calendar.HOUR_OF_DAY) - startCal.get(Calendar.HOUR_OF_DAY));

				tmpCal.setTime(startCal.getTime());
				// tmpCal.add(Calendar.HOUR_OF_DAY, -1);
				tmpCal.set(Calendar.MINUTE, endCal.get(Calendar.MINUTE));
				tmpCal.set(Calendar.SECOND, endCal.get(Calendar.SECOND));
				map.put("end", tmpCal.getTime());

			}
			lst.add(map);
		}

		return lst;
	}

	/**
	 * 根据start与end日期区间，生成以interval为间隔的日期MAP
	 * 
	 * @author 张尧伟
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param interval
	 *            日期间隔天数
	 * @return List<Map<String,Date>>,map中取开始日期key:start，取结束日期key:end
	 */
	public static List<Map<String, Date>> getDateIntervalList(Date start, Date end, int interval)
	{
		List<Map<String, Date>> lst = new ArrayList<Map<String, Date>>();
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(start);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);

		Calendar tmpCal = Calendar.getInstance();
		while (startCal.get(Calendar.DAY_OF_YEAR) < endCal.get(Calendar.DAY_OF_YEAR))
		{
			Map<String, Date> map = new HashMap<String, Date>();
			if ((startCal.get(Calendar.DAY_OF_YEAR) + interval) < endCal.get(Calendar.DAY_OF_YEAR))
			{
				map.put("start", startCal.getTime());

				startCal.add(Calendar.DAY_OF_YEAR, interval);

				tmpCal.setTime(startCal.getTime());
				tmpCal.add(Calendar.DAY_OF_YEAR, -1);
				map.put("end", tmpCal.getTime());

			}
			else
			{
				map.put("start", startCal.getTime());

				startCal.add(Calendar.DAY_OF_YEAR, endCal.get(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR));

				tmpCal.setTime(startCal.getTime());
				// tmpCal.add(Calendar.HOUR_OF_DAY, -1);
				tmpCal.set(Calendar.DAY_OF_YEAR, endCal.get(Calendar.DAY_OF_YEAR));

				map.put("end", tmpCal.getTime());

			}
			lst.add(map);
		}

		return lst;
	}

	public static int getYear(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getMonth(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * get days by month of the paramter date
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 将日期的时间h,m,置为0
	 * 
	 * @author 张尧伟
	 * @param date
	 * @return Date
	 */
	public static Date clearTime(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 生成in条件表达式
	 * 
	 * @author 张尧伟
	 * @param co
	 * @param field
	 *            字段名称
	 * @param preOperator
	 *            前置操作符 如 and or 等
	 * @return expresion
	 */
	public static String buildInExp(List<Integer> lst, String field, String preOperator)
	{
		String in = null;
		if (null == lst || lst.size() == 0)
		{
			return "";
		}

		Iterator<Integer> it = lst.iterator();
		while (it.hasNext())
		{
			Integer intid = it.next();
			if (null == in)
			{
				in = intid + "";
			}
			else
			{
				in += "," + intid;
			}
		}

		if (lst.size() == 1)
		{
			in = field + " = " + in;
		}
		else
		{
			in = field + " in (" + in + ")";
		}

		return (preOperator + " " + in);

	}

	/**
	 * 生成in条件表达式
	 * 
	 * @author 张尧伟
	 * @param co
	 * @param field
	 *            字段名称
	 * @param preOperator
	 *            前置操作符 如 and or 等
	 * @return expresion
	 */
	public static String buildNotInExp(List<Long> lst, String field, String preOperator)
	{
		String in = null;
		if (null == lst || lst.size() == 0)
		{
			return "";
		}

		Iterator<Long> it = lst.iterator();
		while (it.hasNext())
		{
			Long intid = it.next();
			if (null == in)
			{
				in = intid + "";
			}
			else
			{
				in += "," + intid;
			}
		}

		if (lst.size() == 1)
		{
			in = field + " <> " + in;
		}
		else
		{
			in = field + " not in (" + in + ")";
		}

		return (preOperator + " " + in);

	}

	/**
	 * 创建一个以split分隔的字符串
	 * 
	 * @author 张尧伟
	 * @param buf
	 * @param str
	 * @param split
	 * @return StringBuffer
	 */
	public static StringBuffer buildSequenceString(StringBuffer buf, String str, String split)
	{
		if (buf.length() > 0)
		{
			buf.append(split);
			buf.append(str);
		}
		else
		{
			buf.append(str);
		}
		return buf;
	}

	/**
	 * 
	 * 提供精确的加法运算。 * @param v1 被加数
	 * 
	 * @param v2
	 *            加数 * @return 两个参数的和
	 */

	public static double add(double v1, double v2)
	{

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/**
	 * 
	 * 提供精确的减法运算。 * @param v1 被减数
	 * 
	 * @param v2
	 *            减数 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2)
	{

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 
	 * 提供精确的乘法运算。 * @param v1 被乘数
	 * 
	 * @param v2
	 *            乘数 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2)
	{

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}

	/**
	 *提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale)

	{

		return round(v, scale, BigDecimal.ROUND_HALF_UP);

	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @param mode
	 *            指定的舍入模式
	 * 
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale, int mode)

	{

		if (scale < 0)

		{

			throw new IllegalArgumentException("The scale must be a positive integer or zero");

		}

		BigDecimal b = BigDecimal.valueOf(v);

		return b.setScale(scale, mode).doubleValue();

	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_UP
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果，以字符串格式返回
	 */

	public static String round(String v, int scale)
	{
		double tmp = round(Double.valueOf(v), scale, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(tmp);

	}

	/**
	 * 判断字符串是否为空或者是空格
	 * 
	 * @author liuguo
	 * @since 2009-5-22下午02:52:13
	 * @param
	 * @return 如果为空或者是空格则返回true，否则返回false。
	 */
	public static boolean isNvl(String content)
	{
		return (content == null || content.trim().length() == 0) ? true : false;
	}
	public static boolean isNvl(StringBuffer content)
	{
		return (content == null || content.length() == 0) ? true : false;
	}

	public static boolean isNvl(Object obj)
	{
		return (null == obj);
	}

	public static String nvl(String v, String defv)
	{
		return isNvl((Object) v) ? defv : v;
	}

	public static String nvl(Integer v, String defv)
	{
		return isNvl((Object) v) ? defv : v.toString();
	}

	public static String nvl(Long v, String defv)
	{
		return isNvl((Object) v) ? defv : v.toString();
	}

	public static String nvl(Double v, String defv)
	{
		return isNvl((Object) v) ? defv : v.toString();
	}

	public static String nvl(Date v, String defv)
	{
		return isNvl((Object) v) ? defv : v.toString();
	}

	public static String nvl(BigDecimal v, String defv)
	{
		return isNvl((Object) v) ? defv : v.toString();
	}

	public static String nvl(Float v, String defv)
	{
		return isNvl((Object) v) ? defv : v.toString();
	}

	public static Integer nvl(Integer v, Integer defv)
	{
		return isNvl((Object) v) ? defv : v;
	}

	// 字節到十六進制串轉換
	public static String byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs += ("0" + stmp);
			else
				hs += stmp;
		}
		return hs.toUpperCase();
	}

	// 十六進制串到字節轉換
	public static byte[] hex2byte(byte[] b)
	{
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数!");

		byte[] b2 = new byte[b.length / 2];

		for (int n = 0; n < b.length; n += 2)
		{
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 去除字符串中的回车字符
	 * 
	 * @author 张尧伟
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static String clearSpecialChar(String str, String pattern)
	{
		if(Util.isNvl(str)){
			return str;
		}
		
		String tp = "\\s*|\t|\r|\n";
		if (null != pattern)
		{
			tp = pattern;
		}
		Pattern p = Pattern.compile(tp);
		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		return after;
	}

	public static String HTML2JSON(String str)
	{
		// str含有HTML标签的文本
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		// str = str.replaceAll(" ","&nbsp;");
		str = str.replaceAll("\n", "<br>");
		str = str.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		str = str.replaceAll("&", "&amp;");
		return str;
	}

	/**
	 * 当指定的string buffer 长度大于0时，追加 chars
	 * 
	 * @author 张尧伟
	 * @param sb
	 * @return StringBuffer
	 */
	public static StringBuffer appendChars(StringBuffer sb, String chars)
	{
		if (sb.length() > 0)
		{
			sb.append(chars);
		}
		return sb;
	}

	public static Map<String, Class[]> parseClassMethod(Class clazz, String method)
	{
		Map<String, Class[]> paramMap = new HashMap<String, Class[]>();
		Class clz = clazz;
		String rootName = "java.lang.Object";
		String clzName = clz.getName();
		while (!rootName.equals(clzName))
		{
			Method[] destm = clz.getDeclaredMethods();
			for (int i = 0; i < destm.length; ++i)
			{
				String name = destm[i].getName();
				destm[i].setAccessible(true);
				if (name.substring(0, 3).equals(method) && !paramMap.containsKey(name))
				{
					paramMap.put(name, destm[i].getParameterTypes());

				}
			}

			clz = clz.getSuperclass();
			clzName = clz.getName();
			logger.debug(clzName);
		}
		return paramMap;
	}

	public static Map<String, Class[]> parseClassMethod(Class clazz, String method,Class root)
	{
		Map<String, Class[]> paramMap = new HashMap<String, Class[]>();
		Class clz = clazz;
		String clzName = clz.getName();
		do
		{
			Method[] destm = clz.getDeclaredMethods();
			for (int i = 0; i < destm.length; ++i)
			{
				String name = destm[i].getName();
				destm[i].setAccessible(true);
				if (name.substring(0, 3).equals(method) && !paramMap.containsKey(name))
				{
					paramMap.put(name, destm[i].getParameterTypes());

				}
			}

			if(clz.equals(root))
			{
				break;
			}
			
			clz = clz.getSuperclass();
			clzName = clz.getName();
			logger.debug(clzName);
		}while(true);
		return paramMap;
	}
	
	public static BufferedImage resize(BufferedImage source, int width, int height)
	{
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		Image target = null;
		int w = source.getWidth();
		int h = source.getHeight();
		if (w < width)
		{
			width = w;
		}
		if (h < height)
		{
			height = h;
		}
		
		if (type == BufferedImage.TYPE_CUSTOM)
		{ // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		}
		else{
			target = source.getScaledInstance(width, height, Image.SCALE_SMOOTH) ;//new BufferedImage(width, height, type);
		}
		
		BufferedImage image = new BufferedImage(width, height, type);
		Graphics canves = image.createGraphics();
		canves.drawImage(target, 0, 0, null); // 绘制缩小后的图
		canves.dispose();
			
		return image;
	}

	/**
	 * 按比例绽放图片
	 * @author 张尧伟
	 * @param source
	 * @param rate 比例 ,如 0.1
	 * @return 
	 */
	public static BufferedImage resize(BufferedImage source, double rate)
	{
		int width = (int)(rate * source.getWidth());
		int height = (int)(rate * source.getHeight());
		return resize(source,width,height);
	}
	/**
	 * 按比例绽放图片
	 * @author 张尧伟
	 * @param byte[] data
	 * @param rate 比例 ,如 0.1
	 * @return 
	 * @throws IOException 
	 */
	public static BufferedImage resize(byte[] data, double rate) throws IOException
	{
		BufferedImage source = ImageIO.read(new ByteArrayInputStream(data));
		int width = (int)(rate * source.getWidth());
		int height = (int)(rate * source.getHeight());
		return resize(source,width,height);
	}
	/**
	 * 按比例绽放图片
	 * @author 张尧伟
	 * @param String file文件路径
	 * @param rate 比例 ,如 0.1
	 * @return 
	 * @throws IOException 
	 */
	public static BufferedImage resize(String file, double rate) throws IOException
	{
		BufferedImage source = ImageIO.read(new File(file));
		int width = (int)(rate * source.getWidth());
		int height = (int)(rate * source.getHeight());
		return resize(source,width,height);
	}
	/**
	 * 按比例绽放图片后，写出JPG文件流
	 * @author 张尧伟
	 * @param String file文件路径
	 * @param rate 比例 ,如 0.1
	 * @param out 输出流
	 * @return  BufferedImage
	 * @throws IOException 
	 */
	public static BufferedImage resize(String file, double rate,OutputStream out) throws IOException
	{
		BufferedImage source = ImageIO.read(new File(file));
		int width = (int)(rate * source.getWidth());
		int height = (int)(rate * source.getHeight());
		BufferedImage tmp = resize(source,width,height);
		ImageIO.write(tmp, "JPEG", out);
		return tmp;
	}
	/**
	 * 按比例绽放图片后，写出JPG文件流
	 * @author 张尧伟
	 * @param byte[] data
	 * @param rate 比例 ,如 0.1
	 * @param out 输出流
	 * @return  BufferedImage
	 * @throws IOException 
	 */
	public static BufferedImage resize(byte[] data, double rate,OutputStream out) throws IOException
	{
		BufferedImage source = ImageIO.read(new ByteArrayInputStream(data));
		int width = (int)(rate * source.getWidth());
		int height = (int)(rate * source.getHeight());
		BufferedImage tmp = resize(source,width,height);
		ImageIO.write(tmp, "JPEG", out);
		return tmp;
	}
	/**
	 * 按比例将图片保存JPG格式，PNG不转换
	 * @author 张尧伟
	 * @param from 来源图片文件名
	 * @param to 目标图片文件名
	 * @param rate 比例，如0.1
	 * @throws Exception
	 */
	public static void saveAsJPG(String from, String to, double rate) throws Exception
	{
		BufferedImage srcImage = null;
		// String ex =
		// fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
		String imgType = "JPEG";
		if (from.toLowerCase().endsWith(".png"))
		{
			imgType = "PNG";
		}
		// logger.debug(ex);
		File saveFile = new File(to);
		File fromFile = new File(from);
		srcImage = ImageIO.read(fromFile);
		srcImage = resize(srcImage, rate);
		ImageIO.write(srcImage, imgType, saveFile);
	}
	/**
	 * 另存为jpg图片，PNG不变
	 * @author 张尧伟
	 * @param stream
	 * @param to
	 * @param rate
	 * @throws Exception
	 */
	public static void saveAs(InputStream stream, String to, double rate) throws Exception
	{
		// String ex =
		// fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
		String imgType = "JPEG";
		if (to.toLowerCase().endsWith(".png"))
		{
			imgType = "PNG";
		}
		// logger.debug(ex);
		File saveFile = new File(to);
		BufferedImage img = ImageIO.read(stream);
		img = resize(img,rate);
		ImageIO.write(img, imgType, saveFile);
	}
	
	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight) throws Exception
	{
		BufferedImage srcImage = null;
		// String ex =
		// fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
		String imgType = "JPEG";
		if (fromFileStr.toLowerCase().endsWith(".png"))
		{
			imgType = "PNG";
		}
		// logger.debug(ex);
		File saveFile = new File(saveToFileStr);
		File fromFile = new File(fromFileStr);
		srcImage = ImageIO.read(fromFile);
		if (width > 0 || hight > 0)
		{
			srcImage = resize(srcImage, width, hight);
		}
		ImageIO.write(srcImage, imgType, saveFile);
	}

	/**
	 * 从包含路径的文件名中截取完整的文件名（包含扩展名）
	 * 
	 * @example 从C:\\test\test.txt或者/home/test/test.txt中获取test.txt
	 * @param fileNameWithPath
	 * @return
	 */
	public static String fetchFileNameWithExt(String fileNameWithPath)
	{
		String tmpFileName = "";
		if (fileNameWithPath.lastIndexOf("\\\\") > 0)
		{
			tmpFileName = fileNameWithPath.substring(fileNameWithPath.lastIndexOf("\\\\") + 1);
		}
		else if (fileNameWithPath.lastIndexOf("\\") > 0)
		{
			tmpFileName = fileNameWithPath.substring(fileNameWithPath.lastIndexOf("\\") + 1);
		}
		else if (fileNameWithPath.lastIndexOf("/") > 0)
		{
			tmpFileName = fileNameWithPath.substring(fileNameWithPath.lastIndexOf("/") + 1);
		}
		else
		{
			tmpFileName = fileNameWithPath;
		}
		return tmpFileName;
	}

	/**
	 * 比较两个数大小，返回大值
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Number getMaxValue(Number v1, Number v2)
	{
		return v1.doubleValue() - v2.doubleValue() > Double.MIN_VALUE ? v1 : v2;
	}

	/**
	 * 比较两个数大小，返回小值
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Number getMinValue(Number v1, Number v2)
	{
		return v1.doubleValue() - v2.doubleValue() > Double.MIN_VALUE ? v2 : v1;
	}

	/**
	 * 如果此对象为null，返回一个对象的默认值。
	 * 
	 * @param value
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static Object getDefaultValue(Object value, Object defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			return value;
		}
	}

	/**
	 * l1除以l2，如果商不是整数，则取比商大的最小整数，如果商是整数，则返回整数。
	 * 
	 * @param l1
	 *            被除数 正数
	 * @param l2
	 *            除数 正数
	 * @return
	 */
	public static Long div2Big(Long l1, Long l2)
	{
		long resultL = l1.longValue() / l2.longValue();
		double resultD = Double.parseDouble(l1.toString()) / Double.parseDouble(l2.toString());
		if (Math.abs(resultL - resultD) < Double.MIN_VALUE)
		{
			return resultL;
		}
		else
		{
			return resultL + 1;
		}
	}

	/**
	 * 将pdf文件转化成swf文件
	 * 
	 * @param srcFile
	 *            文件的绝对路径
	 * @param destFile
	 *            目标路径
	 * @param swftoolsPath
	 *            swftool的路径
	 * @return -1：源文件不存在,-2:格式不正确,-3：发生异常,0:转化成功
	 */
	public static int convertPDF2SWF(String swftoolsPath, String param, String srcFile, String destFile, long timeout) throws Exception
	{

		int pos = srcFile.lastIndexOf(".");
		if (pos <= 0)
		{
			return -2;
		}

		String fileExt = srcFile.substring(pos + 1);
		if (!fileExt.equalsIgnoreCase("pdf"))
		{// 判断文件是否是pdf格式的文件
			return -2;
		}
		File file = new File(srcFile);
		if (!file.exists())
		{// 判断源文件是否存在
			return -1;
		}

		StringBuffer cmd = new StringBuffer();
		cmd.append(swftoolsPath);
		cmd.append(" ");
		cmd.append(param);
		cmd.append(" ");
		cmd.append(srcFile);
		cmd.append(" -o ");
		cmd.append(destFile);
		Process pro = Runtime.getRuntime().exec(cmd.toString());
		BufferedReader buffer = new BufferedReader(new InputStreamReader(pro.getInputStream()));
		long startTime = System.currentTimeMillis();

		while (buffer.readLine() != null)
		{
			long curtime = System.currentTimeMillis();
			if ((curtime - startTime) > timeout)
			{
				throw new Exception("PDF转换超时" + timeout);
			}
		}

		return pro.exitValue();
	}

	/**
	 * 判断是否能够整除。
	 * 
	 * @param arg0
	 *            被除数
	 * @param arg1
	 *            除数
	 * @return
	 */
	public static boolean isExactDiv(Long arg0, Long arg1)
	{
		if (arg1 == 0)
		{
			return false;
		}

		return div2Big(arg0, arg1) == arg0 / arg1 ? true : false;
	}

	public final static String MD5(String s)
	{
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		try
		{
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++)
			{
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	
	public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            // Create Hex String
            String encode = byte2Hex(digest.digest());
            return encode;
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
	
	public static String SHA256(String decript){
        try {
        	MessageDigest digest = MessageDigest.getInstance("SHA-256");
        	digest.update(decript.getBytes("UTF-8"));
            return byte2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
    
	
	/**
	 * 判断给定日期是否为月末的一天
	 * 
	 * @param date
	 * @return true:是|false:不是
	 */
	public static boolean isLastDayOfMonth(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1)
		{
			return true;
		}
		return false;
	}
	
	/**  
    * 编码 BASE64 
    * @param data  
    * @param isRFC822 是否为标准的RFC822要求，即转换时每76个字符就换行
    * @return String  
    */    
   public static String encodeBASE64(byte[] data,boolean isRFC822 ){    
	   String str =  new sun.misc.BASE64Encoder().encode(data);  
	   if(isRFC822){//标准的，不删除换行
		   return str;
	   }else{//自定义的，删除换行
		   return str.replaceAll("[\\s*\t\n\r]", "");
	   }
   }    
   
   /**  
    * 解码  
    * @param str  
    * @return string  
 * @throws IOException 
    */    
   public static byte[] decodeBASE64(String str) throws IOException{    
       sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
       byte[] data = decoder.decodeBuffer( str );    
       return data;    
   }   
   

   /*

	public static boolean isJSONArray(String json){
		return json.startsWith("[") && json.endsWith("]");
	}
	public static void put(JSONObject jo,String key,Object value) throws BaseException
	{
		try{
			jo.put(key, value);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	public static Object get(JSONObject jo,String key) throws BaseException
	{
		try{
			return jo.get(key);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	public static JSONObject get(JSONArray ja,int index) throws BaseException
	{
		try{
			return ja.getJSONObject(index);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	
	public static String getString(JSONArray ja,int index) throws BaseException
	{
		try{
			return ja.getString(index);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	public static String getString(JSONObject jo,String key) throws BaseException
	{
		try{
			return jo.getString(key);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	public static Long getLong(JSONObject jo,String key) throws BaseException
	{
		try{
			return jo.getLong(key);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}

	public static Integer getInt(JSONObject jo,String key) throws BaseException
	{
		try{
			return jo.getInt(key);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	public static JSONArray getJSONArray(JSONObject jo,String key) throws BaseException
	{
		try{
			return jo.getJSONArray(key);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	public static JSONObject getJSONObject(JSONObject jo,String key) throws BaseException
	{
		try{
			return jo.getJSONObject(key);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	public static JSONArray getJSONArray(String str) throws BaseException
	{
		try{
			return new JSONArray(str);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	public static JSONObject getJSONObject(String str) throws BaseException
	{
		try{
			return new JSONObject(str);
		}catch(JSONException e){
			throw new DSException(ErrConst.ERR_PARAM_JSON,e);
		}
	}
	    
    */
   
   /**
	 * n位随机码
	 * @param 随机数位数长度
	 * @return
	 */
	public static String random(int n) {
		Random random = new Random();
		String tmp = "";
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			tmp += rand;
		}
		return tmp;
	}
	
	/**
	 * 屏蔽账号 开头保留多少位 末尾保留多少位
	 * @return
	 */
	public static String shieldAccount(int head, int end, boolean isshield, String account) {
		if (account == null || "".equals(account)) {
			return "";
		}
		int total = head + end;
		if (account.length() <= total) {
			if (isshield) {// 当位数不够的时候，是否屏蔽所有的
				int length = account.length();
				StringBuilder sb = new StringBuilder();
				while (length > 0) {
					sb.append("*");
					length--;
				}
				return sb.toString();
			} else {
				return account;
			}
		}
//		int sAccount = account.length()-total;//屏蔽成*的个数
		int sAccount = 4;
		StringBuilder sb = new StringBuilder();
		while (sAccount > 0) {
			sb.append("*");
			sAccount--;
		}
		return account.substring(0, head) + sb.toString() + account.substring(account.length() - end);
	}
	
	

   
   public static String md5(byte[] b) {
       try {
       	MessageDigest md = MessageDigest.getInstance("MD5");
       	 md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuffer outStrBuf = new StringBuffer(32);
            for (int i = 0; i < hash.length; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16) {
                	outStrBuf.append('0');
                }
                outStrBuf.append(Integer.toString(v, 16).toLowerCase());
            }
            return outStrBuf.toString();
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
           return new String(b);
       }
      
   }
   public static String md5(String data) {
       try {
       	MessageDigest md = MessageDigest.getInstance("MD5");
       	 md.reset();
            md.update(data.getBytes("utf-8"));
            byte[] hash = md.digest();
            StringBuffer outStrBuf = new StringBuffer(32);
            for (int i = 0; i < hash.length; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16) {
                	outStrBuf.append('0');
                }
                outStrBuf.append(Integer.toString(v, 16).toLowerCase());
            }
            return outStrBuf.toString();
       } catch (Exception e) {
           e.printStackTrace();
           return "";
       }
	       
	}
	
	public static String sign(TreeMap<String,String> params,String key){
		params.put("key", key);
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			if(entry.getValue()!=null&&entry.getValue().length()>0){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		logger.debug("明文:"+sb.toString());
		String sign = md5(sb.toString()).toUpperCase();
		logger.debug("密文:"+sign);
		params.remove("key");
		return sign;
	}
	public static void sign(String field,TreeMap<String,String> map,String key) {
		String str = sign(map, key);
		map.put(field, str);
	}
	public static boolean validSign(TreeMap<String, String> param, String appkey) throws Exception {
		if (param != null && !param.isEmpty()) {
			if (!param.containsKey("sign")) {
				return false;
			}
			param.put("key", appkey);// 将分配的appkey加入排序
			StringBuilder sb = new StringBuilder();
			String sign = param.get("sign").toString();
			param.remove("sign");
			for (String key : param.keySet()) {
				String value = param.get(key);
				if (!StringUtils.isEmpty(value))
					sb.append(key).append("=").append(value).append("&");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			String blank = sb.toString();
			return sign.toLowerCase().equals(md5(blank.getBytes("utf-8")));
		}
		return false;
	}
	
	public static JsonNode parseJSON(String json) throws XoneException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readTree(json);
		} catch (IOException e) {
			XoneException.throwing(Error.PARAM_JSON,e);
		}
		return null;
	}
	public static JsonNode parseJSONEx(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readTree(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static <T> String buildJSON(T value) throws XoneException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			XoneException.throwing(Error.PARAM_JSON,e);
		}
		return null;
	}
	
	public static <T> String buildJSONEx(T value) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> JsonNode buildJsonNode(T value) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(value);
	}
	
	public static <T> ObjectNode buildObjectNode(T value) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(value);
	}
}
