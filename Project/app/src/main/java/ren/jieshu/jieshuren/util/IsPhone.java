package ren.jieshu.jieshuren.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by laomaotao on 2017/9/24.
 */

public class IsPhone {
    public static boolean isPhone(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
