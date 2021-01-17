function renderform(openType,data){
    layui.use(['form', 'layedit', 'laydate','transfer'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate
            ,transfer = layui.transfer
            ,$=layui.$;

        //日期
        $(".laydateCls").each(function(){
            laydate.render({
                elem: this, //指定元素  表示当前的元素
                type: 'date', //date日期  time时间  year年  month月份
                theme: '#009688',  //主题  颜色改变
                trigger: 'click'//呼出事件改成click
            });
        })
        //时间
        $(".laydatetimeCls").each(function(){
            laydate.render({
                elem: this, //指定元素  表示当前的元素
                type: 'datetime', //date日期  time时间  year年  month月份
                theme: '#009688',  //主题  颜色改变
                trigger: 'click'//呼出事件改成click
            });
        })
        //穿梭框
        var num=0;
        $(".transferCls").each(function(){

            $(this).click(function () {
                var elem=this;
                layer.open({
                    type: 1 //Page层类型
                    ,skin: 'layui-layer-molv'
                    ,area: ['50%', '70%'] //宽高
                    ,title: ['选择','font-size:18px']
                    ,btn: ['确定', '取消']
                    ,shadeClose: false
                    ,shade: 0.4 //遮罩透明度
                    ,maxmin: true //允许全屏最小化
                    ,content:$("#transferWin")  //弹窗路径
                    // , scrollbar: false, //屏蔽浏览器滚动条
                    ,success:function(layero,index){
                        var data=[
                            {"value": "1", "title": "李白", "disabled": "", "checked": ""}
                            ,{"value": "2", "title": "杜甫", "disabled": "", "checked": ""}
                            ,{"value": "3", "title": "贤心", "disabled": "", "checked": ""}
                        ];
                        num++
                        rerendTransfer(elem,data,num);
                    },
                    yes:function(index,layero){
                        var getData = transfer.getData('transferIns'+num);
                        console.log(getData);
                        getTransferValesStr(getData,elem)
                        layer.closeAll(); //疯狂模式，关闭所有层
                    }
                });
            });
        });

      if(openType!=1&&data!=""){
          form.val('example', data);
      }

        layui.$('#LAY-component-form-setval').on('click', function(){
            form.val('example',  {"ID":135,"FORG":"白埠","FTASK_KMH":320.9,"FTASK_1":103.36,"FTASK_2":212.13,"FTASK_3":320.9});
        });

        //监听提交
  /*      form.on('submit(layui-layer-iframe)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });*/

        //表单取值
        layui.$('#LAY-component-form-getval').on('click', function(){
            var data = form.val('example');
            alert(JSON.stringify(data));
        });

    });
}


function rerendTransfer(elem,data,num) {
    layui.use('transfer', function(){
        var transfer = layui.transfer;
        //渲染
        transfer.render({
            elem: "#transferForm"  //绑定元素
            ,data: data
            ,id: 'transferIns'+num //定义索引
        });

    });
}

function getTransferValesStr(data,elem) {
    var vals="";
    var tities="";
    if(data!=null&&data.length>0){
        for(i=0;i<data.length;i++){
            vals=vals+data[i]["value"]+";";
            tities=tities+data[i]["title"]+";";
        }
    }
    //$(elem).val(vals);
    $(elem).val(tities);
    $("#"+elem.name.substr(0,elem.name.lastIndexOf("_Val"))).val(vals);
}