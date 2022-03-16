package com.template.selenium.pages;

import com.template.selenium.model.EnvironmentConfig;
import com.template.selenium.utils.FileReaderUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.with;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class FrontPage extends BasePage {
    Logger log = LoggerFactory.getLogger(FrontPage.class);
    private static final EnvironmentConfig configuracion = FileReaderUtil.getInstance().getConfiguration();
    public static final String HOME_URL = "http://" + configuracion.getAPP_HOST();

    // =========================================================================
    //                                 LOCATORS
    // =========================================================================

    // HOME

    @FindBy(how = How.ID, using = "header")
    public WebElement homePageHeader;


    // LOGIN

    @FindBy(how = How.ID, using = "email")
    public WebElement loginEmailField;

    @FindBy(how = How.ID, using = "passwd")
    public WebElement loginPasswordField;

    @FindBy(how = How.CSS, using= "#SubmitLogin > span")
    public WebElement loginSubmitBtn;

    @FindBy(how = How.ID, using= "my-account")
    public WebElement userProfilePage;


    // =========================================================================
    //                                 METHODS
    // =========================================================================

    public void iGoToWebPath(String path) {
        driver.get(HOME_URL + path);
    }


    public void jsClickOnBtn(WebElement btn) throws InterruptedException {
        //waitForVisibilityOf(btn);
        js.executeScript("arguments[0].click();", btn);
        //wait.until(elementToBeClickable(btn)).click();
        //threadWait(600);
        Thread.sleep(1000);
    }

    public void clickOnBtn(WebElement btn) throws InterruptedException {
        wait.until(elementToBeClickable(btn)).click();
        threadWait(600);
    }

    public void clickOnLinkText(String link) throws InterruptedException {
        WebElement element = driver.findElement(By.linkText(link));
        jsClickOnBtn(element);
    }

    public void expectedMessageFromElement(String element, String expectedMessage) {
        WebElement message = driver.findElement(By.cssSelector(element));
        String msgAppeared = message.getText().replaceAll("\\r\\n|\\r|\\n", " ");
        iAwaitUntilAssertedEqual("expectedMessageFromElement", 200, 10, 250, expectedMessage, msgAppeared.trim());
    }

    public void actionOnBtn(WebElement btn) throws InterruptedException {
        scrollToTopOfPage();
        actions.moveToElement(btn).click().perform();
        threadWait(300);
    }

    public void scrollToTopOfPage() throws InterruptedException {
        js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
        threadWait(300);

    }

    public void selectTextFromVertical(String name, String value) {
        if (!value.isEmpty()) {
            new Select(driver.findElement(By.name(name))).selectByVisibleText(value);
        }
    }

    public void scrollToBottomOfPage() throws InterruptedException {
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        threadWait(300);

    }

    public void iWaitSeconds(int segundos) throws InterruptedException {
        Thread.sleep(segundos * 1000);
    }


    public void iAwaitUntilAssertedEqual(String alias, int atLeastMills, int atMostSecs, int pollMills, Object expectedMessage, Object msgAppeared) {
        with()//.conditionEvaluationListener(new ConditionEvaluationLogger())
                .await(alias)
                .atLeast(atLeastMills, MILLISECONDS)
                .atMost(atMostSecs, SECONDS)
                .pollInterval(pollMills, MILLISECONDS)
                .untilAsserted(() -> Assert.assertEquals("ERROR: Following objects are different", expectedMessage, msgAppeared));
    }

    public void iAwaitUntilAssertedNotEqual(String alias, int atLeastMills, int atMostSecs, int pollMills, Object expectedMessage, Object msgAppeared) {
        with()//.conditionEvaluationListener(new ConditionEvaluationLogger())
                .await(alias)
                .atLeast(atLeastMills, MILLISECONDS)
                .atMost(atMostSecs, SECONDS)
                .pollInterval(pollMills, MILLISECONDS)
                .untilAsserted(() -> Assert.assertNotEquals("ERROR: Following objects are different", expectedMessage, msgAppeared));
    }

    public void iAwaitUntilAssertedTrue(String alias, int atLeastMills, int atMostSecs, int pollMills, Boolean assestment) {
        with()//.conditionEvaluationListener(new ConditionEvaluationLogger())
                .await(alias)
                .atLeast(atLeastMills, MILLISECONDS)
                .atMost(atMostSecs, SECONDS)
                .pollInterval(pollMills, MILLISECONDS)
                .untilAsserted(() -> Assert.assertTrue("ERROR: The following statement is not true: ", assestment));
    }

    public void iAwaitUntilAssertedFalse(String alias, int atLeastMills, int atMostSecs, int pollMills, Boolean assestment) {
        with()//.conditionEvaluationListener(new ConditionEvaluationLogger())
                .await(alias)
                .atLeast(atLeastMills, MILLISECONDS)
                .atMost(atMostSecs, SECONDS)
                .pollInterval(pollMills, MILLISECONDS)
                .untilAsserted(() -> Assert.assertFalse("ERROR: The following statement is not false: ", assestment));
    }


    public void sendTxt(WebElement element, String txt) throws InterruptedException {
        jsClickOnBtn(element);
        //clickOnBtn(element);
        //wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        element.clear();
        element.sendKeys(txt);
        //threadWait(300);
    }

    public void sendFile(WebElement element, String path) throws InterruptedException {
        if (!path.endsWith("dataset")) {
            element.sendKeys(path);
            threadWait(300);
        }

    }

    public void threadWait(int milis) throws InterruptedException {
        Thread.sleep(milis);
    }

    public void waitForVisibilityOf(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView();", element);
        wait.until(visibilityOf(element));

        iAwaitUntilAssertedTrue("waitForVisibilityOf", 200, 50, 250, element.isDisplayed());

    }


    public void waitForInvisibilityOf(WebElement element) {
        try {
            js.executeScript("arguments[0].scrollIntoView();", element);
            wait.until(invisibilityOf(element));

            //with().pollInterval(200, MILLISECONDS).await("waiting for visibility of element").atMost(20, SECONDS).until(element::isDisplayed);
            iAwaitUntilAssertedTrue("waitForInvisibilityOf", 200, 50, 250, !element.isDisplayed());
        } catch (Exception ignored) {
        } finally {
            log.info("There's no loader running right now. I skip this step...");
        }

    }

    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView();", element);
        wait.until(visibilityOf(element));
        iAwaitUntilAssertedTrue("waitForVisibilityOf", 200, 50, 250, element.isDisplayed());

    }


    private void iCreateFile(String file) throws IOException {
        File filePath = new File(file);
        boolean result = filePath.createNewFile();
        if (!result) {
            log.info("The file is already created. No further actions needed: {}", filePath);
        }
    }

    private void iDeleteFile(String file) {
        File filePath = new File(file);
        boolean result = filePath.delete();
        if (!result) {
            log.info("The file is already deleted. No further actions needed: {}", filePath);
        }
    }

    public void iClickLink(String linkText) throws InterruptedException {
        WebElement link = driver.findElement(By.linkText(linkText));
        jsClickOnBtn(link);

    }

    public void iGoToCurrentDomain404Page() {
        String currentDomain = driver.getCurrentUrl();
        driver.get(currentDomain + "/any_404_page");
    }

    public void iAssertElementIsDisabled(WebElement element) {
        String enabled = element.getAttribute("disabled");
        Assert.assertEquals("true", enabled);
    }

}