<%@ page import="com.apex.bank.restSql.GenerateRestSqlUtil" %><%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2021/1/23
  Time: 0:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    String cloumData= GenerateRestSqlUtil.generateTbCloums("saa");

%>
<html>
<head>
    <meta charset="utf-8">
    <title>RestSQL</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div>
    <fieldset class="layui-elem-field site-demo-button" style="margin:10px;">
        <legend>查询条件</legend>
        <div style="margin:15px;">
            <div class="layui-inline">
                <%--  <%=customButtons%>--%>
                <div style="float: left">
                    <button class="layui-btn layui-btn-normal" id="add">添加条件</button>
                    <button class="layui-btn layui-btn-danger" id="del">删除选中条件</button>
                    <button style="margin-left: 10px;" class="layui-btn" type="button" id="search"><i class="layui-icon layui-icon-search"></i>查询</button>
                </div>
                <div style="float: left;margin: 5px;display: none;" id="qrycondContainer">
                    <div class="CondStyle" id="combine1"><span>且</span></div>
                    <div id="searchForm" style="border:1px solid darkgray;padding:10px;float: left"></div>
                </div>

            </div>
            <%--<button class="layui-btn" data-type="reload">搜索</button>--%>
        </div>
    </fieldset>
</div>
</body>
<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
<script src="layui/layui.js" charset="utf-8"></script>
<script src="initCond.js" charset="utf-8"></script>
<script type="text/javascript">
    var cloumArr=<%=cloumData%>;
    $(document).ready(function () {
        init(cloumArr);
    });

</script>
</html>
