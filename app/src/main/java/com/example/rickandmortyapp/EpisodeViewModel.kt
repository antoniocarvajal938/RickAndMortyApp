package com.example.rickandmortyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodeViewModel(private val repository: EpisodeRepository) : ViewModel() {

    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    init {
        getEpisodes()
    }

    private fun getEpisodes() {
        viewModelScope.launch {
            _episodes.value = repository.getEpisodes()
        }
    }
}