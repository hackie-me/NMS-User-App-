package com.nms.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nms.user.R
import com.nms.user.models.NotificationModel
import com.nms.user.utils.Helper

class NotificationAdapter(
    private val context: Context,
    private val items: Array<NotificationModel>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.list_item_notification_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.lblDescription.text = item.description
        holder.lblDate.text = Helper.formatDate(item.date)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lblDescription: TextView
        var lblDate: TextView

        init {
            lblDescription = itemView.findViewById(R.id.lblDescription)
            lblDate = itemView.findViewById(R.id.lblDate)
        }
    }
}