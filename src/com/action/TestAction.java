package com.action;

import com.bean.PersonBean;
import com.form.TestForm;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TestAction extends Action {
    private ActionMapping mapping;
    private ActionForm form;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)throws UnsupportedEncodingException {
        this.mapping = mapping;
        this.form = form;
        this.request = request;
        this.response = response;
        TestForm testForm=(TestForm)form;//TODOAuto-generatedmethodstub
        response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
        /*response.setContentType("text/xml;charset=utf-8");传输xml时要用xml
         *response.setCharacterEncoding("utf-8");
         **/
        System.out.println(testForm.getTestValue());
//1.struts1.2+JQuery+Json传递list参数，此时list的类型为String
/*List<String>list=newArrayList<String>();
list.add("string1");
list.add("string2");
list.add("string3");
JSONArrayjson=JSONArray.fromObject(list);
try{
PrintWriterout=response.getWriter();
System.out.println(json);
out.print(json);
out.flush();
}catch(IOExceptione){
//TODOAuto-generatedcatchblock
e.printStackTrace();
}*/
//2.struts1.2+JQuery+Json传递Map参数
/*Map<String,String>map=newHashMap<String,String>();
map.put("name1","string1");
map.put("name2","string2");
map.put("name3","string3");
JSONArrayjson=JSONArray.fromObject(map);
try{
PrintWriterout=response.getWriter();
System.out.println(json);
out.print(json);
out.flush();
}catch(IOExceptione){
//TODOAuto-generatedcatchblock
e.printStackTrace();
}*/
//3.传递单个String值
/*Stringsinglepara="{/"name/":'中国'}";//要注意格式
JSONObjectjson=JSONObject.fromObject(test);
try{
PrintWriterout=response.getWriter();
System.out.println(json);
out.print(json);
out.flush();
}catch(IOExceptione){
//TODOAuto-generatedcatchblock
e.printStackTrace();
}*/
//4.struts1.2+JQuery+Json传递User参数
/*Useruser1=newUser();
user1.setPassword(1);
Stringusername="你好";
user1.setUsername(username);
//user1.setUsername(newString(username.getBytes("utf-8"),"iso8859-1"));
JSONObjectjson=JSONObject.fromObject(user1);
try{
PrintWriterout=response.getWriter();
System.out.println(json);
out.print(json);
out.flush();
}catch(IOExceptione){
//TODOAuto-generatedcatchblock
e.printStackTrace();
}*/
//5.struts1.2+JQuery+Json传递list参数，此时list的类型为String
        List<PersonBean> list=new ArrayList<PersonBean>();
        PersonBean user1=new PersonBean();
        user1.setPassword(1);
        user1.setUserName("u1");
        PersonBean user2=new PersonBean();
        user2.setPassword(2);
        user2.setUserName("u2");
        PersonBean user3=new PersonBean();
        user3.setPassword(3);
        user3.setUserName("u3");
        list.add(user1);
        list.add(user2);
        list.add(user3);
        String string = new Gson().toJson(list);
        JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
        try{
            PrintWriter out=response.getWriter();
            System.out.println(jsonArray);
            out.print(jsonArray);
            out.flush();
        }catch(IOException e){
//TODOAuto-generatedcatchblock
            e.printStackTrace();
        }
        return null;
    }
}
