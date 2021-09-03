package com.example.akmovies.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.akmovies.R
import com.example.akmovies.data.models.TvShow
import com.example.akmovies.databinding.ItemMovieBinding

class TVShowAdapter(val context: Context) :PagingDataAdapter<TvShow,TVShowAdapter.TVShowViewHolder>(
    diffCallback =tvShowCallBack
) {


     object tvShowCallBack: DiffUtil.ItemCallback<TvShow>(){
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.name == newItem.name
        }
    }


    inner class TVShowViewHolder(private val binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindTVShow(tvShow: TvShow){
            binding.apply {
                movieName.text = tvShow.name
                statusMovie.text = context.getString(R.string.current_status,tvShow.status)
                releaseDate.text = context.getString(R.string.release_date,tvShow.start_date)
                networkName.text = context.getString(R.string.network_name,tvShow.network)
                movieThumbnail.load(
                    tvShow.image_thumbnail_path
                )
                root.setOnClickListener {
                    onShowListener?.let { it -> it((tvShow)) }
                }
            }
        }
    }




    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {

        val currentShow = getItem(position)
        holder.bindTVShow(currentShow!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        return TVShowViewHolder(
           ItemMovieBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false))
        )
    }

    private var onShowListener: ((TvShow)-> Unit)? = null

    fun setOnShowListener(listener: (TvShow)->Unit){
        onShowListener = listener
    }
}