// ~ CopyRight © 2012 USTC SINOVATE  SOFTWARE CO.LTD All Rights Reserved.
package com.ustc.channel.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 李三来
 * <br />邮箱： li.sanlai@ustcinfo.com
 * <br />描述：文件上传工具
 * <br />@version:1.0.0
 * <br />日期： 2012-12-25 下午10:54:09
 * <br />CopyRight © 2012 USTC SINOVATE  SOFTWARE CO.LTD All Rights Reserved.
 */
public class UploadUtil {

	private static final int BUFFER_SIZE = 16 * 1024 ;
	/**
	 * 拷贝文件
	 * @param src
	 * @param dst
	 * @throws IOException 
	 */
	public static void copy(File src, File dst) throws IOException  {
           InputStream in = null ;
           OutputStream out = null ;
            try  {                
               in = new BufferedInputStream( new FileInputStream(src), BUFFER_SIZE);
               out = new BufferedOutputStream( new FileOutputStream(dst), BUFFER_SIZE);
                byte [] buffer = new byte [BUFFER_SIZE];
                while (in.read(buffer) > 0 )  {
                   out.write(buffer);
               } 
            } finally  {
                if ( null != in)  {
                   in.close();
               } 
                 if ( null != out)  {
                   out.close();
               } 
           } 
   } 
	
	/**
	 * 拷贝文件
	 * @param src
	 * @param dst
	 * @throws IOException 
	 */
	public static void copy(MultipartFile src, File dst) throws IOException  {
           InputStream in = null ;
           OutputStream out = null ;
            try  {                
               in = new BufferedInputStream( src.getInputStream(), BUFFER_SIZE);
               out = new BufferedOutputStream( new FileOutputStream(dst), BUFFER_SIZE);
                byte [] buffer = new byte [BUFFER_SIZE];
                while (in.read(buffer) > 0 )  {
                   out.write(buffer);
               } 
            } finally  {
                if ( null != in)  {
                   in.close();
               } 
                 if ( null != out)  {
                   out.close();
               } 
           } 
   } 
   
	/**
	 * 取得文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getExtention(String fileName)  {
        int pos = fileName.lastIndexOf( "." );
        return fileName.substring(pos);
   }
	
	/**
	 * 生成UUID文件名
	 * @return
	 */
	public static String getUuidName(String imageType){
		return UUID.randomUUID().toString().replace("-", "")+imageType;
	}
}
