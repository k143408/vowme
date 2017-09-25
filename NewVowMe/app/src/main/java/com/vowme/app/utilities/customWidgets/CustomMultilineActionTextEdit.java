package com.vowme.app.utilities.customWidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

public class CustomMultilineActionTextEdit extends EditText {
    public CustomMultilineActionTextEdit(Context context) {
        super(context);
    }

    public CustomMultilineActionTextEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMultilineActionTextEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= -1073741825;
        return conn;
    }
}
