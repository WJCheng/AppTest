package Tests;


import Tests.base.BaseTest;
import Tests.common.IOSHelper;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertNotNull;

public class BasicTest extends BaseTest {

    /**
     * Send Message Test Case
     */
    @Test
    public void test001_SendMessage() throws Exception {
        okPushRegistrationError();

        String chatSubject = "new_chat_ios_555";
        String chatTarget = "Jake Wei 2";

        findByAccessibilityId("btn list").click();
        findByAccessibilityId("HelloTech").click();
        findByAccessibilityId("Chats").click();
        findByAccessibilityId("Compose").click();

        findElement(By.className("XCUIElementTypeTextField")).sendKeys(chatSubject);
        findElements(By.className("XCUIElementTypeButton")).get(2).click();

        implicitlyWait(3);
        findByAccessibilityId("HelloTech Staffs").click();

        MobileElement target = IOSHelper.scrollDownUnitFound(driver, chatTarget);
        if (target != null) {
            int targetX = target.getLocation().getX() - 20;
            int targetY = target.getLocation().getY() + 20;
            driver.tap(1, targetX, targetY, 300);
        }
        findByAccessibilityId("Done").click();

        findElement(By.className("XCUIElementTypeTextView")).sendKeys("just for testing!");
        findByAccessibilityId("Send").click();

        Thread.sleep(1000);
        List<MobileElement> confirmSend = findsByAccessibilityId("Send");
        if (confirmSend != null) {
            confirmSend.get(0).click();
        }

        implicitlyWait(3);
        List<MobileElement> grantedList = findsByAccessibilityId("OK");
        if (grantedList.size() > 0) {
            grantedList.get(0).click();
        }

        Thread.sleep(3000);
        MobileElement subjectElement = findByAccessibilityId(chatSubject);
        assertNotNull(subjectElement,
                "Send Message Test Case Failed! Can not find " + chatSubject + " on screen.");

        Thread.sleep(3000);
        findByAccessibilityId("Back").click();
    }

//    /**
//     * Send Vote Test Case
//     */
//    @Test
//    public void testSendVote() throws Exception {
//        String voteSubject = "new_vote_2";
//        String voteTarget = "Min Thu";
//
//        driver.findElementById("up").click();
//        driver.findElementsById("tv_item_name").get(1).click();
//        driver.findElementsById("tv_tabtxt").get(1).click();//press vote
//        driver.findElementById("compose_button").click();//press floatingButton
//
//        driver.findElementById("et_new_vote_ask").sendKeys(voteSubject);//set the subject
//        driver.findElementById("ib_new_msg_add_contacts").click();//add recipient
//
//        implicitlyWait(3);
//        List<MobileElement> groupList = driver.findElementsById("rl_en_addr_book_role_name");
//        System.out.println("groupList size = " + groupList.size());
//
//        List<MobileElement> staffList = driver.findElementsByXPath(".//*[@text='HelloTech Staffs']");//if Groups is not expanded, expand it.
//        if (staffList.size() == 0) {
//            groupList.get(1).click();
//        }
//        staffList.get(0).click();
//
//        MobileElement el = HelloHelper.scrollDownUnitFound(driver, voteTarget);
//        if (el != null) {//find the checkbox to the left of the founded target.
//            int targetX = el.getLocation().getX() - 10;
//            int targetY = el.getLocation().getY() + 10;
//            driver.tap(1, targetX, targetY, 800);
//        }
//        driver.findElementById("menu_item_done").click();//press Done
//
//        List<MobileElement> options = driver.findElementsById("tv_compose_new_vote_option_text");
//        createVoteOption(options.get(0), "opt_1");
//        createVoteOption(options.get(1), "opt_2");
//        createVoteOption(options.get(2), "opt_3");
//
//        driver.findElementById("btn_new_vote_create").click();//Create Vote
//
//        implicitlyWait(3);
//        List<MobileElement> voteList = driver.findElementsById("tv_vote_thread_item_subject");
//        Assert.assertTrue("create vote test failed",
//                voteList.size() > 0 && voteList.get(0).getText().equals(voteSubject));
//    }
//
//    private void createVoteOption(WebElement webElement, String option) throws Exception {
//        webElement.click();
//        implicitlyWait(3);
//        driver.findElementById("alertTitle");//ensure the dialog has been showed in the screen
//        driver.findElementByClassName("android.widget.EditText").sendKeys(option);
//        driver.findElementById("button1").click();//press OK
//    }

    /**
     * Contact Picker Test Case
     */
    @Test
    public void test003_ContactPicker() throws Exception {
        okPushRegistrationError();

        String targetName = "Jake Wei 2";

        findByAccessibilityId("btn list").click();
        findByAccessibilityId("HelloTech").click();
        findByAccessibilityId("Directory").click();

        findByAccessibilityId("HelloTech Staffs").click();

        MobileElement target = IOSHelper.scrollDownUnitFound(driver, targetName);
        if (target != null) {
            target.click();
        }
        findByAccessibilityId("Send Tok-Tok! Message").click();

        findElement(By.className("XCUIElementTypeTextView")).sendKeys("IOS Contact Picker Test Case");
        findByAccessibilityId("Send").click();

        implicitlyWait(3);
        findsByAccessibilityId("Send").get(0).click();//Can not find the button if don't find by list.

        assertNotNull(findByAccessibilityId("Reply to all recipients"),
                "Contact Picker Test Case failed! Can not find element: 'Reply to all recipients'.");

        findByAccessibilityId("Back").click();
        findByAccessibilityId("Back").click();
    }

}
