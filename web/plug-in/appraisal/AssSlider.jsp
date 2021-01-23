<%@ page import="com.apex.bank.ext.display.DisPlayEchartsUtil" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="../../echarts/echarts.min.js"></script>
</head>
<%
    String fmodulecode="KMH_BLYJ_2021";
    String date="2021-01";
    String  jsonNameArr="";
    String  percentArr="";
    int listLenght=48/10;
    Map<String,String> jsonMap= DisPlayEchartsUtil.getSliderPercentListStr(fmodulecode,date,2);
    jsonNameArr=jsonMap.get("jsonNameArr");
    percentArr=jsonMap.get("percentArr");

%>
<body>

<div id="main" style="width: 100%;height:400px;"></div>
<script src="js/init.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var jsonNameArr=<%=jsonNameArr%>;
    var percentArr =<%=percentArr%>;
    initSlider(jsonNameArr,percentArr);

</script>
</body>
</html>

