package Tests.base;

import Tests.common.ConstConfig;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public abstract class BaseTest {

    protected static IOSDriver<MobileElement> driver;

    @BeforeSuite
    public void setUp() throws Exception {
        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        DesiredCapabilities capabilities = new DesiredCapabilities();

//        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
//        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.3");
//        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Rented iPhone 6");
//        capabilities.setCapability(MobileCapabilityType.UDID, "c4ef2cbc8a1b397ce838a8ad9fdea45547e44c20");
//        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.hello-technology.toktok.dev");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

        driver = new IOSDriver<>(url, capabilities);
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
        allowSystemNotification();
        okPushRegistrationError();

        if (findByAccessibilityId("Next") != null) {
            System.out.println("ios: need to process register workflow");
            testRegister();
        } else {
            System.out.println("ios: already registered and logged in");
        }
    }

//    @AfterTest
//    public void closeApp() {
//        driver.closeApp();
//    }

    private void testRegister() throws Exception {
        String account = "80000008";
        int len = account.length();

        allowSystemNotification();//"tt uat" Would like to Send You Notifications

        okPushRegistrationError();//Push Notification Error. press "OK"

        List<MobileElement> editsLogin = findElements(By.className("XCUIElementTypeTextField"));
        editsLogin.get(1).clear();
        editsLogin.get(1).sendKeys(account);

        findByAccessibilityId("Next").click();

        implicitlyWait(10);
        List<MobileElement> agree = findsByAccessibilityId("Agree");
        if (agree.size() > 0) {
            agree.get(0).click();
        }

        implicitlyWait(3);
        //Phone number already registered. Assign to this device instead?  Yes / No
        List<MobileElement> yes = findsByAccessibilityId("Yes");
        if (yes.size() > 0) {
            yes.get(0).click();
        }

        implicitlyWait(3);
        Thread.sleep(3000);
        List<MobileElement> edits = findElements(By.className("XCUIElementTypeTextField"));
        edits.get(0).sendKeys(account.charAt(len - 3) + "");
        edits.get(1).sendKeys(account.charAt(len - 2) + "");
        edits.get(2).sendKeys(account.charAt(len - 1) + "");

        handleVerificationFailed(account, len, edits);//Verification Failed, You OTP is incorrect.

        implicitlyWait(8);
        allowSystemNotification();//tt uat Would Like to Access You Contacts

        findByAccessibilityId("Done").click();

        implicitlyWait(3);
        List<MobileElement> grantedList = findsByAccessibilityId("OK");
        if (grantedList.size() > 0) {
            grantedList.get(0).click();
        }

        assertNotNull(findByAccessibilityId("Compose"),
                "Register and Login Failed! because can not find 'Compose' button.");
    }

    private void handleVerificationFailed(String account, int len, List<MobileElement> edits) throws Exception {
        List<MobileElement> okList = findsByAccessibilityId("OK");//Verification Failed
        if (okList.size() == 0) {
            System.out.println("need to Resend OTP verification!");
            return;
        }

        okList.get(0).click();
        Thread.sleep(60000);
        findByAccessibilityId("Resend").click();

        edits.get(0).sendKeys(account.charAt(len - 3) + "");
        edits.get(1).sendKeys(account.charAt(len - 2) + "");
        edits.get(2).sendKeys(account.charAt(len - 1) + "");
    }

    public void deregister() {
        List<MobileElement> enterOkList = findsByAccessibilityId("OK");
        if (enterOkList.size() > 0) {
            enterOkList.get(0).click();
        }
        findByAccessibilityId("Settings").click();
        findByAccessibilityId("Account").click();
        findByAccessibilityId("Deregister").click();

        implicitlyWait(3);
        findElements(By.className("XCUIElementTypeButton")).get(1).click();

        implicitlyWait(8);
        if (findsByAccessibilityId("Deregistered").size() > 0) {
            findByAccessibilityId("OK").click();
        }

        assertTrue(findsByAccessibilityId("Next").size() > 0,
                "Deregister failed!");
    }

    protected MobileElement findElement(By by) {
        try {
            return driver.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    protected MobileElement findByAccessibilityId(String id) {
        return findElement(MobileBy.AccessibilityId(id));
    }

    protected List<MobileElement> findElements(By by) {
        try {
            return driver.findElements(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    protected List<MobileElement> findsByAccessibilityId(String id) {
        return findElements(MobileBy.AccessibilityId(id));
    }

    protected static void implicitlyWait(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    protected void allowSystemNotification() {
        implicitlyWait(5);
        MobileElement allow = findElement(MobileBy.AccessibilityId(ConstConfig.ALLOW));//in allow case
        if (allow != null) {
            allow.click();
        } else {
            MobileElement ok = findElement(MobileBy.AccessibilityId(ConstConfig.OK));//in ok case
            if (ok != null) {
                ok.click();
            }
        }
    }

    protected void okPushRegistrationError() {
        implicitlyWait(3);
        MobileElement ok = findElement(MobileBy.AccessibilityId(ConstConfig.OK));//Push Notification Registration Error
        if (ok != null) {
            ok.click();
        }
    }
}
