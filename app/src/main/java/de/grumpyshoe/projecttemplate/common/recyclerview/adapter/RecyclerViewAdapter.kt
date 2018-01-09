package de.grumpyshoe.projecttemplate.core.view.recyclerview2

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.grumpyshoe.projecttemplate.BR
import de.grumpyshoe.projecttemplate.common.recyclerview.ItemViewmodel
import de.grumpyshoe.projecttemplate.common.recyclerview.RecyclerViewClickListener
import de.grumpyshoe.projecttemplate.databinding.RecyclerviewItemBinding


/**
 * Created by thomas on 26.12.17.
 */
class RecyclerViewAdapter<T>(val layoutResId: Int, val viewModel: ItemViewmodel<T>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder<T>>() {

    var items: List<T> = mutableListOf<T>()
    //    private lateinit var viewModel: ItemViewmodel<T>
    var itemClickListener: RecyclerViewClickListener<T?>? = null;


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder<T> {
        val binding = DataBindingUtil.inflate<RecyclerviewItemBinding>(LayoutInflater.from(parent?.context), layoutResId, parent, false)
        val vm =  viewModel::javaClass.get().newInstance()
        binding.setVariable(BR.viewmodel, vm)
        return ItemViewHolder<T>(vm as ItemViewmodel<T>, binding.root, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        if (items.isNotEmpty()) {
//            viewModel.setItem(items[position])
            holder.setItem(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    /**
     * get item for position
     *
     */
    fun getItem(position: Int): T {

        if (position > items.size - 1 || position < 0) {
            throw IndexOutOfBoundsException("$position is not available. There are $items.size items available}")
        }
        return items[position]
    }

    class ItemViewHolder<T>(val viewModel: ItemViewmodel<T>, itemView: View, val itemClickListener: RecyclerViewClickListener<T?>?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var item : T? = null

        init {
            itemView.setOnClickListener(this)
        }

        internal fun setItem(item: T) {
            this.item = item
            viewModel.setItem(item)
        }

        override fun onClick(view: View?) {
            if (view != null) {
                itemClickListener?.recyclerViewListClicked(item)
            }
        }
    }
}