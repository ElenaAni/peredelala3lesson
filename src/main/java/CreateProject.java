import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static sun.security.jgss.GSSUtil.login;

public class CreateProject {
    private static WebDriver driver;
    private static final String LOGIN_PAGE_URL = "https://crm.geekbrains.space/user/login";
    private static final String USER_LOGIN = "Applanatest1";
    private static final String USER_PASSWORD = "Student2020";
    private static final String expensesMenu = "//li[@class='dropdown']/a[contains(.,'Проекты')]";
    private static final String expenseSubmenu = "//li[@data-route='crm_project_my']/a";
    private static final String createButton = "//a[@title='Создать проект'] ";
    private static final String orgChosen = "//div[@class='company-container']/div/a/span[@class='select2-arrow]";
    private static final String orgInput = "//input[@class='select2-input select2-focused'] ";
    private static final String orgResult = "// div[class='select2-result-label']";
    private static final String saveButton = " // button[contains(.,'Сохранить и закрыть')]";
    private static final String message = "//div[contains (@class, 'alert-success')";


    public static void main(String[] args) {

        //System. setProperty("Webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        login();
        //click on Проекты - Мои проекты menu
        Actions actionProject = new Actions(driver);
        WebElement contractorMenu = driver.findElement(By.xpath(expensesMenu));
        actionProject.moveToElement(contractorMenu).perform();

        driver.findElement(By.xpath(expenseSubmenu)).click();

        //click on button Создать проект
        driver.findElement(By.xpath(createButton)).click();

        // Заполнение полей формы
        //Поле Наименование: заполнение, проверка
        WebElement fieldProjectName = driver.findElement(By.name("crm_project[name]"));
        fieldProjectName.sendKeys("Test_project:" + UUID.randomUUID().toString());

        System.out.println("Поле Наименование заполнено:" + !fieldProjectName.getAttribute("value").isEmpty());
        System.out.println("-------------------------------");

        // Поле Организация: заполнение, проверка
        WebElement fieldOrganisation = driver.findElement(By.name("crm_project[company]"));
        new WebDriverWait(driver, 3).
                until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(orgChosen))));
        driver.findElement(By.xpath(orgChosen)).click();

        new WebDriverWait(driver, 5).
                until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(orgResult))));
        driver.findElement(By.xpath(orgInput)).sendKeys(Keys.ENTER);

        System.out.println("Поле организации заполнено:" + !fieldOrganisation.getAttribute("value").isEmpty());
        System.out.println("-------------------------------");

        // Поле Основное контактное лицо: заполнение, проверка
        Select fieldMainContact = new Select(driver.findElement(By.name("crm_project[contactMain")));
        fieldMainContact.selectByVisibleText("Павлов Павел");
        System.out.println("Поле основное контактное лицо заполнено:" +
                !fieldMainContact.getFirstSelectedOption().getText().equals("Павлов Павел"));
        System.out.println("-------------------------------");

        // Поле Подразделение: заполнение, проверка
        Select fieldBusinessUnit = new Select(driver.findElement(By.name("crm_project[businessUnit]")));
        fieldBusinessUnit.selectByValue("1");
        System.out.println("Поле подразделение заполнено:" +
                !fieldBusinessUnit.getFirstSelectedOption().getAttribute("value").equals("1"));
        System.out.println("-------------------------------");

        // Поле Куратор проекта: заполнение, проверка
        Select fieldBProjectCurator = new Select(driver.findElement(By.name("crm_project[curator]")));
        fieldBProjectCurator.selectByVisibleText("Мясников Сергей");
        System.out.println("Поле куратор проекта заполнено:" +
                !fieldBProjectCurator.getFirstSelectedOption().getText().equals("Мясников Сергей"));
        System.out.println("-------------------------------");

        // Поле Руководитель проекта: заполнение, проверка
        Select fieldBProjectDirector = new Select(driver.findElement(By.name("crm_project[rp]")));
        fieldBProjectDirector.selectByVisibleText("Авласёнок Денис");
        System.out.println("Поле руководитель проекта заполнено:" +
                !fieldBProjectDirector.getFirstSelectedOption().getText().equals("Авласёнок Денис"));
        System.out.println("-------------------------------");

        // Поле Менеджер проекта: заполнение, проверка
        Select fieldBProjectManager = new Select(driver.findElement(By.name("crm_project[manager]")));
        fieldBProjectManager.selectByVisibleText("Амелин Владимир");
        System.out.println("Поле менеджер проекта заполнено:" +
                !fieldBProjectManager.getFirstSelectedOption().getText().equals("Авласёнок Денис"));
        System.out.println("-------------------------------");

        //click on button Сохранить и закрыть
        driver.findElement(By.xpath(saveButton)).click();

        //Проверка: Отображается сообщение 'Проект сохранен'
        WebElement messageSuccess = driver.findElement(By.xpath(message));
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(messageSuccess));
        System.out.println("Отображвется сообщение 'Проект сохранен': " + messageSuccess.isDisplayed());

        tearDown();

    }

    private static void login() {
        driver.get(LOGIN_PAGE_URL);

        driver.manage().window().maximize();

        WebElement loginInput = driver.findElement(By.name("_username"));
        loginInput.sendKeys(USER_LOGIN);


        WebElement passwordInput = driver.findElement(By.name("_password"));
        passwordInput.sendKeys(USER_PASSWORD);


        WebElement loginButton = driver.findElement(By.xpath(".//button[@name='_submit']"));
        loginButton.click();
    }

    private static void tearDown() {
        System.out.println();
        System.out.println("Test is completed!");
        driver.quit();


    }
}