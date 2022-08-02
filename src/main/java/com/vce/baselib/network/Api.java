package com.vce.baselib.network;

import androidx.lifecycle.LiveData;

import retrofit2.http.GET;

/**
 * 描述：Api
 * 创建者: shichengxiang
 * 创建时间：2022/3/23
 */
interface Api {
    @GET
    MyLiveData<BaseResponse<Object>> login();
}
