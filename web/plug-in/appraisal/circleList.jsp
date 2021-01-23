<%--
  Created by IntelliJ IDEA.
  User: Rick
  Date: 2021/1/19
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head>
    <meta charset="utf-8">
    <title>js轮播列表</title>

    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/table.css" type="text/css">

    <script src="../../js/jquery.min.js"></script>

</head>
<body style="">
<div class="lunbo_div">
    <div>
        <img class="cup" src="./images/cup.png">
        <span class="span_bm">
                <span class="today_title">贷款营销任务当日完成</span>
                <br>
                <span class="totady_pers">100</span>
            </span>
    </div>
    <div id="bm_content" class="bm_content">
        <table class="table table-condensed ">
            <thead style="position: fixed; background-color: #eeeeee; ">
            <tr>
                <th style=" width: 60px; text-align: right;">支行</th>
                <th style=" width: 110px; text-align: center;">已完成</th>
                <th style=" width: 110px; padding-right: 30px;">户数任务</th>
                <th style=" width: 110px; padding-right: 30px;">时点任务</th>
                <th style=" width: 110px; padding-right: 30px;">日均任务</th>
            </tr>
            </thead>
            <tbody id="tb1"><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三1" style="margin-left: 10px;">张三1</span></td><td class="ellipsis" title="1年级上海教育版同步课">1年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三2" style="margin-left: 10px;">张三2</span></td><td class="ellipsis" title="2年级上海教育版同步课">2年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三3" style="margin-left: 10px;">张三3</span></td><td class="ellipsis" title="3年级上海教育版同步课">3年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三4" style="margin-left: 10px;">张三4</span></td><td class="ellipsis" title="4年级上海教育版同步课">4年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三5" style="margin-left: 10px;">张三5</span></td><td class="ellipsis" title="5年级上海教育版同步课">5年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三6" style="margin-left: 10px;">张三6</span></td><td class="ellipsis" title="6年级上海教育版同步课">6年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三7" style="margin-left: 10px;">张三7</span></td><td class="ellipsis" title="7年级上海教育版同步课">7年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三8" style="margin-left: 10px;">张三8</span></td><td class="ellipsis" title="8年级上海教育版同步课">8年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三9" style="margin-left: 10px;">张三9</span></td><td class="ellipsis" title="9年级上海教育版同步课">9年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三10" style="margin-left: 10px;">张三10</span></td><td class="ellipsis" title="10年级上海教育版同步课">10年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三11" style="margin-left: 10px;">张三11</span></td><td class="ellipsis" title="11年级上海教育版同步课">11年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三12" style="margin-left: 10px;">张三12</span></td><td class="ellipsis" title="12年级上海教育版同步课">12年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三13" style="margin-left: 10px;">张三13</span></td><td class="ellipsis" title="13年级上海教育版同步课">13年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三14" style="margin-left: 10px;">张三14</span></td><td class="ellipsis" title="14年级上海教育版同步课">14年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三15" style="margin-left: 10px;">张三15</span></td><td class="ellipsis" title="15年级上海教育版同步课">15年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三16" style="margin-left: 10px;">张三16</span></td><td class="ellipsis" title="16年级上海教育版同步课">16年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三17" style="margin-left: 10px;">张三17</span></td><td class="ellipsis" title="17年级上海教育版同步课">17年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三18" style="margin-left: 10px;">张三18</span></td><td class="ellipsis" title="18年级上海教育版同步课">18年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三19" style="margin-left: 10px;">张三19</span></td><td class="ellipsis" title="19年级上海教育版同步课">19年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三20" style="margin-left: 10px;">张三20</span></td><td class="ellipsis" title="20年级上海教育版同步课">20年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr></tbody>
            <tbody id="tb2"><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三1" style="margin-left: 10px;">张三1</span></td><td class="ellipsis" title="1年级上海教育版同步课">1年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三2" style="margin-left: 10px;">张三2</span></td><td class="ellipsis" title="2年级上海教育版同步课">2年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三3" style="margin-left: 10px;">张三3</span></td><td class="ellipsis" title="3年级上海教育版同步课">3年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三4" style="margin-left: 10px;">张三4</span></td><td class="ellipsis" title="4年级上海教育版同步课">4年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三5" style="margin-left: 10px;">张三5</span></td><td class="ellipsis" title="5年级上海教育版同步课">5年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三6" style="margin-left: 10px;">张三6</span></td><td class="ellipsis" title="6年级上海教育版同步课">6年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三7" style="margin-left: 10px;">张三7</span></td><td class="ellipsis" title="7年级上海教育版同步课">7年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三8" style="margin-left: 10px;">张三8</span></td><td class="ellipsis" title="8年级上海教育版同步课">8年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三9" style="margin-left: 10px;">张三9</span></td><td class="ellipsis" title="9年级上海教育版同步课">9年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三10" style="margin-left: 10px;">张三10</span></td><td class="ellipsis" title="10年级上海教育版同步课">10年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三11" style="margin-left: 10px;">张三11</span></td><td class="ellipsis" title="11年级上海教育版同步课">11年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三12" style="margin-left: 10px;">张三12</span></td><td class="ellipsis" title="12年级上海教育版同步课">12年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三13" style="margin-left: 10px;">张三13</span></td><td class="ellipsis" title="13年级上海教育版同步课">13年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三14" style="margin-left: 10px;">张三14</span></td><td class="ellipsis" title="14年级上海教育版同步课">14年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三15" style="margin-left: 10px;">张三15</span></td><td class="ellipsis" title="15年级上海教育版同步课">15年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三16" style="margin-left: 10px;">张三16</span></td><td class="ellipsis" title="16年级上海教育版同步课">16年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三17" style="margin-left: 10px;">张三17</span></td><td class="ellipsis" title="17年级上海教育版同步课">17年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三18" style="margin-left: 10px;">张三18</span></td><td class="ellipsis" title="18年级上海教育版同步课">18年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三19" style="margin-left: 10px;">张三19</span></td><td class="ellipsis" title="19年级上海教育版同步课">19年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr><tr><td class="ellipsis"><img src="./images/portrait_default.png"><span class="center" title="张三20" style="margin-left: 10px;">张三20</span></td><td class="ellipsis" title="20年级上海教育版同步课">20年级上海教育版同步课</td><td class="ellipsis" title="16点46分">16点46分</td> </tr></tbody>
        </table>
    </div>
</div>

<script>

    ; (function ($) {
        var box = document.getElementById("bm_content");
        var l1 = document.getElementById("tb1");
        var l2 = document.getElementById("tb2");
        autoScroll();
        function autoScroll() {
            var product = RenderList();
            l1.innerHTML = product;
            if (l1.offsetHeight > box.offsetHeight) {
                l2.innerHTML = l1.innerHTML;//克隆list1的数据，使得list2和list1的数据一样
                scrollMove = setInterval(scrollup, 30);//数值越大，滚动速度越慢
                box.onmouseover = function () {
                    clearInterval(scrollMove)
                }
            }
        }
        function scrollup() {
            //滚动条距离顶部的值恰好等于list1的高度时，达到滚动临界点，此时将让scrollTop=0,让list1回到初始位置，实现无缝滚动
            if (box.scrollTop >= l1.offsetHeight) {
                box.scrollTop = 0;
            } else {
                box.scrollTop++;
            }
        }
        //鼠标离开时，滚动继续
        box.onmouseout = function () {
            scrollMove = setInterval(scrollup, 30);
        }
        function RenderList() {
            var str = '';
            for (var i = 0; i < 20; i++) {
                var a = i + 1;
                str += '<tr>';
                str += '<td class="ellipsis">';
                str += '<img src="./images/portrait_default.png"/>';
                str += '<span class="center" title="滩上' + a + '" style="margin-left: 10px;">滩上' + a + '</span>';
                str += '</td>';
                str += '<td class="ellipsis" title="' + a + '1月任务">' + a + '1月任务</td>';
                str += '<td class="ellipsis" title="户数任务">户数任务188</td>';
                str += '<td class="ellipsis" title="时点任务">时点任务188</td>';
                str += '<td class="ellipsis" title="日均任务">日均任务188</td>';
                str += ' </tr>';
            }
            return str;
        }

    })(jQuery)
</script>


<div id="qds" style="display:none;"></div></body></html>
