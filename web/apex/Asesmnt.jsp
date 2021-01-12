<%@ page import="com.apex.bank.sftp.DB2Handle" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.apex.bank.link.DataTblUtil" %>
<%@ page import="org.springframework.jdbc.core.JdbcTemplate" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String linksrc="<script type=\"text/javascript\" src=\"../js/jquery-1.8.3.js\"></script>";
    String id = request.getParameter("id");//用request得到
    String tableCode = request.getParameter("TableCode");
    tableCode ="KMH_LXSR_2021";// "F03_BIG_SCREEN_DEPOSIT_INFO";
    String tableName = request.getParameter("TableCode");
    tableName = "";
    Connection connection = null;
    String jsonstring="";
    String  filedjsonstring="";
    String customField="0";
/*    customField="[[ \n" +
            "    {field: 'id', title: 'ID', width: 80, rowspan: 2} \n" +
            "    ,{field: 'username', title: '用户名', width: 80, rowspan: 2}\n" +
            "    ,{align: 'center', title: '个人信息', colspan: 3} \n" +
            "  ], [\n" +
            "    {field: 'email', title: '邮箱', width: 80}\n" +
            "    ,{field: 'sex', title: '性别', width: 120}\n" +
            "    ,{field: 'city', title: '城市', width: 300}\n" +
            "  ]]";*/
    try {
        List<Map<String, Object>> thList = new ArrayList<Map<String, Object>>();//一级表

        String sql = "select * from Exa_TableField  where FTable='" +tableCode+"' order by FSEQ ";//F03_BIG_SCREEN_DEPOSIT_INFO

        //Establish connection
        //connection = DataAccess.getDataSource().getConnection();
   /*     JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());

        Map tableMap= jdbcTemplate.queryForMap("select * from Exa_Table  where FCODE='"+tableCode+"'");
        tableName= tableMap.get("FNAME").toString();

        thList=  jdbcTemplate.queryForList(sql);*/
        Map<String, Object> map1=new HashMap<>();
        map1.put("FFIELDCODE","id");
        map1.put("FFIELDNAME","ID");
        map1.put("FFIELDTYPE","1");
        map1.put("ROWSPAN",2);
        thList.add(map1);
        Map<String, Object> map2=new HashMap<>();
        map2.put("FFIELDCODE","username");
        map2.put("FFIELDNAME","用户名");
        map2.put("ROWSPAN",2);
        map2.put("FFIELDTYPE","2");
        thList.add(map2);

        Map<String, Object> map3=new HashMap<>();
        map3.put("FFIELDCODE","email");
        map3.put("FFIELDNAME","邮箱");
        map3.put("FFIELDTYPE","2");
        thList.add(map3);
        Map<String, Object> map4=new HashMap<>();
        map4.put("FFIELDCODE","sex");
        map4.put("FFIELDNAME","性别");
        map4.put("FFIELDTYPE","1");
        thList.add(map4);
        Map<String, Object> map5=new HashMap<>();
        map5.put("FFIELDCODE","city");
        map5.put("FFIELDNAME","城市");
        map5.put("FFIELDTYPE","3");
        thList.add(map5);


        filedjsonstring = new Gson().toJson( DataTblUtil.getCloumsMap(thList,"FFIELDCODE"));
        jsonstring = new Gson().toJson(thList);
    }catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="css/style.css">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<div class="layui-form" style="padding-top: 10px;padding-left: 10px">

</div>
<fieldset class="layui-elem-field site-demo-button" style="margin:10px;">
    <legend>查询条件</legend>
    <div style="margin:15px;">
        <div class="layui-inline">
            <%--  <%=customButtons%>--%>
            <button class="layui-btn layui-btn-normal" id="add">添加条件</button>
            <button class="layui-btn layui-btn-danger" id="del">删除选中条件</button>
            <button style="margin-left: 10px;" class="layui-btn" type="button" id="search"><i class="layui-icon layui-icon-search"></i>查询</button>
             <div style="margin: 5px;display: none" id="qrycondContainer">
                 <div class="CondStyle"><span>且</span></div>
                 <div id="searchForm" style="border:1px solid darkgray;padding:10px;float: left"></div>
             </div>

        </div>
        <%--<button class="layui-btn" data-type="reload">搜索</button>--%>
    </div>
</fieldset>

<table class="layui-hide" id="assessmentTbl" lay-filter="assessmentTblFilter"></table>

<button type="button" class="layui-btn layui-btn-sm" style="display: none" id="importExcel"><i class="layui-icon"></i>导入文件</button>
<script type="text/html" id="AssmToolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
        <button class="layui-btn layui-btn-sm" lay-event="export"><i class="layui-icon"></i>导出</button>
        <button class="layui-btn layui-btn-sm" lay-event="import"><i class="layui-icon"></i>导入</button>
    </div>
</script>

<script type="text/html" id="editBar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script src="../layui/layui.js" charset="utf-8"></script>
<script src="../layui_exts/excel.js" charset="utf-8"></script>
<script src="assessmnt.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->

<script type="text/javascript">
    var url="<%=basePath%>show.do?method=";
    $(document).ready(function () {
        init( <%=jsonstring%>,'<%=tableCode%>','<%=tableName%>',<%=filedjsonstring%>,<%=customField%>);
    });

</script>


</body>
</html>