<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path=request.getContextPath();
  String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC"-//W3C//DTDHTML4.01Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">
  <title>struts+jquery+json</title>
  <meta http-equiv="pragma"content="no-cache">
  <meta http-equiv="cache-control"content="no-cache">
  <meta http-equiv="expires"content="0">
  <meta http-equiv="keywords"content="keyword1,keyword2,keyword3">
  <meta http-equiv="description"content="This is mypage">
  <!--
  <linkrel="stylesheet"type="text/css"href="styles.css">
  -->
  <script type="text/javascript"src="js/jquery-1.8.3.js"></script>
  <script type="text/javascript">
         <%--1.struts1.2+JQuery+Json传递list参数，此时list的类型为String--%>
        <%--  $(function(){
          $("#submitbutton").click(function(){
          varparams={testvalue:$('#test').val()};
          $.ajax({
          url:"test.do",
          data:params,
          type:'post',
          dataType:'json',
          success:function(data){
          //data值：["string1","string2","string3"]
          alert("成功");
          alert(data.length);
          for(varj=0;j<data.length;j++)
          {
          alert(data[j]);
          }
          },
          error:function(){
          alert("失败");
          }
          })
          })
          })--%>

         <%-- //2.struts1.2+JQuery+Json传递Map参数
          $(function(){
          $("#submitbutton").click(function(){
          alert("start");
          varparams={testvalue:$('#test').val()};
          $.ajax({
          url:"test.do",
          data:params,
          type:'post',
          dataType:'json',
          success:function(data){
          //alert(data.name1);//缺点是须知道属性名，在JSONArray下
          //迭代循环输出
          //data值为:[{"name3":"string3","name1":"string1","name2":"string2"}]
          $.each(data[0],function(key,value){
          alert(key+""+value);
          })
          },
          error:function(){
          alert("失败");
          }
          })
          })
          })--%>


         <%-- //3.struts1.2+JQuery+Json传递String参数
          $(function(){
          $("#submitbutton").click(function(){
          varparams={testvalue:$('#test').val()};
          alert("start");
          $.ajax({
          url:"test.do",
          data:params,
          type:'post',
          dataType:'json',
          success:function(data){
          alert("成功");
          alert(data.name);
          },
          error:function(){
          alert("失败");
          }
          })
          })
          })--%>

        <%--  //4.struts1.2+JQuery+Json传递User参数
          $(function(){
          $("#submitbutton").click(function(){
          varparams={testvalue:$('#test').val()};
          $.ajax({
          url:"test.do",
          data:params,
          type:'post',
          dataType:'json',
          success:function(data){
          //data值为：{"password":1,"username":"你好"}
          alert("成功");
          alert(data.username);
          alert(data.password);
          },
          error:function(){
          alert("失败");
          }
          })
          })
          })--%>
          <%--5.struts1.2+JQuery+Json传递list参数，此时list的类型为User--%>

          $(function(){
            $("#submitbutton").click(function(){
              var params={testvalue:$('#test').val()};
              $.ajax({
                url:"test.do",
                data:params,
                type:'post',
                dataType:'json',
                success:function(data){
                  alert("成功");////data值：[{"password":1,"username":"u1"},{"password":2,"username":"u2"},{"password":3,"username":"u3"}]
                  /*for(var j=0;j<data.length;j++)
                  {
                  alert(data[j].username);
                  alert(data[j].password);
                  }*/
                  $.each(data,function(i){
                    $.each(data[i],function(key,value){
                      alert(key+""+value);
                    })
                  })
                },
                error:function(){
                  alert("失败");
                }
              })
            })
          })
  </script>
</head>
<body>
测试输入框：<input  type="text"id="test" name="hello"><br>
<input type="button" name="submitbutton" id="submitbutton" value="提交">
</body>
</html>