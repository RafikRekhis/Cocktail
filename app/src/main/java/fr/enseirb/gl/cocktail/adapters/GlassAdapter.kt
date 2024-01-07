package fr.enseirb.gl.cocktail.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.enseirb.gl.cocktail.databinding.CategoryItemBinding
import fr.enseirb.gl.cocktail.databinding.FilterItemBinding
import fr.enseirb.gl.cocktail.models.Glass

class GlassAdapter(private val spacing: Int) : RecyclerView.Adapter<GlassAdapter.GlassViewHolder>() {
    private var glass = ArrayList<Glass>()
    var onGlassClick: ((Glass) -> Unit)? = null

    fun setGlass(glass: List<Glass>) {
        this.glass = glass as ArrayList<Glass>
        notifyDataSetChanged()
    }

    inner class GlassViewHolder(binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlassViewHolder {
        return GlassViewHolder(
            CategoryItemBinding.inflate(
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
            val binding = CategoryItemBinding.bind(this)
            binding.tvCategoryName.text = glass.strGlass
        }

        holder.itemView.setOnClickListener {
            onGlassClick?.invoke(glass)
        }
    }
    class GlassItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val position = parent.getChildAdapterPosition(view)

            // Add spacing to all items except the first one
            if (position == 0) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        }
    }
}