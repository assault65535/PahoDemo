package com.tnecesoc.pahodemo.Util;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by Tnecesoc on 2016/9/8.
 */
public class ClauseFormatter {

    public static String create(String format, Object... attrs) {

        StringBuilder sb = new StringBuilder();

        new Formatter(sb, Locale.getDefault()).format(format, attrs);

        return sb.toString();
    }

}
