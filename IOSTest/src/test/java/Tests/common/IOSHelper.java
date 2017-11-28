package Tests.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Helper for IOS platform
 * Created by weijiancheng on 2017/8/29.
 */

public class IOSHelper {

    static public MobileElement scrollDownUnitFound(AppiumDriver<MobileElement> driver, String content) {
//        int width = driver.manage().window().getSize().getWidth();
//        int height = driver.manage().window().getSize().getHeight();
        int width = 300;
        int height = 600;
        int count = 0;
        List<MobileElement> list;
        do {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            list = driver.findElementsByName(content);
            if (list.size() > 0 && list.get(0).isDisplayed()) {
                return list.get(0);
            }
            driver.swipe(width / 2, height / 2, width / 2, -120, 500);
            count++;
        } while (count < 20);

        return null;
    }
}
