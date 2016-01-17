package com.ustc.channel.util;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.web.socket.WebSocketSession;

import com.cmcc.zysoft.spring_mvc.websocket.WebsocketUtil;
import com.jcraft.jsch.SftpProgressMonitor;

public class MyProgressMonitor extends TimerTask implements SftpProgressMonitor {
	
	private long progressInterval = 500; // 默认间隔时间为5秒
	
	private boolean isEnd = false; //传输是否结束
	
	private long transfered; //已经传输的大小 
	
	private long fileSize; // 记录文件总大小
	
    private Timer timer; // 定时器对象
	
    private boolean isScheduled = false; // 记录是否已启动timer记时器
    
	private WebSocketSession session; //当前登录连接的client
	
	public MyProgressMonitor(long fileSize,WebSocketSession session) {
		this.fileSize = fileSize;
		this.session = session;
	}
	
	@Override
	public void init(int op, String src, String dest, long max) {
		WebsocketUtil.sendMsg(session, "<br/>开始上传文件至数据库服务器...");
	}

	@Override
	public boolean count(long count) {
		  if (isEnd()) return false;
	        if (!isScheduled) {
	            start();
	        }
	        add(count);
	        return true;
	}

	@Override
	public void end() {
		setEnd(true);
		WebsocketUtil.sendMsg(session, "sftp<br/>上传至数据服务器: 100%");
		WebsocketUtil.sendMsg(session, "<br/>上传文件至数据库服务器完毕,开始导入数据...");
	}
	
	/**
     * 打印progress信息
     * @param transfered
     */
    private void sendProgressMessage(long transfered) {
        if (fileSize != 0) {
            double d = ((double)transfered * 100)/(double)fileSize;
            DecimalFormat df = new DecimalFormat( "#.##"); 
            WebsocketUtil.sendMsg(session, "sftp上传至数据服务器: " + df.format(d) + "%");
        } else {
        	WebsocketUtil.sendMsg(session,"Sending progress message: " + transfered);
        }
    }

	@Override
	public void run() {
		 if (!isEnd()) { // 判断传输是否已结束
            long transfered = getTransfered();
            if (transfered != fileSize) { // 判断当前已传输数据大小是否等于文件总大小
                sendProgressMessage(transfered);
            } else {
                setEnd(true); // 如果当前已传输数据大小等于文件总大小，说明已完成，设置end
            }
        } else {
            stop(); // 如果传输结束，停止timer记时器
            return;
        }
		
	}
	
	public void start() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(this, 1000, progressInterval);
        isScheduled = true;
    }
	
	  public void stop() {
	        if (timer != null) {
	            timer.cancel();
	            timer.purge();
	            timer = null;
	            isScheduled = false;
	        }
	  }
	
	private synchronized void add(long count) {
        transfered = transfered + count;
    }
    
    private synchronized long getTransfered() {
        return transfered;
    }
    
    public synchronized void setTransfered(long transfered) {
        this.transfered = transfered;
    }
    
    private synchronized void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
    
    private synchronized boolean isEnd() {
        return isEnd;
    }
	

}
