package com.template.selenium.model;

import java.io.Serializable;

public class EnvironmentConfig implements Serializable {

    private String APP_HOST;

    public void setAPP_HOST(String APP_HOST) {
        this.APP_HOST = APP_HOST;
    }

    public String getAPP_HOST() {
        return APP_HOST;
    }


}
