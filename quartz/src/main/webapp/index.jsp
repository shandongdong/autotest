<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/user.js?"></script>
</head>
<body>
<h2>Hello World!</h2>

<a href="user/jumpLogin">去登录</a>
<br/>
<a href="${pageContext.request.contextPath}/user/getUserById?id=1">获取用户信息</a>
<br/>
</body>
</html>
