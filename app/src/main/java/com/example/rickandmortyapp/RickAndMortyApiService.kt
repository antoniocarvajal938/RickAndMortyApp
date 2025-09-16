package com.example.rickandmortyapp
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("api/character")
    suspend fun getCharacters(): Response<CharacterResponse>

    @GET("api/episode")
    suspend fun getEpisodes(): Response<EpisodeResponse>
}