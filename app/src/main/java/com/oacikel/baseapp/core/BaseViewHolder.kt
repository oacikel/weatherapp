package com.oacikel.baseapp.core

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<T : ViewDataBinding>(val binding : T) : RecyclerView.ViewHolder(binding.root)