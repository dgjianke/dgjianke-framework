package com.ustc.channel.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadComboUtil {
	/**
	 * 定义允许上传的文件扩展名.
	 */
	private static final HashMap<String, String> extMap = new HashMap<String, String>();
	
	static{
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,mp4,asf,rm,rmvb");
		extMap.put("file", "doc,docx,pdf,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,csv");
		extMap.put("excel", "xls,xlsx,csv");
		extMap.put("zip", "zip,rar,tar,tar.gz,war,jar,7z");
	}
	
	private static final String SUCCESS_KEY = "success";
	private static final String MESSAGE_KEY = "message";
	
	//运行上传的最大字节数
	private static final long MAX_UPLOAD_SIZE = 1524288000;//500M
	
	/**
	 * 文件上传路径开发环境.
	 */
	private static final String UPLOAD_PATH_DEV = "upload.picture.dev"; 
	
	/**
	 * 文件上传路径生产环境.
	 */
	private static final String UPLOAD_PATH_PRO = "upload.picture.path";
	
	/**
	 * 上传图标.
	 * @param request 请求
	 * @param paths 文件名
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> uploadCombo(HttpServletRequest request,String... paths) {
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest req = (MultipartHttpServletRequest)request;
		String path;
		if(paths.length > 0){
			path = paths[0];
		}else{
			path = "Filedata";
		}
		MultipartFile filedata = req.getFile(path);
		if(filedata == null || filedata.isEmpty()){
			map.put(SUCCESS_KEY, false);
			map.put(MESSAGE_KEY, "上传文件为空!");
			return map;
		}
		String fileName = filedata.getOriginalFilename();
		//获取当前操作系统的类型，根据不同的操作系统类型获取文件上传路径
		//文件保存路径
		String savePath = "";
		String osName = System.getProperty("os.name");
		if(osName.contains("Windows")||osName.contains("windows")){
			savePath = PropertyUtil.getStringValue(UPLOAD_PATH_DEV);
		}else{
			savePath = PropertyUtil.getStringValue(UPLOAD_PATH_PRO);
		}
		
		//检查扩展名
		String fileType = "excel";
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(fileType).split(",")).contains(fileExt)){
			map.put(SUCCESS_KEY, false);
			map.put(MESSAGE_KEY, "文件类型错误，\n只允许" + extMap.get(fileType) + "格式。");
			return map;
		}
		
		//检查文件大小
		long fileSize = filedata.getSize();
		if(fileSize > MAX_UPLOAD_SIZE){
			map.put(SUCCESS_KEY, false);
			map.put(MESSAGE_KEY, "文件上传失败，文件大小超过500M");
			return map;
		}
		
		//检查目录是否存在
		File uploadDir = new File(savePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		//检查目录写权限
		uploadDir.setWritable(true);
		if(!uploadDir.canWrite()){
			map.put(SUCCESS_KEY, false);
			map.put(MESSAGE_KEY, "文件上传失败,没有对目录"+savePath+"的写权限");
			return map;
		}
		
		File destFile = new File(savePath + "/" + fileName);
		try {
			UploadUtil.copy(filedata, destFile);
			map.put(SUCCESS_KEY, true);
			map.put(MESSAGE_KEY, "文件上传成功!");
			map.put("fileName", fileName);
		} catch (IOException e) {
			e.printStackTrace();
			map.put(SUCCESS_KEY, false);
			map.put(MESSAGE_KEY, "文件上传失败,文件拷贝出现异常!");
			return map;
		}
		return map;
	}
}
