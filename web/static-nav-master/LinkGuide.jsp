<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>只言片语导航 &nbsp;- 不懂就搜，一搜就懂</title>
    <meta name="keywords" content="只言片语导航,不懂就搜,实用网址,主页导航,简单网址导航">
    <meta name="description" content="只言片语，可寻万物！"/>
    <link rel="shortcut icon" href="static/img/favicon.ico">
    <link href="static/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="inner-center main">
    <div class="content-inside">
        <!-- slogan start -->
        <div class="logo-box">
            <div class="logo-left">
                <img src="./static/img/logo.gif" alt="logo">
            </div>
            <div class="logo-right">
                <span>网址分类导航</span>
            </div>
        </div>
        <!-- slogan end -->

        <!-- 导航地址 start -->
        <div class="nav-content" id="content">
            <div class="jj-list">
                <div class="jj-list-tit">麦 · Mai</div>
                <ul class="jj-list-con">
                    <li><a href="https://www.google.com/" class="jj-list-link" target="_blank">Google</a></li>
                    <li><a href="https://www.baidu.com/" class="jj-list-link" target="_blank">百度</a></li>
                    <li><a href="https://cn.bing.com/" class="jj-list-link" target="_blank">Bing</a></li>
                    <li><a href="https://www.sogou.com/" class="jj-list-link" target="_blank">搜狗搜索</a></li>
                    <li><a href="https://www.so.com/" class="jj-list-link" target="_blank">360搜索</a></li>
                    <li><a href="https://www.toutiao.com/search/?keyword=/" class="jj-list-link" target="_blank">头条搜索</a></li>
                    <li><a href="https://translate.google.cn/" class="jj-list-link" target="_blank">谷歌翻译</a></li>
                    <li><a href="https://fanyi.baidu.com/" class="jj-list-link" target="_blank">百度翻译</a></li>
                    <li><a href="http://fanyi.youdao.com/" class="jj-list-link" target="_blank">有道翻译</a></li>
                </ul>
            </div>

            <div class="jj-list">
                <div class="jj-list-tit">胖 · Pang</div>
                <ul class="jj-list-con">
                    <li><a href="https://www.zhihu.com/" class="jj-list-link" target="_blank">知乎</a></li>
                    <li><a href="https://web.okjike.com/feed/" class="jj-list-link" target="_blank">即刻</a></li>
                    <li><a href="https://www.v2ex.com/" class="jj-list-link" target="_blank">V2EX</a></li>
                    <li><a href="https://weibo.com/" class="jj-list-link" target="_blank">微博</a></li>
                    <li><a href="https://www.jianshu.com/" class="jj-list-link" target="_blank">简书</a></li>
                    <li><a href="https://www.douban.com/" class="jj-list-link" target="_blank">豆瓣</a></li>
                    <li><a href="http://www.woshipm.com/" class="jj-list-link" target="_blank">产品经理</a></li>
                    <li><a href="http://www.chanpin100.com/" class="jj-list-link" target="_blank">产品壹佰</a></li>
                    <li><a href="http://pm.budong.me/" class="jj-list-link" target="_blank">只言片语</a></li>
                </ul>
            </div>
            <div class="jj-list">
                <div class="jj-list-tit">皮 · Pi</div>
                <ul class="jj-list-con">
                    <li><a href="https://ke.qq.com/" class="jj-list-link" target="_blank">腾讯课堂</a></li>
                    <li><a href="https://study.163.com/" class="jj-list-link" target="_blank">网易云课堂</a></li>
                    <li><a href="https://www.imooc.com/" class="jj-list-link" target="_blank">慕课网</a></li>
                    <li><a href="https://www.bilibili.com/" class="jj-list-link" target="_blank">哔哩哔哩</a></li>
                    <li><a href="https://www.iqiyi.com/" class="jj-list-link" target="_blank">爱奇艺</a></li>
                    <li><a href="https://v.qq.com/" class="jj-list-link" target="_blank">腾讯视频</a></li>
                    <li><a href="https://www.youku.com/" class="jj-list-link" target="_blank">优酷</a></li>
                    <li><a href="http://cpc.people.com.cn/" class="jj-list-link" target="_blank">1024</a></li>
                    <li><a href="http://www.zimuzu.tv/" class="jj-list-link" target="_blank">人人影视</a></li>

                </ul>
            </div>
            <div class="jj-list">
                <div class="jj-list-tit">笑 · Xiao</div>
                <ul class="jj-list-con">
                    <li><a href="https://www.36kr.com/" class="jj-list-link" target="_blank">36氪</a></li>
                    <li><a href="https://www.huxiu.com/" class="jj-list-link" target="_blank">虎嗅</a></li>
                    <li><a href="https://www.toutiao.com/" class="jj-list-link" target="_blank">今日头条</a></li>
                    <li><a href="https://www.163.com/" class="jj-list-link" target="_blank">网易</a></li>
                    <li><a href="https://sspai.com/" class="jj-list-link" target="_blank">少数派</a></li>
                    <li><a href="http://ac.scmor.com/" class="jj-list-link" target="_blank">谷歌镜像</a></li>
                    <li><a href="http://crud.budong.me/" class="jj-list-link" target="_blank">小仙女博客</a></li>
                    <li><a href="https://coding.net/" class="jj-list-link" target="_blank">Coding</a></li>
                    <li><a href="https://www.coolist.net/" class="jj-list-link" target="_blank">酷软清单</a></li>
                </ul>
            </div>
            <div class="jj-list">
                <div class="jj-list-tit">嘻 · Xi</div>
                <ul class="jj-list-con">
                    <li><a href="https://map.baidu.com/" class="jj-list-link" target="_blank">百度地图</a></li>
                    <li><a href="http://www.google.cn/maps" class="jj-list-link" target="_blank">谷歌地图</a></li>
                    <li><a href="https://www.amap.com/" class="jj-list-link" target="_blank">高德地图</a></li>
                    <li><a href="https://sm.ms/" class="jj-list-link" target="_blank">SM图床</a></li>
                    <li><a href="https://tool.lu/" class="jj-list-link" target="_blank">程序员工具</a></li>
                    <li><a href="http://www.alloyteam.com/nav/" class="jj-list-link" target="_blank">前端工具</a></li>
                    <li><a href="http://www.99baiduyun.com/" class="jj-list-link" target="_blank">网盘搜索</a></li>
                    <li><a href="https://cli.im/" class="jj-list-link" target="_blank">二维码</a></li>
                    <li><a href="http://www.qinms.com/webapp/barcode/index.aspx" class="jj-list-link" target="_blank">条形码</a></li>

                </ul>
            </div>
            <div class="jj-list">
                <div class="jj-list-tit">嘻 · Xi</div>
                <ul class="jj-list-con">

                    <li><a href="http://www.dytt8.net/" class="jj-list-link" target="_blank">电影天堂</a></li>
                    <li><a href="https://zhuanlan.zhihu.com/p/36456284" class="jj-list-link" target="_blank">图片素材</a></li>
                    <li><a href="https://www.portablesoft.org/" class="jj-list-link" target="_blank">效率工具</a></li>
                    <li><a href="https://www.taobao.com/" class="jj-list-link" target="_blank">淘宝</a></li>
                    <li><a href="https://www.jd.com/" class="jj-list-link" target="_blank">京东</a></li>
                    <li><a href="https://www.suning.com/" class="jj-list-link" target="_blank">苏宁</a></li>
                    <li><a href="https://www.smzdm.com/" class="jj-list-link" target="_blank">什么值得买</a></li>
                    <li><a href="http://www.zealer.com/" class="jj-list-link" target="_blank">Zealer</a></li>
                    <li><a href="http://www.manmanbuy.com/" class="jj-list-link" target="_blank">慢慢买</a></li>


                </ul>
            </div>
        </div>
        <!-- 导航地址 end -->
    </div>
</div>
<footer class="inner-center footer">
    <p style="margin-top:8px;"><span>Copyright © 2020 Hosted by ApexSoft</span></p>
</footer>
<script src="./static/js/jquery.min.js"></script>
<script src="./static/js/initLink.js"></script>
<script type="text/javascript">
    var url="<%=basePath%>linkurl.do?method=getLinkAddress";
</script>


</body>
</html>
