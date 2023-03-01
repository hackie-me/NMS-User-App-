package com.nms.user.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nms.user.R
import com.nms.user.adapters.NotificationAdapter
import com.nms.user.models.NotificationModel
import com.nms.user.utils.api.ApiRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NotificationFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiRequest.getRequest(ApiRequest.URL_GET_NOTIFICATIONS)
            if (response.code == 200) {
                val notifications =
                    Gson().fromJson(response.data, Array<NotificationModel>::class.java)
                withContext(Dispatchers.Main) {
                    view.findViewById<RecyclerView>(R.id.lstNotification).adapter =
                        NotificationAdapter(requireContext(), notifications)
                }
            }
        }
    }
}