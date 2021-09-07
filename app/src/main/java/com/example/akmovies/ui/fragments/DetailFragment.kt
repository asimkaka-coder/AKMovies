package com.example.akmovies.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.example.akmovies.R
import com.example.akmovies.databinding.DetailScreenBinding
import com.example.akmovies.viewmodels.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment:Fragment(R.layout.detail_screen) {

    private var _binding:DetailScreenBinding? = null
    val binding get() = _binding!!
    val args:DetailFragmentArgs by navArgs()
    private val viewModel by viewModels<TVShowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShow = args.tvShow

        val showDetails = viewModel.getShowDetails(tvShow.id)

        binding.apply {
        ratingText.visibility = View.GONE
        movieRatingBar.visibility = View.GONE}

        viewModel.movieDetail.observe(viewLifecycleOwner,{
            val tvShowDetails = it.tvShow
            val categories = tvShowDetails.genres.toString()
            binding.apply {
                movieName.text = tvShowDetails.name
                movieDescription.text = tvShowDetails.description
                movieCategory.text = getString(R.string.category_text,categories)
                totalSeasons.text = getString(R.string.total_seasons,tvShowDetails.episodes.size)
                movieRatingBar.rating = 0.5.toFloat()*tvShowDetails.rating.toFloat()
                ratingText.visibility = View.VISIBLE
                movieRatingBar.visibility = View.VISIBLE
                Log.d("RATING",movieRatingBar.rating.toString())
                moviePoster.load(tvShowDetails.image_path){
                    crossfade(true)
                    crossfade(500)
                }
            }
        })


        /* Using navController instead of navigation action
        * so that the fragment does not get reload
        * everytime
        * */
        binding.backButton.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }


}
