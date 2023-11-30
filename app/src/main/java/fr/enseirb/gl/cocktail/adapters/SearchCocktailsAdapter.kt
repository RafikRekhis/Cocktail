package fr.enseirb.gl.cocktail.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.activities.CocktailDetailsActivity
import fr.enseirb.gl.cocktail.databinding.CocktailItemBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.models.Drink

class SearchCocktailsAdapter :
    RecyclerView.Adapter<SearchCocktailsAdapter.SearchCocktailsViewHolder>() {
    private var cocktails = ArrayList<Drink>()
    private var unfilteredCocktails = ArrayList<Drink>()

    fun setCocktails(cocktails: List<Drink>) {
        this.cocktails = cocktails as ArrayList<Drink>
        notifyDataSetChanged()
    }

    fun filterCocktails(category: String, ingredient: String, glass: String, cocktailType: String) {
        if (category.isNotEmpty() || ingredient.isNotEmpty() || glass.isNotEmpty() || cocktailType.isNotEmpty()) {
            if (unfilteredCocktails.isEmpty()) {
                unfilteredCocktails = ArrayList(cocktails)
            }

            val filteredCocktails = ArrayList<Drink>()

            for (cocktail in unfilteredCocktails) {
                val categoryMatch = if (category.isNotEmpty()) {
                    cocktail.strCategory == category
                } else {
                    true
                }

                val ingredientMatch = if (ingredient.isNotEmpty()) {
                    cocktail.strIngredient1 == ingredient ||
                            cocktail.strIngredient2 == ingredient ||
                            cocktail.strIngredient3 == ingredient ||
                            cocktail.strIngredient4 == ingredient ||
                            cocktail.strIngredient5 == ingredient ||
                            cocktail.strIngredient6 == ingredient ||
                            cocktail.strIngredient7 == ingredient ||
                            cocktail.strIngredient8 == ingredient ||
                            cocktail.strIngredient9 == ingredient ||
                            cocktail.strIngredient10 == ingredient ||
                            cocktail.strIngredient11 == ingredient ||
                            cocktail.strIngredient12 == ingredient ||
                            cocktail.strIngredient13 == ingredient ||
                            cocktail.strIngredient14 == ingredient ||
                            cocktail.strIngredient15 == ingredient
                } else {
                    true
                }

                val glassMatch = if (glass.isNotEmpty()) {
                    cocktail.strGlass == glass
                } else {
                    true
                }

                val cocktailTypeMatch = if (cocktailType.isNotEmpty()) {
                    cocktail.strAlcoholic == cocktailType
                } else {
                    true
                }

                if (categoryMatch && ingredientMatch && glassMatch && cocktailTypeMatch) {
                    filteredCocktails.add(cocktail)
                }
            }

            setCocktails(filteredCocktails)
        } else {
            if (unfilteredCocktails.isNotEmpty()) {
                setCocktails(unfilteredCocktails)
            } else {
                setCocktails(cocktails)
            }
        }
    }

    fun resetFilter() {
        if (unfilteredCocktails.isNotEmpty()) {
            cocktails = ArrayList(unfilteredCocktails)
            unfilteredCocktails.clear()
        }
    }

    inner class SearchCocktailsViewHolder(binding: CocktailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCocktailsViewHolder {
        return SearchCocktailsViewHolder(
            CocktailItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    override fun onBindViewHolder(holder: SearchCocktailsViewHolder, position: Int) {
        val cocktail = cocktails[position]
        holder.itemView.apply {
            val binding = CocktailItemBinding.bind(this)
            binding.tvCocktailName.text = cocktail.strDrink
            Glide.with(this)
                .load(cocktail.strDrinkThumb)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.ivCocktailImage)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CocktailDetailsActivity::class.java)
            intent.putExtra(HomeFragment.COCKTAIL_ID, cocktail.idDrink)
            intent.putExtra(HomeFragment.COCKTAIL_NAME, cocktail.strDrink)
            intent.putExtra(HomeFragment.COCKTAIL_IMAGE, cocktail.strDrinkThumb)
            holder.itemView.context.startActivity(intent)
        }
    }
}