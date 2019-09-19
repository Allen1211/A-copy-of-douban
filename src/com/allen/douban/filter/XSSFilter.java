package com.allen.douban.filter;


import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.allen.douban.util.XSSUtil;

/**
 * Servlet Filter implementation class HTMLFilter
 */
@WebFilter(filterName = "XSSFilter", urlPatterns = "/*")
public class XSSFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public XSSFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		XssEscapingWrapper request = new XssEscapingWrapper((HttpServletRequest) req);
		// pass the request along the filter chain
		chain.doFilter(request, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}


}

class XssEscapingWrapper extends HttpServletRequestWrapper{

	public XssEscapingWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value = super.getParameter(name);
		if(value == null) {
			return null;
		}
		value = XSSUtil.escapeScript(value);
		value = XSSUtil.replaceHTML(value);
		return value;
	}
	
	@Override
	public String[] getParameterValues(String name) {
		// TODO Auto-generated method stub
		String[] values = super.getParameterValues(name);
		if(values == null) {
			return null;
		}
		for (int i = 0; i < values.length; i++) {
			String str = values[i];
			str = XSSUtil.escapeScript(str);
			str = XSSUtil.replaceHTML(str);
			values[i] = str;
		}
		return values;
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String,String[]> map = super.getParameterMap();
		if(map == null) {
			return null;
		}
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String[] values = map.get(it.next());
			for (int i = 0; i < values.length; i++) {
				String str = values[i];
				str = XSSUtil.escapeScript(str);
				str = XSSUtil.replaceHTML(str);
				values[i] = str;
			}
		}
		return null;
	}
	

}
