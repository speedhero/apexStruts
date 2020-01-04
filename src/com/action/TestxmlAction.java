package com.action;

import com.form.TestForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestxmlAction  extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {
        TestForm testForm=(TestForm)form;//TODOAuto-generatedmethodstub
        response.setContentType("text/xml;charset=utf-8");//传输xml时要用html
        response.setCharacterEncoding("utf-8");
        System.out.println(testForm.getTestValue());
        PrintWriter pw=response.getWriter();
            //1.传递单个参数,注意应将text/xml改为text/html
            /*inti=9;
            pw.print(i);
            pw.flush();*/
            //2.生成xml文件返回给html页面,此时list里面为单个String
            /*StringBuilderxml=newStringBuilder();
            List<String>list=newArrayList<String>();
            list.add("aaa");
            list.add("bbb");
            list.add("ccc");
            xml.append("<items>");
            for(Objecto:list){
            xml.append("<item>").append(o).append("</item>");
            }
            xml.append("</items>");
            System.out.println(xml);
            pw.print(xml.toString());
            pw.flush();*/
            //3.生成xml文件返回给html页面,此时list里面为对象类型
            /*response.setContentType("text/xml;charset=utf-8");
            StringBuilderxml=newStringBuilder();
            List<User>list=newArrayList<User>();
            Useruser1=newUser();
            Useruser2=newUser();
            Useruser3=newUser();
            user1.setUsername("username1");
            user1.setPassword(1);
            user2.setUsername("username2");
            user2.setPassword(2);
            user3.setUsername("username3");
            user3.setPassword(3);
            list.add(user1);
            list.add(user2);
            list.add(user3);
            xml.append("<items>");
            for(inti=0;i<list.size();i++){
            xml.append("<itemslist>");
            xml.append("<username>").append(list.get(i).getUsername()).append("</username>");
            xml.append("<password>").append(list.get(i).getPassword()).append("</password>");
            xml.append("</itemslist>");
            }
            xml.append("</items>");
            System.out.println(xml);
            pw.print(xml.toString());*/
        return null;
    }
}
