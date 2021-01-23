

var colNames=[];
var filetFields={};
var fieldCols=[];
var columnData=[];
var combingStr='and';
var tableInfo={};//主表信息
var num = 0;
var index = 0;
// 删除查询条件
var delList = [];
var tableCode="";
var tableName="";
var filedjsonstring="";

function CreateTalbeCols(dataArry,isColNumber,isCheckbox,operate) {
    var cols=[];
    var col=[];
    //col.push({field:"ID", title:"ID",width:60});
   // colNames.push("ID");
    for(i=0;i<dataArry.length;i++){
        var list= dataArry[i];
        var ffieldcode=list["FFIELDCODE"];
        var ffieldname =list['FFIELDNAME'];
        var ffieldtype =list['FFIELDTYPE'];//1|文本类型;2|数值类型;3|日期类型;4|大数据类型;
        var totalRow=list['FTOTALROW']==1?true:false;
        var sort=list['FSORT']==1?true:false;
        var funresize=list['FUNRESIZE']==1?true:false;
        var lay_checked =list['FLAY_CHECKED']==1?true:false;
        var fclumtype =list['FCLUMTYPE'];
        var fisedit =list['FISEDIT']==1?'text':null;
        var fixed=list['FISFIXED']==0?'':'left';
        var ishide=list['FISHIDE']==1?true:false;
        var falign=list['FALIGN']==2?'center':(list['FALIGN']==1?'left':'right');
        switch(fclumtype){
            case 1:
                fclumtype='normal';
                break;
            case 2:
                fclumtype='checkbox';
                break;
            case 3:
                fclumtype='radio';
                break;
            case 4:
                fclumtype='numbers';
                break;
            case 5:
                fclumtype='space';
                break;
        };
        var column={field:ffieldcode.toUpperCase(), title:ffieldname, width:list['FWIDTH'],style:list['FSTYLE'] ,type:fclumtype,fixed:fixed,edit:fisedit,
                    templet: list['FTOOLBAR'], sort: sort,totalRow: totalRow,unresize:funresize,LAY_CHECKED:lay_checked,hide:ishide};
        var columnSrc={field:ffieldcode.toUpperCase(), title:ffieldname,ffieldtype:ffieldtype,align:falign };
        col.push(column);
        if(!ishide){//设置用于导入导出的字段,隐藏的属性不支持导入导出
            fieldCols.push(ffieldcode.toUpperCase());
            filetFields[ffieldcode.toUpperCase()]=ffieldname;
        }

        columnData.push(columnSrc);
        colNames.push(ffieldcode.toUpperCase());//所有字段
    }
    if(isCheckbox==1){
        col.unshift({type: 'checkbox'});
    }
    if(isColNumber==1){
        col.unshift({type:'numbers'});
    }
    if(operate!=""){
        col.push({fixed: 'right', title:'操作', toolbar: '#editBar', width:180});
    }
    cols.push(col);
    return cols;
}
var table=null;
var filed= [];
var customField=null;
function init(tablejsonstring,jsonstring,tableCodeStr,tableNameStr,filedjsonStr,customFields,operate){
    tableCode=tableCodeStr;
    filedjsonstring=filedjsonStr;
    tableName=tableNameStr;
    var dataArry=jsonstring;
     tableInfo=tablejsonstring;
    customField=customFields;

    var isColNumber=1;
    var isCheckbox=1;
    var operate="#editBar";
    filed= CreateTalbeCols(dataArry,isColNumber,isCheckbox);
/*    if(customField!=null&& customField!=""&& customField!="0"){
        filed=customField;
    }*/


    layui.use(['table','upload', 'form','laydate'], function(){
        table = layui.table;
        table.render({
             elem: '#assessmentTbl'
            ,totalRow: true
            ,url: url+'getDispalyData'+'&tableCode='+tableCode
            ,toolbar: '#AssmToolbar' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                ,layEvent: 'LAYTABLE_TIPS'
                ,icon: 'layui-icon-tips'
            }]
            ,where: {tableCode: tableCode, colums:colNames }
            ,title: tableName
            ,cols: customField==0? filed:customField
           // ,page: true,
            ,page : {
                limit : 10, // 初始 每页几条数据
                limits : [ 10, 20, 30,100,200,500,1000,3000 ]
                // 可以选择的 每页几条数据
            }
            //,skin: 'line' //表格风格
            ,even: true
            ,height:tableInfo.FHEIGHT
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

       bindArea(table);
        //条件区域事件
        queryCondintionArea(layui);
    });

    //ImportExcel(tableCode,filedjsonstring);
    ImportExcel();

}





