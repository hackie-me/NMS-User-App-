package com.nms.user.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nms.user.models.NotificationModel
import com.nms.user.R

class NotificationAdapter(
    private val activity: Activity,
    private val persons: Array<NotificationModel>
) : ArrayAdapter<NotificationModel>(activity, R.layout.list_item_notification_card, persons)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        var view = convertView
        if (view == null)
        {
            view = LayoutInflater.from(activity).inflate(R.layout.list_item_notification_card, null)

            val viewHolder = ViewHolder()
            viewHolder.imgdp = view!!.findViewById(R.id.imgdp)
            viewHolder.lblDescription = view.findViewById(R.id.lblDescription)
            viewHolder.lblDate = view.findViewById(R.id.lblDate)

            view.tag = viewHolder
        }

        val existingViewHolder = view.tag as ViewHolder

        existingViewHolder.imgdp.setImageResource(R.drawable.ic_notification_bottom_nav)
        existingViewHolder.lblDescription.text = persons[position].name
        existingViewHolder.lblDate.text = persons[position].age.toString()

        return view
    }

    class ViewHolder
    {
        lateinit var imgdp: ImageView
        lateinit var lblDescription: TextView
        lateinit var lblDate: TextView
    }
}