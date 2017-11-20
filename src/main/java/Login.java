import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Login {

    private static ChromeDriverService service;
    private WebDriver driver;

    public static String getChromeDriverPath() {

        String os = System.getProperty("os.name").toLowerCase();
        String bytes = System.getProperty("os.arch");

        boolean isWin = os.contains("win");
        boolean isMac = os.contains("mac");
        boolean isLinux = os.contains("nix") || os.contains("nux") || os.contains("aix");
        boolean is32 = bytes.equals("x86") || bytes.equals("i386") || bytes.equals("i486") || bytes.equals("i586") || bytes.equals("i686");
        boolean is64 = bytes.equals("x86_64") || bytes.equals("amd64");

        // Detect chrome driver directory
        String fileName = "";
        Path chromeDriverDirectory = null;
        if (isMac || isLinux) {
            chromeDriverDirectory = Paths.get("src/main/resources/chrome_driver");
        }
        if (isWin) {
            chromeDriverDirectory = Paths.get("src\\main\\resources\\chrome_driver");
        }

        String chromeDriverPath = chromeDriverDirectory.toAbsolutePath().toString();

        // Detect which driver to use
        if (isMac) {
            chromeDriverPath = chromeDriverPath.concat("/chromedriver_mac");
        }

        if (isLinux) {
            if (is32) {
                chromeDriverPath = chromeDriverPath.concat("/chromedriver_linux32");
            }
            if (is64) {
                chromeDriverPath = chromeDriverPath.concat("/chromedriver_linux32");
            }
        }

        if (isWin) {
            chromeDriverPath = chromeDriverPath.concat("\\chromedriver.exe");
        }

        return chromeDriverPath;
    }


    @BeforeClass
    public static void createAndStartService() {

        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(getChromeDriverPath()))
                .usingAnyFreePort()
                .build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void createAndStopService() {
       //  service.stop(); //остановка сервиса
    }

    @Before
    public void createDriver() {
        //System.setProperty("webdriver.chrome.driver", "/Users/myhail/Downloads/chromedriver");
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
    }

    @After
    public void quitDriver() {
//       driver.quit(); // закрытия браузера
    }


//    @Test
//    public void PassRegistration()  {
//        driver.get("https://accounts.google.com/SignUp"); //переходим по ссылке
//
//        WebElement firstName = driver.findElement(By.id("FirstName"));
//        firstName.sendKeys("Misha");
//
//        WebElement lastName = driver.findElement(By.id("LastName"));
//        lastName.sendKeys("Bidnyj");
//
//        WebElement gmailAdress = driver.findElement(By.id("GmailAddress"));
//        gmailAdress.sendKeys("sdhfljsdlfksjdhfk");
//
//        WebElement password = driver.findElement(By.id("Passwd"));
//        password.sendKeys("misha1234");
//
//        WebElement passRep = driver.findElement(By.id("PasswdAgain"));
//        passRep.sendKeys("misha1234");
//
//        WebElement mounth = driver.findElement(By.xpath("//*[@id=\"BirthMonth\"]/div[1]"));
//        mounth.click();
//        mounth.sendKeys(Keys.ARROW_DOWN);
//        mounth.sendKeys(Keys.ENTER);
//
//        WebElement dayBith = driver.findElement(By.id("BirthDay"));
//        dayBith.sendKeys("9");
//
//        WebElement yearBith = driver.findElement(By.id("BirthYear"));
//        yearBith.sendKeys("1995");
//
//        WebElement gender = driver.findElement(By.xpath("//*[@id=\"Gender\"]/div[1]"));
//        gender.click();
//        gender.sendKeys(Keys.ARROW_DOWN);
//        gender.sendKeys(Keys.ENTER);
//
//        WebElement phoneNumber = driver.findElement(By.id("RecoveryPhoneNumber"));
//        phoneNumber.sendKeys("991812099");
//
//        WebElement buttonNext = driver.findElement(By.id("submitbutton"));
//        buttonNext.click();
//        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
//
//        WebElement termOfUse = driver.findElement(By.id("tos-scroll"));
//
//    }
    // попытка создать слишком короткий эмейл That username is taken. Try another.
    @Test
    public void IncorrectLengtsEmail() {
        driver.get("https://accounts.google.com/SignUp"); //переходим по ссылке

        WebElement gmailAdress = driver.findElement(By.id("GmailAddress"));
        gmailAdress.sendKeys("wev");
        WebElement password = driver.findElement(By.id("Passwd"));
        password.click();


        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

//        WebElement errorText = driver.findElement(By.className("      form-error"));
//        errorText.getText();
        // //*[@id="errormsg_0_GmailAddress"]
        // //*[text()='Please use between 6 and 30 characters.']
        WebElement errorText = driver.findElement((By.xpath("//*[@id=\"errormsg_0_GmailAddress\"]")));
        errorText.getText();
        System.out.println(errorText.getText());

    }

    //попытка зарегестрировать уже существующий адрес
//    @Test
//    public void AddedEmail() {
//        driver.get("https://accounts.google.com/SignUp"); //переходим по ссылке
//
//        WebElement gmailAdress = driver.findElement(By.id("GmailAddress"));
//        gmailAdress.sendKeys("testaccount");
//        gmailAdress.sendKeys(Keys.ENTER);
//        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
//        WebElement errorText = driver.findElement(By.linkText("That username is taken. Try another."));
//        System.out.println(errorText);
//    }
}
