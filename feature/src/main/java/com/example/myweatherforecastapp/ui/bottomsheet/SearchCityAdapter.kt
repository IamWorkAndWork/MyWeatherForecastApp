package com.example.myweatherforecastapp.ui.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.CityEntity
import com.example.myweatherforecastapp.R
import com.example.myweatherforecastapp.databinding.ItemSearchCityBinding

class SearchCityAdapter(
    private val onSelectedCountry: ((CityEntity) -> Unit)? = null
) : PagingDataAdapter<CityEntity, RecyclerView.ViewHolder>(diffItem()) {

    companion object {
        fun diffItem(): DiffUtil.ItemCallback<CityEntity> {
            return object : DiffUtil.ItemCallback<CityEntity>() {
                override fun areItemsTheSame(oldItem: CityEntity, newItem: CityEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: CityEntity, newItem: CityEntity): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? SearchCityViewHolder)?.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSearchCityBinding>(
            inflater,
            R.layout.item_search_city,
            parent,
            false
        )
        return SearchCityViewHolder(binding)
    }

    inner class SearchCityViewHolder(private val binding: ItemSearchCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.itemCardView.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { cityEntity ->
                    onSelectedCountry?.invoke(cityEntity)
                }
            }

        }

        fun bind(item: CityEntity?) {

            binding.cityEntity = item

        }

    }

}