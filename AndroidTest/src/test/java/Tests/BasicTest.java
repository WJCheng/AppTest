package Tests;

import Tests.base.BaseTest;
import Tests.common.AndroidHelper;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class BasicTest extends BaseTest {

    /**
     * Send Message Test Case
     */
    @Test
    public void test001_SendMessage() throws Exception {
        String subject = "new_chat";
        String target = "Min Thu";

        findElement(By.id("up")).click();
        implicitlyWait(3);
        findElements(By.id("tv_item_name")).get(1).click();
        findElements(By.id("tv_tabtxt")).get(0).click();//press chat
        findElement(By.id("compose_button")).click();//press floatingButton

        findElement(By.id("et_new_msg_subject")).sendKeys(subject);//set the subject
        findElement(By.id("ib_new_msg_add_contacts")).click();//add recipient

        List<MobileElement> groupList = findElements(By.id("rl_en_addr_book_role_name"));

        List<MobileElement> staffList = findElements(By.xpath(".//*[@text='HelloTech Staffs']"));//if Groups is not expanded, expand it.
        if (staffList.size() == 0) {
            groupList.get(1).click();
        }
        staffList.get(0).click();

        List<MobileElement> subGroupList = findElements(By.id("rl_en_addr_book_role_name"));//Employee(31)
        if (subGroupList.size() != 0){
            subGroupList.get(1).click();
        }

        WebElement el = AndroidHelper.scrollDownUnitFound(driver, target);
        if (el != null) {//find the checkbox to the left of the founded target.
            int targetX = el.getLocation().getX() - 20;
            int targetY = el.getLocation().getY() + 20;
            System.out.println("coordinates：x = " + targetX + "    y = " + targetY);
            driver.tap(1, targetX, targetY, 300);
        }
        findElement(By.id("menu_item_done")).click();//press Done

        findElement(By.id("et_conversation_list_msg")).sendKeys("just for test!");
        findElement(By.id("b_conversation_list_send")).click();
        findElement(By.id("button1")).click();//Send


        implicitlyWait(8);
        assertNotNull(findElement(By.id("b_conversation_list_send")),
                "failed, can not find 'Send' button on screen.");
        findElement(By.id("up")).click();
    }

    /**
     * Send Vote Test Case
     */
    @Test
    public void test002_SendVote() throws Exception {
        String voteSubject = "new_vote_2";
        String voteTarget = "Min Thu";

        findElement(By.id("up")).click();
        implicitlyWait(3);
        findElements(By.id("tv_item_name")).get(1).click();
        findElements(By.id("tv_tabtxt")).get(1).click();//press vote
        findElement(By.id("compose_button")).click();//press floatingButton

        findElement(By.id("et_new_vote_ask")).sendKeys(voteSubject);//set the subject
        findElement(By.id("ib_new_msg_add_contacts")).click();//add recipient

        implicitlyWait(3);
        List<MobileElement> groupList = findElements(By.id("rl_en_addr_book_role_name"));//contact groups

        List<MobileElement> staffList = findElements(By.xpath(".//*[@text='HelloTech Staffs']"));//if Groups is not expanded, expand it.
        if (staffList.size() == 0) {
            groupList.get(1).click();
        }
        staffList.get(0).click();

        List<MobileElement> subGroupList = findElements(By.id("rl_en_addr_book_role_name"));//Employee(31)
        if (subGroupList.size() != 0){
            subGroupList.get(1).click();
        }

        MobileElement el = AndroidHelper.scrollDownUnitFound(driver, voteTarget);
        if (el != null) {//find the checkbox to the left of the founded target.
            int targetX = el.getLocation().getX() - 20;
            int targetY = el.getLocation().getY() + 1;//Galaxy S6[1440,2560],[148,2574]偏移到一个没有超过屏幕边界的坐标
            System.out.println("coordinates：x = " + targetX + "    y = " + targetY);
            driver.tap(1, targetX, targetY, 300);
        }
        findElement(By.id("menu_item_done")).click();//press Done

        List<MobileElement> options = findElements(By.id("tv_compose_new_vote_option_text"));
        createVoteOption(options.get(0), "opt_1");
        createVoteOption(options.get(1), "opt_2");
        createVoteOption(options.get(2), "opt_3");

        hideKeyboard();
        findElement(By.id("btn_new_vote_create")).click();//Create Vote

        implicitlyWait(8);
        List<MobileElement> voteList = findElements(By.id("tv_vote_thread_item_subject"));
        assertTrue(voteList.size() > 0 && voteList.get(0).getText().equals(voteSubject),
                "create vote test failed. Can not find vote subject on the screen");
    }

    private void createVoteOption(MobileElement element, String option) throws Exception {
        element.click();
        implicitlyWait(3);
        findElement(By.id("alertTitle"));//ensure the dialog has been showed in the screen
        findElement(By.className("android.widget.EditText")).sendKeys(option);
        findElement(By.id("button1")).click();//press OK
    }

    /**
     * Contact Picker Test Case
     */
    @Test
    public void test003_ContactPicker() throws Exception {
        String targetName = "Jake Wei 3";

        findElement(By.id("up")).click();
        findElements(By.id("tv_item_name")).get(1).click();
        findElements(By.id("tv_tabtxt")).get(2).click();//press Directory

        List<MobileElement> groupList = findElements(By.id("tv_en_addr_book_group_name"));
        if (groupList.size() == 1) {
            assertTrue(groupList.get(0).getText().contains("Groups"), "Groups wrong!");
        } else if (groupList.size() == 2) {
            assertTrue(groupList.get(0).getText().contains("admin")
                            && groupList.get(1).getText().contains("Directory"),
                    "Admin group wrong!");
        }

        findElement(By.xpath(".//*[@text='HelloTech Staffs']")).click();
        List<MobileElement> subGroupList = findElements(By.id("rl_en_addr_book_role_name"));//Employee(31)
        if (subGroupList.size() != 0){
            subGroupList.get(1).click();
        }

        WebElement target = AndroidHelper.scrollDownUnitFound(driver, targetName);
        if (target != null) {
            target.click();
        }

        findElement(By.id("b_en_contact_detail_send_msg")).click();//Send Tok-Tok Message

        findElement(By.id("et_conversation_list_msg")).sendKeys("Contact Picker Test Case");
        findElement(By.id("b_conversation_list_send")).click();//Send
        findElement(By.id("button1")).click();//Send Confirm Dialog


        implicitlyWait(8);
        assertNotNull(findElement(By.id("b_conversation_list_send")),
                "failed, can not find 'Send' button on screen.");
    }

}
