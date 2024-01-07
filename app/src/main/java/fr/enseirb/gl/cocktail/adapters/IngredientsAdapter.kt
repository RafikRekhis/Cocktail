package fr.enseirb.gl.cocktail.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.enseirb.gl.cocktail.databinding.CategoryItemBinding
import fr.enseirb.gl.cocktail.databinding.FilterItemBinding
import fr.enseirb.gl.cocktail.models.Ingredient

class IngredientsAdapter(private val spacing: Int) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {
    private var ingredients = ArrayList<Ingredient>()
    var onIngredientClick: ((Ingredient) -> Unit)? = null

    fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients as ArrayList<Ingredient>
        notifyDataSetChanged()
    }

    inner class IngredientViewHolder(binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.itemView.apply {
            val binding = CategoryItemBinding.bind(this)
            binding.tvCategoryName.text = ingredient.strIngredient1
        }

        holder.itemView.setOnClickListener {
            onIngredientClick?.invoke(ingredient)
        }
    }

    class IngredientItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
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