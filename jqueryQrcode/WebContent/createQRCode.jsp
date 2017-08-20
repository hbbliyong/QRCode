<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二维码生成</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.qrcode.min.js"></script>
</head>
<body>
生成的二维码如下：<br>
<dir id="qrcode"></dir>
<script type="text/javascript">
jQuery('#qrcode').qrcode('https://github.com/hbbliyong/QRCode.git');
</script>
</body>
</html>