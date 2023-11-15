package fr.enseirb.gl.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.enseirb.gl.cocktail.databinding.FilterItemBinding
import fr.enseirb.gl.cocktail.models.Category

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var categories = ArrayList<Category>()
    var onCategoryClick: ((Category) -> Unit)? = null

    fun setCategories(categories: List<Category>) {
        this.categories = categories as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(binding: FilterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            FilterItemBinding.inflate(
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
            val binding = FilterItemBinding.bind(this)
            binding.filterName.text = category.strCategory
        }

        holder.itemView.setOnClickListener {
            onCategoryClick?.invoke(category)
        }
    }
}