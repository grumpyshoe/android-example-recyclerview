package de.grumpyshoe.projecttemplate.common.recyclerview

import android.databinding.BaseObservable

/**
 * Created by thomas on 15.12.17.
 */
abstract class  ItemViewmodel<T>(internal val isSelected: Boolean = false) : BaseObservable() {

    abstract fun setItem(newItem: T)
    abstract fun setSelected(selected: Boolean)
}