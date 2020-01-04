package com.action;

import com.util.Constants;
import com.form.HelloForm;
import com.bean.PersonBean;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloAction  extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MessageResources messageResources=getResources(request);
        ActionMessages errors=new ActionMessages();
        String userName= (String)((HelloForm)form).getUserName();
        String badUserName="Monster";
        if(userName.equalsIgnoreCase(badUserName)){
            errors.add("username",new ActionMessage("hello.dont.talk.monstar",badUserName));
            saveErrors(request,errors);
            return new ActionForward(mapping.getInput());
        }
        PersonBean personBean=new PersonBean();
        personBean.setUserName(userName);
        personBean.savePersitStore();
        request.setAttribute(Constants.PERSON_KEY,personBean);
        request.removeAttribute(mapping.getAttribute());
        return  mapping.findForward("SayHello");
    }
}
