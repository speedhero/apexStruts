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
    <script src="/plug-in/bank/dsc/js/echarts.min.js" ></script>
</head>
<%
    String fmodulecode="";
    String chartType="";
    String ftitle=" ";
    int fsort=1;
    int percentType=2;
    fmodulecode= request.getParameter("FModuleCode");
    chartType= request.getParameter("FChart");
    ftitle= request.getParameter("FTitle")==null?"":request.getParameter("FTitle");
    fsort= (request.getParameter("FSort")==null||"null".equalsIgnoreCase(request.getParameter("FSort"))||"".equalsIgnoreCase(request.getParameter("FSort")))?1:Integer.parseInt(request.getParameter("FSort"));
    percentType= request.getParameter("PercentType")==null?1:Integer.parseInt(request.getParameter("PercentType"));
    //chartType=5;

    String date= DisPlayEchartsUtil.getMaxDataDate(fmodulecode);// "2021-01";
    String  jsonNameArr="[]";
    String  percentArr="[]";
    String  extpercentArr="[]";
    String  actualtArr="[]";
    String  jsonTrgArr="[]";
    String  groupArr="[]";
    String pageArr="[]";
    String colorList="[]";
    boolean isSlider=true;
    int listLenght=48/10;
     //1|分组柱状图折线图;2滑动柱状图;3排名图;4折线图;5分页图
    if("1".equalsIgnoreCase(chartType)){
        Map<String,String> jsonMap= DisPlayEchartsUtil.getGroupChartListStr(fmodulecode,date,percentType);//1
        jsonNameArr=jsonMap.get("jsonNameArr");
        percentArr =jsonMap.get("percentArr");
        actualtArr =jsonMap.get("actualtArr");
        groupArr   =jsonMap.get("groupArr");
    }else  if("2".equalsIgnoreCase(chartType)){
        Map<String,String> jsonMap= DisPlayEchartsUtil.getSliderPercentListStr(fmodulecode,date,percentType);//2
        jsonNameArr=jsonMap.get("jsonNameArr");
        percentArr=jsonMap.get("percentArr");
        colorList=DisPlayEchartsUtil.getColor(fmodulecode,ftitle);
    }else  if("3".equalsIgnoreCase(chartType)){
       // 1|前10名;2|后10名;2|前5名;2|后5名;5|所有排名
           fsort=5;
           Map<String,String> jsonMap= DisPlayEchartsUtil.getSortChartListStr(fmodulecode,date,percentType,fsort);//2
           jsonNameArr=jsonMap.get("jsonNameArr");
           percentArr=jsonMap.get("percentArr");
           extpercentArr=jsonMap.get("extpercentArr");
    }else  if("4".equalsIgnoreCase(chartType)){
        Map<String,String> jsonMap= DisPlayEchartsUtil.getZlineChartListStr(fmodulecode,date,percentType);//2
        jsonNameArr=jsonMap.get("jsonNameArr");
        percentArr =jsonMap.get("percentArr");
        actualtArr =jsonMap.get("actualtArr");
        groupArr   =jsonMap.get("groupArr");
        jsonTrgArr= jsonMap.get("jsonTrgArr");
        pageArr=jsonMap.get("pageArr");
    }else if("5".equalsIgnoreCase(chartType)){
        Map<String,String> jsonMap= DisPlayEchartsUtil.getPageListStr(fmodulecode,date,percentType);//1
        jsonNameArr=jsonMap.get("jsonNameArr");
        jsonTrgArr=jsonMap.get("jsonTrgArr");
        actualtArr=jsonMap.get("jsonActArr");
        pageArr=jsonMap.get("pageArr");
    }
    isSlider="1".equalsIgnoreCase(DisPlayEchartsUtil.getIsSlider(fmodulecode,ftitle))?true:false;
    String dataMon=date.substring(5,7);
    ftitle=ftitle+"("+dataMon+"月)";
%>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<%--<a href="http://localhost:8080/apexstruts/plug-in/appraisal/AssSlider.jsp" target="_blank">新窗口打开</a><br />--%>
<div id="main" style="width: 100%;height:100%"></div>
<script src="/plug-in/bank/dsc/appraisal/js/init.js"></script>
<script>
    var  chartType=<%=chartType%>;
    var ftitle='<%=ftitle%>';
    var isSlider=<%=isSlider%>;
    //1|分组柱状图折线图;2滑动柱状图;3排名图;4折线图;5分页图
    if(1==chartType){

        var  jsonNameArr=<%=jsonNameArr%>;
        var  percentArr= <%=percentArr%>;
        var  actualtArr=<%=actualtArr%>;
        var  groupArr=<%=groupArr%>;
        renderGroupCharts(jsonNameArr,percentArr,actualtArr,groupArr,isSlider,ftitle);//1|分组柱状图折线图;
    }else if(2==chartType){
        var  jsonNameArr=<%=jsonNameArr%>;
        var  percentArr=<%=percentArr%>;
        var colorList=<%=colorList%>;
        initSlider(jsonNameArr,percentArr,colorList,isSlider,ftitle);//滑动柱状图
    }else if(3==chartType){
       // var yData = ["河北", "北京", "天津", "河南", "山东", "内蒙", "宁夏", "陕西", "四川", "湖南"];
        //百分比数据
        //var ratio = [100, 12, 10, 7, 6, 5, 5, 4, 3, 2];
        //100%-ratio
        //var surplus = [0+10, 88+10, 90+10, 93+10, 94+10, 95+10, 95+10, 96+10, 97+10, 98+10];
        var  jsonNameArr=<%=jsonNameArr%>;
        var  percentArr=<%=percentArr%>;
        var  extpercentArr=<%=extpercentArr%>;
        drawPercentage(jsonNameArr,percentArr,extpercentArr,isSlider,ftitle);
    }else if(4==chartType){
        var  jsonNameArr=<%=jsonNameArr%>;
        var  percentArr= <%=percentArr%>;
        var  actualtArr=<%=actualtArr%>;
        var  groupArr=<%=groupArr%>;
        var jsonTrgArr=<%=jsonTrgArr%>;

        renderZline(groupArr,jsonNameArr,jsonTrgArr,actualtArr,percentArr,ftitle,isSlider);
    }else if(5==chartType){
        // 基于准备好的dom，初始化echarts实例
        var jsonNameArr=<%=jsonNameArr%>;
        var jsonActArr =<%=actualtArr%>;
        var jsonTrgArr =<%=jsonTrgArr%>;
        var pageArr =<%=pageArr%>;
        initPageAss(jsonActArr,jsonNameArr,jsonTrgArr,pageArr,ftitle);
    }






</script>
</body>
</html>