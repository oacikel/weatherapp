package com.oacikel.baseapp.binding.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.oacikel.baseapp.R
import com.oacikel.baseapp.databinding.ItemComicBinding
import com.oacikel.baseapp.db.entity.marvelEntities.ComicEntity
import com.oacikel.baseapp.db.entity.marvelEntities.ResourceListEntity
import com.oacikel.baseapp.db.entity.marvelEntities.SummaryViewEntity

class ComicsAdapter () :
    RecyclerView.Adapter<ComicsAdapter.ComicListViewHolder>() {

    var comics = mutableListOf<SummaryViewEntity>()

    fun submitList(list: MutableList<SummaryViewEntity>) {
        comics = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ComicListViewHolder(
            DataBindingUtil.inflate
                (
                LayoutInflater.from(parent.context),
                R.layout.item_comic,
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ComicListViewHolder,
        position: Int
    ) {
        holder.itemComicBinding.comic = comics[position]
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    inner class ComicListViewHolder(
        val itemComicBinding: ItemComicBinding
    ) :
        RecyclerView.ViewHolder(itemComicBinding.root)
}