package com.template.selenium.steps;

import com.template.selenium.pages.FrontPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FrontSteps {

    private final FrontPage frontPage = new FrontPage();


    @And("espero {int} segundos")
    public void esperoSegundos(int segundos) throws InterruptedException {
        frontPage.iWaitSeconds(segundos);
    }


    @Given("estoy en el entorno de test")
    public void estoyEnElEntornoDeTest() {
        frontPage.iGoToWebPath("/");
        frontPage.waitForVisibilityOf(frontPage.homePageHeader);
    }

    @And("voy a la modal de identificación")
    public void voyALaModalDeIdentificacion() throws InterruptedException {
        frontPage.iClickLink("Sign in");
        frontPage.waitForVisibilityOf(frontPage.homePageHeader);

    }

    @When("me identifico con el usuario {string} y contraseña {string}")
    public void meIdentificoConElUsuarioYContrasena(String user, String password) throws InterruptedException {
        frontPage.sendTxt(frontPage.loginEmailField, user);
        frontPage.sendTxt(frontPage.loginPasswordField, password);
        frontPage.clickOnBtn(frontPage.loginSubmitBtn);
    }

    @Then("estoy en la página de mi perfil de usuario")
    public void estoyEnLaPaginaDeMiPerfilDeUsuario() {
        frontPage.waitForVisibilityOf(frontPage.userProfilePage);
    }

    @Then("veo el mensaje {string} en el elemento {string}")
    public void veoElMensajeEnElElemento(String message, String element) {
        frontPage.expectedMessageFromElement(element,message);
    }
}
