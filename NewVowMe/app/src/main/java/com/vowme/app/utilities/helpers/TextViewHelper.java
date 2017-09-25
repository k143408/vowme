package com.vowme.app.utilities.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.facebook.appevents.AppEventsConstants;
import com.vowme.app.utilities.spannables.CustomClickableSpan;
import com.vowme.vol.app.R;

public class TextViewHelper {
    private static final String FOR = "for";

    public static void formatOppForSubtitle(Context context, TextView textView, String orgaName, String cause, CustomClickableSpan span) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(FOR);
        stringBuilder.append(" ");
        stringBuilder.append(orgaName);
        stringBuilder.append(" - ");
        stringBuilder.append(cause);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorAccent)), 0, FOR.length(), 33);
        stringBuilder.setSpan(new TypefaceSpan("serif"), 0, FOR.length(), 33);
        stringBuilder.setSpan(new StyleSpan(2), 0, FOR.length(), 33);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_light)), FOR.length() + 1, (FOR.length() + 1) + orgaName.length(), 33);
        stringBuilder.setSpan(new StyleSpan(1), FOR.length() + 1, (FOR.length() + 1) + orgaName.length(), 33);
        if (span != null) {
            stringBuilder.setSpan(span, FOR.length() + 1, (FOR.length() + 1) + orgaName.length(), 33);
        }
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.sec_icon_light)), (FOR.length() + 1) + orgaName.length(), stringBuilder.length(), 33);
        textView.setText(stringBuilder);
    }

    public static void formatTermsPrivacy(Context context, TextView textView, CustomClickableSpan termSpan, CustomClickableSpan privacySpan) {
        String part1 = "By creating an account, I accept " + context.getResources().getString(R.string.app_name) + "'s ";
        String terms = "Terms of Service";
        String part2 = " and ";
        String privacy = "Privacy Policy";
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(part1);
        stringBuilder.append(terms);
        stringBuilder.append(part2);
        stringBuilder.append(privacy);
        stringBuilder.append(".");
        stringBuilder.setSpan(new UnderlineSpan(), part1.length(), part1.length() + terms.length(), 33);
        if (termSpan != null) {
            stringBuilder.setSpan(termSpan, part1.length(), part1.length() + terms.length(), 33);
        }
        stringBuilder.setSpan(new UnderlineSpan(), (part1.length() + terms.length()) + part2.length(), ((part1.length() + terms.length()) + part2.length()) + privacy.length(), 33);
        if (privacySpan != null) {
            stringBuilder.setSpan(privacySpan, (part1.length() + terms.length()) + part2.length(), ((part1.length() + terms.length()) + part2.length()) + privacy.length(), 33);
        }
        textView.setText(stringBuilder);
    }

    public static void formatAvaibiliTySentence(Context context, TextView textView, String day, String when, String partOfDay) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(day);
        stringBuilder.append(" ");
        stringBuilder.append(when);
        stringBuilder.append(" ");
        stringBuilder.append(partOfDay);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_light)), 0, day.length(), 33);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.sec_icon_light)), day.length() + 1, (day.length() + 1) + when.length(), 33);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_light)), (day.length() + 1) + when.length(), stringBuilder.length(), 33);
        textView.setText(stringBuilder);
    }

    public static void breakManually(TextView tv, Editable editable) {
        int width = (tv.getWidth() - tv.getPaddingLeft()) - tv.getPaddingRight();
        if (width != 0) {
            float[] widths = new float[editable.length()];
            tv.getPaint().getTextWidths(editable.toString(), widths);
            float curWidth = 0.0f;
            int lastWSPos = -1;
            int strPos = 0;
            String newLineStr = "\n";
            boolean reset = false;
            int insertCount = 0;
            while (strPos < editable.length()) {
                curWidth += widths[strPos];
                char curChar = editable.charAt(strPos);
                if (curChar == '\n') {
                    reset = true;
                } else if (Character.isWhitespace(curChar)) {
                    lastWSPos = strPos;
                } else if (curWidth > ((float) width) && lastWSPos >= 0) {
                    editable.replace(lastWSPos, lastWSPos + 1, "\n");
                    insertCount++;
                    strPos = lastWSPos;
                    lastWSPos = -1;
                    reset = true;
                }
                if (reset) {
                    curWidth = 0.0f;
                    reset = false;
                }
                strPos++;
            }
            if (insertCount != 0) {
                tv.setText(editable);
            }
        }
    }

    public static String getHourTimeToString(Integer time) {
        if (time == null) {
            return "00";
        }
        return time.intValue() == 0 ? "00" : time.toString();
    }

    public static String getMinuteTimeToString(Integer time) {
        if (time == null) {
            return "00";
        }
        return time.intValue() < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + time.toString() : time.toString();
    }

    public static SpannableStringBuilder formatOppForExperience(Context context, String oppName, String orgaName) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(oppName + " ");
        stringBuilder.append(FOR);
        stringBuilder.append(" ");
        stringBuilder.append(orgaName);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorAccent)), oppName.length() + 1, (FOR.length() + oppName.length()) + 1, 33);
        stringBuilder.setSpan(new TypefaceSpan("serif"), oppName.length() + 1, (FOR.length() + oppName.length()) + 1, 33);
        stringBuilder.setSpan(new StyleSpan(2), oppName.length() + 1, (FOR.length() + oppName.length()) + 1, 33);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_light)), (oppName.length() + FOR.length()) + 2, ((oppName.length() + FOR.length()) + 2) + orgaName.length(), 33);
        stringBuilder.setSpan(new StyleSpan(1), (oppName.length() + FOR.length()) + 2, ((oppName.length() + FOR.length()) + 2) + orgaName.length(), 33);
        return stringBuilder;
    }
}
