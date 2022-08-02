package com.vce.baselib.network

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 描述：BaseRetrofit
 * 创建者: shichengxiang
 * 创建时间：2022/3/23
 */
class BaseRetrofit<T> {
    private val DEFAULT_TIME = 10L
    var retrofit: Retrofit? = null
    private var server: T? = null
    private var tClass: Class<T>? = null
    companion object{
        var baseHost:String?=""
        fun init(host:String){
            baseHost = host
        }
    }
    constructor(tClass: Class<T>) {
        this.tClass = tClass
        init(tClass)
    }

    private fun init(tClass: Class<T>) {
        //调度器
        var dispatcher = Dispatcher()
        dispatcher.maxRequests = 10
        dispatcher.maxRequestsPerHost = 10
        //拦截器
        var headInterceptor = HeadInterceptor()
        var okHttpClient = OkHttpClient.Builder()
            .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
            .callTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
            .addInterceptor(headInterceptor)
            .dispatcher(dispatcher)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .baseUrl(baseHost)
            .build()
        server = retrofit?.create(tClass)
    }

    public fun getServer(): T {
        if (server == null) {
            server = retrofit?.create(tClass)
        }
        return server!!
    }
}