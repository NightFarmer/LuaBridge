package com.nightfarmer.luabridge.sample;

/**
 * Created by zhangfan on 16-8-9.
 */
public class ObjectAnimator {

    public static android.animation.ObjectAnimator ofFloat(Object target, String propertyName,float v1,float v2) {
        android.animation.ObjectAnimator objectAnimator = android.animation.ObjectAnimator.ofFloat(target, propertyName, v1, v2);
//        objectAnimator.setFloatValues(v1, v2);
        return objectAnimator;
    }
}
