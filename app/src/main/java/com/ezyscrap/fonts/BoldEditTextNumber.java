package com.ezyscrap.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by bitware on 26/12/17.
 */

public class BoldEditTextNumber extends AppCompatEditText {
    public BoldEditTextNumber(Context context) {
        super(context);
        setFont();
    }

    public BoldEditTextNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public BoldEditTextNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    public void setFont() {

        Typeface typedValue = Typeface.createFromAsset(getContext().getAssets(), "lato_bold.ttf");
        setTypeface(typedValue);
    }
}
