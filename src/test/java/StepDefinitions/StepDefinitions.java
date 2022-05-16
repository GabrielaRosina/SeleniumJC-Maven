package StepDefinitions;

import Functions.CreateDriver;
import Functions.SeleniumFunctions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StepDefinitions {
    WebDriver driver;

    SeleniumFunctions functions = new SeleniumFunctions();

    public static boolean actual = Boolean.parseBoolean(null);
    /******** Log Attribute ********/
    Logger log = Logger.getLogger(StepDefinitions.class);

    public StepDefinitions() {

        driver = Hooks.driver;
    }

    @Given("^I am in App main site")
    public void iAmInAppMainSite() throws IOException {
        String url = functions.readProperties("MainAppUrlBase");
        log.info("Navigate to: " + url);
        driver.get(url);
        functions.page_has_loaded();

    }


    @Given("^I go to site (.*)$")
    public void iGoToSiteHttpWwwGoogleComAr(String URL) {
        log.info("Navigate to: " + URL);
        driver.get(URL);
        functions.page_has_loaded();
        functions.WindowsHandle("Principal");


    }

    @Then("I close the windows")
    public void iCloseTheWindows() {
        driver.close();
    }

    @Then("I quit the aplication")
    public void iQuitTheAplication() {
        driver.quit();
    }

    @Then("^I load the DOM Information (.*)$")
    public void iLoadTheDOMInformationGaliciaJson(String json) throws Exception {
        SeleniumFunctions.FileName =  json;
        SeleniumFunctions.readJson();
        log.info("initialize file: " + json);
    }

    @And("^I do a click in the element (.*)$")
    public void iDoAClickInTheElementEmail(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        functions.waitForElementPresent(element);
        functions.waitForElementClickable(element);
        driver.findElement(SeleniumElement).click();
        log.info("Click on element by " + element);

    }

    @And("^I set (.*) with the text (.*)$")
    public void iSetEmailWithTheTextGnRosinaGmailCom(String element, String text) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).sendKeys(text);
        log.info("Send text "+ text + " to element " + element);
    }

    @Given("^I set (.*) value in Data Scenario$")
    public void iSetUserEmailValueInDataScenario(String parameter) throws IOException {
        functions.RetriveTestData(parameter);
    }

    @And("^Save text of (.*) as Scenario Content$")
    public void saveTextOfTituloAsScenarioContent(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        String ScenarioElementText = driver.findElement(SeleniumElement).getText();
        functions.SaveInScenario(element+ ".text", ScenarioElementText);
    }

    @And("^I set (.*) with key value (.*)$")
    public void iSetEmailWithKeyValueTituloText(String element, String key) throws Exception {
        functions.iSetElementWithKeyValue(element, key);
    }

    @And("^I set text (.*) in dropdown (.*)$")
    public void iSetFeberoInDropdownMesDeNacimiento(String option, String element) throws Exception {
        functions.selectOptionDropdownByText(element, option);

    }

    @And("^I set index (.*) in dropdown (.*)$")
    public void iSet3InDropdownMesDeNacimiento(int option, String element) throws Exception {
        functions.selectOptionDropdownByIndex(element, option);

    }

    @And("^I wait for element (.*) to be present$")
    public void iwaitForElementMesDeNacimientoToBePresent(String element) throws Exception {
        functions.waitForElementPresent(element);
    }

    @And("^I wait for element (.*) to be visible$")
    public void iWaitForElementPassToBeVisible(String element) throws Exception {
        functions.waitForElementVisible(element);
    }

    @And("^I check if (.*) error message is (.*)$")
    public void iCheckIfEmailErrorMessageIsFalse(String element, boolean state) throws Exception {

        boolean actual =  functions.isElementDisplayed(element);
        Assert.assertEquals("El estado es diferente al esperado",actual,state);
    }

    @And("^I switch to Frame: (.*)$")
    public void iSwitchToFrameFrame(String frame) throws Exception {
        functions.switchToFrame(frame);
    }

    @And("I switch to parent frame")
    public void iSwitchToParentFrame() {
        functions.switchToParentFrame();
    }

    @And("^set element (.*) with text (.*)$")
    public void setElementFrameInputWithTextEstoEsUnaPrueba(String element, String text) throws Exception {
        functions.iSetElementWithText(element, text);
    }

    @And("^I check the checkbox having (.*)$")
    public void iCheckTheCheckboxHavingMale(String element) throws Exception {
        functions.checkCheckbox(element);
    }

    @And("^I do a click with JS in the element (.*)$")
    public void iDoAClickWithJSInTheElementMiCuenta(String element) throws Exception {
        functions.ClickJSElement(element);
    }

    @And("^I scroll to element (.*)$")
    public void scrollToElementSobreAmazon(String element) throws Exception {
        functions.scrollToElement(element);
    }

    @And("^I scroll to (.*) of page$")
    public void iScrollToTopOfPage(String to) throws Exception {
        functions.scrollPage(to);
    }

    @And("^I open new tab with  URL (.*)$")
    public void iOpenNewTabWithURL(String url) {
        functions.OpenNewTabWithURL(url);
    }


    @And("^I switch to new window$")
    public void switchNewWindow()
    {
        System.out.println(driver.getWindowHandles());
        for(String winHandle : driver.getWindowHandles()){
            System.out.println(winHandle);
            log.info("Switching to new windows");
            driver.switchTo().window(winHandle);

        }
    }

    @When("^I go to (.*) window$")
    public void iGoToPrincipalWindow(String WindowsHandle) {
        functions.WindowsHandle(WindowsHandle);
    }


    @And("^I click in element (.*)")
    public void iClicInElement(String element) throws Exception {

        functions.iClicInElement(element);

    }


    @And("^I wait (.*) seconds$")
    public void iWaitSeconds(int seconds) throws InterruptedException {
        int secs = seconds * 1000;
        Thread.sleep(secs);
    }


    @Then("^I (accept|dismiss) alert$")
    public void iAcceptAlert(String option) {
        functions.alert(option);
    }


    @And("I wait site is loaded")
    public void iWaitSiteIsLoaded() {
        functions.page_has_loaded();
    }

    @And("^I take screenshot: (.*)$")
    public void iTakeScreenshotSpotify(String testCaptura) throws IOException {
        functions.ScreenShot1(testCaptura);
    }

    @Then("^Assert if (.*) contains text (.*)$")
    public void assertIfEmailExistenteContainsText(String element, String text) throws Exception {
        functions.checkPartialTextElementPresent(element, text);
        //functions.checkPartialTextElementNoPresent(element,text);
    }

    @Then("^Assert if (.*) is equals text (.*)$")
    public void assertIfEmailExistenteIsEqualsText(String element, String text) throws Exception {
        functions.checkTextElementEqualTo(element,text);
    }

    @Then("^check if element (.*) is Displayed$")
    public void checkIfElementRegistrateConGoogleIsDisplayed(String element) throws Exception {

        boolean isDisplayed = functions.isElementDisplayed(element);
        Assert.assertTrue("The element is not present : " +element, isDisplayed);
    }

    @And("I Attach a Screenshot to Report: (.*)$")
    public void attachAScreenshotToReport(String testCaptura) {

        functions.attachScreenShot(testCaptura);
    }

}
