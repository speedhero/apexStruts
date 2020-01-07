package com.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ImgForm extends ActionForm {

    private FormFile file;

    private String custId;

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }


    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
