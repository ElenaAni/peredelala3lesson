import java.util.concurrent.TimeUnit;
import javax.security.auth.Subject;
import org.omg.CORBA.WCharSeqHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static sun.security.jgss.GSSUtil.login;

public class FirstProject {
private static WebDriver driver;
private static final String LOGIN_PAGE_URL="https://crm.geekbrains.space/user/login";
private static final String USER_LOGIN = "Applanatest1";
private static final String USER_PASSWORD = "Student2020";
private static final String expensesMenu = "//ul[contains(@class,'nav-multilevel')]/li[contains(.'Котрагенты')]";
private static final String expenseSubmenu = "//li[@data-route='crm_contact_index']/a";
private static final String createButton = "//a[@title='Создать контактное лицо'] ";
private static final String orgChosen = "//span[@class='select2-arrow']";
private static final String orgInput = "//input[contains (@class, 'select2-input')] ";
private static final String orgResult = "// div[class='select2-result-label']";
private static final String saveButton = " // button[contains(.,'Сохранить и закрыть')]";
private static final String message = "//div[contains (@class, 'alert-success')";



    public static void main (String[] args) {
        //System. setProperty("Webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

         login();


//Переход по меню Контагенты -Контактные Лица
        Actions actionContractor = new Actions(driver);
        WebElement contractorMenu = driver.findElement(By.xpath(expensesMenu));
        actionContractor.moveToElement(contractorMenu).perform();

        driver.findElement(By.xpath(expenseSubmenu)).click();

//click on button Создать контактные лица
        driver.findElement(By.xpath(createButton)).click();

        //Заполнение полей формы
        //Поле Фамилия:Заполнение, проверка
        WebElement fieldLastName = driver.findElement(By.name("crm_contact[LastName]"));
        fieldLastName.sendKeys("Павлов");
        System.out.println("Поле Фамилия заполнено:" + !fieldLastName.getAttribute("value").isEmpty());
        System.out.println("-------------------------------");

        // Поле Имя: заполнение,проверка
        WebElement fieldFirstName = driver.findElement(By.name("crm_contact[FirstName]"));
        fieldFirstName.sendKeys("Павел");
        System.out.println("Поле Имя заполнено:" + !fieldFirstName.getAttribute("value").isEmpty());
        System.out.println("-------------------------------");

        // Поле Организация: заполнение, проверка
        WebElement fieldOrganisation = driver.findElement(By.name("crm_contact[company]"));

        driver.findElement(By.xpath(orgChosen)).click();
        driver.findElement(By.xpath(orgInput)).sendKeys("Hamill");
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(orgResult))));
        driver.findElement(By.xpath(orgInput)).sendKeys(Keys.ENTER);

        System.out.println("Поле организации заполнено:" + !fieldOrganisation.getAttribute("value").isEmpty());
        System.out.println("-------------------------------");

        // Поле Должность: заполнение, проверка
        WebElement fieldJobTitle = driver.findElement(By.name("crm_contact[jobtitle]"));
        fieldJobTitle.sendKeys("менеджер");
        System.out.println("Поле Должность заполнено:" + !fieldJobTitle.getAttribute("value").isEmpty());
        System.out.println("-------------------------------");

        //click on button Сщхранить и закрыть
        driver.findElement(By.xpath(saveButton)).click();

        //Проверка: Отображается сообщение 'Контактное лицо сохранено'
        WebElement messageSuccess = driver.findElement(By.xpath(message));
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(messageSuccess));
        System.out.println("Отображвется сообщение 'Контактное лицо сохранено': " + messageSuccess.isDisplayed());

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


