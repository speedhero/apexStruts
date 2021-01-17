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
    String id = request.getParameter("id");//用request得到
    String tableCode = request.getParameter("TableCode");
    tableCode ="KMH_LXSR_2021";// "F03_BIG_SCREEN_DEPOSIT_INFO";
    String tableName =  "";
    String template="";
    String toolBarButtons="";
    String toolColButtons="";
    Connection connection = null;
    String jsonstring="";
    String  filedjsonstring="";
    String  tablejsonstring="";
    String customField="0";

    try {
        Gson gson= new Gson();
        List<Map<String, Object>> thList =null;//一级表
        List<Map<String, Object>> tbList=null;
        //Establish connection
        //connection = DataAccess.getDataSource().getConnection();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB2Handle.getDataSource());
        thList= DataTblUtil.getHeadList(tableCode);
        int cnt= jdbcTemplate.queryForInt("select count(*) from Exa_TableField WHERE FTABLE='" +tableCode+"'");
         if(thList!=null&&thList.size()>cnt){
            return;
         }
        customField=DataTblUtil.getCustomFiledStr();
        //Map tableMap= jdbcTemplate.queryForMap("select * from Exa_Table  where FCODE='"+tableCode+"'");
        //tableName= tableMap.get("FNAME").toString();

        tbList= jdbcTemplate.queryForList("select * from Exa_Table  where FCODE='"+tableCode+"'");
        if(tbList.size()>0){
            tableName=  tbList.get(0).get("FNAME").toString();
            tablejsonstring = gson.toJson( tbList.get(0));
        }
        filedjsonstring = gson.toJson( DataTblUtil.getCloumsMap(thList,"FFIELDCODE"));
        jsonstring = gson.toJson(thList);
        template=DataTblUtil.getTemplate(tableCode);
        toolBarButtons=DataTblUtil.getToolBarButtons(tableCode,1);
        toolColButtons=DataTblUtil.getToolBarButtons(tableCode,2);

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
    <style>
        .layui-table-cell {
            height: auto;
            line-height: 28px;
        }
    </style>
</head>
<body>
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
                 <div class="CondStyle"><span>且</span></div>
                 <div id="searchForm" style="border:1px solid darkgray;padding:10px;float: left"></div>
             </div>

        </div>
        <%--<button class="layui-btn" data-type="reload">搜索</button>--%>
    </div>
</fieldset>

<table class="layui-hide" id="assessmentTbl" lay-filter="assessmentTblFilter"></table>


<script type="text/html" id="AssmToolbar">
    <div class="layui-btn-container">
<%--        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
       <button class="layui-btn layui-btn-sm" lay-event="getCheckLength"><i class="layui-icon layui-icon-add-circle-fine" style="font-size:20px;font-weight:bold"></i>新增开门红</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
        <button class="layui-btn layui-btn-sm" lay-event="export"><i class="layui-icon"></i>导出</button>
        <button class="layui-btn layui-btn-sm layui-btn-dange" lay-event="import"><i class="layui-icon"></i>导入</button>--%>
        <%=toolBarButtons%>
    </div>
</script>

<script type="text/html" id="editBar">
<%--    <a class="layui-btn layui-btn-xs layui-btn-primary layui-btn-mini " lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger " lay-event="del">删除</a>--%>
    <%=toolColButtons%>
</script>

<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script src="../layui/layui.js" charset="utf-8"></script>
<script src="../layui_exts/excel.js" charset="utf-8"></script>
<script src="assessmnt.js" charset="utf-8"></script>
<script src="customfun.js" charset="utf-8"></script>


<script type="text/javascript">
    var url="<%=basePath%>show.do?method=";
    $(document).ready(function () {
        init(<%=tablejsonstring%>, <%=jsonstring%>,'<%=tableCode%>','<%=tableName%>',<%=filedjsonstring%>,<%=customField%>);
    });

</script>

<%--生成模板模块--%>
<%--<script type="text/html" id="orgTpl">
    <span style="color: #F581B1;"> <a href="www.baidu.com" class="layui-table-link" target="_blank">{{ d.FORG }}</a></span>
</script>--%>
<%=template%>
<%--生成模板模块--%>
<script>
    function bindArea(table) {
        var layer = layui.layer;
        //头工具栏事件
        table.on('toolbar(assessmentTblFilter)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);

            switch(obj.event){
                case 'add':
                    var data = checkStatus.data;
                    openForm(layer,data,1,obj);
                    break;
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;

                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
                case 'import':
                    importFile();
                    break;
                case 'export':
                    exportFile();
                    break;
                case 'exportCheck':
                    var data = checkStatus.data;
                    exportCheckedFile(data);
                    break;
                case 'deleteChecked':
                    var data = checkStatus.data;
                    delChecked(layer,data,obj);
                    break;


            };
        });

        //监听工具条
        table.on('tool(assessmentTblFilter)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                //layer.msg('ID：'+ data.ID + ' 的查看操作');
                openForm(layer,data,3,obj);
            } else if(obj.event === 'del'){
                del(layer,data,obj);
            } else if(obj.event === 'edit'){
                openForm(layer,data,2,obj);//1新增2修改3查看4删除
            }

        });
    }
</script>
</body>
</html>
<%@include file="ResInclude.jsp"%>



