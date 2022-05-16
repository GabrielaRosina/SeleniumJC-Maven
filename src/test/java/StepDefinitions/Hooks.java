package StepDefinitions;

import Functions.CreateDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

public class Hooks {

    public static WebDriver driver;
    Logger log = Logger.getLogger(String.valueOf(Hooks.class));
    //libreria scenario de cucumber. inicializarla en null
    Scenario scenario = null;
    @Before
    public void initDriver(Scenario scenario) throws IOException {
        log.info("***********************************************************************************************************");
        log.info("[ Configuration ] - Initializing driver configuration");
        log.info("***********************************************************************************************************");
        //Se configura el driver
        driver = CreateDriver.initConfig();
        //se setea el escenario
        this.scenario = scenario;
        log.info("***********************************************************************************************************");
        log.info("[ Scenario ] - "+ scenario.getName());
        log.info("***********************************************************************************************************");

    }

    @After
    public void tearDown(){
        log.info("***********************************************************************************************************");
        log.info("[ Driver Status ] - Clean and close the intance of the driver");
        log.info("***********************************************************************************************************");
        //driver.quit();
    }


}
