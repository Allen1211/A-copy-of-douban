package com.allen.douban.util;

import java.util.regex.Pattern;

public class XSSUtil {
	
	public static String escapeXSS(String str) {
		str = escapeScript(str);
		str = replaceHTML(str);
		return str;
	}
	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String replaceHTML(String input) {
		StringBuilder sb = new StringBuilder();
		char[] text = new char[input.length()];
		input.getChars(0, input.length(), text, 0);
		for (int i = 0; i < text.length; i++) {
			switch (text[i]) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
//			case '&':
//				sb.append("&amp;");
//				break;
//			case '"':
//				sb.append("＂");
//				break;
			case '\'' :
				sb.append("&#39;");
				break;
//			case '\\' :
//				sb.append("＼");
//				break;
			default:
				sb.append(text[i]);
				break;
			}
		}
		return  sb.toString();
	}
	
	/**
	 * script注入过滤,匹配的直接替换成空.
	 * https://blog.csdn.net/fangleijiang/article/details/73696866 
	 * @param value
	 * @return
	 */
	public static String escapeScript(String value) {
		Pattern scriptPattern =Pattern.compile("<script>(.*?)</script>",  
                Pattern.CASE_INSENSITIVE);
        value=scriptPattern.matcher(value).replaceAll("");
        
        scriptPattern = Pattern.compile("</script>",  
                Pattern.CASE_INSENSITIVE);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("<script(.*?)>",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("javascript:",  
                Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("<iframe>(.*?)</iframe>",  
                Pattern.CASE_INSENSITIVE);  
        value = scriptPattern.matcher(value).replaceAll("");  

        scriptPattern = Pattern.compile("</iframe>",  
                Pattern.CASE_INSENSITIVE);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("<iframe(.*?)>",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("onload(.*?)=",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("oninput(.*?)=",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("onerror(.*?)=",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("onclick(.*?)=",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("confirm(.*?)",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll("");  
        
        scriptPattern = Pattern.compile("onfocus(.*?)=",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll(""); 
        
        scriptPattern = Pattern.compile("alert(.*?)",  
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE  
                        | Pattern.DOTALL);  
        value = scriptPattern.matcher(value).replaceAll("");

		return value;
	}
}
