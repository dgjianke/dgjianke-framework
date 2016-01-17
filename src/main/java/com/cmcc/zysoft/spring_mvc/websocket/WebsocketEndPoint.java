package com.cmcc.zysoft.spring_mvc.websocket;


import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import ch.ethz.ssh2.Connection;

import com.jcraft.jsch.ChannelSftp;
import com.ustc.channel.util.SSHUtil;
import com.ustc.channel.util.SftpUtil;

public class WebsocketEndPoint extends TextWebSocketHandler{
	
	/**
	 * 应用服务器保存csv文件路径.
	 */
	@Value("${csv.upload.path}")
	private String upload_path; 
	
	/**
	 * 数据库服务器保存csv文件路径.
	 */
	@Value("${upload.oracle}")
	private String upload_oracle;
	
	@Value("${host}")
	private String host;
	
	@Value("${usero}")
	private String usero;
	
	@Value("${userr}")
	private String userr;
	
	@Value("${pwd}")
	private String pwd;
	

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		String msg = message.getPayload();
		if(msg.indexOf("upload")!=-1) { //如果要开始上传至数据服务器
			String fileName = msg.split(":")[1];
			uploadAndLoadData(session, fileName);
		}else {
			TextMessage returnMessage = new TextMessage("<br/>"+message.getPayload()+"开始上传至244...");
			session.sendMessage(returnMessage);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		WebsocketUtil.remove(session);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		super.afterConnectionEstablished(session);
		WebsocketUtil.put(session);
	}
	
	public void uploadAndLoadData(WebSocketSession session,String fileName) {
		String savePath = upload_path;
		String filePath = savePath+File.separator+fileName;
		ChannelSftp sftp = null;
		try{
			sftp = SftpUtil.connect(host, 22, userr, pwd);
		}catch (Exception e) {
			WebsocketUtil.sendMsg(session, "<br/><font color='red'>sftp:22连接至数据库服务器出现异常</font>");
			e.printStackTrace();
			return;
		}
		WebsocketUtil.sendMsg(session, "<br/>已经成功建立应用与数据库服务器连接!");
		try {
			SftpUtil.upload(upload_oracle, filePath, sftp, session);
		} catch (Exception e) {
			WebsocketUtil.sendMsg(session, "上传文件"+fileName+"至数据库服务器出现异常");
			e.printStackTrace();
			return;
		}
		Connection conn = null;
		try {
			conn = SSHUtil.getConnection(host, usero, pwd);
		} catch (Exception e) {
			WebsocketUtil.sendMsg(session, "<br/><font color='red'>ssh:22连接至数据库服务器出现异常</font>");
			e.printStackTrace();
			return;
		}
		//String cmd1 = "date -R";
		String prefix = fileName.substring(0, fileName.indexOf("."));
		String cmd2 = "cd /home/oracle/upload/;./load.sh "+prefix;
		//String cmd2 = "pwd";
		try {
			SSHUtil.execCommand(conn, cmd2, session);
		} catch (IOException e) {
			WebsocketUtil.sendMsg(session, "<br/><font color='red'>导入数据出现异常</font>");
			e.printStackTrace();
		}finally{
			if(conn!=null) {
				conn.close();
			}
		}
		
	}
	
}
