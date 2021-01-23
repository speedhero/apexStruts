package com.apex.bank.link;


import com.apex.bank.sftp.DB2Handle;
import com.apex.form.DataAccess;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutUtil {

    public static String itemTpl="<div class=\"layui-form-item\"  ";
    public static String endTpl=">";
    public static String lblTpl="<label class=\"layui-form-label\">";
    public static String blockTpl="<div class=\"layui-input-block\">";
    public static String inlineTpl="<div class=\"layui-input-inline\">";
    public static String divTplEnd="</div>";
    public static String lblTplEnd="</label>";
    public static Map<String,String> flayverifyMap=new HashMap();
    public static JdbcTemplate  jdbcTemplate=null;

    public static void init() throws SQLException{
        if(jdbcTemplate==null){
            jdbcTemplate  = new JdbcTemplate(DB2Handle.getDataSource());
            // jdbcTemplate  = new JdbcTemplate(DataAccess.getDataSource());
        }

    }

    public static void initVerifyMap(){
        //1|Required;2|Phone;3|Email;4|Url;5|Number;6|Data;7|Identity;8|自定义;
        flayverifyMap.put("1","required");
        flayverifyMap.put("2","phone");
        flayverifyMap.put("3","email");
        flayverifyMap.put("4","url");
        flayverifyMap.put("5","number");
        flayverifyMap.put("6","date");
        flayverifyMap.put("7","identity");
        flayverifyMap.put("8","自定义");
    }
   // public static String formitemTplEnd="<div class=\"layui-form-item\">";


    public static Map<String,String> getLayoutGroup(String tableCode) throws SQLException {
        //select B.* from Exa_TableField A,Exa_TableFieldForm B where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='KMH_LXSR_2021';
        Map<String,String> layouts=new HashMap<>();
        init();
        List<Map<String,Object>> groups =jdbcTemplate.queryForList("select  A.FGROUPNAME " +
                "from Exa_TableField A,Exa_TableFieldForm B  where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='"+tableCode+"'" +
                "group by A.FGROUPNAME");
        if(groups!=null&groups.size()>0){
            String layout="";
            String groupName="";
            for(int i=0;i<groups.size();i++){
                Map<String,Object> map= groups.get(i);
                groupName=map.get("FGROUPNAME")==null?"":map.get("FGROUPNAME").toString();
                if(groupName!=null&&groupName!=""){
                    layout="   <fieldset class=\"layui-elem-field layui-field-title\" style=\"margin-top: 20px;\">\n" +
                            "        <legend>"+groupName+"</legend>\n" +
                            "    </fieldset>";
                    layouts.put(groupName,layout);
                }
            }

        }
        return layouts;
    }

    public static String getLayverify(String flayverify){
        if(flayverifyMap.size()==0){
            initVerifyMap();
        }
        String verify="";
        String[] items=flayverify.split(";");
        for (String item: items) {
            if("".equalsIgnoreCase(item)){
                continue;
            }
            verify=flayverifyMap.get(item)+"|";
        }
        if("".equalsIgnoreCase(verify)){
            return "";
        }
        verify= verify.substring(0,verify.lastIndexOf("|"));
        return verify;
    }

    /*    <option value=""></option>
      <option value="0">写作</option>
      <option value="1" selected="">阅读</option>*/
    public static String getSelectOption(String tableCode, String fieldCode) throws SQLException{
        String optionsStr="<option value=\"\"></option>";
        String querySql="select  B.FZDYXZX,A.FFIELDCODE " +
                "from Exa_TableField A,Exa_TableFieldForm B  where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='KMH_LXSR_2021' AND upper(A.FFIELDCODE)='"+fieldCode+"' ";
        init();
        List<Map<String,Object>> items =jdbcTemplate.queryForList(querySql);
        String value="";
        // String name="";
        if(items!=null&&items.size()>0){
            Map valueMap=items.get(0);
            value= valueMap.get("FZDYXZX")==null?"":valueMap.get("FZDYXZX").toString();
            // name= valueMap.get("FZDYXZX")==null?"":valueMap.get("FZDYXZX").toString();
            String[] options= value.split(";");
            if(options.length>0){
                for (String option: options) {
                    String[] val_Name=option.split("\\|");
                    if(val_Name.length>1){
                        optionsStr=optionsStr+" <option value=\""+val_Name[0]+"\">"+val_Name[1]+"</option>";
                    }
                }
            }
        }
        return optionsStr;
    }

 /*   <input type="checkbox" name="like[write]" title="写作">
    <input type="checkbox" name="like[read]" title="阅读" checked="">
    <input type="checkbox" name="like[game]" title="游戏">*/
    public static String getCheckBoxOption(String tableCode, String fieldCode,int fisEdit) throws SQLException{
        String checkBoxStr="";
        String querySql="select  B.FZDYXZX,A.FFIELDCODE " +
                "from Exa_TableField A,Exa_TableFieldForm B  where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='KMH_LXSR_2021' AND upper(A.FFIELDCODE)='"+fieldCode+"' ";
        init();
        List<Map<String,Object>> items =jdbcTemplate.queryForList(querySql);
        String value="";
        // String name="";
        if(items!=null&&items.size()>0){
            Map valueMap=items.get(0);
            value= valueMap.get("FZDYXZX")==null?"":valueMap.get("FZDYXZX").toString();
            // name= valueMap.get("FZDYXZX")==null?"":valueMap.get("FZDYXZX").toString();
            String[] options= value.split(";");
            if(options.length>0){
                for (String option: options) {
                    String[] val_Name=option.split("\\|");
                    if(val_Name.length>1){
                        checkBoxStr=checkBoxStr+"<input type=\"checkbox\" name=\""+fieldCode+"\" value=\""+val_Name[0]+"\""+" title=\""+val_Name[1]+"\"/>";
                    }
                }
            }
        }
        return checkBoxStr;
    }

    public static String getRadioOption(String tableCode, String fieldCode,int fisEdit) throws SQLException{
        String radiokBoxStr="";
        String querySql="select  B.FZDYXZX,A.FFIELDCODE " +
                "from Exa_TableField A,Exa_TableFieldForm B  where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='KMH_LXSR_2021' AND upper(A.FFIELDCODE)='"+fieldCode+"' ";
        init();
        List<Map<String,Object>> items =jdbcTemplate.queryForList(querySql);
        String value="";
        // String name="";
        if(items!=null&&items.size()>0){
            Map valueMap=items.get(0);
            value= valueMap.get("FZDYXZX")==null?"":valueMap.get("FZDYXZX").toString();
            // name= valueMap.get("FZDYXZX")==null?"":valueMap.get("FZDYXZX").toString();
            String[] options= value.split(";");
            if(options.length>0){
                for (String option: options) {
                    String[] val_Name=option.split("\\|");
                    if(val_Name.length>1){
                        radiokBoxStr=radiokBoxStr+"<input type=\"radio\" name=\""+fieldCode+"\" value=\""+val_Name[0]+"\""+" title=\""+val_Name[1]+"\"/>";
                    }
                }
            }
        }
        return radiokBoxStr;
    }


/*    String fishide=map.get("FISHIDE").toString();//0|否;1|是
    String fedittype=map.get("FEDITTYPE").toString();//1|不显示;2|输入框;3|下拉选择框;4|复选框;5|开关;6|单选框;7|日期;8|日期时间;9|穿梭框;10|长文本;
    String fevent=map.get("FEVENT").toString();//1|lay-filter;2|select;3|checkbox;4|switch;5|radio;6|submit;
    String flayverify=map.get("FLAYVERIFY").toString();//1|Required;2|Phone;3|Email;4|Url;5|Number;6|Data;7|Identity;8|自定义;
    String fplaceholder=map.get("FPLACEHOLDER").toString();*/
    public static String getEditCtrl( int fedittype, String flayverify,int fisEdit, String fplaceholder,String fieldCode,String tableCode) throws SQLException {
        String editCtrl="";
        String disabled="";

        String layverify=LayoutUtil.getLayverify(flayverify);
         // <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input layui-disabled" disabled="disabled" value="nihao">
        if(fisEdit==0){
            disabled="  disabled=\"disabled\"  ";
        }


        switch(fedittype){
            case 1 :
                editCtrl="<input type=\"text\"  name=\""+fieldCode+"\""+" lay-filter=\""+fieldCode+"\"" +" autocomplete=\"off\" "+" placeholder=\""+fplaceholder+"\" "+disabled
                       +"class=\"layui-input"+"  />";
                break; //可选
            case 2 :
                editCtrl="<input type=\"text\"  name=\""+fieldCode+"\""+" lay-filter=\""+fieldCode+"\""+" autocomplete=\"off\" "+" placeholder=\""+fplaceholder+"\" " +"lay-verify=\""+layverify+"\"  "+disabled
                        +"class=\"layui-input\"  />";
                break; //可选
            case 3 :
                editCtrl="<select lay-verify=\""+layverify+"\""+" lay-search=\"\"  name=\""+fieldCode+"\'"+"  lay-filter=\""+fieldCode+"\"  "+disabled+" >"+getSelectOption(tableCode,fieldCode)+"</select>";
                break; //可选
            case 4 :
                editCtrl=getCheckBoxOption(tableCode,fieldCode,fisEdit);
                break; //可选
            case 5 :
                editCtrl=" <input type=\"checkbox\"  name=\""+fieldCode+"\""+" lay-filter=\""+fieldCode+"\"  "+disabled+" lay-skin=\"switch\" lay-text=\"ON|OFF\">";
                break; //可选
            case 6 :
                editCtrl=getRadioOption( tableCode,  fieldCode,fisEdit);
                break; //可选
            case 7 :
                editCtrl="<input type=\"text\"  name=\""+fieldCode+"\" id=\""+fieldCode+"\" autocomplete=\"off\" class=\"layui-input laydateCls\""+disabled+" lay-filter=\""+fieldCode+"\" "+">";
                break; //可选
            case 8 :
                editCtrl="<input type=\"text\"  name=\""+fieldCode+"\" id=\""+fieldCode+"\" autocomplete=\"off\" class=\"layui-input laydatetimeCls\""+disabled+" lay-filter=\""+fieldCode+"\" "+">";
                break; //可选
            case 9 ://9|穿梭框
                editCtrl="<input type=\"text\"  name=\""+fieldCode+"_Val\""+" lay-filter=\""+fieldCode+"\""+" autocomplete=\"off\" "+" placeholder=\""+fplaceholder+"\" " +"lay-verify=\""+layverify+"\" "+disabled
                        +"class=\"layui-input  transferCls\"  />"
                         +"<input id=\""+fieldCode+"\" hidden=\"hidden\"  name=\""+fieldCode+"\"> ";

                break; //可选
            case 10 :
                editCtrl="<textarea name=\""+fieldCode+"\" placeholder=\"请输入内容\" class=\"layui-textarea\""+disabled+" lay-filter=\""+fieldCode+"\" ></textarea>";
                break; //可选
            default : //可选
                editCtrl="<input type=\"text\"  name=\""+fieldCode+" autocomplete=\"off\" "+" placeholder=\""+fplaceholder+"\" " +"lay-verify=\""+layverify+"\""+disabled+" lay-filter=\""+fieldCode+"\" "
                        +"class=\"layui-input"+"  />";
        }

        return editCtrl;
    }

/*      <div class="layui-form-item">
        <label class="layui-form-label">单行输入框</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input layui-disabled" disabled="disabled" value="nihao">
        </div>
    </div>*/
    public static String getFormShowCloums(String tableCode,String method) throws SQLException{
        String cloums="";
        String querySql="select  B.*,A.FFIELDNAME,A.FFIELDTYPE,A.FFIELDCODE " +
                " from Exa_TableField A,Exa_TableFieldForm B  where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='"+tableCode
                +"' and FMETHODCODE='"+tableCode+"."+method+"'";
        System.out.println("=====start"+querySql);
        init();
        System.out.println("=====end"+querySql);
        List<String> cloumList=new ArrayList<>();
        List<Map<String,Object>> items =jdbcTemplate.queryForList(querySql);
        if(items!=null&items.size()>0){
            for(int i=0;i<items.size();i++){
                Map<String,Object> map= items.get(i);
                int fishide=Integer.parseInt(map.get("FISHIDE")==null?"0":map.get("FISHIDE").toString());//0|否;1|是
                if(fishide==0){
                    String fieldCode=map.get("FFIELDCODE").toString().toUpperCase();//转大写统一
                    cloumList.add(fieldCode);
                }
            }
            Gson gson=new Gson();
            cloums= gson.toJson(cloumList);
        }
        return  cloums;
    }

    public static String getFormShowField(String tableCode,String method) throws SQLException{
        String cloums="";
        String querySql="select  B.*,A.FFIELDNAME,A.FFIELDTYPE,A.FFIELDCODE " +
                " from Exa_TableField A,Exa_TableFieldForm B  where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='"+tableCode
                +"' and FMETHODCODE='"+tableCode+"."+method+"'";
        init();
        Map<String,String> cloumMap=new HashMap();
        List<Map<String,Object>> items =jdbcTemplate.queryForList(querySql);
        if(items!=null&items.size()>0){
            for(int i=0;i<items.size();i++){
                Map<String,Object> map= items.get(i);
                int fishide=Integer.parseInt(map.get("FISHIDE")==null?"0":map.get("FISHIDE").toString());//0|否;1|是
                if(fishide==0){
                    String fieldCode=map.get("FFIELDCODE").toString().toUpperCase();//转大写统一
                    String fieldname=map.get("FFIELDNAME")==null?"":map.get("FFIELDNAME").toString();
                    cloumMap.put(fieldCode,fieldname);
                }
            }
            Gson gson=new Gson();
            cloums= gson.toJson(cloumMap);
        }
        return  cloums;
    }




    public static String getLayoutGroupItems(String tableCode,String method,String groupName) throws SQLException {
        //select B.* from Exa_TableField A,Exa_TableFieldForm B where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='KMH_LXSR_2021';
        String layouts="";
        jdbcTemplate.update("update Exa_TableField set  FGROUPNAME=null where FGROUPNAME=''");//清除groupName为空的
        String querySql="select  B.*,A.FFIELDNAME,A.FFIELDTYPE,A.FFIELDCODE " +
                " from Exa_TableField A,Exa_TableFieldForm B  where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='"+tableCode
                +"' and FMETHODCODE='"+tableCode+"."+method+"' and A.FGROUPNAME ";
        if(groupName==null||groupName==""){
           querySql=querySql+" IS NULL ORDER BY A.FSEQ";
        }else {
            querySql=querySql+"='"+groupName+"' ORDER BY A.FSEQ";
        }
        init();
        List<Map<String,Object>> items =jdbcTemplate.queryForList(querySql);
        if(items!=null&items.size()>0){
            for(int i=0;i<items.size();i++){
                String layout="";
                String itemName="";
                Map<String,Object> map= items.get(i);
                String fieldName=map.get("FFIELDNAME").toString();
                String fieldCode=map.get("FFIELDCODE").toString().toUpperCase();//转大写统一
                int feditstyle=Integer.parseInt(map.get("FEDITSTYLE").toString());//1|内联;2|块模式
               // String fieldName=map.get("FFIELDNAME").toString();

                String itemCss=feditstyle==1?inlineTpl:blockTpl;
                String editctrl="";
                int fishide=Integer.parseInt(map.get("FISHIDE")==null?"0":map.get("FISHIDE").toString());//0|否;1|是
                int fedittype=Integer.parseInt(map.get("FEDITTYPE")==null?"2":map.get("FEDITTYPE").toString());//1|不显示;2|输入框;3|下拉选择框;4|复选框;5|开关;6|单选框;7|日期;8|日期时间;9|穿梭框;10|长文本;
               // int fevent=Integer.parseInt(map.get("FEVENT").toString().toString());//1|lay-filter;2|select;3|checkbox;4|switch;5|radio;6|submit;
                String flayverify=map.get("FLAYVERIFY")==null?"":map.get("FLAYVERIFY").toString();//1|Required;2|Phone;3|Email;4|Url;5|Number;6|Data;7|Identity;8|自定义;
                String fplaceholder=map.get("FPLACEHOLDER")==null?"":map.get("FPLACEHOLDER").toString();
                int fisEdit=Integer.parseInt(map.get("FISEDIT")==null?"1":map.get("FISEDIT").toString());
               // editctrl=getEditCtrl(fishide,fedittype,fevent,flayverify,fplaceholder,value,fieldCode,tableCode);
                String isHide="";
                if(fishide==1){
                    isHide=" style=\"display:none\" ";
                }
                editctrl=getEditCtrl(fedittype,flayverify,fisEdit,fplaceholder,fieldCode,tableCode);

                layout=itemTpl+isHide+endTpl+lblTpl+fieldName+lblTplEnd+itemCss+editctrl+divTplEnd+divTplEnd;
                layouts=layouts+layout;
            }
        }
        return layouts;
    }


    public static  String getFormLayout(String tableCode,String method) throws SQLException{
        //select B.* from Exa_TableField A,Exa_TableFieldForm B where A.FTABLEFIELD=B.FTABLEFIELDCODE AND  A.FTABLE='KMH_LXSR_2021';
        String layoutForm="";
        layoutForm=layoutForm+getLayoutGroupItems(tableCode,method,null);
        Map<String,String> group=LayoutUtil.getLayoutGroup(tableCode);
        for(Map.Entry<String, String> entry : group.entrySet()){
            String groupName = entry.getKey();
            String groupLayout = entry.getValue();
            System.out.println(groupName+":"+groupLayout);
            layoutForm=layoutForm+groupLayout+getLayoutGroupItems(tableCode,method,groupName);
        }
        return layoutForm;
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(LayoutUtil.getLayoutGroup("KMH_LXSR_2021")); ;
        //System.out.println(LayoutUtil.getFormLayout("KMH_LXSR_2021","add"));
        //System.out.println(LayoutUtil.getFormShowCloums("KMH_LXSR_2021","import"));
        System.out.println(LayoutUtil.getFormShowField("KMH_LXSR_2021","import"));

    }
}
