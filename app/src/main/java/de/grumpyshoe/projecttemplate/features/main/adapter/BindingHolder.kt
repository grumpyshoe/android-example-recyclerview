package de.grumpyshoe.projecttemplate.features.main.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by grumpyshoe on 22.11.17.
 *
 * Bindingholder
 */
class BindingHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {

    val binding: ViewDataBinding = DataBindingUtil.bind(rowView)

}