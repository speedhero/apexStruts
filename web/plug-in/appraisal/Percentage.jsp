<%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2021/1/21
  Time: 23:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="main" style="height:400px;width: 100%;padding: 20px"></div>
</body>
<script src="../../echarts/echarts.min.js"></script>
<script src="js/init.js"></script>
<script>
    var yData = ["河北", "北京", "天津", "河南", "山东", "内蒙", "宁夏", "陕西", "四川", "湖南"];
    //百分比数据
    var ratio = [100, 12, 10, 7, 6, 5, 5, 4, 3, 2];
    //100%-ratio
    var surplus = [0+10, 88+10, 90+10, 93+10, 94+10, 95+10, 95+10, 96+10, 97+10, 98+10];
    drawPercentage('main', yData, ratio, surplus);
</script>
</html>
