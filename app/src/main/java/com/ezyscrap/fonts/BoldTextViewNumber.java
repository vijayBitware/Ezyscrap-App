package com.ezyscrap.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by bitware on 26/12/17.
 */

public class BoldTextViewNumber extends AppCompatTextView {
    public BoldTextViewNumber(Context context) {
        super(context);
        setFont();
    }

    public BoldTextViewNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public BoldTextViewNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    public void setFont(){

        Typeface typedValue = Typeface.createFromAsset(getContext().getAssets(), "lato_bold.ttf");
        setTypeface(typedValue);
    }
}
