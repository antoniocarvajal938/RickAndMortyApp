package com.example.rickandmortyapp

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import androidx.compose.ui.res.painterResource
import com.example.rickandmortyapp.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomAppBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentDestination?.route == Screens.Characters.route,
                                    onClick = {
                                        navController.navigate(Screens.Characters.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(Icons.Filled.Person, contentDescription = "Personajes")
                                    },
                                    label = { Text("Personajes") }
                                )
                                NavigationBarItem(
                                    selected = currentDestination?.route == Screens.Episodes.route,
                                    onClick = {
                                        navController.navigate(Screens.Episodes.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(Icons.Filled.List, contentDescription = "Episodios")
                                    },
                                    label = { Text("Episodios") }
                                )
                            }
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Characters.route,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Screens.Characters.route) {
                            CharacterListScreen()
                        }
                        composable(Screens.Episodes.route) {
                          EpisodeListScreen()  // Aquí irá la nueva pantalla de episodios
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CharacterListScreen(
        viewModel: CharacterViewModel = viewModel(
            factory = CharacterViewModelFactory(
                CharacterRepository()
            )
        )
    ) {
        val characters by viewModel.characters.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (characters.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(characters) { character ->
                        CharacterItem(character = character)
                    }
                }
            }
        }
    }

    @Composable
    fun CharacterItem(character: Character) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = "Imagen de ${character.name}",
                modifier = Modifier.size(100.dp)
            )
            Text(text = character.name)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        RickAndMortyAppTheme {
            CharacterListScreen(
                viewModel = viewModel(
                    factory = CharacterViewModelFactory(
                        CharacterRepository()
                    )
                )
            )
        }
    }

    @Composable  //Pantalla principal para los episodios.
    fun EpisodeListScreen(viewModel: EpisodeViewModel = viewModel(factory = EpisodeViewModelFactory(EpisodeRepository()))) {
        val episodes by viewModel.episodes.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (episodes.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn {
                    items(episodes) { episode ->
                        EpisodeItem(episode = episode)
                    }
                }
            }
        }
    }

    @Composable
    fun EpisodeItem(episode: Episode) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = R.drawable.ic_launcher,
                contentDescription = "Imagen de episodio por defecto",
                modifier = Modifier.size(100.dp)
            )
            Text(text = "Nombre: ${episode.name}")
            Text(text = "Episodio: ${episode.episode}")
        }
    }

}