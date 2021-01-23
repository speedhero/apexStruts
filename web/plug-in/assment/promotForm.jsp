<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.apex.bank.link.LayoutUtil" %><%--
  Created by IntelliJ IDEA.
  User: 蒋涛jiangtao
  Date: 2021/1/14
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String data="";
    String path=request.getContextPath();
  //  String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String tablecode = request.getParameter("tablecode")==null?"KMH_LXSR_2021":request.getParameter("tablecode");//用request得到
    String  method = request.getParameter("method")==null?"add":request.getParameter("method");//1新建2修改
    if(!"add".equalsIgnoreCase(method)&&request.getParameter("data")!=null){
        data = URLDecoder.decode(request.getParameter("data"),"UTF-8");
    }
    System.out.println(data);
    String layoutform= LayoutUtil.getFormLayout(tablecode,method);

%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
</head>
<body style="padding: 20px">
<form class="layui-form layui-form-pane" action="" lay-filter="example">
    <%=layoutform%>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="submit" class="layui-btn" style="display: none" lay-submit="" lay-filter="LAY-user-front-submit" id="LAY-user-front-submit">立即提交</button>
<%--            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            <button type="button" class="layui-btn layui-btn-normal" id="LAY-component-form-getval">取值</button>
            <button type="button" class="layui-btn layui-btn-normal" id="LAY-component-form-setval">赋值</button>--%>
        </div>
    </div>
</form>
<%--<form class="layui-form" action="" lay-filter="example">
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn layui-btn-normal" id="LAY-component-form-setval">赋值</button>
            <button type="button" class="layui-btn layui-btn-normal" id="LAY-component-form-getval">取值</button>
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
        </div>
    </div>
</form>--%>
<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
<script src="layui/layui.js" charset="utf-8"></script>
<script src="layui_exts/excel.js" charset="utf-8"></script>
<script src="customfun.js" charset="utf-8"></script>
<script src="renderform.js" charset="utf-8"></script>
<script>
    var method='<%=method%>';
    if(method=="add"){
        renderform(method,"");
    }else{
        renderform(method,<%=data%>);
    }

</script>
</body>
</html>
<div class="site-text" style="margin: 5%; display: none" id="transferWin">
    <div id="transferForm"></div>
</div>

