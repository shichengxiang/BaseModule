package com.vce.baselib;

import android.app.Application;

import androidx.lifecycle.Observer;

import com.tencent.mmkv.MMKV;
import com.vce.baselib.network.BaseResponse;
import com.vce.baselib.network.Client;
import com.vce.baselib.utils.Util;

/**
 * 描述：BaseApplication
 * 创建者: shichengxiang
 * 创建时间：2022/3/24
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Util.log("baselib 初始化了");
        Util.init(this);
    }
}
