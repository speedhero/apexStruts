<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.apex.bank.link.LayoutUtil" %><%--
  Created by IntelliJ IDEA.
  User: 蒋涛jiangtao
  Date: 2021/1/14
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String data="";
    String path=request.getContextPath();
  //  String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String tablecode = request.getParameter("tablecode")==null?"KMH_LXSR_2021":request.getParameter("tablecode");//用request得到
    int openType = request.getParameter("openType")==null?1:Integer.parseInt(request.getParameter("openType"));//1新建2修改
    if(request.getParameter("data")!=null){
        data = URLDecoder.decode(request.getParameter("data"),"UTF-8");
    }
    System.out.println(data);
    String layoutform= LayoutUtil.getFormLayout(tablecode);
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../layui/css/layui.css"  media="all">
</head>
<body style="padding: 20px">
<form class="layui-form" action="">
    <%=layoutform%>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<%--<form class="layui-form" action="" lay-filter="example">
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn layui-btn-normal" id="LAY-component-form-setval">赋值</button>
            <button type="button" class="layui-btn layui-btn-normal" id="LAY-component-form-getval">取值</button>
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
        </div>
    </div>
</form>--%>
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script src="../layui/layui.js" charset="utf-8"></script>
<script src="../layui_exts/excel.js" charset="utf-8"></script>
<script src="customfun.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#date'
        });
        laydate.render({
            elem: '#date1'
        });

        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');

        //自定义验证规则
        form.verify({
            title: function(value){
                if(value.length < 5){
                    return '标题至少得5个字符啊';
                }
            }
            ,pass: [
                /^[\S]{6,12}$/
                ,'密码必须6到12位，且不能出现空格'
            ]
            ,content: function(value){
                layedit.sync(editIndex);
            }
        });

        //监听指定开关
        form.on('switch(switchTest)', function(data){
            layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
                offset: '6px'
            });
            layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
        });

        //监听提交
        form.on('submit(demo1)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });

        //表单赋值
        layui.$('#LAY-component-form-setval').on('click', function(){
            form.val('example', {
                "username": "贤心" // "name": "value"
                ,"password": "123456"
                ,"interest": 1
                ,"like[write]": true //复选框选中状态
                ,"close": true //开关状态
                ,"sex": "女"
                ,"desc": "我爱 layui"
            });
        });

        //表单取值
        layui.$('#LAY-component-form-getval').on('click', function(){
            var data = form.val('example');
            alert(JSON.stringify(data));
        });

    });
</script>

</body>
</html>
