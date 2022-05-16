package Functions;

import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class CreateDriver {

    //se crean variables globales que son leidas en el test.properties
    private static String browser;
    private static String os;
    private static String logLevel;

    //se setea el  nombre del archivo de propiedades y se levanta como archivo properties
    private static Properties prop = new Properties();
    //que lea la ruta deonde esta el archivo
    private static InputStream in = CreateDriver.class.getResourceAsStream("../test.properties");

    //private static Logger log = Logger.getLogger(String.valueOf(CreateDriver.class));
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CreateDriver.class);

    //se comienza a leer el archivo test.properties
    public static WebDriver initConfig() throws IOException {
        WebDriver driver;

        try {
            log.info("***********************************************************************************************************");
            log.info("[ POM Configuration ] - Read the basic properties configuration from: ../test.properties");
            //se carga el archivo dentro de prop
            prop.load(in);
            //se buscan dentro del archivo las propiedades
            browser = prop.getProperty("browser");
            os = prop.getProperty("os");
            logLevel = prop.getProperty("logLevel");

        } catch (IOException e) {
            //sino se encuentra el archivo o no se encuentran las propiedades dentro del archivo arroja mensaje
            log.error("initConfig Error", e);
        }

        /******** POM Information ********/
        log.info("[ POM Configuration ] - OS: " + os + " | Browser: " + browser + " |");
        log.info("[ POM Configuration ] - Logger Level: " + logLevel);
        log.info("***********************************************************************************************************");

        /****** Load the driver *******/
        driver = WebDriverFactory.createNewWebDriver(browser, os);

        return driver;



    }


}
