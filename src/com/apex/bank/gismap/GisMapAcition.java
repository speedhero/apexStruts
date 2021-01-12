package com.apex.bank.gismap;

import com.alibaba.fastjson.JSON;
import com.bean.PersonBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GisMapAcition  extends Action {
    private ActionMapping mapping;
    private ActionForm form;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.mapping = mapping;
        this.form = new GisForm();
        this.request = request;
        this.response = response;
        response.setContentType("text/json;charset=utf-8");//注意设置为json，如果为xml，则设为xml
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
        //String string = new Gson().toJson(list);
        //JsonArray jsonArray = new JsonParser().parse(string).getAsJsonArray();
        String jsonArray= JSON.toJSONString(list);
        try{
            PrintWriter out=response.getWriter();
            System.out.println(jsonArray);
            out.print(jsonArray);
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;

    }
}
