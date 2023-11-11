package fr.enseirb.gl.cocktail.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.activities.CocktailDetailsActivity
import fr.enseirb.gl.cocktail.databinding.CocktailItemBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.models.CocktailByCategory

class CategoryCocktailsAdapter :
    RecyclerView.Adapter<CategoryCocktailsAdapter.CategoryCocktailsViewHolder>() {
    private var cocktails = ArrayList<CocktailByCategory>()

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
            val intent = Intent(holder.itemView.context, CocktailDetailsActivity::class.java)
            intent.putExtra(HomeFragment.COCKTAIL_ID, cocktail.idDrink)
            intent.putExtra(HomeFragment.COCKTAIL_NAME, cocktail.strDrink)
            intent.putExtra(HomeFragment.COCKTAIL_IMAGE, cocktail.strDrinkThumb)
            holder.itemView.context.startActivity(intent)
        }
    }
}