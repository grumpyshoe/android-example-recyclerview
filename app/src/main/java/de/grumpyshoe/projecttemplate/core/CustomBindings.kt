package de.grumpyshoe.projecttemplate.core

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.LayoutAnimationController

/**
 * Created by grumpyshoe on 16.11.17.+
 *
 * Custom BindingAdapter to handle all kind of 'glue-code'
 */
object CustomBindings {


    /**
     * handle visibiliy according to boolean
     *
     */
    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setImageUrl(view: View, visibility: Boolean) {
        if (visibility) {
            view.visibility = View.VISIBLE
        }
        else {
            view.visibility = View.GONE
        }
    }


    /**
     * add onRefreshListener to SwipeRefreshLayout
     *
     */
    @JvmStatic
    @BindingAdapter("android:onRefreshListener")
    fun setOnRefreshListener(view: SwipeRefreshLayout, onRefreshListener: SwipeRefreshLayout.OnRefreshListener){
        view.setOnRefreshListener(onRefreshListener)
    }


    /**
     * set flag for refresh state to SwipeRefreshLayout
     *
     */
    @JvmStatic
    @BindingAdapter("android:setRefreshing")
    fun setRefreshing(view: SwipeRefreshLayout, refreshing: Boolean){
        view.isRefreshing = refreshing
    }


    /**
     * handle layout animation if adapter has been updated
     *
     */
    @JvmStatic
    @BindingAdapter("android:adapterUpdated")
    fun isAdapterUpdated(view: RecyclerView,updated: Boolean){
        if(updated){
            view.scheduleLayoutAnimation()
        }
    }


    /**
     * set LayoutAnimationController to RecyclerView
     *
     */
    @JvmStatic
    @BindingAdapter("android:layoutAnimationController")
    fun isAdapterUpdated(view: RecyclerView,animation: LayoutAnimationController){
        view.layoutAnimation = animation
    }
}