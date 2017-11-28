package de.grumpyshoe.projecttemplate.features.main.view

import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import de.grumpyshoe.projecttemplate.R
import de.grumpyshoe.projecttemplate.core.view.recyclerview.adapter.RecyclerViewAdapter
import de.grumpyshoe.projecttemplate.databinding.ActivityMainBinding
import de.grumpyshoe.projecttemplate.features.main.viewmodel.MainViewModel



/**
 * Created by grumpyshoe on 13.11.17.
 *
 * Main Activity, loaded on app start
 *
 */
class MainActivity : AppCompatActivity() {


    /**
     * onCreate
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create adapter
        val adapter  = RecyclerViewAdapter(R.layout.recyclerview_item)

        // create/init binding
        val binding = setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // set viewmodel to binding
        binding.viewmodel = MainViewModel(adapter)

        // init recyclerview
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}
