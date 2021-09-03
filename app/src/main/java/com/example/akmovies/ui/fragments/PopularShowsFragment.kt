package com.example.akmovies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.akmovies.R
import com.example.akmovies.databinding.PopularShowsFragmentBinding
import com.example.akmovies.ui.adapters.HeaderFooterAdapter
import com.example.akmovies.ui.adapters.TVShowAdapter
import com.example.akmovies.viewmodels.TVShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PopularShowsFragment : Fragment(R.layout.popular_shows_fragment) {

    private val viewModel by viewModels<TVShowViewModel>()
    lateinit var mAdapter: TVShowAdapter

    private var _binding: PopularShowsFragmentBinding? = null
    val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularShowsFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()



        mAdapter.setOnShowListener {
            val bundle = Bundle().putSerializable(
                "tvShow",it
            )

           val direction =  PopularShowsFragmentDirections.actionPopularShowsFragmentToDetailFragment(it)
            findNavController().navigate(direction, navOptions {

            })
        }

        mAdapter.addLoadStateListener {
            if (it.refresh == LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE

                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.tvShowData.collect {
                        data-> mAdapter.submitData(data)
                    }
                }
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    fun setUpRecyclerView() {
        mAdapter = TVShowAdapter(requireContext())
        binding.rvTVShow.apply {
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = HeaderFooterAdapter(),
                footer = HeaderFooterAdapter()
            )
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}