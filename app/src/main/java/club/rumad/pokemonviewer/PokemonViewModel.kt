package club.rumad.pokemonviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.rumad.pokemonviewer.network.Pokemon
import club.rumad.pokemonviewer.network.PokemonAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    private val _currentPokemon = MutableLiveData<Pokemon>()
    val currentPokemon: LiveData<Pokemon>
        get() = _currentPokemon

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean>
        get() = _networkError

    fun onHandledNetworkError() {
        _networkError.value = false
    }

    fun showPokemon(input: String) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { coroutineContext, throwable ->
            _networkError.postValue(true)
        }) {
            val pokemon = PokemonAPI.retrofitService.getPokemon(input)
            _currentPokemon.postValue(pokemon)
        }
    }
}
