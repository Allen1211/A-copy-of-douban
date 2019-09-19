package com.allen.douban.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.allen.douban.bean.ResultData;

public class ResultUtil {
	// 统一结果集对象
	private static ResultData<Object> result = new ResultData<>();
	
	/**
	 * 
	 * @param response
	 * @param data
	 * @throws IOException
	 */
	public static void successResponse(HttpServletResponse response, Object data) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		String result = successJSON(data);
		response.getWriter().write(result);
	}
	
	/**
	 * 
	 * @param response
	 * @param data
	 * @throws IOException
	 */
	public static void successResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		String result = successJSON();
		response.getWriter().write(result);
	}
	
	/**
	 * 
	 * @param response
	 * @param data
	 * @throws IOException
	 */
	public static void failResponse(HttpServletResponse response, Integer code, String msg) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		String result = failJSON(code,msg);
		response.getWriter().write(result);
	}
	
	/**
	 * 成功，并返回数据
	 * @param data
	 * @return
	 */
	public static String successJSON(Object data){
		result.setCode(200);
		result.setMsg("success");
		result.setData(data);
		return parseJSON(result);
	}
	
	/**
	 * 成功，无数据返回
	 */
	public static String successJSON(){
		return successJSON(null);
	}
	
	/**
	 * 失败，传入失败码和错误信息
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String failJSON(Integer code, String msg){
		result.setCode(code);
		result.setMsg(msg);
		result.setData(null);
		return parseJSON(result);
	}
	
	/**
	 * 将结果封装到JSON字符串中
	 * 
	 */
	private static String parseJSON(ResultData result) {
		String resultStr = JSONObject.toJSONString(result);
		return resultStr;
	}
}
