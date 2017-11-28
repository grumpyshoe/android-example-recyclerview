package de.grumpyshoe.projecttemplate.core.view.recyclerview

import android.view.View

/**
 * Created by grumpyshoe on 27.11.17.
 */
interface RecyclerViewClickListener {

    fun recyclerViewListClicked(v: View, position: Int)
}