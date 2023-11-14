package fr.enseirb.gl.cocktail.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.activities.CocktailDetailsActivity
import fr.enseirb.gl.cocktail.databinding.CocktailItemBinding
import fr.enseirb.gl.cocktail.fragments.HomeFragment
import fr.enseirb.gl.cocktail.models.SavedCocktail

class FavoritesCocktailsAdapter : RecyclerView.Adapter<FavoritesCocktailsAdapter.FavoritesCocktailsViewHolder>() {
    inner  class FavoritesCocktailsViewHolder(val binding:CocktailItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<SavedCocktail>() {
        override fun areItemsTheSame(oldItem: SavedCocktail, newItem: SavedCocktail): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: SavedCocktail, newItem: SavedCocktail): Boolean {
            return oldItem == newItem
        }
    }

    val differ= AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesCocktailsViewHolder {
        return FavoritesCocktailsViewHolder(CocktailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesCocktailsViewHolder, position: Int) {
        val cocktail = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(cocktail.strDrinkThumb)
            .into(holder.binding.ivCocktailImage)
        holder.binding.tvCocktailName.text = cocktail.strDrink

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CocktailDetailsActivity::class.java)
            intent.putExtra(HomeFragment.COCKTAIL_ID, cocktail.idDrink)
            intent.putExtra(HomeFragment.COCKTAIL_NAME, cocktail.strDrink)
            intent.putExtra(HomeFragment.COCKTAIL_IMAGE, cocktail.strDrinkThumb)
            holder.itemView.context.startActivity(intent)
        }
    }


}