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
            url: 'importExcel'+'&tableCode=', //改成您自己的上传接口
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
                uploadExcel(fileArr); // 如果只需要最新选择的文件，可以这样写： uploadExcel([files.pop()])

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

//var filetFields={'ID':'机构号','ORGNAME':'机构名称','BASE':'逾期60天以上贷款考核基数'};
var filetFields={'机构号':'ID','机构名称':'ORGNAME','逾期60天以上贷款考核基数':'BASE'};
function uploadExcel(files) {
   var filed=['id','orgname','base']
    try {
        var layer = layui.layer;
        var excel = layui.excel;
        excel
        excel.importExcel(files, {}, function (data) {
            var arr = new Array();
            if(data[0].hasOwnProperty('sheet1')){
                var headCloums=[];
                if( data[0].sheet1.length>0){
                    var headMSet=data[0].sheet1[0];
                    var fieldHead={};
                    for(var key in headMSet){
                        headCloums.push(filetFields[headMSet[key]]);
                        //fieldHead[headMSet[key]]=key;
                        fieldHead[key]=filetFields[headMSet[key]];
                    }
                }
                for(i = 1; i < data[0].sheet1.length; i++){
                    var tt = {};
                    for (var key in headMSet)
                    {
                        tt[fieldHead[key]]=data[0].sheet1[i][key];
                    }

                    arr.push(tt);
                }
            }


            console.log(arr);
        });
    } catch (e) {
        layer.alert(e.message);
    }
}

function Generate(headMSet) {

   var fileds=[];
   for(var key  in headMSet){
       fileds.push(key,headMSet[key])
   }

}