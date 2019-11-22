package com.example.urbandictionaryapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.data.model.Definition
import com.example.urbandictionaryapp.databinding.ActivityMainBinding
import com.example.urbandictionaryapp.viewModel.CustomViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    companion object {
        private const val ET_INPUT_TEXT = "ET_INPUT_TEXT"
        private const val SELECTED_RADIO_BUTTON_ID = "SELECTED_RADIO_BUTTON_ID"
    }

    private lateinit var binding: ActivityMainBinding
    private val definitionsViewModel: CustomViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setUpRecyclerView()
        setUpRadioGroup()
        setUpProgressBar()

        btnSearch.setOnClickListener { onBtnSearchClicked() }
        btnClear.setOnClickListener { clearInputs() }

        if (savedInstanceState != null) {
            val etText = savedInstanceState.getString(ET_INPUT_TEXT)
            val btnId = savedInstanceState.getInt(SELECTED_RADIO_BUTTON_ID)
            if (etText != null) etInput.text = SpannableStringBuilder(etText)
            if (btnId != -1) findViewById<RadioButton>(btnId).isChecked = true
            updateDefinitionRecyclerView(definitionsViewModel.definitions.value ?: emptyList())
        }
    }

    private fun setUpProgressBar() {
        definitionsViewModel.isLoading.observe(this, Observer {
            binding.progressBar.visibility = changeProgressBarVisibility(it)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putString(ET_INPUT_TEXT, etInput.text.toString())
            putInt(SELECTED_RADIO_BUTTON_ID, rgSort.checkedRadioButtonId)
        }
    }

    private fun setUpRadioGroup() =
        rgSort.setOnCheckedChangeListener { radioGroup, _ ->
            run {
                val selected = radioGroup.checkedRadioButtonId
                val defList = (rvDefinitions.adapter as CustomRecyclerViewAdapter).list
                if (selected != -1) {
                    val sortedList = when (selected) {
                        thumbsUp.id -> {
                            definitionsViewModel.sortDefinitionBy("up", defList)
                        }
                        thumbsDown.id -> {
                            definitionsViewModel.sortDefinitionBy("down", defList)
                        }
                        else -> defList
                    }
                    updateDefinitionRecyclerView(sortedList)
                }
            }
        }

    private fun setUpRecyclerView() {
        rvDefinitions.layoutManager = LinearLayoutManager(this@MainActivity)
        rvDefinitions.adapter = CustomRecyclerViewAdapter(
            definitionsViewModel.definitions.value ?: emptyList()
        )
        definitionsViewModel.definitions.observe(this@MainActivity, Observer {
            updateDefinitionRecyclerView(it ?: emptyList())
        })
    }

    private fun clearInputs() {
        etInput.text = SpannableStringBuilder("")
        thumbsUp.isChecked = false
        thumbsDown.isChecked = false
        updateDefinitionRecyclerView(emptyList())
    }

    private fun onBtnSearchClicked() {
        rvScrollToTop()
        definitionsViewModel.getDefinitions(binding.etInput.text.toString())
    }

    private fun changeProgressBarVisibility(isLoading: Boolean) =
        if (isLoading) View.VISIBLE
        else View.INVISIBLE

    private fun updateDefinitionRecyclerView(newData: List<Definition>) =
        (rvDefinitions.adapter as CustomRecyclerViewAdapter).apply {
            list = newData
            this.notifyDataSetChanged()
        }

    private fun rvScrollToTop() = (rvDefinitions.layoutManager as LinearLayoutManager)
        .scrollToPositionWithOffset(0, 0)

}
