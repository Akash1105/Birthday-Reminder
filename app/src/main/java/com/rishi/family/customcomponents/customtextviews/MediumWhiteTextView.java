package com.rishi.family.customcomponents.customtextviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.rishi.family.R;
import com.rishi.family.utilities.UIFonts;


public class MediumWhiteTextView extends android.support.v7.widget.AppCompatTextView {
    private Typeface mTypeface;

    public MediumWhiteTextView(Context context) {

        super(context);
    }

    public MediumWhiteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);


        UIFonts objectUIFonts = new UIFonts();
        if (isEnabled()) {
            setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            setTypeface(context, objectUIFonts);
        }
    }


    public void setTypeface(Context context, UIFonts objectUIFonts) {
        mTypeface = Typeface.createFromAsset(context.getAssets(),
                objectUIFonts.getFontPathRegular());
        if (mTypeface != null)
            setTypeface(mTypeface);
    }
}

