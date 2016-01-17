var basePath = $("#basePath").val();
var ws = null;    
function startWebSocket(msg) {    
    if ('WebSocket' in window)    
        ws = new WebSocket('ws://'+ window.location.host +'/simple/websocketHello');    
    else if ('MozWebSocket' in window)    
        ws = new MozWebSocket('ws://'+ window.location.host +'/simple/websocketHello');    
    else    
        ws = new SockJS("/simple/sockjs/websocketHello", undefined, {protocols_whitelist: []});    
        
    ws.onmessage = function(evt) {  
    	var msg = evt.data;
    	if(msg.indexOf("sftp") > -1) { //second step
    		if(msg.indexOf("100%") > -1) {
    			$('#sftpProgress').html("");
    			$('#progress').append(msg.substring(4));
    			return;
    		}
    		$('#sftpProgress').html(msg.substring(4));
    	}else if(msg.indexOf("ssh")> -1) { // last step
    		if(msg.indexOf("605") > -1) { //如果数据库满了
    	    	$('#sshProgress').html("");
    			$('#progress').append("<br/><font color='red'>数据库已经满了，请联系管理员清理</font>");
    			return;
	    	}
    		if(msg.indexOf("over") > -1) {
    			$('#sshProgress').html("");
    			$('#progress').append(msg.substring(7));
    		}else {
    			$('#sshProgress').html(msg.substring(3));
    		}
    	}else { //first step
    		$('#progress').append(msg); 
    	}
    };    
        
    ws.onclose = function(evt) {    
        alert("已经与服务器断开连接");
        $("#start").html("已经与服务器断开连接");
		$("#progress").html("");
		$("#sftpProgress").html("");
		$("#sshProgress").html("");
    };    
        
    ws.onopen = function(evt) {    
    	$('#progress').append(
		"<br/>建立websocket连接成功!");   
    	ws.send("upload:"+msg);
    };    
}    
    
function sendMsg() {    
    ws.send(document.getElementById('writeMsg').value);    
}
		
$(function() {
	$('#file_upload').uploadify({
		'formData'     : {
			'channeltwoId' : 'e',
			'token'     : 'dd'
		},
		'method'   : 'post',
		'auto'     : false,
		'swf'      : basePath+'/resources/js/common/uploadify/uploadify.swf',
		'cancelImg' : basePath+'/resources/img/uploadify-cancel.png',
		'uploader' : basePath+'/pc/combo/uploadCombo.htm',
		'fileTypeDesc' : 'xls文件或csv文件或xlsx文件',
		'fileTypeExts' : '*.xls;*.csv;*.xlsx',
		'fileObjName' : 'file',
		'queueID' : 'queue',
		'buttonText' : '上传',
		'onUploadSuccess' : function(file, data,
				response) {
			if (response) {
				$('#progress').append(
						"<br/>上传至243完毕，开始与243建立websocket连接...");
				startWebSocket(file.name);
			}
		},
		'onUploadStart' : function(file) {
			$('#start').append(
					'<br/>开始上传' + file.name + "...");
		},
		'onUploadProgress' : function(file,
				bytesUploaded, bytesTotal,
				totalBytesUploaded, totalBytesTotal) {
			var progress = parseInt(bytesUploaded
					/ bytesTotal * 100, 10);
			$('#progress').html(
					"正在上传至243服务器..." + progress + "%");
		},
		'onUploadError' : function(file, errorCode,
				errorMsg, errorString) {
			if (errorCode != "-280") { //对于主动取消的类型不作处理
				$('#progress').html(
						"<font color='red'>文件-"
								+ file.name
								+ "上传至243服务器失败: "
								+ errorString
								+ "</font>");
			}
		},
		'onSelect' : function(file) {
			$('#start').html(
					'已经添加 ' + file.name
							+ '到上传文件队列，请点击开始上传按钮进行上传');
			$("#progress").html("");
			$("#sftpProgress").html("");
			$("#sshProgress").html("");
		},
		'onCancel' : function(file) {
			$("#start").html("已经取消文件上传");
			$("#progress").html("");
		}
	});
});
