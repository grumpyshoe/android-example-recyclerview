package de.grumpyshoe.projecttemplate.common.recyclerview

/**
 * Created by grumpyshoe on 27.11.17.
 */
interface RecyclerViewClickListener<T> {

    fun recyclerViewListClicked(item : T)
}