package com.nms.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nms.user.R
import com.nms.user.models.CartModel
import com.nms.user.models.ProductModel
import com.nms.user.repo.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(
    private val context: Context,
    private val products: Array<CartModel>,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = products[position]
        holder.bind(clickListener)
        // set the data to the views
        val pid = itemsViewModel.pid
        var productPrice = 0

        // Get Product Name, Description, Price, Image from Product ID
        CoroutineScope(Dispatchers.IO).launch {
            val response = ProductRepository.getProductById(pid)
            val product = Gson().fromJson(response.data, ProductModel::class.java)
            if (response.code == 200) {
                withContext(Dispatchers.Main) {
                    holder.productName.text = product.name
                    holder.productDescription.text = product.description
                    holder.productPrice.text = "₹ ${product.price}"
                    productPrice = product.price.toInt()
                    // if Product Image is Blank then set default image
                    Glide.with(context)
                        .load(product.thumbnail)
                        .into(holder.ivProductImage)
                }
            }
        }

        // Set Quantity
        holder.productQuantity.text = itemsViewModel.quantity

        // Set Total Price
        val totalPrice = itemsViewModel.quantity.toInt() * productPrice

        // Set Total Price
        holder.productPrice.text = "₹ $totalPrice"
    }

    override fun getItemCount(): Int = products.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.txtProductName)
        val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val productDescription: TextView = itemView.findViewById(R.id.txtProductDescription)
        private val btnMinus: FrameLayout = itemView.findViewById(R.id.btnMinus)
        private val btnPlus: FrameLayout = itemView.findViewById(R.id.btnPlus)
        private val btnRemoveFromCart: AppCompatImageView =
            itemView.findViewById(R.id.ivRemoveCartItem)
        val productQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val productPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
        fun bind(clickListener: OnClickListener) {
            btnMinus.setOnClickListener {
                clickListener.onMinusClick(adapterPosition)
            }
            btnPlus.setOnClickListener {
                clickListener.onPlusClick(adapterPosition)
            }

            btnRemoveFromCart.setOnClickListener {
                clickListener.onRemoveClick(adapterPosition)
            }
        }
    }

    interface OnClickListener {
        fun onMinusClick(position: Int)
        fun onPlusClick(position: Int)
        fun onRemoveClick(position: Int)
    }
}