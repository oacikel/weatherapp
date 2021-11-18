package com.oacikel.baseapp.binding.listAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.oacikel.baseapp.R
import com.oacikel.baseapp.databinding.ItemCharacterBinding
import com.oacikel.baseapp.db.entity.marvelEntities.CharacterEntity
import com.oacikel.baseapp.ui.callback.ListItemFocusCallback

class CharactersAdapter(var focusCallback: ListItemFocusCallback) :
    RecyclerView.Adapter<CharactersAdapter.CharacterListViewHolder>() {
    var characters = mutableListOf<CharacterEntity>()

    fun submitList(list: MutableList<CharacterEntity>) {
        characters = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterListViewHolder(
            DataBindingUtil.inflate
                (
                LayoutInflater.from(parent.context),
                R.layout.item_character,
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: CharacterListViewHolder,
        position: Int
    ) {
        holder.itemCharacterBinding.character = characters[position]
        focusCallback.onComicListAvailable(characters[position].comics?.collectionItems)


    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterListViewHolder(
        val itemCharacterBinding: ItemCharacterBinding
    ) :
        RecyclerView.ViewHolder(itemCharacterBinding.root)
}