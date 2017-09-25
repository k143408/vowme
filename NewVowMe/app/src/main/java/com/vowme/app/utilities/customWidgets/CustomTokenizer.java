package com.vowme.app.utilities.customWidgets;

import android.text.SpannableStringBuilder;
import android.text.style.MetricAffectingSpan;
import android.text.style.StyleSpan;
import android.widget.MultiAutoCompleteTextView.Tokenizer;

import com.vowme.app.utilities.spannables.BackgroundColorWithoutLineHeightSpan;

public class CustomTokenizer implements Tokenizer {
    public int findTokenStart(CharSequence text, int cursor) {
        int i = cursor - text.length();
        if (!(text instanceof SpannableStringBuilder)) {
            return i;
        }
        SpannableStringBuilder textSpan = (SpannableStringBuilder) text;
        MetricAffectingSpan[] list = (MetricAffectingSpan[]) textSpan.getSpans(0, text.length(), MetricAffectingSpan.class);
        int end = -1;
        if (list.length > 0) {
            end = textSpan.getSpanEnd(list[list.length - 1]);
        }
        if (end > 0) {
            return end;
        }
        return i;
    }

    public int findTokenEnd(CharSequence text, int cursor) {
        return text.length();
    }

    public CharSequence terminateToken(CharSequence text) {
        text = " " + text + " ";
        int end = text.length();
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(new String(text.toString()));
        stringBuilder.setSpan(new BackgroundColorWithoutLineHeightSpan(-3355444), 0, end, 33);
        stringBuilder.append(" ");
        stringBuilder.setSpan(new StyleSpan(1), end, end + 1, 33);
        return stringBuilder;
    }
}
