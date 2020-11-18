<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>对私客户画像</title>
    <link rel="stylesheet" href="<%=basePath%>css/common.css">
    <link rel="stylesheet" href="<%=basePath%>css/css.css">
    <link rel="stylesheet" href="<%=basePath%>css/jquery.Jcrop.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.Jcrop.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.form.js"></script>

    <title>Title</title>
</head>
<body>
<div class="box">
    <div class="l-box fl">
        <div class="customer-top">
            <div class="mt10">
                <div class="customer-ph">
                    <form action="" method="post" enctype="multipart/form-data" name="form" id="ImgForm"><td width="40%">
                        <div class="bgPhoto">
                            <a href="javascript:void(0)" class="cal_decoration"><img id="defauPhoto" title="编辑头像" style="width: 120px;height: 120px;" src="<%=basePath%>uploadImage.do?method=getImage&custId=11111111"/></a>
                        </div>
                        <div id="light" class="white_content">
                            <div style="width: 97.8%;height: 20px;background-color: #B1834C;font-size: 12px;color: white;
									padding-left: 9px;padding-top:2px;float: left;">更换头像
                                <a href="javascript:void(0)" id="close" class="cal_decoration">X</a></div>
                            <table style="margin-left: 4%">
                                <tr>
                                    <td><a href="javascript:" class="a-upload">
                                        <input type="file" name="file" id="file" onchange="checkImgType(this)">本地图片
                                    </a>
                                    </td>
                                    <td style="color: #888888;width: 100%;padding-left: 4%;">预览</td>
                                </tr>
                                <tr>
                                    <td id="USERIMAGE"><img id="userimg" style="width: 200px; height: 200px;"/></td>
                                    <td>
                                        <div id="preview-pane" style="width: 120px; height: 120px; overflow: hidden; margin-left: 5px;">
                                            <div class="preview-container">
                                                <img id="preview"/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="hidden" id="x" title="截取区相对于图片左上角原点的横坐标"/>
                                        <input type="hidden" id="y" title="截取区相对于图片左上角的原点纵坐标"/>
                                        <input type="hidden" id="w" title="截取区的宽度"/>
                                        <input type="hidden" id="h" title="截取区的高"/>
                                        <a href="javascript:;" class="a-upload">
                                            <input type="button" id="photo" onclick="saveHeadPhoto()"/>保存头像
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="fade" class="black_overlay"></div>
                    </form>
                </div>
            </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var width;  // 裁剪框的宽度
    var height; // 裁剪框的高度
    var x;      // 裁剪框的起点x
    var y;      // 裁剪框的起点y
    var jcrop_api;
    var xsize = $('#preview-pane .preview-container').width();  // 获取预览窗格相关信息
    var ysize = $('#preview-pane .preview-container').height();
    var $pimg = $('#preview-pane .preview-container img');
    var $preview = $('#preview-pane');
    var boundx;
    var boundy;
    /*点击默认头像触发*/
    $("#defauPhoto").click(function(){
        $("#light").css("display","block");
        $("#fade").css("display","block");
    });


    /*本地相片按钮触发*/
    /*检查图片上传类型*/
    function checkImgType(obj){//选中图片后预览
        if(""==obj.value){//如果没有选择图片则不提示
            return;
        }
        var imgFile = '';
        var imgFilePath = getImgFullPath(obj);//获取图像路径
        /*图片类型是否正确标识*/
        var tag = true;
        /*通过判断endIndex是否为-1来判断是否选择了文件*/
        var endIndex = imgFilePath.lastIndexOf(".");
        if(endIndex==-1){
            tag = false;
        }
        /*截取图片类型*/
        var imgType = imgFilePath.substring(endIndex+1);
        /*全部转为大写*/
        imgType = imgType.toUpperCase();
      /*  if("GIF"!=imgType && "PNG"!=imgType && "JPG"!=imgType && "BMP"!=imgType){
            tag = false;
        }*/
        if("JPG"!=imgType){
            tag = false;
        }
        if(!tag){
            //alert("上传图片的文件类型必须为: *.gif,*.jpg,*.png,*.bmp,请重新选择!");
            alert("上传图片的文件类型必须为: *.jpg 请重新选择!");
            return;
        }
        var options = {
            url : 'uploadImage.do?method=uploadHeadPhoto&tempid='+new Date()+Math.random(),
            type: 'post',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            dataType: 'text',
            success: cutImgCallback,
        };
        //$.fn.jqLoading();//打开等待层
        $("#ImgForm").ajaxSubmit(options);
    }/*上传头像回调*/
    function cutImgCallback(data){
        //$.fn.jqLoading("destroy");//关闭等待层
        if("1"==data){
            var imgUrl = document.getElementById("file").files[0];
            var src = window.URL.createObjectURL(imgUrl);
            $("#userimg").attr("src",src);
            $(".jcrop-holder img").attr("src",src);
            //头像剪切函数
            cutImg(src);
        }else{
            alert("网络连接出现问题,稍后请重试！");
        }
    }

    /*剪切头像,Jcrop插件，官网有教程*/
    function cutImg(src){
        $("#userimg").Jcrop({
            onChange : showPreview,
            onSelect : showPreview,
            allowSelect: true,
            trackDocument: true,
            aspectRatio : xsize / ysize,
            onSelect : function () {
                $(".jcrop-vline.right").remove();
            }
        }, function() {
            var bounds = this.getBounds();
            boundx = bounds[0];
            boundy = bounds[1];
            jcrop_api = this;
            $preview.appendTo(jcrop_api.ui.holder);
            $("#preview").attr("src",src);//在插件加载完成后，重新给预览区赋值解决二次进入预览区因缓存问题图片还是上次的。之前预览区第二次选择图片就是上次的图片，找了好久好久，最后把它重新赋值就好了，我觉得是缓存问题

        });
    }/*选择框改变是执行此函数*/
    function showPreview(c) {
        if (parseInt(c.w) > 0) {
            var rx = xsize / c.w;
            var ry = ysize / c.h;
            $("#x").val(Math.round(rx * c.x));
            $("#y").val(Math.round(ry * c.y));
            $("#w").val(Math.round(rx * boundx));
            $("#h").val(Math.round(ry * boundy));
            $pimg.css({
                width : Math.round(rx * boundx) + 'px',
                height : Math.round(ry * boundy) + 'px',
                marginLeft : '-' + Math.round(rx * c.x) + 'px',
                marginTop : '-' + Math.round(ry * c.y) + 'px'
            });
        }
    }

    /*获取图片全路径*/
    function getImgFullPath(obj){
        if(obj){
            //ie
            if(window.navigator.userAgent.indexOf("MSIE")>=1){
                obj.select();
                return document.selection.createRange().text();
            }else if(window.navigator.userAgent.indexOf("Firefox")>=1){
                //firefox
                if(obj.files){
                    return obj.files.item(0).getAsDataURL();
                }
                return obj.value;
            }
            return obj.value;
        }
    }

    /*保存头像按钮触发*/
    function saveHeadPhoto(){
        var fileImage = $("#file").val();
        if(""==fileImage){
            alert("请选择上传图片！");
            return;
        }
        var w = $("#w").val();
        var h = $("#h").val();
        if(w==0||h==0){
            alert("请选择所要裁剪的头像区域！");
            return;
        }
        var url = "uploadImage.do?method=uploadImageCut";
        var type = "text";
        var data = {
            "x": $("#x").val(),
            "y": $("#y").val(),
            "w1": xsize,//预览区宽度
            "h1": ysize,//预览区高度
            "w": $("#w").val(),
            "h": $("#h").val(),
        };
        //$.fn.jqLoading({text:'正在保存,请稍后...'});//打开等待层
        $.post(url,data,ctuImageCallback,type);
    }
    /*保存头像回调函数*/
    function ctuImageCallback(data){
        //var jsonData = eval("("+data+")");//最简单一种字符串json转为json对象
        var jsonData = JSON.parse(data);//第二种字符串json转为json对象
        //$.fn.jqLoading("destroy");//关闭等待层
        if("1"==jsonData.flag){
            destroyImageCut();//请求成功即销毁头像截取及预览以便下次选择头像初始化为未选择
            //$("#defauPhoto").attr("title","userImage");//头像上传成功改变title标识确定用户是否上传过头像
            var src = "<%=basePath%>"+jsonData.url;//保存后立即回显图片的路径
            $("#defauPhoto").attr("src",src);
        }else{
            alert("保存失败!"+jsonData.desc);
        }
    }

    /*点击X触发*/
    $("#close").click(function(){
        destroyImageCut();//销毁头像截取及预览以便下次选择头像初始化为未选择
    });
    function destroyImageCut(){
        $("#file").val("");//清除选择的图片文件
        if(jcrop_api){
            jcrop_api.destroy();//销毁
        }
        //重新写入img标签，防止第二次进入选择图片时，Jcrop插件把选择的图片宽度赋给img宽度
        $("#USERIMAGE").html("<img id='userimg' style='width: 200px; height: 200px;'/>");
        $("#light").css("display","none");
        $("#fade").css("display","none");
    }

    function showImg(data){
        var jsonData = JSON.parse(data);//第二种字符串json转为json对象
        //$.fn.jqLoading("destroy");//关闭等待层
        if("1"==jsonData.flag){
            var src = "<%=basePath%>"+jsonData.url;//保存后立即回显图片的路径
            $("#defauPhoto").attr("src",src);
        }else{
            $("#defauPhoto").attr("src","<%=basePath%>img/defaultPhoto.jpg");
        }
    }


     //初始化函数，如果请求不到图像则使用默认的图像
   /* $(document).ready(function(){
        //$("#defauPhoto")

        var url = "uploadImage.do?method=getImage";
        var type = "text";
        var data = {
            "custId": "11111111"
        };
        //$.fn.jqLoading({text:'正在保存,请稍后...'});//打开等待层
        $.post(url,data,showImg,type);
    });*/
</script>
</body>
</html>
