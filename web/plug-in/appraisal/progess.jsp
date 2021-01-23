<%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2021/1/19
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>jquery,css3特效</title>
    <script src="../../js/jquery.min.js" type="text/javascript"></script>
    <script src="js/progressBar.js"></script></head>


<!--
	在需要插入进度条的节点添加 class:div,和进度条的值。也可动态取
 -->
<body style="">
<div class="div" w="50">

    <dl class="barbox" style="height: 15px; line-height: 15px; text-align: center; color: rgb(255, 255, 255);"><dd class="barline" style="float: left; width: 200px; background: rgb(223, 223, 223); height: 15px; overflow: hidden; display: inline; position: relative; border-radius: 8px;"><div w="50" class="charts" style="width: 50%; background: red; height: 15px; border-radius: 8px;"><d>50%</d></div></dd></dl></div>
<div class="div" w="60">

    <dl class="barbox" style="height: 15px; line-height: 15px; text-align: center; color: rgb(255, 255, 255);"><dd class="barline" style="float: left; width: 200px; background: rgb(223, 223, 223); height: 15px; overflow: hidden; display: inline; position: relative; border-radius: 8px;"><div w="60" class="charts" style="width: 60%; background: red; height: 15px; border-radius: 8px;"><d>60%</d></div></dd></dl></div>
<div class="div" w="70">

    <dl class="barbox" style="height: 15px; line-height: 15px; text-align: center; color: rgb(255, 255, 255);"><dd class="barline" style="float: left; width: 200px; background: rgb(223, 223, 223); height: 15px; overflow: hidden; display: inline; position: relative; border-radius: 8px;"><div w="70" class="charts" style="width: 70%; background: red; height: 15px; border-radius: 8px;"><d>70%</d></div></dd></dl></div>
<div class="div" w="80">

    <dl class="barbox" style="height: 15px; line-height: 15px; text-align: center; color: rgb(255, 255, 255);"><dd class="barline" style="float: left; width: 200px; background: rgb(223, 223, 223); height: 15px; overflow: hidden; display: inline; position: relative; border-radius: 8px;"><div w="80" class="charts" style="width: 80%; background: red; height: 15px; border-radius: 8px;"><d>80%</d></div></dd></dl></div>
<div class="div" w="90">

    <dl class="barbox" style="height: 15px; line-height: 15px; text-align: center; color: rgb(255, 255, 255);"><dd class="barline" style="float: left; width: 200px; background: rgb(223, 223, 223); height: 15px; overflow: hidden; display: inline; position: relative; border-radius: 8px;"><div w="90" class="charts" style="width: 90%; background: red; height: 15px; border-radius: 8px;"><d>90%</d></div></dd></dl></div>
<div class="div" w="100">

    <dl class="barbox" style="height: 15px; line-height: 15px; text-align: center; color: rgb(255, 255, 255);"><dd class="barline" style="float: left; width: 200px; background: rgb(223, 223, 223); height: 15px; overflow: hidden; display: inline; position: relative; border-radius: 8px;"><div w="100" class="charts" style="width: 100%; background: red; height: 15px; border-radius: 8px;"><d>100%</d></div></dd></dl></div>




<div id="qds" style="display:none;"></div></body></html>
