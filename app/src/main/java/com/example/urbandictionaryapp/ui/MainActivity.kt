package com.example.urbandictionaryapp.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.SpannableStringBuilder
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.common.listFromJson
import com.example.urbandictionaryapp.common.listToJson
import com.example.urbandictionaryapp.databinding.ActivityMainBinding
import com.example.urbandictionaryapp.viewModel.CustomViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    companion object {
        private const val ET_INPUT_TEXT = "ET_INPUT_TEXT"
        private const val SELECTED_RADIO_BUTTON_ID = "SELECTED_RADIO_BUTTON_ID"
        private const val RV_LIST = "RV_LIST"
    }

    private lateinit var binding: ActivityMainBinding
    private val customViewModel: CustomViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setUpRecyclerView()
        setUpRadioGroup()
        implementCallbacks()

        if (savedInstanceState != null){
            val etText = savedInstanceState.getString(ET_INPUT_TEXT)
            val btnId = savedInstanceState.getInt(SELECTED_RADIO_BUTTON_ID)
            val rvList = savedInstanceState.getString(RV_LIST)
            if(etText != null) etInput.text = SpannableStringBuilder(etText)
            if(btnId != -1) findViewById<RadioButton>(btnId).isChecked = true
            if (rvList != null) (rvDefinitions.adapter as CustomRecyclerViewAdapter).updateAdapter(listFromJson(rvList))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putString(ET_INPUT_TEXT, etInput.text.toString())
            putInt(SELECTED_RADIO_BUTTON_ID, rgSort.checkedRadioButtonId)
            putString(RV_LIST, listToJson((rvDefinitions.adapter as CustomRecyclerViewAdapter).getRecyclerViewList()))
        }
    }

    private fun setUpRadioGroup() {
        rgSort.setOnCheckedChangeListener { radioGroup, _ ->
            run {
                val selected = radioGroup.checkedRadioButtonId
                val recyclerViewList = (rvDefinitions.adapter as CustomRecyclerViewAdapter).getRecyclerViewList()
                if (selected != -1) {
                    val sortedList = when(selected){
                        thumbsUp.id -> {customViewModel.sortDefinitionBy("up", recyclerViewList)}
                        thumbsDown.id -> {customViewModel.sortDefinitionBy("down", recyclerViewList)}
                        else -> recyclerViewList
                    }
                    (rvDefinitions.adapter as CustomRecyclerViewAdapter)
                        .updateAdapter(sortedList)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        rvDefinitions.layoutManager = LinearLayoutManager(this@MainActivity)
        rvDefinitions.adapter = CustomRecyclerViewAdapter(emptyList())
    }

    private fun implementCallbacks() {
        customViewModel.callbacks = object : CustomViewModel.Callbacks {
            override fun clearInputs() {
                etInput.text = SpannableStringBuilder("")
                thumbsUp.isChecked = false
                thumbsDown.isChecked = false
            }

            override fun onBtnSearchClicked() {
                (rvDefinitions.layoutManager as LinearLayoutManager)
                    .scrollToPositionWithOffset(0, 0)
                val definitions = customViewModel.definitions(binding.etInput.text.toString())
                definitions.observe(this@MainActivity, Observer {
                    if (definitions.value?.size ?: 0 > 0) {
                        (rvDefinitions.adapter as CustomRecyclerViewAdapter)
                            .updateAdapter(definitions.value!!)
                    }
                })
            }
        }

        /* ideally this would be in the xml in the onclick but there were some issues with the binding not
        * taking these callbacks, so I put it here for now. If more time I will research some fixes */
        btnSearch.setOnClickListener { (customViewModel.callbacks as CustomViewModel.Callbacks).onBtnSearchClicked() }
        btnClear.setOnClickListener{ (customViewModel.callbacks as CustomViewModel.Callbacks).clearInputs()}
    }
}
