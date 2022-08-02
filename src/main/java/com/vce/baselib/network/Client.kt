package com.vce.baselib.network

import androidx.lifecycle.LiveData
import java.util.*

/**
 * 描述：Client
 * 创建者: shichengxiang
 * 创建时间：2022/3/22
 */
class Client {
    companion object{
        @JvmStatic
        val instance:Client by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Client() }
//        @JvmStatic
//        fun get()= lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Client }
    }
    private var retrofit:BaseRetrofit<Api>?=null
    private fun getServer():Api{
        if(retrofit==null){
            retrofit= BaseRetrofit(Api::class.java)
        }
        return retrofit!!.getServer()
    }
    //登录
    fun login():MyLiveData<BaseResponse<Any>> = getServer().login()
}