package fr.enseirb.gl.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.enseirb.gl.cocktail.databinding.FilterItemBinding
import fr.enseirb.gl.cocktail.models.Alcohol

class AlcoholAdapter() : RecyclerView.Adapter<AlcoholAdapter.AlcoholViewHolder>() {
    private var alcohol = ArrayList<Alcohol>()
    var onAlcoholClick: ((Alcohol) -> Unit)? = null

    fun setAlcohol(alcohol: List<Alcohol>) {
        this.alcohol = alcohol as ArrayList<Alcohol>
        notifyDataSetChanged()
    }

    inner class AlcoholViewHolder(binding: FilterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlcoholViewHolder {
        return AlcoholViewHolder(
            FilterItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return alcohol.size
    }

    override fun onBindViewHolder(holder: AlcoholViewHolder, position: Int) {
        val alcohol = alcohol[position]
        holder.itemView.apply {
            val binding = FilterItemBinding.bind(this)
            binding.filterName.text = alcohol.strAlcoholic
        }

        holder.itemView.setOnClickListener {
            onAlcoholClick?.invoke(alcohol)
        }
    }
}