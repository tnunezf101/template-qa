package com.template.selenium.steps;


import com.template.selenium.utils.WebDriverUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private final WebDriverUtil webDriverUtil;

    public Hooks(WebDriverUtil webDriverUtil) {
        this.webDriverUtil = webDriverUtil;
    }


    @Before("not @REST")
    public void tearUp()  {
       webDriverUtil.setChromeDriver();


    }

    @After("not @REST")
    public void deleteCookiesAndDestroy(Scenario scenario) {
        webDriverUtil.tearDown(scenario);

    }

}