package com.android.vfinance.custom

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.vce.baselib.base.BaseFragment
import java.lang.ref.WeakReference

/**
 * 基于viewpager 都可以用
 */
abstract class AbstractWrapFragmentAdapter : FragmentStatePagerAdapter {

    var mWeakContext: WeakReference<Context>? = null
    var fragments = SparseArray<BaseFragment<*>>()
    var mTitles= arrayOf<String>()
    var mCurrentView:View?=null

    constructor(context: Context?,titles:Array<String>,manager: FragmentManager) : super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        mWeakContext = WeakReference(context)
        mTitles=titles
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_UNCHANGED
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        mCurrentView=(`object` as BaseFragment<*>).view
    }

    fun getPrimaryItem():View?=mCurrentView


    override fun getCount() = mTitles.size
}