package Tests.common;

public class ConstConfig {

    /**
     * 对于PopupWindow或Dialog之类的id查找，需要带上"com.android.packageinstaller:id/"的前缀
     * Android 7.0
     */
    public static String ALLOW_BUTTON = "com.android.packageinstaller:id/permission_allow_button";
    public static String DENY_BUTTON = "com.android.packageinstaller:id/permission_deny_button";
    public static String PERMISSION_DIALOG_ID = "com.android.packageinstaller:id/dialog_container";
    public static String DO_NOT_ASK = "com.android.packageinstaller:id/do_not_ask_checkbox";
    public static String PERMISSION_MESSAGE = "com.android.packageinstaller:id/permission_message";

    public static String ALERT_OK = "android:id/button1";
    public static String ALERT_CANCEL = "android:id/button2";
}
