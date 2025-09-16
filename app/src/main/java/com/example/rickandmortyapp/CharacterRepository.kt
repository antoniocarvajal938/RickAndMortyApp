package com.example.rickandmortyapp
import com.example.rickandmortyapp.network.RetrofitInstance



class CharacterRepository {
    private val apiService = RetrofitInstance.apiService

    suspend fun getCharacters(): List<Character> {
        val response = apiService.getCharacters()
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }
}