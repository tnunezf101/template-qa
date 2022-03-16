package com.template.selenium.utils;


import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.Duration;



public class WebDriverUtil {

    Logger log = LoggerFactory.getLogger(WebDriverUtil.class);
    private static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions action;
    public static JavascriptExecutor js;

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Actions getAction() {
        return action;
    }

    public JavascriptExecutor getJavascript() {
        return js;
    }


    @SuppressWarnings("unchecked")
    public void setChromeDriver()  {
        WebDriverManager.chromedriver()
                .setup();

        ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true);
        options.addArguments("--window-size=1920,1200", "--test-type", "--silent", "--no-sandbox", "--ignore-certificate-errors", "--start-maximized");
        options.addArguments("--expose-internals-for-testing"); // Enable javascript scripts
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--whitelisted-ips");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-proxy-server");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);


        log.info("Chrome instance is about to start");

        driver = new ChromeDriver(options);

        setSeleniumProperties();
    }


    @SuppressWarnings("unchecked")
    public void setSeleniumProperties() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        action = new Actions(driver);
        js = (JavascriptExecutor) driver;


    }


    public void tearDown(Scenario scenario) {
        if (driver != null && scenario.isFailed()) {
            log.error("Result: {}", scenario.getStatus());
            final byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        } else {
            log.info("Result: {}", scenario.getStatus());
        }
        driver.manage().deleteAllCookies();
        driver.quit();

    }


}