<%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2020/1/2
  Time: 0:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPEHTMLPUBLIC"-//W3C//DTDHTML4.01Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>MyStrutsJQueryJson</title>
    <meta http-equiv="pragma"content="no-cache">
    <meta http-equiv="cache-control"content="no-cache">
    <meta http-equiv="expires"content="0">
    <meta http-equiv="keywords"content="keyword1,keyword2,keyword3">
    <meta http-equiv="description"content="Thisismypage">
    <!--
    <linkrel="stylesheet"type="text/css"href="styles.css">
    -->
    <script type="text/javascript"src="/js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        //1.jquery单独传递单个参数
        /*$(function(){
        alert("start");
        $.post("testxml.do",{testvalue:$('#test').val()},function(data){
        alert(data);
        })
        })*/
        //2.jquery单独传递含单个参数的list,采用的是xml形式
        $(function(){
            $.post("testxml.do",{testvalue:$('#test').val()},function(data){
                varitems=data.getElementsByTagName("item");
                alert(items.length);
                for(vari=0;i<items.length;i++){
                    alert(items[i].childNodes[0].nodeValue);
                }
            })
        })
    //3.jquery单独传递含对象类型的list,,采用的是xml形式
    /*$(function(){
    $.post("testxml.do",{testvalue:$('#test').val()},function(data){
    varitems=data.getElementsByTagName("itemslist");
    //循环输出username和password
    for(vari=0;i<items.length;i++){
    varchilds=items[i].childNodes;
    for(varj=0;j<childs.length;j++){
    alert(childs[j].firstChild.nodeValue);
    }
    }
    })
    })
    */
    </script>
</head>
<body>&nbsp;
<form action="/testxml.do">
<input type="button"value="JQuery"id="jquerytest"><br>
<input type="text"value="JQuerytest"name="test"id="test">
<font id="userlogin">登陆</font><span id="loading"></span>
<p id="result"></p>
</form>
</body>
</html>
