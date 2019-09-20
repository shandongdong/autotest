<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="statics/js/user.js?"></script>
    <%--    引入bootstrap的样式文件 --%>
    <link href="statics/bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
    <%--    引入 bootstrap的js文件--%>
    <script type="text/javascript" src="statics/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
    <%--    引入JQuery --%>
    <script type="text/javascript" src="statics/js/jquery-3.4.1.js"></script>
</head>
<body>
<h2>Hello World!</h2>

<a href="${pageContext.request.contextPath}/user/getUserById?id=1">获取用户信息</a>
<br/>
<h1><a href="https://getbootstrap.com/docs/4.3/components/buttons/">文档</a></h1>
<h1><a href="https://v3.bootcss.com/css/">文档</a></h1>
<button type="button" class="btn btn-danger">使用BootStrap样式的按钮</button>

<button type="button" class="btn btn-primary">Primary</button>
<button type="button" class="btn btn-secondary">Secondary</button>
<button type="button" class="btn btn-success">Success</button>
<button type="button" class="btn btn-danger">Danger</button>
<button type="button" class="btn btn-warning">Warning</button>
<button type="button" class="btn btn-info">Info</button>
<button type="button" class="btn btn-light">Light</button>
<button type="button" class="btn btn-dark">Dark</button>

<button type="button" class="btn btn-link">Link</button>
</body>
</html>
