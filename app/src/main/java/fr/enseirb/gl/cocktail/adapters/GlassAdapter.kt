package fr.enseirb.gl.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.enseirb.gl.cocktail.databinding.FilterItemBinding
import fr.enseirb.gl.cocktail.models.Glass

class GlassAdapter() : RecyclerView.Adapter<GlassAdapter.GlassViewHolder>() {
    private var glass = ArrayList<Glass>()
    var onGlassClick: ((Glass) -> Unit)? = null

    fun setGlass(glass: List<Glass>) {
        this.glass = glass as ArrayList<Glass>
        notifyDataSetChanged()
    }

    inner class GlassViewHolder(binding: FilterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlassViewHolder {
        return GlassViewHolder(
            FilterItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return glass.size
    }

    override fun onBindViewHolder(holder: GlassViewHolder, position: Int) {
        val glass = glass[position]
        holder.itemView.apply {
            val binding = FilterItemBinding.bind(this)
            binding.filterName.text = glass.strGlass
        }

        holder.itemView.setOnClickListener {
            onGlassClick?.invoke(glass)
        }
    }
}