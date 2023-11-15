package fr.enseirb.gl.cocktail.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.activities.CocktailDetailsActivity
import fr.enseirb.gl.cocktail.databinding.RecentCocktailsViewedBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.models.RecentViewedCocktail

class RecentViewedCocktailAdapter() :
    RecyclerView.Adapter<RecentViewedCocktailAdapter.RecentViewedCocktailViewHolder>() {
    class RecentViewedCocktailViewHolder(val binding: RecentCocktailsViewedBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val recentViewedCocktails = mutableListOf<RecentViewedCocktail>()

    fun updateRecentViewedCocktails(recentViewedCocktails: List<RecentViewedCocktail>) {
        this.recentViewedCocktails.clear()
        this.recentViewedCocktails.addAll(recentViewedCocktails)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentViewedCocktailViewHolder {
        return RecentViewedCocktailViewHolder(
            RecentCocktailsViewedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return recentViewedCocktails.size
    }

    override fun onBindViewHolder(holder: RecentViewedCocktailViewHolder, position: Int) {
        val cocktail = recentViewedCocktails[position]
        Glide.with(holder.itemView.context)
            .load(cocktail.strDrinkThumb)
            .into(holder.binding.imgRecentCocktailViewed)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CocktailDetailsActivity::class.java)
            intent.putExtra(HomeFragment.COCKTAIL_ID, cocktail.idDrink)
            intent.putExtra(HomeFragment.COCKTAIL_NAME, "cocktail.strDrink")
            intent.putExtra(HomeFragment.COCKTAIL_IMAGE, cocktail.strDrinkThumb)
            holder.itemView.context.startActivity(intent)
        }
    }

}