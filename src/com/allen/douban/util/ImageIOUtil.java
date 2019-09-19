package com.allen.douban.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageIOUtil {
	public static Boolean base64ToImage(String base64, String path) {
		File file = new File(path);
		File fileParent = file.getParentFile();
		if(!fileParent.exists()) {
			if(!fileParent.mkdirs()) {
				return false;
			}
		}
//		if(!file.exists()) {
//			if(!file.mkdir()) {
//				return false;
//			}
//		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			int i = base64.indexOf("base64,") + 7;
			String image = base64.substring(i);
			byte[] b = decoder.decodeBuffer(image);
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static String imageToBase64(String path) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(new File(path));
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		if(data != null) {
			String base64 = "data:image/jpeg;base64,"+encoder.encode(data);
			return base64;
		}else {
			return null;
		}
	}
	
	public static byte[] getFileByPath(String path) {
		File file = new File(path);
		try {
			FileInputStream fileInput = new FileInputStream(file);
			byte[] data = new byte[fileInput.available()];
			fileInput.read(data);
			fileInput.close();
			return data;
		} catch (FileNotFoundException e) {
			System.out.println("无法找到文件");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		File file = new File("C:\\Users\\83780\\Desktop\\head.jpg");		
		try {
			BufferedImage bi = ImageIO.read(file);
			ImageIO.write(bi, "jpg"	, new FileOutputStream("C:\\Users\\83780\\Desktop\\head11.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
