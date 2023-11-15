package fr.enseirb.gl.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.enseirb.gl.cocktail.databinding.FilterItemBinding
import fr.enseirb.gl.cocktail.models.Ingredient

class IngredientsAdapter() : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {
    private var ingredients = ArrayList<Ingredient>()
    var onIngredientClick: ((Ingredient) -> Unit)? = null

    fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients as ArrayList<Ingredient>
        notifyDataSetChanged()
    }

    inner class IngredientViewHolder(binding: FilterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            FilterItemBinding.inflate(
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
            val binding = FilterItemBinding.bind(this)
            binding.filterName.text = ingredient.strIngredient1
        }

        holder.itemView.setOnClickListener {
            onIngredientClick?.invoke(ingredient)
        }
    }
}