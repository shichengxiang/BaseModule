package com.vce.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * 描述：BaseActivity
 * 创建者: shichengxiang
 * 创建时间：2022/8/2
 */
abstract class BaseActivity<T:ViewBinding>: AppCompatActivity() {
    var bind:T?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass
        if(type is ParameterizedType){
            val clz = type.actualTypeArguments[0] as Class<T>
            val method = clz.getMethod("inflate", LayoutInflater::class.java)
            bind = method.invoke(null,layoutInflater) as T
        }
        setContentView(bind?.root)
        initView(savedInstanceState)
        loadData()
    }
    abstract fun initView(bundle: Bundle?)
    abstract fun loadData()
}