package de.grumpyshoe.projecttemplate.features.main.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.RadioGroup
import de.grumpyshoe.projecttemplate.R
import de.grumpyshoe.projecttemplate.core.dagger.Injector
import de.grumpyshoe.projecttemplate.core.repository.Callback
import de.grumpyshoe.projecttemplate.core.repository.RepositoryManager
import de.grumpyshoe.projecttemplate.core.repository.model.Post
import de.grumpyshoe.projecttemplate.core.toast
import de.grumpyshoe.projecttemplate.core.view.recyclerview.RecyclerViewClickListener
import de.grumpyshoe.projecttemplate.core.view.recyclerview.adapter.RecyclerViewAdapter
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by grumpyshoe on 13.11.17.
 *
 * MainViewModel contains all logic for interacting with or during the UI
 *
 */
class MainViewModel(val adapter: RecyclerViewAdapter) : BaseObservable() {

    // public fields
    @Inject lateinit var repositoryManager: RepositoryManager
    @Inject lateinit var context: Context
    val noData = ObservableBoolean(false)
    val error = ObservableBoolean(false)
    val isLoading = ObservableBoolean(false)
    val updateDataFinished = ObservableBoolean(false)
    val layoutAnimationController = ObservableField<LayoutAnimationController>()
    val layoutManager = ObservableField<LinearLayoutManager>()

    // private fields
    private var errorText: String = ""
    private var layoutManagerLinear : LinearLayoutManager
    private var layoutManageGrid : GridLayoutManager
    private var animationTop : LayoutAnimationController
    private var animationBottom : LayoutAnimationController
    private var animationLeft : LayoutAnimationController
    private var animationRight : LayoutAnimationController


    /**
     * init
     *
     */
    init {
        Injector.INSTANCE.get().inject(this)

        layoutManagerLinear = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        layoutManageGrid = GridLayoutManager(context, 3)

        animationTop = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_top)
        animationBottom = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
        animationLeft = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_left)
        animationRight = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right)

        layoutAnimationController.set(animationTop)
        layoutManager.set(layoutManagerLinear)

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
        adapter.items.clear()
        adapter.notifyDataSetChanged()

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
                onErrorResult(code, errorBody)
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
        adapter.items = result.toMutableList()
        isLoading.set(false)
        noData.set(false)
        updateDataFinished.set(true)
    }


    /**
     * handle error
     *
     */
    internal fun onErrorResult(code: Int, errorBody: ResponseBody?) {
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
        when (checkedId) {
            R.id.from_top -> layoutAnimationController.set(animationTop)
            R.id.from_left ->layoutAnimationController.set(animationLeft)
            R.id.from_bottom -> layoutAnimationController.set(animationBottom)
            R.id.from_right -> layoutAnimationController.set(animationRight)
            else -> layoutAnimationController.set(animationTop)
        }

        // reload data
        loadPosts(false)

    }


    /**
     * click listener for recyclerview items
     *
     */
    val itemClickListener = object : RecyclerViewClickListener {
        override fun recyclerViewListClicked(v: View, position: Int) {
            val post = adapter.getItem (position) as Post
            "position : $position:\ntitle:${post.title}".toast(context)
        }
    }


    /**
     * set layoutmanager for recyclerview
     *
     */
    val radioGrpChangeLayout= RadioGroup.OnCheckedChangeListener { group, checkedId ->

        // set layout animation according to selection
        val anim: Int
        when (checkedId) {
            R.id.layout_linear -> layoutManager.set(layoutManagerLinear)
            R.id.layout_grid ->layoutManager.set(layoutManageGrid)
        }

    }


}