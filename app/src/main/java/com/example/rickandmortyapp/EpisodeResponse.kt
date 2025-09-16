package com.example.rickandmortyapp

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("results") val results: List<Episode>
)