package com.first.dlam.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * 判断手势缩放：event.getPointCount
 * 返回值大于等于2就表示缩放
 * 这个时候webview.requestDisallowInterceptTouchEVent(true) 拦截事件
 * 否则
 * webview.requestDisallowInterceptTouchEVent(false)
 */
public class ScrollWebView extends WebView {
    private float startx;
    private float starty;
    private float offsetx;
    private float offsety;

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                startx = event.getX();
                starty = event.getY();
                Log.e("MotionEvent", "webview按下");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("MotionEvent", "webview滑动");
                offsetx = Math.abs(event.getX() - startx);
                offsety = Math.abs(event.getY() - starty);
                if (offsetx > offsety) {  // ||event.getPointCount>=2
                    getParent().requestDisallowInterceptTouchEvent(true);
                    Log.e("MotionEvent", "屏蔽了父控件");
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    Log.e("MotionEvent", "事件传递给父控件");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
