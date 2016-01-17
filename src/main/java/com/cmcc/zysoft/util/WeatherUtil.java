package com.cmcc.zysoft.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @ClassName WeatherUtil
 * @Description 天气信息Util
 * @author xiao.yu@ustcinfo.com
 * @date 2014-2-11 上午11:31:51
 *
 */
public class WeatherUtil {
	
	
	/**
	 * @Title:getContentFromUrl
	 * @Description:获取指定url端口的返回报文
	 * @param sendUrl url接口地址
	 * @return 报文
	 * @throws Exception 异常 
	 */
	public static String getContentFromUrl(String sendUrl) throws Exception{
		HttpURLConnection httpURLConnection = getUrlConnection(sendUrl);
		InputStream is = httpURLConnection.getInputStream();//接受报文体信息
		String content = getStringFromInputstream(is);
		content = new String(content.getBytes("utf-8"),"utf-8");
		return content;
	}
	
	/**
	 * @Title:getUrlConnection
	 * @Description:获取http链接 
	 * @param sendUrl 请求的接口地址 
	 * @return http链接 
	 * @throws Exception 异常 
	 */
	private static HttpURLConnection getUrlConnection(String sendUrl) throws Exception{
		URL url = new URL(sendUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setDoOutput(false);//能输出正文参数
		httpURLConnection.setDoInput(true);//能接受返回报文
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setConnectTimeout(180 * 1000); //连接超时
		httpURLConnection.setReadTimeout(30 * 1000);//读取超时
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty("Content-Type","");
		return httpURLConnection;
	}
	
	/**
	 * @Title:getStringFromInputstream
	 * @Description:输入流转换成字符串
	 * @param is 输入流
	 * @return 字符串 
	 * @throws Exception 异常 
	 */
	private static String getStringFromInputstream(InputStream is) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1; //每次读取的内容
		while((i = is.read()) != -1) { 
			baos.write(i);
		}
		return baos.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String sendUrl = "http://localhost:8184/redirect/r.htm";
		System.out.println(getContentFromUrl(sendUrl));
	}
	
}
