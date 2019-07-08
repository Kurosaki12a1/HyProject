package com.hy.project;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class ClearableAutoCompleteEditText extends AppCompatAutoCompleteTextView {

    private Drawable drawableEnd = null;

    private Drawable imgClearButton ;

    private Drawable imgSearchButton ;

    public ClearableAutoCompleteEditText(Context context) {
        super(context);
        imgClearButton = getResources().getDrawable(R.drawable.ic_close);
        imgSearchButton = getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp);
        initButtonClear();

    }

    public ClearableAutoCompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        imgClearButton = getResources().getDrawable(R.drawable.ic_close);
        imgSearchButton = getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp);
        initButtonClear();

    }

    public ClearableAutoCompleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imgClearButton = getResources().getDrawable(R.drawable.ic_close);
        imgSearchButton = getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp);
        initButtonClear();
    }

    private void initButtonClear() {
        setCompoundDrawablesWithIntrinsicBounds(imgSearchButton, null, null, null);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        drawableEnd = right;
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float touchX = event.getX();
            float touchY = event.getY();
            if (drawableEnd != null) {
                if (touchX > getWidth() - drawableEnd.getBounds().right - 2 * getPaddingEnd()
                        && touchX <= getWidth() && touchY >= 0 && touchY <= getHeight()) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(text.length()>0){
            setCompoundDrawablesWithIntrinsicBounds(imgSearchButton, null, imgClearButton, null);
        }
        else{
            setCompoundDrawablesWithIntrinsicBounds(imgSearchButton, null, null, null);
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
}
