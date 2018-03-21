package com.ezyscrap.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by bitware on 3/6/17.
 */

public class RegularTextView extends AppCompatTextView{
    public RegularTextView(Context context) {
        super(context);
        setFont();
    }

    public RegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    public void setFont(){

        Typeface typedValue = Typeface.createFromAsset(getContext().getAssets(), "raleway_regular.ttf");
        setTypeface(typedValue);
    }
}
