package Tests.base;

import Tests.common.ConstConfig;
import Tests.common.KeyCodeHelper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertNotNull;

public abstract class BaseTest {

    protected static AndroidDriver<MobileElement> driver;

    @BeforeSuite
    public void setUp() throws Exception {
        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidPhone");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "hello.talksg.uat");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "hello.talksg.activity.TalkSGActivity_");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

        driver = new AndroidDriver<>(url, capabilities);
        implicitlyWait(20);
    }

    @AfterSuite
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeTest
    public void checkRegistration() throws Exception {
        if (findElement(By.id("b_reg_next")) != null) {
            System.out.println("need to process register workflow");
            testRegister();
        } else {
            System.out.println("already registered and logged in");
        }
    }

    @AfterMethod
    public void closeApp() {
    }

    protected MobileElement findElement(By by) {
        try {
            return driver.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    protected List<MobileElement> findElements(By by) {
        return driver.findElements(by);
    }

    protected static void implicitlyWait(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Allow the permission needed to continue. for android 7.0
     */
    protected void grantPermissionIfPrompt() {
        int count = 0;
        implicitlyWait(5);
        MobileElement allowBtn = findElement(By.id(ConstConfig.ALLOW_BUTTON));
        while (allowBtn != null && count++ < 5) {//at most 5 time to try
            allowBtn.click();
            implicitlyWait(3);
            allowBtn = findElement(By.id(ConstConfig.ALLOW_BUTTON));
        }
    }

    /**
     * handle the situation that showing the permission dialog on the HomeActivity.
     * the button is OK, not Allow any more.
     */
    protected void grantPermissionAfterLogin() {
        int count = 0;
        implicitlyWait(5);
        MobileElement okBtn = findElement(By.id(ConstConfig.ALERT_OK));
        while (okBtn != null && count++ < 5) {
            okBtn.click();
            implicitlyWait(3);
            okBtn = findElement(By.id(ConstConfig.ALERT_OK));
        }
    }

    protected void hideKeyboard() {
        driver.hideKeyboard();
    }

    private void testRegister() throws Exception {
        String account = "80000008";
        int len = account.length();

        MobileElement deregisterDialogBtn = findElement(By.id("button1"));
        if (deregisterDialogBtn != null){
            deregisterDialogBtn.click();
        }

        findElement(By.id("et_reg_mobile")).sendKeys(account);//account
        findElement(By.id("b_reg_next")).click();//Next
        findElement(By.id("button1")).click();//Agree

        implicitlyWait(3);
        List<MobileElement> okList = findElements(By.id("button1"));//{".//*[@text='OK']"}，already login on the other phone
        if (okList.size() > 0) {
            okList.get(0).click();
        }
        hideKeyboard();//这里必须先隐藏软键盘，不然会导致后面系统权限对话框弹出的时候，来不及隐藏，对话框按钮实际焦点常常是软键盘展开时的坐标，而不是软键盘收起的坐标

        Thread.sleep(5000);
        driver.pressKeyCode(KeyCodeHelper.getKeyCodeNumberByString(account.charAt(len - 3) + ""));
        driver.pressKeyCode(KeyCodeHelper.getKeyCodeNumberByString(account.charAt(len - 2) + ""));
        driver.pressKeyCode(KeyCodeHelper.getKeyCodeNumberByString(account.charAt(len - 1) + ""));

        //Need Permission, You need to grant access to 1,2,3.
        List<MobileElement> needOKList = findElements(By.id("button1"));
        if (needOKList.size() > 0) {
            needOKList.get(0).click();
        }
        grantPermissionIfPrompt();//System Permission Granted Dialog

        implicitlyWait(3);
        if (findElements(By.xpath(".//*[@text='Enter Your Name']")).size() > 0) {
            findElement(By.className("android.widget.EditText")).sendKeys("8888");
            findElement(By.xpath(".//*[@text='Save']")).click();
        }

        MobileElement welcomeBtn = findElement(By.id("mi_done"));//viewpager show new feature welcome page
        if (welcomeBtn != null){
            welcomeBtn.click();
        }

        grantPermissionAfterLogin();

        implicitlyWait(8);
        List<MobileElement> newEnterprise = findElements(By.id("button1"));//New Enterprise
        if (newEnterprise.size() > 0) {
            newEnterprise.get(0).click();
        }

        assertNotNull(findElement(By.id("compose_button")),
                "testRegister Failed, can not find 'compose_button' on the screen");
    }

    public void testDeregister() throws Exception {
        List<MobileElement> enterpriseOK = findElements(By.id("button1"));
        if (enterpriseOK != null && enterpriseOK.size() > 0) {
            enterpriseOK.get(0).click();
        }

        findElements(By.id("tv_tabtxt")).get(3).click();        //press Setting
        findElements(By.id("summary")).get(0).click();          //click the account.
        findElement(By.id("b_contact_id_deregister")).click();  //deregister
        findElement(By.id("button1")).click();                  //confirm to deregister dialog OK.

        implicitlyWait(10);
        findElement(By.id("button1")).click();//You will no longer receive messages. Please register again to re-activate.

        implicitlyWait(3);
        Assert.assertTrue(findElements(By.id("tv_reg_terms")).size() > 0,
                "testDeregister Failed, can not find 'reg term' text in login screen.");
    }
}
