package com.allen.douban.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class SecurityUtil {
	public static void main(String[] args) {
	}
	
	public static String encodeMD5(String src) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] pwd = md.digest(src.getBytes());
		String pwd_str = Base64.encodeBase64String(pwd);
		return pwd_str;
	}


}
