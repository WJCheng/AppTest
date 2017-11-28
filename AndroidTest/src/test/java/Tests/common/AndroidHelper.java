package Tests.common;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * Helper for Android platform
 * Created by weijiancheng on 2017/8/29.
 */

public class AndroidHelper {

    static public String createXpathDesc(String content) {
        return ".//*[@text='" + content + "']";
    }

    static public MobileElement scrollDownUnitFound(AndroidDriver<MobileElement> driver, String content) throws Exception {
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        int count = 0;
        String xpathStr = createXpathDesc(content);
        do {
            if (driver.findElementsByXPath(xpathStr).size() > 0) {
                //in case of the element is still hiding, or the element is showing only a little part of it.
                driver.swipe(width / 2, height / 2, width / 2, height / 3, 600);
                Thread.sleep(1000);//sleep 1 second to avoid getting the element in the wrong position.
                return driver.findElementsByXPath(xpathStr).get(0);
            }
            driver.swipe(width / 2, height / 2, width / 2, height / 4, 600);
            count++;
        } while (count < 20);

        return null;
    }
}
