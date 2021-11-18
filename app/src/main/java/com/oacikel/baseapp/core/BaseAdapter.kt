package com.oacikel.baseapp.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : ViewDataBinding, V>(@LayoutRes val layout: Int, val recyclable: Boolean) :
    RecyclerView.Adapter<BaseAdapter.BaseHolder<T, V>>() {

    var items: ArrayList<V>? = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var layoutInflater: LayoutInflater
    var binding: T? = null
    var selectedPos = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, container: Int): BaseHolder<T, V> {
        layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false)
        return BaseHolder(binding!!, recyclable)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: BaseHolder<T, V>, position: Int) {
        holder.bind(items?.get(position), this)
    }

    class BaseHolder<T : ViewDataBinding, V>(val binding: T, recyclable: Boolean) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: V?, adapter: BaseAdapter<T, V>) {
            adapter.bindView(binding, item, adapterPosition)
            itemView.setOnClickListener {
                if ((adapter.items?.size ?: -1) < adapterPosition || adapterPosition < 0) return@setOnClickListener
                adapter.clickListener(adapter.items?.get(adapterPosition), adapterPosition, binding)
            }
        }

        init {
            setIsRecyclable(recyclable)
        }
    }

    abstract fun bindView(binding: T, item: V?, adapterPosition: Int)

    open fun clickListener(item: V?, position: Int, binding: T) {
        clickListener(item, position)
    }

    open fun clickListener(item: V?, position: Int) {

    }
}