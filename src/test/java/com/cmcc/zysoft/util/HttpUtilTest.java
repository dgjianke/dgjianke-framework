package com.cmcc.zysoft.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class HttpUtilTest {
	
	private static Logger logger = LogManager.getLogger(HttpUtilTest.class); 
	
	//@Test
	public void testGet() throws Exception {
		String url = "http://127.0.0.1:8080/src/main/java";
		String response = HttpUtil.get(url);
		logger.info(response);
	}
	
	@Test
	public void testPost() throws Exception {
		String url = "http://localhost:8080/mobile/order/getOrderById.htm";
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "402857aa48ef9aeb0148ef9ccfed0000");
		String response = HttpUtil.post(url, params);
		logger.info(response);
	}
	
	//@Test
	public void testUpload() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		File file = new File("E:/动漫MAD素材/图片/棋魂/03.jpg");
		logger.info(file.exists());
		logger.info(file.length());
		params.put("file", file);
		String url = "http://localhost:8080/mobile/order/uploadImg.htm";
		String response = HttpUtil.upload(url, params);
		logger.info(response);
	};
	
	//@Test
	public void testDownload() throws Exception {
		String url = "http://localhost:8080/mobile/order/image/1412760808826_sampleFile1.JPG";
		byte[] bytes = HttpUtil.download(url, null);
		File file = new File("d:/1.jpg");
		byte2file(bytes, file);
		System.out.println("the qrcode is generated : "+file.getAbsolutePath());
	}
	
	
	private static void byte2file(byte[] bytes,File file) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(bos!=null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
}
