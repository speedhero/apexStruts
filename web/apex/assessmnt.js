

var colNames=[];
var filetFields={};
var fieldCols=[];
var columnData=[];
var combingStr='and';
function CreateTalbeCols(dataArry) {
    var cols=[];
    var col=[];
    for(i=0;i<dataArry.length;i++){
        var list= dataArry[i];
        var ffieldcode=list["FFIELDCODE"];
        var ffieldname =list['FFIELDNAME'];
        var ffieldtype =list['FFIELDTYPE'];//1|文本类型;2|数值类型;3|日期类型;4|大数据类型;
        var column={field:ffieldcode.toUpperCase(), title:ffieldname, width:120,   sort: true};
        var columnSrc={field:ffieldcode.toUpperCase(), title:ffieldname,ffieldtype:ffieldtype };
        col.push(column);
        fieldCols.push(ffieldcode.toUpperCase());
        filetFields[ffieldcode.toUpperCase()]=ffieldname;
        columnData.push(columnSrc);
        colNames.push(ffieldcode.toUpperCase());
    }
    cols.push(col);
    return cols;
}
var table=null;
function init(jsonstring,tableCode,tableName,filedjsonstring,customField){
    var dataArry=jsonstring;

    var filed= [];
    filed= CreateTalbeCols(dataArry);
    if(customField!=null&& customField!=""&& customField!="0"){
        filed=customField;
    }

    var index = 0;
    // 删除查询条件
    var delList = [];
    var num = 0;

    layui.use(['table','upload', 'form','laydate'], function(){
        table = layui.table;
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
            ,where: {tableCode: tableCode, colums:colNames }
            ,title: tableName
            ,cols:filed
            ,page: true
            ,parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": 0, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
            ,id:'assessmentTbl'
        });

        //头工具栏事件
        table.on('toolbar(assessmentTblFilter)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            var layer = layui.layer;
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;

                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
                case 'import':
                    $("#importExcel").click();//模拟上传事件
                    break;
                case 'export':
                    ExportExcel(tableCode,tableName,filedjsonstring,delList,num);

            };
        });

        //监听行工具事件
        table.on('tool(assessmentTblFilter)', function(obj){
            var data = obj.data;
            var layer = layui.layer;
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.prompt({
                    formType: 2
                    ,value: data.email
                }, function(value, index){
                    obj.update({
                        email: value
                    });
                    layer.close(index);
                });
            }
        });


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

            //执行重载
            table.reload('assessmentTbl', {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    "list": JSON.stringify(list),
                    "combingStr":combingStr
                },
                done: function (res, curr, count) {
                    this.where = {tableCode: tableCode, colums:colNames};
                }
            }, 'data');

        })


    });

    ImportExcel(tableCode,filedjsonstring);

}

//导入方法
function ImportExcel(tableCode,filedjsonstring){

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
                    console.log(key); 	//Type, Height
                    console.log(filed[key]);	//Coding, 100
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
                    colums:colNames,
                    data : JSON.stringify(arr)
                },
                success: function (data) {
                    if(data.code==1){
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

function ExportExcel(tableCode,tableName,filed,delList,num) {
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
            "list": JSON.stringify(list),
            "combingStr":combingStr
        },
        success: function (res) {
            if(res.code>0) {
                var data=res.data;
                data.unshift(filetFields)
                data = excel.filterExportData(data, fieldCols);
                excel.exportExcel(data, tableName+'.xlsx', 'xlsx');
            }
        },
        error: function (msg) {
            layer.msg('请联系管理员!!!');
        }
    });
}


