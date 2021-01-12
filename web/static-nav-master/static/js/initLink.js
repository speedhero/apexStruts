
function CreateTable(dataArry) {
    var tableHtml='';
    for(i=0;i<dataArry.length;i++){
        var list= dataArry[i];
        var linkCatalog=list["title"];
        var infos=list["info"];
        tableHtml=tableHtml+'<div class="jj-list">';
        tableHtml=tableHtml+'<div class="jj-list-tit">'+linkCatalog+'</div>';
        tableHtml=tableHtml+' <ul class="jj-list-con">';
        for(j=0;j<infos.length;j++){
            if(infos[j].hasOwnProperty('FWELCOMEURL') ){
                tableHtml=tableHtml+' <li><a href="#"  class="jj-list-link"  onclick="linkUrl(\''+infos[j]['FCODE']+'\',\''+infos[j]['FWELCOMEURL']+'\',0,0,'+infos[j]['FPUB_SYSTEM']+')">'+infos[j]['FAPPNAME'] +'</a></li>';
            }else{
                tableHtml=tableHtml+' <li><a href="'+infos[j]['FURLLINK']+'"  class="jj-list-link" target="_blank">'+infos[j]['FAPPNAME'] +'</a></li>';
            }

        }
        tableHtml=tableHtml+'</ul>';
        tableHtml=tableHtml+'</div>';
    }
    $("#content").html(tableHtml);
}


$(document).ready(function(){
    $.ajax({
        async:false,//同步，异步
        url:url, //请求的服务端地址
        /*   data:{
               method:"getLinkAddress",
           },*/
        type:"post",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        success:function(data){
            //成功之后的处理，返回的数据就是 data
            console.log(data);
            var strify=eval(data)
            CreateTable(strify)
        },
        error:function(){
            alert('error'); //错误的处理
        }
    });


})