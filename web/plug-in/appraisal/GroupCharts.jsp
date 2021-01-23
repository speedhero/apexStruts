<%@ page import="com.apex.bank.ext.display.DisPlayEchartsUtil" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2021/1/21
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="../../echarts/echarts.min.js"></script>
</head>
<%
    String fmodulecode="KMH_DKYX_2021";
    String date="2021-01";
    String  jsonNameArr="";
    String  percentArr="";
    String  actualtArr="";
    String  groupArr="";
    int listLenght=48/10;
    Map<String,String> jsonMap= DisPlayEchartsUtil.getGroupChartListStr(fmodulecode,date,1);
    jsonNameArr=jsonMap.get("jsonNameArr");
    percentArr =jsonMap.get("percentArr");
    actualtArr =jsonMap.get("actualtArr");
    groupArr   =jsonMap.get("groupArr");

%>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<a href="http://localhost:8080/apexstruts/plug-in/appraisal/AssSlider.jsp" target="_blank">新窗口打开</a><br />
<div id="main" style="width: 100%;height:100%"></div>
<script src="js/init.js"></script>
<script>
    var  jsonNameArr=<%=jsonNameArr%>;
    var  percentArr=<%=percentArr%>;
    var  actualtArr=<%=actualtArr%>;
    var  groupArr=<%=groupArr%>;
    renderMoreCharts(jsonNameArr,percentArr,actualtArr,groupArr);
</script>
</body>
</html>
