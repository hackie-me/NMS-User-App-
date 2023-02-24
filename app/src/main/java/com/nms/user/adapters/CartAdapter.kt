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
import com.nms.user.models.CartModel

class CartAdapter(
    private val context: Context,
    private val products: Array<CartModel>,
) : RecyclerView.Adapter<CartAdapter.ViewHolder>()
{
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val itemsViewModel = products[position]
        holder.bind()
        // set the data to the views
        holder.productName.text = itemsViewModel.productName
        holder.productDescription.text = itemsViewModel.productDescription
        holder.productTotalPrice.text = itemsViewModel.productPrice

        // if Product Image is Blank then set default image
        if (itemsViewModel.productImage == "")
        {
            Glide.with(context)
                .load("https://picsum.photos/200/300")
                .into(holder.ivProductImage)
        }
        else
        {
            Glide.with(context)
                .load(itemsViewModel.productImage)
                .into(holder.ivProductImage)
        }

    }

    override fun getItemCount(): Int = products.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val productName: TextView = itemView.findViewById(R.id.txtProductName)
        val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val productDescription: TextView = itemView.findViewById(R.id.txtProductDescription)
        private val btnMinus: TextView = itemView.findViewById(R.id.btnMinus)
        private val btnPlus: TextView = itemView.findViewById(R.id.btnPlus)
        val btnRemoveFromCart: TextView = itemView.findViewById(R.id.ivRemoveCartItem)
        private val productQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val productTotalPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
        fun bind()
        {
            var quantity = 1 // default quantity
            btnMinus.setOnClickListener {
                if (quantity > 1)
                {
                    quantity--
                    productQuantity.text = quantity.toString()
                    // Update total price
                    val productPrice = productTotalPrice.text.toString().toInt()
                    productTotalPrice.text = (quantity * productPrice).toString()
                }
            }
            btnPlus.setOnClickListener {
                quantity++
                productQuantity.text = quantity.toString()

                // Update total price
                val productPrice = productTotalPrice.text.toString().toInt()
                productTotalPrice.text = (quantity * productPrice).toString()
            }
        }
    }
}