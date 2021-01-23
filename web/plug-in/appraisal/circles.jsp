<%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2021/1/19
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<head>
    <title></title>
    <style type="text/css">
        *{
            margin: 0;
            padding: 0;
        }
    </style>

    <script type="text/javascript" src="js/Progress.js"></script>
    <script src="../../js/jquery.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="css/style.css" media="all">

</head>
<body>
<div class="circle"><canvas id="canvas1"> </canvas><span>支行1</span></div>
<div class="circle"><canvas id="canvas2"> </canvas><span>支行2</span></div>
<div class="circle"><canvas id="canvas3"> </canvas><span>支行3</span></div>
<div class="circle"><canvas id="canvas4"> </canvas><span>支行4</span></div>
<div class="circle"><canvas id="canvas5"> </canvas><span>支行5</span></div>
<div class="circle"><canvas id="canvas6"> </canvas><span>支行6</span></div>
<div class="circle"><canvas id="canvas7"> </canvas><span>支行7</span></div>
<div class="circle"><canvas id="canvas8"> </canvas><span>支行8</span></div>
<div class="circle"><canvas id="canvas9"> </canvas><span>支行9</span></div>
<div class="circle"><canvas id="canvas10"></canvas><span>支行10</span></div>
<script type="text/javascript">
    function laodProgress(){
        new Progress().renderOne('canvas1',100,8,10);
        new Progress().renderOne('canvas2',100,8,20);
        new Progress().renderOne('canvas3',100,8,30);
        new Progress().renderOne('canvas4',100,8,40);
        new Progress().renderOne('canvas5',100,8,50);
        new Progress().renderOne('canvas6',100,8,60);
        new Progress().renderOne('canvas7',100,8,70);
        new Progress().renderOne('canvas8',100,8,80);
        new Progress().renderOne('canvas9',100,8,90);
        new Progress().renderOne('canvas10',100,8,100);
    }
    laodProgress();
    setInterval("laodProgress()",5000)

</script>

</body>
</html>
