package com.nms.user.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.nms.user.R
import com.nms.user.adapters.CategoriesHomeAdapter
import com.nms.user.adapters.TopCategoriesHomeAdapter
import com.nms.user.models.CategoriesHomeModel
import com.nms.user.models.TopCategoriesHomeModel


class HomeFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val categoriesRV = view.findViewById<RecyclerView>(R.id.idRVCourse)
        val topcategoriesRV = view.findViewById<RecyclerView>(R.id.TopCategories)
        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider) // init imageSlider
        val imgIcoNotification = view.findViewById<ImageView>(R.id.imageIcoNotification)
        val imageIcoShoppingBag = view.findViewById<ImageView>(R.id.imageIcoShoppingBag)

        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.card_home,   ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider,ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider_1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider_2, ScaleTypes.CENTER_CROP))


        imgIcoNotification.setOnClickListener {
            var notificationFragment = NotificationFragment()

            val manager = activity?.supportFragmentManager?.beginTransaction()
            if (manager != null) {
                manager.replace(R.id.fragmentContainer, notificationFragment)
                manager.commit()
            }
        }

        imageIcoShoppingBag.setOnClickListener {
            var shoppingBag = MyOrdersFragment()

            val manager = activity?.supportFragmentManager?.beginTransaction()
            if (manager != null) {
                manager.replace(R.id.fragmentContainer, shoppingBag)
                manager.commit()
            }
        }


        imageSlider.setImageList(imageList)

        imageSlider.setItemClickListener(object : ItemClickListener
        {
             override fun onItemSelected(position: Int) {
                 imageList.add(SlideModel(R.drawable.card_home,  ScaleTypes.CENTER_CROP))
            }
        })

        imageSlider.setItemChangeListener(object : ItemChangeListener
        {
            override fun onItemChanged(position: Int) {

            }
        })

        imageSlider.setTouchListener(object : TouchListener
        {
            override fun onTouched(touched: ActionTypes) {
                if (touched == ActionTypes.DOWN){
                    imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP ) {
                    imageSlider.startSliding(5000)
                }
            }
        })



        val TopcategoriesModelArrayList: ArrayList<TopCategoriesHomeModel> = ArrayList<TopCategoriesHomeModel>()
        TopcategoriesModelArrayList.add(TopCategoriesHomeModel("Dental", R.drawable.medicine))
        TopcategoriesModelArrayList.add(TopCategoriesHomeModel("Wellness", R.drawable.medicine1))
        TopcategoriesModelArrayList.add(TopCategoriesHomeModel("Homeo", R.drawable.img_card_items))
        TopcategoriesModelArrayList.add(TopCategoriesHomeModel("Eye care", R.drawable.medicine))
        TopcategoriesModelArrayList.add(TopCategoriesHomeModel("Dental", R.drawable.medicine1))
        TopcategoriesModelArrayList.add(TopCategoriesHomeModel("Wellness", R.drawable.img_card_items))
        TopcategoriesModelArrayList.add(TopCategoriesHomeModel("Homeo", R.drawable.medicine))
        val TopCategoriesHomeAdapter = TopCategoriesHomeAdapter(this, TopcategoriesModelArrayList)

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        topcategoriesRV.setLayoutManager(linearLayoutManager)
        topcategoriesRV.setAdapter(TopCategoriesHomeAdapter)

//        val topcategories = GridLayoutManager(context,2)
//        topcategoriesRV.layoutManager = topcategories
//        topcategoriesRV.adapter = TopCategoriesHomeAdapter
//        super.onViewCreated(view, savedInstanceState)


        val categoriesModelArrayList: ArrayList<CategoriesHomeModel> = ArrayList<CategoriesHomeModel>()
        categoriesModelArrayList.add(CategoriesHomeModel("Accu-check Active" + "Test Strip", "$122", "4.2",
            R.drawable.img_card_items
        ))
        categoriesModelArrayList.add(CategoriesHomeModel("Omron HEM-8712" + "BP Monitor", "$122","4.2",
            R.drawable.img_card_items
        ))
        categoriesModelArrayList.add(CategoriesHomeModel("Accu-check Active+ Test Strip", "$122","4.2",
            R.drawable.img_card_items
        ))
        categoriesModelArrayList.add(CategoriesHomeModel("Omron HEM-8712" + "BP Monitor", "$122","4.2",
            R.drawable.img_card_items
        ))
        categoriesModelArrayList.add(CategoriesHomeModel("Accu-check Active + Test Strip", "$122","4.2",
            R.drawable.img_card_items
        ))
        categoriesModelArrayList.add(CategoriesHomeModel("Omron HEM-8712" +"BP Monitor","$122", "4.2",
            R.drawable.img_card_items
        ))
        categoriesModelArrayList.add(CategoriesHomeModel("Accu-check Active+Test Strip","$122", "4.2",
            R.drawable.img_card_items
        ))// we are initializing our adapter class and passing our arraylist to it.
        val categoriesHomeAdapter = CategoriesHomeAdapter(this, categoriesModelArrayList)


        val LayoutManager = GridLayoutManager(context,2)
        categoriesRV.layoutManager = LayoutManager
        categoriesRV.adapter = categoriesHomeAdapter
        super.onViewCreated(view, savedInstanceState)

        }
    }
