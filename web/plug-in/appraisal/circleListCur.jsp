<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>jQuery文字无缝滚动效果代码</title>

    <link type="text/css" href="css/style.css" rel="stylesheet">

    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <script type="text/javascript" src="js/scroll.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('.list_lh li:even').addClass('lieven');

            $("div.list_lh").myScroll({
                speed:40, //数值越大，速度越慢
                rowHeight:76 //li的高度
            });
        })
    </script>
</head>

<body style="">

<div class="bcon">
    <h2><b>不良压降任务完成情况</b></h2>
    <!-- 代码开始 -->
    <div class="list_lh">
        <ul style="margin-top: -53px;">



            <li>
                <p><span class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>
            <li>
                <p><span  class="sp1">八义集</span><em>完成</em><span class="btn_lh">533</span></p>
                <p style="font-size: 12px"><a>一月任务</a><span class="basestyle">11.41</span></p>
            </li>

        </ul>
    </div>
    <!-- 代码结束 -->
</div>



<div id="qds" style="display:none;"></div>
</body>
</html>
