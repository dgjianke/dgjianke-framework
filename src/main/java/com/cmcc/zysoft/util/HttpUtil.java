package com.cmcc.zysoft.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtil {
	
	AtomicInteger integer = new AtomicInteger();

	private static Logger logger = LogManager.getLogger(HttpUtil.class);
	
	/**
	 * get请求.
	 * @param url 要请求的url
	 * @return 请求结果
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url); // 构造请求方式及内容
			final String requestId = UUIDUtil.generate(); // 构造请求id，方便以后查找时匹配请求及返回消息
			logger.info("请求id:{},开始请求:{}", requestId,httpGet.getRequestLine());
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"utf-8") : null;
					} else {
						logger.error("请求id:{},请求发生异常,错误代码:{}", requestId,status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpClient.execute(httpGet, responseHandler);
			logger.info("请求id:{},返回信息:{}",requestId, responseBody);
			return responseBody;
		} finally {
			httpClient.close();
		}

	}
	
	/**
	 * post请求.
	 * @param url 请求url
	 * @param params 请求参数，请将key和value放在一个map里
	 * @return 请求结果 
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params)
			throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url); // 构造请求方式及内容
			final String requestId = UUIDUtil.generate(); // 构造请求id，方便以后查找时匹配请求及返回消息
			List<NameValuePair> list = new ArrayList<NameValuePair>(); // 构造请求参数
			if(params!=null&&params.size()>0) { //如果有参数
				Set<String> keys = params.keySet();
				for (String key : keys) {
					String value = params.get(key);
					NameValuePair pair = new BasicNameValuePair(key, value);
					list.add(pair);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						"UTF-8");
				httpPost.setEntity(entity);
			}
			logger.info("请求id:{},开始请求:{},参数:{}", requestId,httpPost.getRequestLine(), EntityUtils.toString(httpPost.getEntity(),
					"utf-8"));
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"utf-8") : null;
					} else {
						logger.error("请求id:{},请求发生异常,错误代码:{}", requestId,status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpClient.execute(httpPost, responseHandler);
			logger.info("请求id:{},返回信息:{}",requestId, responseBody);
			return responseBody;
		} finally {
			httpClient.close();
		}
	}
	
	/**
	 * 上传文件
	 * @param url 请求url
	 * @param params 请求参数 文件类型放入<String,File> 文本类型放入<String,String> 
	 * @return 请求结果 
	 * @throws Exception
	 */
	public static String upload(String url,Map<String, Object> params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			final String requestId = UUIDUtil.generate(); // 构造请求id，方便以后查找时匹配请求及返回消息
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			if(params!=null&&params.size()>0) { //如果有参数
				Set<String> keys = params.keySet();
				for (String key : keys) { //添加文本参数内容
					Object value = params.get(key);
					if(value instanceof String) { //文本类型的参数
						StringBody body = new StringBody((String)value,
								ContentType.TEXT_PLAIN);
						builder.addPart(key, body);
					}else if(value instanceof File) { //文件类型的参数
						FileBody body = new FileBody((File)value);
						builder.addPart(key, body);
					}
				}
				HttpEntity reqEntity = builder.build();
				httppost.setEntity(reqEntity);
			}

			logger.info("请求id:{},开始请求:{}", requestId,httppost.getRequestLine());

			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity,
								"utf-8") : null;
					} else {
						logger.error("请求id:{},请求发生异常,错误代码:{}", requestId,status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httppost, responseHandler);
			logger.info("请求id:{},返回信息:{}",requestId, responseBody);
			return responseBody;
		} finally {
			httpclient.close();
		}
	}
	
	/**
	 * download请求.
	 * @param url 请求url
	 * @param params 请求参数，请将key和value放在一个map里
	 * @return 请求结果 
	 * @throws Exception
	 */
	public static byte[] download(String url, Map<String, String> params)
			throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url); // 构造请求方式及内容
			final String requestId = UUIDUtil.generate(); // 构造请求id，方便以后查找时匹配请求及返回消息
			List<NameValuePair> list = new ArrayList<NameValuePair>(); // 构造请求参数
			if(params!=null&&params.size()>0) { //如果有参数
				Set<String> keys = params.keySet();
				for (String key : keys) {
					String value = params.get(key);
					NameValuePair pair = new BasicNameValuePair(key, value);
					list.add(pair);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						"UTF-8");
				httpPost.setEntity(entity);
			}
			logger.info("请求id:{},开始请求:{}", requestId,httpPost.getRequestLine());
			ResponseHandler<byte[]> responseHandler = new ResponseHandler<byte[]>() {// 匿名类，用于处理返回请求至指定类型，这里是string

				@Override
				public byte[] handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode(); // 获取状态返回码
					if (status >= 200 && status < 300) { // 2开头说明请求成功
						HttpEntity entity = response.getEntity();
						logger.info(ContentType.getOrDefault(entity));
						logger.info(entity.getContentType().getValue());
						return entity != null ? EntityUtils.toByteArray(entity) : null;
					} else {
						logger.error("请求id:{},请求发生异常,错误代码:{}", requestId,status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			byte[] responseBody = httpClient.execute(httpPost, responseHandler);
			logger.info("请求id:{},返回信息:{}",requestId, responseBody);
			return responseBody;
		} finally {
			httpClient.close();
		}
	}
	
	public static void main(String[] args) {
		//String url = "http://58.16.129.243:9098/channel/mobile/money/queryMoney.htm?channeltwoId=123456&page=1&rows=5&moneyType=2";
		/*String url = "http://58.16.129.243:9098/channel/mobile/money/queryMoney.htm";
		Map<String, String> params = new HashMap<String, String>();
		params.put("channeltwoId", "123456");
		params.put("page", "1");
		params.put("rows", "5");
		params.put("moneyType", "2");
		try {
			post(url,params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	//	System.out.println(2 << 3%2);
		String str = new String("计算器");
		System.out.println(str.intern() == str);
		
		String str2 = new StringBuilder("计算机").append("软件").toString();
		System.out.println(str2.intern() == str2);
	}
}
