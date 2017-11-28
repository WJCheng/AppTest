package Tests.common;

import io.appium.java_client.android.AndroidKeyCode;

/**
 * Created by weijiancheng on 2017/8/4.
 */

public class KeyCodeHelper {

    public static int[] KeyCodeLetter = new int[]{
        AndroidKeyCode.KEYCODE_A,
        AndroidKeyCode.KEYCODE_B,
        AndroidKeyCode.KEYCODE_C,
        AndroidKeyCode.KEYCODE_D,
        AndroidKeyCode.KEYCODE_E,
        AndroidKeyCode.KEYCODE_F,
        AndroidKeyCode.KEYCODE_G,
        AndroidKeyCode.KEYCODE_H,
        AndroidKeyCode.KEYCODE_I,
        AndroidKeyCode.KEYCODE_J,
        AndroidKeyCode.KEYCODE_K,
        AndroidKeyCode.KEYCODE_L,
        AndroidKeyCode.KEYCODE_M,
        AndroidKeyCode.KEYCODE_N,
        AndroidKeyCode.KEYCODE_O,
        AndroidKeyCode.KEYCODE_P,
        AndroidKeyCode.KEYCODE_Q,
        AndroidKeyCode.KEYCODE_R,
        AndroidKeyCode.KEYCODE_S,
        AndroidKeyCode.KEYCODE_T,
        AndroidKeyCode.KEYCODE_U,
        AndroidKeyCode.KEYCODE_V,
        AndroidKeyCode.KEYCODE_W,
        AndroidKeyCode.KEYCODE_X,
        AndroidKeyCode.KEYCODE_Y,
        AndroidKeyCode.KEYCODE_Z,
    };
    public static int[] KeyCodeNumber = new int[]{
        AndroidKeyCode.KEYCODE_0,
        AndroidKeyCode.KEYCODE_1,
        AndroidKeyCode.KEYCODE_2,
        AndroidKeyCode.KEYCODE_3,
        AndroidKeyCode.KEYCODE_4,
        AndroidKeyCode.KEYCODE_5,
        AndroidKeyCode.KEYCODE_6,
        AndroidKeyCode.KEYCODE_7,
        AndroidKeyCode.KEYCODE_8,
        AndroidKeyCode.KEYCODE_9,
    };

    public static int getKeyCodeLetterByString(String str){
        str = str.toLowerCase();
        if (str.length() >= 1){
            return KeyCodeLetter[str.charAt(0) - 'a'];
        }
        return -1;
    }

    public static int getKeyCodeNumberByString(String str){
        str = str.toLowerCase();
        if (str.length() >= 1){
            return KeyCodeNumber[str.charAt(0) - '0'];
        }
        return -1;
    }
}
