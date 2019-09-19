package com.allen.douban.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@WebFilter(filterName = "encodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if (request.getMethod().equals("GET")) {
			request = new EncordingWrapper(request, "UTF-8");
		}else {
			request.setCharacterEncoding("UTF-8");
		}
		chain.doFilter(request, resp);
	}

	@Override
	public void destroy() {

	}
}

class EncordingWrapper extends HttpServletRequestWrapper {
	private String ENCORDING;

	public EncordingWrapper(HttpServletRequest request, String ENCODING) {
		super(request);
		// TODO Auto-generated constructor stub
		this.ENCORDING = ENCODING;
	}
	
	@Override
	public String getParameter(String name) {
		String value = super.getRequest().getParameter(name);
		return encode(value);
	}
	
	@Override 
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if(values == null) {
			return null;
		}
		for (int i = 0; i < values.length; i++) {
			values[i] = encode(values[i]);
		}
		return values;
	}
	@Override
	public Map<String, String[]> getParameterMap(){
		Map<String,String[]> map = super.getParameterMap();
		if(map == null) {
			return null;
		}
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String[] values = map.get(key);
			for (int i = 0; i < values.length; i++) {
				values[i] = encode(values[i]);
			}
			map.replace(key, values);
		}
		return map;
	}
	private String encode(String value) {
		if (value != null) {
			try {
				byte[] bytes = value.getBytes("ISO-8859-1");
				value = new String(bytes, this.ENCORDING);
			} catch (UnsupportedEncodingException e) {
				System.out.println("Class encodingFilter: 不支持的编码");
				throw new RuntimeException();
			}
		}
		return value;
	}
}
