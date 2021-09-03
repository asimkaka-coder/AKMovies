package com.example.akmovies.ui.adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.akmovies.R
import com.example.akmovies.databinding.LayoutLoadingStateBinding

class HeaderFooterAdapter() : LoadStateAdapter<HeaderFooterAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        if (loadState == LoadState.Loading) {
            holder.progressBar.visibility = View.VISIBLE

        } else{
        //hide the view
            holder.progressBar.visibility = View.GONE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutLoadingStateBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_state,parent,false)
            )
        )
    }

    class LoadStateViewHolder(private val binding: LayoutLoadingStateBinding) : RecyclerView.ViewHolder(binding.root){
        val progressBar = binding.progressBar
    }
}