package com.example.urbandictionaryapp

import com.example.urbandictionaryapp.common.createDefinitionList
import com.example.urbandictionaryapp.data.repo.DefinitionRepo
import com.example.urbandictionaryapp.viewModel.CustomViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.ArgumentMatchers.anyString

import java.lang.Exception

@RunWith(JUnit4::class)
class UnitTest {

    @Mock
    private lateinit var repo: DefinitionRepo
    private lateinit var viewModel: CustomViewModel


    @ExperimentalCoroutinesApi
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Default)
        viewModel = CustomViewModel(repo)
    }

    @Test
    fun givenRepositoryFetch_Success_LiveDataHasValue() {
        CoroutineScope(Dispatchers.Main).launch {
            val word = "word"
            val defList = createDefinitionList(word)
            Mockito.`when`(repo.getDefinitions(anyString())).thenReturn(defList)

            assert(viewModel.definitions(word).value == defList)
        }
    }
}