package fr.enseirb.gl.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.databinding.CocktailItemBinding
import fr.enseirb.gl.cocktail.models.CocktailByCategory

class CategoryCocktailsAdapter :
    RecyclerView.Adapter<CategoryCocktailsAdapter.CategoryCocktailsViewHolder>() {
    private var cocktails = ArrayList<CocktailByCategory>()
    var onCocktailClick: ((CocktailByCategory) -> Unit)? = null

    fun setCocktails(cocktails: List<CocktailByCategory>) {
        this.cocktails = cocktails as ArrayList<CocktailByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryCocktailsViewHolder(binding: CocktailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCocktailsViewHolder {
        return CategoryCocktailsViewHolder(
            CocktailItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    override fun onBindViewHolder(holder: CategoryCocktailsViewHolder, position: Int) {
        val cocktail = cocktails[position]
        holder.itemView.apply {
            val binding = CocktailItemBinding.bind(this)
            binding.tvCocktailName.text = cocktail.strDrink
            Glide.with(this)
                .load(cocktail.strDrinkThumb)
                .into(binding.ivCocktailImage)
        }

        holder.itemView.setOnClickListener {
            onCocktailClick?.invoke(cocktail)
        }
    }
}