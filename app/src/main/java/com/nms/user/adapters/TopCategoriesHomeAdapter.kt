package com.nms.user.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nms.user.R
import com.nms.user.fragments.HomeFragment
import com.nms.user.models.TopCategoriesHomeModel

class TopCategoriesHomeAdapter(private val context: HomeFragment, topCategoriesHomeModelArrayList: ArrayList<TopCategoriesHomeModel>) :
    RecyclerView.Adapter<TopCategoriesHomeAdapter.ViewHolder>() {
    private val topCategoriesHomeModelArrayList: ArrayList<TopCategoriesHomeModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.home_top_categories_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model: TopCategoriesHomeModel = topCategoriesHomeModelArrayList[position]

        holder.courseNameTV.setText(model.getCourse_name())
        holder.courseIV.setImageResource(model.getCourse_image())
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return topCategoriesHomeModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseIV: ImageView
        val courseNameTV: TextView
        init {
            courseIV = itemView.findViewById(R.id.imageCardItems)
            courseNameTV = itemView.findViewById(R.id.txtTopCategoriesName)

        }
    }
    // Constructor
    init {
        this.topCategoriesHomeModelArrayList = topCategoriesHomeModelArrayList
    }
}
