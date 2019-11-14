package com.example.urbandictionaryapp.common

import com.example.urbandictionaryapp.data.model.Definition

fun createDefinitionList(word: String) =
    listOf<Definition>(
        Definition("definition",
        "permalink",
        1,
        listOf("Url1", "Url2"),
            "author",
            word,
            0,
            "currentVote",
            "writtenOn",
            "example",
            1)
    )