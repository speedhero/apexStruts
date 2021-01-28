<%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2021/1/24
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<fieldset class="layui-elem-field site-demo-button" style="margin:10px;">
    <legend>查询条件</legend>
    <div style="margin:15px;">
        <div class="layui-inline">
            <%--  <%=customButtons%>--%>
            <div style="float: left">
                <button class="layui-btn layui-btn-normal" id="importExcel">导入</button>
            </div>
        </div>
    </div>
</fieldset>
</body>
<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
<script src="layui/layui.js" charset="utf-8"></script>
<script src="layui_exts/excel.js" charset="utf-8"></script>
<script src="test.js" charset="utf-8"></script>
<script>
    ImportExcel();
</script>
</html>
