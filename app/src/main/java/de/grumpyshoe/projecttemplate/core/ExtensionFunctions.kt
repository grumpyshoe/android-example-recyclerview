package de.grumpyshoe.projecttemplate.core

import android.content.Context
import android.widget.Toast

/**
 * Created by grumpyshoe on 28.11.17.
 */

inline fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
