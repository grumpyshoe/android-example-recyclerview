package de.grumpyshoe.projecttemplate.features.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import de.grumpyshoe.projecttemplate.BR
import de.grumpyshoe.projecttemplate.R


/**
 * Created by grumpyshoe on 22.11.17.
 *
 * RecaclerView adapter for any kind of Items
 */
class RecyclerViewAdapter(var items : MutableList<Any>? = mutableListOf()) : RecyclerView.Adapter<BindingHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_contributor_item, parent, false)
        return BindingHolder(v)
    }


    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.binding.setVariable(BR.item, items?.get(position))
        holder.binding.executePendingBindings()
    }


    override fun getItemCount(): Int {
        return items!!.size
    }

}