package com.example.urbandictionaryapp.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.urbandictionaryapp.data.model.Definition
import com.example.urbandictionaryapp.data.repo.DefinitionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomViewModel(private val definitionRepo: DefinitionRepo) : ViewModel() {
    var callbacks: Callbacks? = null
    val isLoading = MutableLiveData<Boolean>(false)

    // its best that viewmodel don't know anything about the views, thus the Callbacks
    interface Callbacks {
        fun onBtnSearchClicked()
        fun clearInputs()
    }


    fun definitions(string: String): LiveData<List<Definition>?>{
        changeIsLoadingState()
        Log.d("FETCH ", "START " + isLoading.value.toString())

        val definitionLiveData = liveData(Dispatchers.IO) {
            val definitions = definitionRepo.getDefinitions(string)
            emit(definitions)
        }
        changeIsLoadingState()
        Log.d("FETCH ", "END " + isLoading.value.toString())

        return definitionLiveData
    }

    // change isLoading state, a live data connected to the progress bar in the xml,
    private fun changeIsLoadingState() {
        viewModelScope.launch(Dispatchers.Main) {
            isLoading.value = !(isLoading.value ?: false)
        }
    }

    fun sortDefinitionBy(
        type: String,
        definitionList: List<Definition>
    ): List<Definition> {
        val map = mutableMapOf<Int, Definition>()
        when (type) {
            "up" -> {
                for (d in definitionList) {
                    map[d.thumbsUp] = d
                }
            }
            "down" -> {
                for (d in definitionList) {
                    map[d.thumbsDown] = d
                }
            }
        }
        // sorted map return natural order (increasing), so reverse
        return map.toSortedMap().values.reversed()
    }

}