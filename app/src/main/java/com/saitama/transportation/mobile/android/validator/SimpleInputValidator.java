package com.saitama.transportation.mobile.android.validator;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.saitama.transportation.mobile.android.R;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sharezzorama on 10/27/16.
 * Util class for user input validation
 */

public class SimpleInputValidator {

    private static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    /**
     * Returns true if the textView's text is null or 0-length.
     * @param textView the input textView
     * @return true if text is null or zero length
     */
    public static boolean isEmpty(TextView textView, int errorMessageResId) {
        Context context = textView.getContext();
        if (TextUtils.isEmpty(textView.getText())) {
            textView.setError(context.getString(errorMessageResId));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the textView's text has valid email format .
     * @param textView the input textView
     * @return true if text has valid email format
     */
    public static boolean isEmail(TextView textView) {
        return isMatch(textView, EMAIL_PATTERN, R.string.error_wrong_email);
    }

    /**
     * Returns true if the textView's text matches with regular expression .
     * @param textView the input textView
     * @param regEx the regular expression
     * @return true if text has valid email format
     */
    public static boolean isMatch(TextView textView, String regEx, int errorMessageResId) {
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(textView.getText());
        boolean result = m.find();
        if (!result) {
            Context context = textView.getContext();
            textView.setError(context.getString(errorMessageResId));
        }
        return result;
    }

    /**
     * Returns true if the textView's text length equals the specified value
     * @param textView
     * @param length
     * @param errorMessageResId
     * @return
     */
    public static boolean isLengthEq(TextView textView, int length, int errorMessageResId) {
        StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("\\d{");
        patternBuilder.append(length);
        patternBuilder.append("}");
        Pattern p = Pattern.compile(patternBuilder.toString());
        Matcher m = p.matcher(textView.getText());
        boolean result = m.find();
        if (!result) {
            Context context = textView.getContext();
            textView.setError(context.getString(errorMessageResId));
        }
        return result;
    }
}
