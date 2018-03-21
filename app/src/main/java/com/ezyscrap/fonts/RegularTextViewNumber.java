package com.ezyscrap.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by bitware on 3/6/17.
 */

public class RegularTextViewNumber extends AppCompatTextView{
    public RegularTextViewNumber(Context context) {
        super(context);
        setFont();
    }

    public RegularTextViewNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RegularTextViewNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    public void setFont(){

        Typeface typedValue = Typeface.createFromAsset(getContext().getAssets(), "lato_regular.ttf");
        setTypeface(typedValue);
    }
}
