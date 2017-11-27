package de.grumpyshoe.projecttemplate.features.main.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.v4.widget.SwipeRefreshLayout
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.RadioGroup
import de.grumpyshoe.projecttemplate.R
import de.grumpyshoe.projecttemplate.core.dagger.Injector
import de.grumpyshoe.projecttemplate.core.repository.Callback
import de.grumpyshoe.projecttemplate.core.repository.RepositoryManager
import de.grumpyshoe.projecttemplate.core.repository.model.Post
import de.grumpyshoe.projecttemplate.features.main.adapter.RecyclerViewAdapter
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by grumpyshoe on 13.11.17.
 *
 * MainViewModel contains all logic for interacting with or during the UI
 */
class MainViewModel(val recyclerViewAdapter: RecyclerViewAdapter) : BaseObservable() {

    // public fields
    @Inject lateinit var repositoryManager: RepositoryManager
    @Inject lateinit var context: Context
    val noData = ObservableBoolean(false)
    val error = ObservableBoolean(false)
    val isLoading = ObservableBoolean(false)
    val updateDataFinished = ObservableBoolean(false)
    val layoutAnimationController = ObservableField<LayoutAnimationController>()

    // private fields
    private var errorText: String = ""


    /**
     * init
     *
     */
    init {
        Injector.INSTANCE.get().inject(this)

        layoutAnimationController.set(AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_top))

        loadPosts()
    }


    /**
     * just a dummy method to show how to use RepositoryManager
     *
     */
    private fun loadPosts(forceDbClean: Boolean = false) {

        // reset view state
        isLoading.set(true)
        error.set(false)
        noData.set(false)
        updateDataFinished.set(false)

        // clear prvious data
        recyclerViewAdapter.items?.clear()
        recyclerViewAdapter.notifyDataSetChanged()

        // load data and handle result
        repositoryManager.getPosts(forceDbClean, object : Callback<List<Post>> {

            override fun onResult(result: List<Post>) {
                if (result.isEmpty()) {
                    onEmptyResult()
                } else {
                    onDataResult(result)
                }
            }

            override fun onError(throwable: Throwable?, code: Int, errorBody: ResponseBody?) {
                onErrorResult(throwable, code, errorBody)
            }

        })
    }


    /**
     * load data from webservice and refresh database
     *
     */
    val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {

        loadPosts(true)

    }


    /**
     * handle success
     *
     */
    internal fun onEmptyResult() {
        noData.set(true)
        isLoading.set(false)
        updateDataFinished.set(true)
    }


    /**
     * handle success
     *
     */
    internal fun onDataResult(result: List<Post>) {
        recyclerViewAdapter.items = result.toMutableList()
        isLoading.set(false)
        noData.set(false)
        updateDataFinished.set(true)
    }


    /**
     * handle error
     *
     */
    internal fun onErrorResult(throwable: Throwable?, code: Int, errorBody: ResponseBody?) {
        errorText = "HTTP CODE : " + code.toString() + " - " + errorBody?.string()
        error.set(true)
        noData.set(false)
        isLoading.set(false)
        updateDataFinished.set(true)
    }


    /**
     * get error text
     *
     */
    fun errorText(): String {
        return errorText
    }


    /**
     *  listener for changes on radiogroup
     *
     */
    val radioGrpChangeListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->

        // set layout animation according to selection
        val anim: Int
        when (checkedId) {
            R.id.from_top -> anim = R.anim.layout_animation_from_top
            R.id.from_left -> anim = R.anim.layout_animation_from_left
            R.id.from_bottom -> anim = R.anim.layout_animation_from_bottom
            R.id.from_right -> anim = R.anim.layout_animation_from_right
            else -> anim = R.anim.layout_animation_from_top
        }
        layoutAnimationController.set(AnimationUtils.loadLayoutAnimation(context, anim))

        // reload data
        loadPosts(false)

    }


}