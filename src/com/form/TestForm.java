package com.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class TestForm extends ActionForm {
    private String testValue=null;

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors actionErrors=new ActionErrors();
        if((testValue==null)||(testValue.length()<1)){
            actionErrors.add("testValue",new ActionMessage(""));
        }
        return super.validate(mapping, request);
    }
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.testValue=null;
    }
}
