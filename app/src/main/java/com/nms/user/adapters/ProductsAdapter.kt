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
import com.nms.user.models.ProductModel
import com.nms.user.utils.Helper
import java.util.*

class ProductsAdapter(
    private val context: Context,
    private val products : Array<ProductModel>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>()
{

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_product_card, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = products[position]
        holder.bind(itemsViewModel, clickListener)

        // set the data to the views
        holder.productName.text = itemsViewModel.name
        holder.productPrice.text = itemsViewModel.price

        // set rating to 0 if it is null
        if (itemsViewModel.rating == "")
        {
            // Random Average Rating for testing in float value (0.0 to 5.0)
            val randomRating = Random().nextInt(5)
            val ratingDecimal = Random().nextInt(9)
            var averageRating = "$randomRating.$ratingDecimal"
            if (randomRating == 0) {
                averageRating = "1.$ratingDecimal"
            }
            holder.productRating.text = averageRating
        }else{
            holder.productRating.text = itemsViewModel.rating
        }

        // if Product Image is Blank then set default image
        Glide.with(holder.itemView.context).load("https://source.unsplash.com/random?${itemsViewModel.name}")
            .into(holder.productImage)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int = products.size

    // inner class to hold the views
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val productImage: ImageView = itemView.findViewById(R.id.ivProductImage)!!
        val productName: TextView = itemView.findViewById(R.id.txtPoductName)
        val productPrice: TextView = itemView.findViewById(R.id.txtPoductPrice)
        val productRating: TextView = itemView.findViewById(R.id.txtPoductRating)

        fun bind(ProductModel: ProductModel, clickListener: ClickListener) {
            itemView.setOnClickListener {
                clickListener.onProductCardClick(ProductModel)
            }
        }
    }

    interface ClickListener {
        fun onProductCardClick(ProductModel: ProductModel)
    }

}
