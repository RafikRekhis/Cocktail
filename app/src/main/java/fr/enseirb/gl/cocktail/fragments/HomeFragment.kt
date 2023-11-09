package fr.enseirb.gl.cocktail.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import fr.enseirb.gl.cocktail.R
import fr.enseirb.gl.cocktail.databinding.FragmentHomeBinding
import fr.enseirb.gl.cocktail.models.CocktailList
import fr.enseirb.gl.cocktail.models.Drink
import fr.enseirb.gl.cocktail.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RetrofitInstance.api.getRandomCocktail().enqueue(object : Callback<CocktailList>{
            override fun onResponse(call: Call<CocktailList>, response: Response<CocktailList>) {
                if(response.body() != null){
                    val randomCocktail:Drink = response.body()!!.drinks[0]
                    Glide.with(this@HomeFragment)
                        .load(randomCocktail.strDrinkThumb)
                        .into(binding.randomCocktailImage)
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CocktailList>, t: Throwable) {
                Log.d("HomeFragment", "onFailure: ${t.message.toString()}")
            }
        })
    }


}