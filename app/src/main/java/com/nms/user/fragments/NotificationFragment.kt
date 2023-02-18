package com.nms.user.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.nms.user.models.NotificationModel
import com.nms.user.R
import com.nms.user.adapters.NotificationAdapter


class NotificationFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val persons = arrayOf(
            NotificationModel("We know that — for children AND adults — learning is most effective when it is", "Aug 12, 2020 at 12:08 PM"),
            NotificationModel("The future of professional learning is immersive, communal experiences for ", "Aug 12, 2020 at 12:08 PM"),
            NotificationModel("With this in mind, Global Online Academy created the Blended Learning Design ", "Aug 12, 2020 at 12:08 PM"),
            NotificationModel("Technology should serve, not drive, pedagogy. Schools often discuss ", "Aug 12, 2020 at 12:08 PM"),
            NotificationModel("Peer learning works. By building robust personal learning communities both  ", "Aug 12, 2020 at 12:08 PM"),
            NotificationModel("Technology should serve, not drive, pedagogy. Schools often discuss ", "Aug 12, 2020 at 12:08 PM"),
            NotificationModel("The future of professional learning is immersive, communal experiences for ", "Aug 12, 2020 at 12:08 PM"),
            NotificationModel("With this in mind, Global Online Academy created the Blended Learning Design ", "Aug 12, 2020 at 12:08 PM"),
            NotificationModel("We know that — for children AND adults — learning is most effective when it is", "Aug 12, 2020 at 12:08 PM"),

            )

        val adpater = NotificationAdapter(context as Activity, persons)
        view.findViewById<ListView>(R.id.lstPersons).adapter = adpater
        super.onViewCreated(view, savedInstanceState)
    }

}