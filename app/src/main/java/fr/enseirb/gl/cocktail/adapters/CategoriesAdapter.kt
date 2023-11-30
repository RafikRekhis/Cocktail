package fr.enseirb.gl.cocktail.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.enseirb.gl.cocktail.databinding.CategoryItemBinding
import fr.enseirb.gl.cocktail.models.Category

class CategoriesAdapter(private val spacing: Int) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categories = ArrayList<Category>()
    var onCategoryClick: ((Category) -> Unit)? = null

    fun setCategories(categories: List<Category>) {
        this.categories = categories as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.itemView.apply {
            val binding = CategoryItemBinding.bind(this)
            binding.tvCategoryName.text = category.strCategory
        }

        holder.itemView.setOnClickListener {
            onCategoryClick?.invoke(category)
        }
    }
    class CategoryItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
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