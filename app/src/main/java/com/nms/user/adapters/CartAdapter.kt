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
import com.nms.user.R
import com.nms.user.models.CartModel

class CartAdapter(
    private val context: Context,
    private val products: ArrayList<CartModel>,
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
        holder.productName.text = itemsViewModel.productName
        holder.productDescription.text = itemsViewModel.productDescription
        holder.productQuantity.text = itemsViewModel.productQuantity.toString()
        holder.productTotalPrice.text = "₹ ${itemsViewModel.productPrice}"
        // if Product Image is Blank then set default image
        if (itemsViewModel.productImage == "") {
            Glide.with(context)
                .load("https://picsum.photos/480")
                .into(holder.ivProductImage)
        } else {
            Glide.with(context)
                .load(itemsViewModel.productImage)
                .into(holder.ivProductImage)
        }
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
        val productTotalPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
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