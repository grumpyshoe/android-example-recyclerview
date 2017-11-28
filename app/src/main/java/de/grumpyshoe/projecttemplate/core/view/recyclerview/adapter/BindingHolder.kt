package de.grumpyshoe.projecttemplate.core.view.recyclerview.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import de.grumpyshoe.projecttemplate.core.view.recyclerview.RecyclerViewClickListener


/**
 * Created by grumpyshoe on 22.11.17.
 *
 * Bindingholder
 */
class BindingHolder(rowView: View, val itemClickListener : RecyclerViewClickListener?) : RecyclerView.ViewHolder(rowView), View.OnClickListener {

    init {
        rowView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            itemClickListener?.recyclerViewListClicked(view, layoutPosition)
        }
    }

    val binding: ViewDataBinding = DataBindingUtil.bind(rowView)

}
