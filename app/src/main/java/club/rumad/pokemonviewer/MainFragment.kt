package club.rumad.pokemonviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import club.rumad.pokemonviewer.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private val viewModel by viewModels<PokemonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.showPokemonButton.setOnClickListener {
            val input = binding.pokemonEditText.text.toString()
            viewModel.showPokemon(input)
            val ime =
                ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            ime?.hideSoftInputFromWindow(requireView().windowToken, 0)
        }

        viewModel.networkError.observe({ lifecycle }) {
            if (it) {
                Snackbar.make(
                    binding.constraintLayout,
                    getString(R.string.error_message),
                    Snackbar.LENGTH_LONG
                )
                    .show()
                viewModel.onHandledNetworkError()
            }
        }

        return binding.root
    }

}
