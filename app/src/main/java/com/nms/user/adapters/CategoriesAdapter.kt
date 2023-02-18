package com.nms.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nms.user.R
import com.nms.user.models.CategoryModel

class CategoriesAdapter(
    private val context: Context,
    private val category: Array<CategoryModel>
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_top_categories_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemViewModel = category[position]

        // set the data to the views
        holder.categoryName.text = itemViewModel.name
        // if Product Image is Blank then set default image
        if (itemViewModel.image == "") {
            Glide.with(context)
                .load("https://picsum.photos/200/300")
                .into(holder.categoryImage)
        } else {
            Glide.with(context)
                .load(itemViewModel.image)
                .into(holder.categoryImage)
        }

    }

    override fun getItemCount(): Int = category.size

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImage: ImageView
        val categoryName: TextView

        init {
            categoryImage = itemView.findViewById(R.id.imageCardItems)
            categoryName = itemView.findViewById(R.id.txtTopCategoriesName)
        }
    }
}
