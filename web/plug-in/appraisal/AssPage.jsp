<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.apex.bank.ext.display.DisPlayEchartsUtil" %>
<%@ page import="com.apex.bank.util.MapUtil" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2021/1/20
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<%
    String fmodulecode="KMH_CXCKRJ_2021";
    String date="2021-01";
    String  jsonNameArr="";
    String  jsonTrgArr="";
    String  jsonActArr="";
    String pageArr="";
    int listLenght=48/10;
    Map<String,String> jsonMap= DisPlayEchartsUtil.getPageListStr(fmodulecode,date,1);
    jsonNameArr=jsonMap.get("jsonNameArr");
    jsonTrgArr=jsonMap.get("jsonTrgArr");
    jsonActArr=jsonMap.get("jsonActArr");
    pageArr=jsonMap.get("pageArr");

%>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="../../echarts/echarts.min.js"></script>
    <script src="js/init.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 100%;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var jsonNameArr=<%=jsonNameArr%>;
    var jsonActArr =<%=jsonActArr%>;
    var jsonTrgArr =<%=jsonTrgArr%>;
    var pageArr =<%=pageArr%>;
    initPageAss(jsonActArr,jsonNameArr,jsonTrgArr,pageArr);

</script>
</body>
</html>
