<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<html>
<head>
<base href="<%=basePath%>" />
<script type="text/javascript"
	src="<%=basePath%>/resources/js/common/jquery/jquery-1.7.2.min.js"></script>
<script
	src="<%=basePath%>/resources/js/common/uploadify/jquery.uploadify.min.js"
	type="text/javascript"></script>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/resources/js/common/uploadify/uploadify.css">

</head>
<body>
	<input id="basePath" type="hidden" value="<%=basePath%>" />
	<h1>发展人套餐相关信息统计导入</h1>
	<form>
		<div style="float: right;">
			<a href="javascript:$('#file_upload').uploadify('upload')">开始上传</a> 
		   <a href="javascript:$('#file_upload').uploadify('cancel')">取消上传</a>
		</div>
		<input id="file_upload" name="file" type="file">

		<div id="queue"></div>
		<div id="start"></div>
		<div id="progress"></div>
		<div id="sftpProgress"></div>
		<div id="sshProgress"></div>
	</form>
<script src="<%=basePath%>/resources/js/upload.js" type="text/javascript"></script>
</body>
</html>
