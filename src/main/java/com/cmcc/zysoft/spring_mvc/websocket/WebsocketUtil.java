package com.cmcc.zysoft.spring_mvc.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebsocketUtil {
	
	private static final Logger logger = LogManager.getLogger(WebsocketUtil.class);
	
	/**
	 * 存放当前存活的客户端连接.
	 */
	private static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());
	
	public static void broadCast(String text) {
		TextMessage returnMessage = new TextMessage(text);
		for(WebSocketSession session : sessions) { 
			try {
				session.sendMessage(returnMessage);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	public static void put(WebSocketSession session) {
		sessions.add(session);
	}
	
	public static void remove(WebSocketSession session) {
		sessions.remove(session);
	}
	
	public static int size() {
		return sessions.size();
	}
	
	public static void sendMsg(WebSocketSession session,String msg) {
		TextMessage returnMessage = new TextMessage(msg);
		try {
			session.sendMessage(returnMessage);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
