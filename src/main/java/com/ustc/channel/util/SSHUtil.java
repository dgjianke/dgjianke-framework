package com.ustc.channel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.web.socket.WebSocketSession;

import com.cmcc.zysoft.spring_mvc.websocket.WebsocketUtil;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * ssh远程登录工具.
 * @author xiao.yu
 *
 */
public class SSHUtil {
	
	
	/**
	 * 
	 * @param host 远程主机IP
	 * @param username 用户名
	 * @param password 密码 
	 * @return 连接 
	 */
	public static Connection getConnection(String host,String username,String password) {
		Connection conn = new Connection(host);
		boolean isconn = false;
		try {
			conn.connect();
			isconn = conn.authenticateWithPassword(username, password); 
		} catch (IOException e) {
			System.err.println("10到11链路有问题:"+e.getMessage());
			if (conn != null) {
				conn.close();
			}
			return null;
		} 
		if(!isconn) {
			System.out.println("用户名称或者是密码不正确");
		}else {
			return conn;
		}
		return null;
	}
	
	
	public static void execCommand(Connection connection,String command,WebSocketSession session) throws IOException {
		Session ssh = null;
		InputStream is = null;
		InputStream stdErr = null;
		try {
			ssh = connection.openSession();
			ssh.execCommand(command);
			is = new StreamGobbler(ssh.getStdout());  
			stdErr = new StreamGobbler(ssh.getStderr());
            BufferedReader brs = new BufferedReader(new InputStreamReader(is));
            String loaded = new String();
            long beginTime = System.currentTimeMillis();
            while(true){  
                String line = brs.readLine();  
                if(line==null){  
                    break;  
                }
                if (line.length()>45) {
                	loaded = line.substring(44);
                	long elapse = System.currentTimeMillis()-beginTime;
                	if(elapse>500) { //一秒查看一次发送情况
                		WebsocketUtil.sendMsg(session, "ssh正在导入...已经导入"+loaded+"条");
                		beginTime = System.currentTimeMillis();
                	}
				}
            }
            WebsocketUtil.sendMsg(session,"sshover<br/>导入完毕，共导入"+loaded+"条");
            BufferedReader brs2 = new BufferedReader(new InputStreamReader(stdErr));
            while(true){  
                String line = brs2.readLine();  
                if(line==null){  
                    break;  
                }  
                System.out.println(line);
                WebsocketUtil.sendMsg(session, "ssh"+line);
            }
		}  finally {
			if(ssh!=null) {
				ssh.close();
			}
	        if(is!=null) {
	        	is.close();
	        }
		}
		
	}
	
}
