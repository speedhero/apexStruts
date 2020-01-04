package com.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class HelloForm extends ActionForm {

    private String userName=null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.userName=null;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors actionErrors=new ActionErrors();
        if((userName==null)||(userName.length()<1)){
            actionErrors.add("userName",new ActionMessage(""));
        }
        return super.validate(mapping, request);
    }
}
