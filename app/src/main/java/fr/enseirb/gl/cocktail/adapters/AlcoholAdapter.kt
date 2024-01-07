package fr.enseirb.gl.cocktail.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.enseirb.gl.cocktail.databinding.CategoryItemBinding
import fr.enseirb.gl.cocktail.databinding.FilterItemBinding
import fr.enseirb.gl.cocktail.models.Alcohol

class AlcoholAdapter(private val spacing: Int) : RecyclerView.Adapter<AlcoholAdapter.AlcoholViewHolder>() {
    private var alcohol = ArrayList<Alcohol>()
    var onAlcoholClick: ((Alcohol) -> Unit)? = null

    fun setAlcohol(alcohol: List<Alcohol>) {
        this.alcohol = alcohol as ArrayList<Alcohol>
        notifyDataSetChanged()
    }

    inner class AlcoholViewHolder(binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlcoholViewHolder {
        return AlcoholViewHolder(
            CategoryItemBinding.inflate(
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
            val binding = CategoryItemBinding.bind(this)
            binding.tvCategoryName.text = alcohol.strAlcoholic
        }

        holder.itemView.setOnClickListener {
            onAlcoholClick?.invoke(alcohol)
        }
    }

    class AlcoholItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
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