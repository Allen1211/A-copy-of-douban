package com.allen.douban.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	private final static String charset = "UTF-8";
	
	/**
	 * 读取request中传过来的JSON字符串，转为JSON对象
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static JSONObject getResquestJSONObject(HttpServletRequest request) throws IOException {
		String jsonString = getRequestJSONString(request);
		//JSON字符串转变为JSON对象
		JSONObject json = new JSONObject();
		/**
		 * 如果含有非法html输入，经过滤后json解析会报错。
		 */
		try {
			json = JSONObject.parseObject(jsonString);
		} catch (JSONException e) {
			System.out.println("json转换失败，非法数据输入?");			
			json.put("err", "输入数据有误");
		}
		return json;
	}
	public static JSONObject getResquestJSONObjectNoEscape(HttpServletRequest request) throws IOException {
		String jsonString = getRequestJSONStringNoEscape(request);
		//JSON字符串转变为JSON对象
		JSONObject json = new JSONObject();
		/**
		 * 如果含有非法html输入，经过滤后json解析会报错。
		 */
		try {
			json = JSONObject.parseObject(jsonString);
		} catch (JSONException e) {
			System.out.println("json转换失败，非法数据输入?");			
			json.put("err", "输入数据有误");
		}
		return json;
	}
	public static JSONArray getResquestJSONArray(HttpServletRequest request) throws IOException {
		JSONArray jsonArray = new JSONArray();
		try {
			String jsonString = getRequestJSONString(request);
			jsonArray = JSONArray.parseArray(jsonString);
		} catch (JSONException e) {
			System.out.println("json转换失败，非法数据输入?");			
		}
		return jsonArray;
	}
	
	public static String getRequestJSONString(HttpServletRequest request) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), charset));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
//		System.out.println(sb.toString());
		return sb.toString();
	}
	public static String getRequestJSONStringNoEscape(HttpServletRequest request) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), charset));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
//		System.out.println(sb.toString());
		return sb.toString();
	}

}
