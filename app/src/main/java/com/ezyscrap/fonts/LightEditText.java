package com.ezyscrap.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by bitware on 3/6/17.
 */

public class LightEditText extends AppCompatEditText {
    public LightEditText(Context context) {
        super(context);
        setFont();
    }

    public LightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public LightEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    public void setFont(){

        Typeface typedValue = Typeface.createFromAsset(getContext().getAssets(), "raleway_light.ttf");
        setTypeface(typedValue);
    }
}
