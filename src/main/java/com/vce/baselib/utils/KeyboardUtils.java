package com.vce.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public final class KeyboardUtils {
    private static boolean isVisible = false;
    private static int keyboardHeight = 687;

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void registViewTreeObserver(View content, MonitorKeyBoard monitorKeyBoard) {
        content.getViewTreeObserver().addOnGlobalLayoutListener(monitorKeyBoard);
    }

    public static void unRegistViewTreeObserver(View content, MonitorKeyBoard monitorKeyBoard) {
        content.getViewTreeObserver().removeOnGlobalLayoutListener(monitorKeyBoard);
    }

    public static abstract class MonitorKeyBoard implements ViewTreeObserver.OnGlobalLayoutListener {
        WeakReference<View> mV;

        public MonitorKeyBoard(View view) {
            mV = new WeakReference<View>(view);
        }

        @Override
        public void onGlobalLayout() {
            if (mV.get() != null) {
                Rect rect = new Rect();
                mV.get().getWindowVisibleDisplayFrame(rect);
                int resourceId = mV.get().getResources().getIdentifier("status_bar_height", "dimen", "android");
                int statusBarHeight = mV.get().getResources().getDimensionPixelSize(resourceId);
                int navHeight = 0;
                if (Util.isNavigationBarExist((Activity) mV.get().getContext())) {
                    navHeight = Util.getNavigationBarHeight(mV.get().getContext());
                }
                int diff = mV.get().getRootView().getHeight() - (rect.bottom - rect.top) - statusBarHeight;
//                Util.log("status heightt= " + statusBarHeight + "navheight  =" + navHeight);
                isVisible = diff > 200;
                if (isVisible)
                    keyboardHeight = mV.get().getRootView().getHeight() - (rect.bottom - rect.top);// - statusBarHeight - navHeight;
                keyboardState(isVisible);
            }
        }

        public abstract void keyboardState(boolean isShow);
    }

    /*
      ???????????????????????????
      <p>???manifest.xml???activity?????????</p>
      <p>android:windowSoftInputMode="adjustPan"</p>
     */

    /**
     * ?????????????????????
     *
     * @param activity activity
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        boolean b = imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * ?????????????????????
     *
     * @param context ?????????
     * @param view    ??????
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * ???????????????????????????????????????
     * <p>??????EditText???????????????????????????????????????????????????????????????????????????</p>
     * <p>?????????dispatchTouchEvent</p>
     * <p>????????????????????????</p>
     */
    public static void clickBlankArea2HideSoftInput() {
        Log.d("tips", "U should copy the following code.");
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // ??????EditText???????????????????????????????????????????????????????????????????????????
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        */
    }

    /**
     * ?????????????????????
     *
     * @param edit ?????????
     */
    public static void showSoftInput(EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) Util.mApp.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.showSoftInput(edit, 0);
    }

    /**
     * ??????????????????????????????
     */
    public static void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) Util.mApp.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * ???????????????????????????
     *
     * @return
     */
    public static boolean isKeyBoardVisible() {
        return isVisible;
    }

    /**
     * ?????????????????????
     */
    public static int getKeyBoardHeight() {
        return keyboardHeight;
    }

    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**
     * ?????? ?????????????????????
     *
     * @param context
     * @return
     */
    public static int getKeyboardHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);
        Util.log("hhhhheight="+(totalHeight-contentHeight));
        return totalHeight - contentHeight;
    }
}