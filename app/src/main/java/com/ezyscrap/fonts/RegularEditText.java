package com.ezyscrap.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by bitware on 3/6/17.
 */

public class RegularEditText extends AppCompatEditText{

    public RegularEditText(Context context) {
        super(context);
        setFont();
    }

    public RegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    public void setFont(){

        Typeface typedValue = Typeface.createFromAsset(getContext().getAssets(), "raleway_regular.ttf");
        setTypeface(typedValue);
    }
}
