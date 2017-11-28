package de.grumpyshoe.projecttemplate.core.view.recyclerview.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import de.grumpyshoe.projecttemplate.BR
import de.grumpyshoe.projecttemplate.core.view.recyclerview.RecyclerViewClickListener


/**
 * Created by grumpyshoe on 22.11.17.
 *
 * RecaclerView adapter for any kind of Items
 */
class RecyclerViewAdapter(@LayoutRes val item_layout : Int, var items : MutableList<Any> = mutableListOf()) : RecyclerView.Adapter<BindingHolder>() {


    var itemClickListener : RecyclerViewClickListener? = null;


    /**
     * create holder
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val v = LayoutInflater.from(parent.getContext()).inflate(item_layout, parent, false)
        return BindingHolder(v, itemClickListener)
    }


    /**
     * bind holder to data
     *
     */
    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.binding.setVariable(BR.item, items.get(position))
        holder.binding.executePendingBindings()
    }


    /**
     * get item count
     *
     */
    override fun getItemCount(): Int {
        return items.size
    }


    /**
     * get item for position
     *
     */
    fun getItem(position: Int) : Any {

        if(position > items.size-1 || position < 0){
            throw IndexOutOfBoundsException("$position is not available. There are $items.size items available}")
        }
        return items[position]
    }

}