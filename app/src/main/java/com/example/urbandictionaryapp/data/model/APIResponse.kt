package com.example.urbandictionaryapp.data.model

import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("list") val list : List<Definition>
)