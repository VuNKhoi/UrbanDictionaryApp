package com.example.urbandictionaryapp.viewModel

import androidx.lifecycle.*
import com.example.urbandictionaryapp.data.model.Definition
import com.example.urbandictionaryapp.data.repo.DefinitionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CustomViewModel(private val definitionRepo: DefinitionRepo) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>(false)
    val definitions = MutableLiveData<List<Definition>>()

    fun getDefinitions(string: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            changeIsLoadingState()
            definitions.postValue(
                if (string != null) definitionRepo.getDefinitions(string)
                else emptyList()
            )
            changeIsLoadingState()
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