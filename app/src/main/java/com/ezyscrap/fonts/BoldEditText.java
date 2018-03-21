package com.ezyscrap.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by bitware on 26/12/17.
 */

public class BoldEditText extends AppCompatEditText {
    public BoldEditText(Context context) {
        super(context);
        setFont();
    }

    public BoldEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public BoldEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    public void setFont() {

        Typeface typedValue = Typeface.createFromAsset(getContext().getAssets(), "raleway_bold.ttf");
        setTypeface(typedValue);
    }
}
