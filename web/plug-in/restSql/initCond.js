
var combingStr='and';
var num = 0;
var index = 0;
var columnData=[];
var delList = [];
function init(cloumArr) {
    layui.use(['table','upload', 'form','laydate'], function(){
        table = layui.table;
        columnData=cloumArr;
        //条件区域事件
        queryCondintionArea(layui);
    });
}


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
        for ( x=0;x<formuaData.length;x++) {
            htmls += '<option value = "' + formuaData[x].val + '">' + formuaData[x].title +'</option>'
        }
        $("#fnum" + num + " #type").html(htmls);

        var htmls = '<option value="">请选择字段名</option>';
        for ( x=0;x<columnData.length;x++) {
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
            ,cols:customField==0? filed:customField
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