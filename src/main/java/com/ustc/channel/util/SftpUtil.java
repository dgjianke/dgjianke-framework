package com.ustc.channel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.web.socket.WebSocketSession;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * sftp上传下载工具类
 * @author xiao.yu
 *
 */
public class SftpUtil {
	
	
	/**
	* 连接sftp服务器.
	* @param host 主机
	* @param port 端口
	* @param username 用户名
	* @param password 密码
	* @return sftp
	 * @throws JSchException 
	*/
	public static ChannelSftp connect(String host, int port, String username,String password) throws JSchException {
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		jsch.getSession(username, host, port);
		Session sshSession = jsch.getSession(username, host, port);
		System.out.println("Session created.");
		sshSession.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);
		sshSession.connect();
		System.out.println("Session connected.");
		System.out.println("Opening Channel.");
		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		System.out.println("Connected to " + host + ".");
		return sftp;
	}
	

	/**
	* 上传文件.
	* @param directory 上传的目录
	* @param uploadFile 要上传的文件
	* @param sftp
	 * @throws SftpException 
	 * @throws IOException 
	*/
	public static void upload(String directory, String uploadFile, ChannelSftp sftp,WebSocketSession session) throws SftpException, IOException {
		FileInputStream fis = null;
		try {
			sftp.cd(directory);
			File file=new File(uploadFile);
			long fileSize = file.length();
			fis = new FileInputStream(file);
			sftp.put(fis, file.getName(),new MyProgressMonitor(fileSize, session));
			fis.close();
			sftp.exit();
		} finally {
			if(fis!=null) {
				fis.close();
			}
			if(sftp!= null) {
				sftp.exit();
			}
		}
	}
	
}
