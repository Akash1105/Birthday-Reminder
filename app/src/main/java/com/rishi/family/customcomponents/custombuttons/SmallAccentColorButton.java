package com.rishi.family.customcomponents.custombuttons;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.rishi.family.R;
import com.rishi.family.utilities.UIFonts;


public class SmallAccentColorButton extends android.support.v7.widget.AppCompatButton implements View.OnTouchListener {
    private Typeface mTypeface;

    public SmallAccentColorButton(Context context) {

        super(context);
    }

    public SmallAccentColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);


        UIFonts objectUIFonts = new UIFonts();
        if (isEnabled()) {
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            setTransformationMethod(null);
            setTypeface(context, objectUIFonts);
            //If padding not set to zero the button gets cut to half when screen resizing in login-activity.
            setPadding(0, 0, 0, 0);

            this.setOnTouchListener(this);

        }
    }


    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.setOnTouchListener(this);
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {


        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {


        }
        return false;
    }


    public void setTypeface(Context context, UIFonts objectUIFonts) {
        mTypeface = Typeface.createFromAsset(context.getAssets(),
                objectUIFonts.getFontPathRegular());
        if (mTypeface != null)
            setTypeface(mTypeface);
    }
}
