package Functions;

import StepDefinitions.Hooks;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class SeleniumFunctions {

    static WebDriver driver;
    public SeleniumFunctions(){
        driver = Hooks.driver;
    }

    /******** Page Path ********/
    public static String PagesFilePath = "src/test/resources/Pages/";
    public static String FileName = "";

    /******** Escenario test data ********/
    public static Map<String, String> ScenaryData = new HashMap<>();
    public static Map<String, String> HandleMyWindows = new HashMap<>();
    public static String Environment = "";
    public static String ElementText = "";

    /******** Explicit Wait ********/
    public static  int EXPLICIT_TIMEOUT = 15;


    /******** Test properties config. DOM/Pages Json ********/
    public static String GetFieldBy = "";
    public static String ValueToFind = "";

    //se setea el  nombre del archivo de propiedades y se levanta como archivo properties
    private static Properties prop = new Properties();
    //que lea la ruta deonde esta el archivo
    private static InputStream in = CreateDriver.class.getResourceAsStream("../test.properties");

    public static boolean isDisplayed = Boolean.parseBoolean(null);
    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(SeleniumFunctions.class);



    //este metodo lee el json
    public static Object readJson() throws Exception {
        //Se busca la ruta del archivo, se lee el archivo
        FileReader reader = new FileReader(PagesFilePath + FileName);
        try {

            //si logro abrir elachivo, que me parsee los json para poder extraer su valor
            if (reader != null) {
                JSONParser jsonParser = new JSONParser();
                return jsonParser.parse(reader);
            } else {
                return null;
            }
        } catch (FileNotFoundException | NullPointerException e) {
            log.error("ReadEntity: No existe el archivo " + FileName);
            throw new IllegalStateException("ReadEntity: No existe el archivo " + FileName, e);
        }

    }

    //este metodo lee una entity del json-un bloque
    public static JSONObject ReadEntity(String element) throws Exception {
        JSONObject Entity = null;

        //Accede al json para leerlo
        JSONObject jsonObject = (JSONObject) readJson();
        //Accede a algunas de las entities
        Entity = (JSONObject) jsonObject.get(element);
        log.info(Entity.toJSONString());
        return Entity;

    }


    //esta funcion lee los dos atruibutos dentro de la entity del json
    public static By getCompleteElement(String element) throws Exception {
        By result = null;
        JSONObject Entity = ReadEntity(element);

        GetFieldBy = (String) Entity.get("GetFieldBy");
        ValueToFind = (String) Entity.get("ValueToFind");

        if ("className".equalsIgnoreCase(GetFieldBy)) {
            result = By.className(ValueToFind);
        } else if ("cssSelector".equalsIgnoreCase(GetFieldBy)) {
            result = By.cssSelector(ValueToFind);
        } else if ("id".equalsIgnoreCase(GetFieldBy)) {
            result = By.id(ValueToFind);
        } else if ("linkText".equalsIgnoreCase(GetFieldBy)) {
            result = By.linkText(ValueToFind);
        } else if ("name".equalsIgnoreCase(GetFieldBy)) {
            result = By.name(ValueToFind);
        } else if ("link".equalsIgnoreCase(GetFieldBy)) {
            result = By.partialLinkText(ValueToFind);
        } else if ("tagName".equalsIgnoreCase(GetFieldBy)) {
            result = By.tagName(ValueToFind);
        } else if ("xpath".equalsIgnoreCase(GetFieldBy)) {
            result = By.xpath(ValueToFind);
        }
        return result;
    }

    //lee en el test properties en que ambiente nos encontramos
    public String readProperties(String property) throws IOException {
        prop.load(in);
        return prop.getProperty(property);
    }

    //se crea una variable q recibe dos parametros y se va leyendo la data
    public void SaveInScenario(String key, String text) {

        if (!this.ScenaryData.containsKey(key)) {
            this.ScenaryData.put(key,text);
            log.info(String.format("Save as Scenario Context key: %s with value: %s ", key,text));
            System.out.println(String.format("Save as Scenario Context key: %s with value: %s ", key,text));
        } else {
            this.ScenaryData.replace(key,text);
            log.info(String.format("Update Scenario Context key: %s with value: %s ", key,text));
        }
    }

    //se lee el enviroment para ejecutar la prueba
    public void RetriveTestData(String parameter) throws IOException {
        Environment = readProperties("Environment");
        try {
            SaveInScenario(parameter, readProperties(parameter+"."+Environment));
            System.out.println("Este es el valor de la parametrizacion " + parameter + " : " + this.ScenaryData.get(parameter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iSetElementWithText(String element, String text) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).sendKeys(text);
        log.info(String.format("Set on element %s with text %s", element, text));
    }

    public void iSetElementWithKeyValue(String element, String key) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean exist = this.ScenaryData.containsKey(key);
        if (exist){
            String text = this.ScenaryData.get(key);
            driver.findElement(SeleniumElement).sendKeys(text);
            log.info(String.format("Set on element %s with text %s", element, text));
        }else{
            Assert.assertTrue(String.format("The given key %s do not exist in Context", key), this.ScenaryData.containsKey(key));
        }

    }

    public void selectOptionDropdownByIndex(String element, int option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Waiting Element: %s", element));

        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Select option: " + option + "by text");
        opt.selectByIndex(option);
    }

    public void selectOptionDropdownByText(String element, String option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Waiting Element: %s", element));

        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Select option: " + option + "by text");
        opt.selectByVisibleText(option);
    }

    public void selectOptionDropdownByValue(String element, String option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Waiting Element: %s", element));

        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Select option: " + option + "by text");
        opt.selectByValue(option);
    }

    public void waitForElementPresent(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        log.info("Waiting for the element: "+element + " to be present");
        wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
    }

    public void waitForElementVisible(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        log.info("Waiting for the element: "+element + " to be visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(SeleniumElement));
    }

    public void waitForElementClickable(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        log.info("Waiting for the element: "+element + " to be visible");
        wait.until(ExpectedConditions.elementToBeClickable(SeleniumElement));
    }


    public boolean isElementDisplayed(String element) throws Exception {
        try {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            isDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement)).isDisplayed();
        }catch (NoSuchElementException | TimeoutException e){
            isDisplayed = false;
            log.info(e);
        }
        log.info(String.format("%s visibility is: %s", element, isDisplayed));
        System.out.println(String.format("%s visibility is: %s", element, isDisplayed));
        return isDisplayed;
    }


    public void switchToFrame(String Frame) throws Exception {

        //aqui se le pasa el elemento del frame al cual quiero llegar
        By SeleniumElement = SeleniumFunctions.getCompleteElement(Frame);
        log.info("Switching to frame: " + Frame);
        //Aqui tengo q pasarle el elemento al cual quiero llegar dentro del frame
        driver.switchTo().frame(driver.findElement(SeleniumElement));

    }

    public void switchToParentFrame() {

        log.info("Switching to parent frame");
        driver.switchTo().parentFrame();

    }

    public void checkCheckbox(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean isChecked = driver.findElement(SeleniumElement).isSelected();
        if(!isChecked){
            log.info("Clicking on the checkbox to select: " + element);
            driver.findElement(SeleniumElement).click();
        }
    }

    public void UncheckCheckbox(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean isChecked = driver.findElement(SeleniumElement).isSelected();
        if(isChecked){
            log.info("Clicking on the checkbox to select: " + element);
            driver.findElement(SeleniumElement).click();
        }
    }

    //scroll a al principio y final de la pagina
    public void ClickJSElement(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        log.info("Scrolling to element: " + element);
        jse.executeScript("arguments[0].click()", driver.findElement(SeleniumElement));

    }

    //scroll de principip a fin de la pagina
    public void scrollPage(String to) throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        if(to.equals("top")){
            log.info("Scrolling to the top of the page");
            jse.executeScript("scroll(1440, 900);");

        }
        else if(to.equals("end")){
            log.info("Scrolling to the end of the page");
            jse.executeScript("scroll(1440, 900);");
        }
    }

    //scroll hacia un elemento especifico
    public void scrollToElement(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        log.info("Scrolling to element: " + element);
        jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(SeleniumElement));

    }

    public void page_has_loaded (){
        String GetActual = driver.getCurrentUrl();
        System.out.println(String.format("Checking if %s page is loaded.", GetActual));
        log.info(String.format("Checking if %s page is loaded.", GetActual));
        new WebDriverWait(driver, EXPLICIT_TIMEOUT).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void OpenNewTabWithURL(String URL)
    {
        log.info("Open New tab with URL: " + URL);
        System.out.println("Open New tab with URL: " + URL);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript(String.format("window.open('%s','_blank');", URL));
    }


    public void WindowsHandle(String WindowsName){
        if (this.HandleMyWindows.containsKey(WindowsName)) {
            driver.switchTo().window(this.HandleMyWindows.get(WindowsName));
            log.info(String.format("I go to Windows: %s with value: %s ", WindowsName ,this.HandleMyWindows.get(WindowsName)));
        } else {
            for(String winHandle : driver.getWindowHandles()){
                this.HandleMyWindows.put(WindowsName,winHandle);
                System.out.println(" The New window "+ WindowsName + " is saved in scenario with value " + this.HandleMyWindows.get(WindowsName));
                log.info(" The New window "+ WindowsName + " is saved in scenario with value " + this.HandleMyWindows.get(WindowsName));
                driver.switchTo().window(this.HandleMyWindows.get(WindowsName));
            }

        }
    }

    public void iClicInElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).click();
        log.info("Click on element by " + element);

    }

    public void alert(String option)
    {
        try{
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            if(option== "accept"){
                alert.accept();
                System.out.println("Se acepto la alerta");
            }else{
                alert.dismiss();
                System.out.println("Se cerro la alerta");
            }

        }catch(Throwable e){
            log.error("Error came while waiting for the alert popup. "+e.getMessage());
        }
    }

    public void ScreenShot1(String testCaptura) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
        String screenShotName = readProperties("ScreenShotPath");
        //aqui se toma la captura y se guarda en una variable
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //se le da se da nombre y formato a la captura
        FileUtils.copyFile(scrFile,new File(String.format("%s.png", screenShotName +  " _ " + testCaptura + "_(" + dateFormat.format(GregorianCalendar.getInstance().getTime()) + ")")));

    }

    public byte[] attachScreenShot(String testCaptura){

        log.info("Attaching Screenshot");
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        //se retorna la captura al reporte. Allure toma la captura y la escribe dentro del reporte
        return screenshot;

    }


    //retorna el texto de un objeto
    public String getTextElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));

        ElementText = driver.findElement(SeleniumElement).getText();

        return ElementText;

    }

    //valida si el texto esta presente de manera parcial con contains
    public void checkPartialTextElementPresent(String element,String text) throws Exception {
        ElementText = getTextElement(element);
        //aqui se pregunta si parte de ese texto coincide con el que yo espero.
        //devuelve tru si coincide y false sino
        boolean isFound = ElementText.indexOf(text) !=-1? true: false;
        System.out.print(isFound);
        Assert.assertTrue("Text is not present in element: " + element + " current text is: " + ElementText, isFound);

    }

    //valida si el texto esta presente de manera parcial con contains
    public void checkPartialTextElementNoPresent(String element,String text) throws Exception {
        ElementText = getTextElement(element);
        //aqui se pregunta si parte de ese texto coincide con el que yo espero
        boolean isFound = ElementText.indexOf(text) !=-1? true: false;
        System.out.print(isFound);
        Assert.assertFalse("Text is  present in element: " + element + " current text is: " + ElementText, isFound);

    }

    //aqui se valida que el texto que espero sea igual al obtenido
    public void checkTextElementEqualTo(String element,String text) throws Exception {
        ElementText = getTextElement(element);
        Assert.assertEquals("Text is not present in element: " + element + " current text is: " + ElementText, text, ElementText);

    }


}
