
function queryCondintionArea(layui) {

    $(".CondStyle>span").click(function () {
        if($(".CondStyle>span").text()=='且'){
            $(".CondStyle>span").text('或');
            combingStr='or';
        }else {
            $(".CondStyle>span").text('且');
            combingStr='and';
        }
    });
    var form = layui.form;

    var formuaData=[{val:'<',title:'小于'},{val:'>',title:'大于'},{val:'=',title:'等于'},{val:'!=',title:'≠'},
        {val:'≤',title:'≤'},{val:'≥',title:'≥'},{val:'like',title:'模糊查询'},{val:'between',title:'区间'}];

    $("#add").click(function () {
        $("#qrycondContainer").show();
        num++;
        var htmls = '<form id="fnum' + num + '" class="layui-form" lay-filter="fnum' + num + '"><br /> ' +
            '<div class="layui-input-inline" style="width:20px">' +
            '<input type="checkbox" name="ifDel' + num + '" value="fnum' + num + '" lay-skin="primary"></div> ' +
            '<input type="hidden" id="columnedittype"  class="layui-input" value="1" />' +
            '<div class="layui-input-inline"> <select id="columns' + num + '" name="field" lay-filter="itpow'+num+'"></select></div>' +

            '<div class="layui-input-inline" style="margin-left: 20px;"><select id="type" name="type"></select></div>  ' +

            ' <div class="layui-input-inline" style="margin-left: 20px;"> ' +
            '<input type="text" name="value" id="value" lay-verify="date" placeholder="输入匹配值"   class="layui-input">' +
            '</div>' +
            ' </form>';
        $("#searchForm").append(htmls);

        var htmls = '<option value="">请选择查询方式</option>';
        for (var x in formuaData) {
            htmls += '<option value = "' + formuaData[x].val + '">' + formuaData[x].title +'</option>'
        }
        $("#fnum" + num + " #type").html(htmls);

        var htmls = '<option value="">请选择字段名</option>';
        for (var x in columnData) {
            htmls += '<option value = "' + columnData[x].field +':'+columnData[x].ffieldtype + '">' + columnData[x].title + '</option>'
        }
        $("#fnum" + num + " #columns"+ num).html(htmls);
        form.on('select(itpow'+num+')', function (data) {
            console.log(data.elem.id);
            // data.value
            var elementName=data.elem.id;
            var curNum=elementName.substr(7);
            var editType=  data.value.split(':')[1];
            if(editType=="3"){
                // $("#fnum" + num + " #value")
                layui.use('laydate', function(){
                    var laydate = layui.laydate;

                    //执行一个laydate实例
                    laydate.render({
                        elem: "#fnum" + curNum + " #value" //指定元素
                    });
                });
            }else if(editType=="2"){
                $("#fnum" + curNum + " #value").replaceWith('<input type="text" name="value" id="value" lay-verify="title" placeholder="输入匹配值"   class="layui-input">' );

            }else{
                //$("#fnum" + num + " #value").empty();
                // $("#fnum" + num + " #value").append('<input type="text" id="key"/>');
                $("#fnum" + curNum + " #value").replaceWith('<input type="text" name="value" id="value" lay-verify="title" placeholder="输入匹配值"   class="layui-input">' );
            }
            form.render();
        });
        /*     //给每个字段选择绑定绑定事件
             $("#fnum" + num + " #columns").on('change',
                 function () {

                 }
             );*/
        // 重新渲染一下
        $(".CondStyle").height($("#searchForm").outerHeight(true));
        form.render();
    });



    $("#del").click(function () {
        $('input[type="checkbox"]:checkbox:checked').each(function (index, value) {
            var id = $(this).val();
            $("#" + id).remove();
            delList[index++] = id;
        });
        $(".CondStyle").height($("#searchForm").outerHeight(true));
    })

    //监听搜索按钮
    $("#search").click(function () {
        var list = [];
        console.log(delList);
        for (var i = 1, j = 0; i <= num; i++) {
            if ($.inArray('fnum' + i, delList) == -1) {
                var data = form.val('fnum' + i);
                list[j++] = data;
            }
        }
        console.log(list);
        table.render({
            elem: '#assessmentTbl'
            /*  ,url:'../json/demo1.json'*/
            ,url: url+'getDispalyData'+'&tableCode='+tableCode
            ,toolbar: '#AssmToolbar' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                ,layEvent: 'LAYTABLE_TIPS'
                ,icon: 'layui-icon-tips'
            }]
            ,where: {
                tableCode: tableCode,
                colums:colNames ,
                "list": JSON.stringify(list),
                "combingStr":combingStr
            }
            ,title: tableName
            ,cols:filed
            ,page: true
            ,even:true
            ,parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
            ,id:'assessmentTbl'
        });

    });
}

//导入方法
function ImportExcel(){//tableCode,filedjsonstring

    layui.use('upload',function () {
        var $ = layui.jquery
            ,upload = layui.upload;
        var layer = layui.layer;
        var uploadInst = upload.render({
            elem: '#importExcel',
            /*method: 'POST',*/
            /* url: url,*/
            url: url+'importExcel'+'&tableCode='+tableCode, //改成您自己的上传接口
            accept: 'file', //普通文件
            exts: 'xls|excel|xlsx', //导入表格
            auto: false,  //选择文件后不自动上传
            before: function (obj) {
                layer.load(); //上传loading
            },
            choose: function (obj) {// 选择文件回调
                uploadInst.config.elem.next()[0].value = '';//处理只能chose一次的问题
                var files = obj.pushFile();
                var fileArr = Object.values(files);// 注意这里的数据需要是数组，所以需要转换一下
                //console.debug(fileArr)
                // 用完就清理掉，避免多次选中相同文件时出现问题
                for (var index in files) {
                    if (files.hasOwnProperty(index)) {
                        delete files[index];
                    }
                }
                uploadExcel(fileArr,tableCode,filedjsonstring); // 如果只需要最新选择的文件，可以这样写： uploadExcel([files.pop()])

            },
            done: function(res){
                layer.msg('上传成功');
                console.log(res);
            },
            error : function(){
                setTimeout(function () {
                    layer.msg("上传失败！", {
                        icon : 1
                    });
                    //关闭所有弹出层
                    layer.closeAll(); //疯狂模式，关闭所有层
                },1000);
            }
        });
    });

}


function uploadExcel(files,tableCode,filed) {
    try {
        var layer = layui.layer;
        var excel = layui.excel;
        excel.importExcel(files, {
            range:1,
            // 读取数据的同时梳理数据
            /*       fields: {
                       'id' : 'A',
                       'username' : 'B',
                       'email' : 'C',
                       'sex' : 'D',
                       'city' : 'E'
                   }*/
            fields:filed
        }, function (data) {
            var arr = new Array();
            for(i = 0; i < data[0].Sheet1.length; i++){
                var tt = {
                    /*   id : data[0].Sheet1[i].id,
                       username: data[0].Sheet1[i].username,
                       email: data[0].Sheet1[i].email,
                       sex: data[0].Sheet1[i].sex,
                       city: data[0].Sheet1[i].city*/
                };
                for (var key in filed)
                {
                   // console.log(key); 	//Type, Height
                    //console.log(filed[key]);	//Coding, 100
                    tt[key]=data[0].Sheet1[i][key];
                }

                arr.push(tt);
            }
            console.log(arr);
            $.ajax({
                async: false,
                url: url+'importExcel'+'&tableCode='+tableCode,
                type: 'post',
                dataType: "json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: {
                    isapend:isapend,
                    importCheckData:importCheckData,
                    colums:colNames,
                    data : JSON.stringify(arr)
                },
                success: function (data) {
                    if(data.code==0){
                        layer.msg(data.msg);
                        /*        setTimeout(function () {
                                    layer.closeAll(); //疯狂模式，关闭所有层
                                },1000);*/
                        //表格导入成功后，重载表格
                        table.reload('assessmentTbl',{
                            url : url+'getDispalyData'+'&tableCode='+tableCode,
                            page : {
                                limit : 10, // 初始 每页几条数据
                                limits : [ 10, 20, 30 ]
                                // 可以选择的 每页几条数据
                            },
                            where: {tableCode: tableCode, colums:colNames },
                            parseData: function(res){ //res 即为原始返回的数据
                                return {
                                    "code": 0, //解析接口状态
                                    "msg": res.msg, //解析提示文本
                                    "count": res.count, //解析数据长度
                                    "data": res.data //解析数据列表
                                };
                            }
                        }, 'data');
                        layer.closeAll();//关闭弹出框
                    }else{
                        //表格导入失败后，重载文件上传
                        layer.alert(data.error+"请重新上传",{icon : 2});
                    }
                },
                error: function (msg) {
                    layer.msg('请联系管理员!!!');
                }
            });
        });
    } catch (e) {
        layer.alert(e.message);
    }
}

var isapend=0;
var importCheckData=0;
function importFile() {
    var layer=layui.layer;
    var $=layui.$;
    layer.open({
        type: 1 //Page层类型
        ,skin: 'layui-layer-molv'
        ,area: ['50%', '50%']
        ,title: ['导入数据','font-size:18px']
        ,btn: ['确定', '取消']
        ,shadeClose: false
        ,shade: 0.4 //遮罩透明度
        ,maxmin: true //允许全屏最小化
        //,content:$("#window")  //弹窗路径
        ,content:$("#window")  //弹窗路径
        // , scrollbar: false, //屏蔽浏览器滚动条
        ,success:function(layero,index){
            /*     $('#bid').val(data.bid);
                 $('#bname').val(data.bname);
                 $('#price').val(data.price);*/
            isapend= $('input[name="isapend"]:checked').val();
            importCheckData=$('input[name="checkData"]:checked').val();
        },
        yes:function(index,layero){
            $("#importExcel").click();//模拟上传事件
        }
    });
}

function exportFile() {
    var isAll=1;
    var $=layui.$;
    var layer=layui.layer;
    layer.open({
        type: 1 //Page层类型
        ,skin: 'layui-layer-molv'
        ,area: ['30%', '30%']
        ,title: ['导入数据','font-size:18px']
        ,btn: ['确定', '取消']
        ,shadeClose: false
        ,shade: 0.4 //遮罩透明度
        ,maxmin: true //允许全屏最小化
        ,content:$("#exportWin")  //弹窗路径
        // , scrollbar: false, //屏蔽浏览器滚动条
        ,success:function(layero,index){
            isAll=$('input[name="isAll"]:checked').val();
        },
        yes:function(index,layero){
            ExportExcel(tableCode,tableName,filed,delList,num,isAll);
        }
    });
}
//导出选中数据
function exportCheckedFile(data) {
    var excel = layui.excel;
    data.unshift(filetFields)
    data = excel.filterExportData(data, fieldCols);
    excel.exportExcel(data, tableName+'.xlsx', 'xlsx');
}


function ExportExcel(tableCode,tableName,filed,delList,num,isAll) {

    var layer = layui.layer;
    var excel = layui.excel;
    var form = layui.form;
    var list = [];
    console.log(list);
    for (var i = 1, j = 0; i <= num; i++) {
        if ($.inArray('fnum' + i, delList) == -1) {
            var data = form.val('fnum' + i);
            list[j++] = data;
        }
    }
    console.log(list);
    //获取当前页
    var recodePage = $(".layui-laypage-skip .layui-input").val();
    //获取当前页条数
    var recodeLimit = $(".layui-laypage-limits").find("option:selected").val();
    $.ajax({
        async: false,
        url:  url+'getDispalyData'+'&tableCode='+tableCode,
        type: 'post',
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {
            tableCode: tableCode,
            colums:colNames,
            page:recodePage,
            limit:recodeLimit,
            isAll:isAll,
            "list": JSON.stringify(list),
            "combingStr":combingStr
        },
        success: function (res) {
            if(res.code==0) {
                var data=res.data;
                data.unshift(filetFields)
                data = excel.filterExportData(data, fieldCols);
                excel.exportExcel(data, tableName+'.xlsx', 'xlsx');
            }else {
                var data=[];
                data.unshift(filetFields)
                excel.exportExcel(data, tableName+'.xlsx', 'xlsx');
            }
            layer.closeAll();
        },
        error: function (msg) {
            layer.msg('请联系管理员!!!');
        }
    });
}




//编辑的方法
function  EidtUv(data,index,obj,layer,type) {
    var urladdr=url;
    var IDs=[];
    var datas=null;
    if(type==1){
        datas=JSON.stringify(data);
        urladdr=url+'addData'+'&tableCode='+tableCode;
    }else {
        urladdr=url+'updateData'+'&tableCode='+tableCode;
        var dataArr=[];
        dataArr.push(data);
        IDs.push(data['ID']);
        datas=JSON.stringify(dataArr);

    }


    $.ajax({
        url: urladdr,
        type: "POST",
        data:{
            data:datas,
            colums:colNames,
            IDs:IDs
        },
        dataType: "json",
        success: function(data){

            if(data.code==0){
                //关闭弹框
                layer.close(index);
                //同步更新表格和缓存对应的值
                table.reload('assessmentTbl',{
                    url : url+'getDispalyData'+'&tableCode='+tableCode,
                    page : {
                        limit : 10, // 初始 每页几条数据
                        limits : [ 10, 20, 30 ]
                        // 可以选择的 每页几条数据
                    },
                    where: {tableCode: tableCode, colums:colNames },
                    parseData: function(res){ //res 即为原始返回的数据
                        return {
                            "code": 0, //解析接口状态
                            "msg": res.msg, //解析提示文本
                            "count": res.count, //解析数据长度
                            "data": res.data //解析数据列表
                        };
                    }
                }, 'data');
                layer.msg("修改成功", {icon: 6});
            }else{
                layer.msg("修改失败", {icon: 5});
            }
        }

    });
}

//当前行数据,类型1新增，2修改 3 查看
function openForm(layer,data,type,obj) {
    var btn=['确定', '取消'];
    if(type==3){
        btn=[]
    }
    layer.open({
        type: 2 //Page层类型
        ,skin: 'layui-layer-molv'
        ,area: ['60%', '80%']
        ,title: [tableName,'font-size:18px']
        ,btn: btn
        ,shadeClose: false
        ,shade: 0.4 //遮罩透明度
        ,maxmin: true //允许全屏最小化
        //,content:$("#window")  //弹窗路径
        ,content:'promotForm.jsp?tablecode='+tableCode+"&openType="+type+"&data="+encodeURIComponent(JSON.stringify(data)) //弹窗路径
       // , scrollbar: false, //屏蔽浏览器滚动条
        ,success:function(layero,index){
        },
        yes:function(index,layero){
            //模板自带,固定写法,要注意子窗口中的提交按钮id对应submitID
            var iframeWindow = window['layui-layer-iframe'+ index]//得到iframe页的窗口对象，执行iframe页的方法：
                ,submitID = 'LAY-user-front-submit'
                ,submit = layero.find('iframe').contents().find('#'+ submitID);
            //监听提交
            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
                var field = data.field; //获取提交的字段
                EidtUv(field,index,obj,layer,type);
               // layer.close(index); //关闭弹层
                return false;//return false，这个就可以不再刷新
            });

            submit.trigger('click');
        }
    });
}


function del(layer,data,obj) {
    layer.confirm('真的删除行么', function(index){
        console.log(data);
        $.ajax({
            url: url+'deletData'+'&tableCode='+tableCode,
            type: "POST",
            data:{"uvid":data.ID},
            dataType: "json",
            success: function(data){

                if(data.code==0){
                    //删除这一行
                    obj.del();
                    //关闭弹框
                    layer.close(index);
                    layer.msg("删除成功", {icon: 6});
                }else{
                    layer.msg("删除失败", {icon: 5});
                }
            }

        });
    });
}

function delChecked(layer,checkData,obj) {
    layer.confirm('真的删除行么', function(index){
       var delList=[];
        for (var i = 0; i < checkData.length; i++) {
            delList.push(checkData[i].ID);
        }
        var strify = JSON.stringify(delList);
        console.log(data);
        $.ajax({
            url: url+'deletData'+'&tableCode='+tableCode,
            type: "POST",
            data:{
                datas:strify
            },
            dataType: "json",
            success: function(data){

                if(data.code==0){
                    //删除这一行
                    obj.del();
                    //关闭弹框
                    layer.close(index);
                    layer.msg("删除成功", {icon: 6});
                }else{
                    layer.msg("删除失败", {icon: 5});
                }
            }

        });
    });
}


