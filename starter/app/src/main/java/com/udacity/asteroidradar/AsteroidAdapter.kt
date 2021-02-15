package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListViewItemBinding

class AsteroidAdapter(
          val onClickListener: AsteroidListener): ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RecyclerView.ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onClickListener,
                    getItem(position)!!)
    }

    class ViewHolder private constructor(
              private val binding: ListViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: AsteroidListener, item: Asteroid) {
            binding.asteroid = item
            binding.onClickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListViewItemBinding.inflate(layoutInflater,
                                                          parent,
                                                          false)
                return ViewHolder(binding)
            }
        }
    }
}

class AsteroidDiffCallback: DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(previousAsteroid: Asteroid, newAsteroid: Asteroid): Boolean {
        return previousAsteroid.id == newAsteroid.id
    }

    override fun areContentsTheSame(
              previousAsteroid: Asteroid, newAsteroid: Asteroid): Boolean {
        return previousAsteroid == newAsteroid
    }
}

class AsteroidListener(val onClickListener: (asteroidId: Long) -> Unit) {
    fun onClick(asteroid: Asteroid) = onClickListener(asteroid.id)
}
