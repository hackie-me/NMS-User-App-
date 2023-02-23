package com.nms.user.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.Gson
import com.nms.user.ProductDetailsActivity
import com.nms.user.R
import com.nms.user.adapters.CategoriesAdapter
import com.nms.user.adapters.ProductsAdapter
import com.nms.user.models.CategoryModel
import com.nms.user.models.ProductModel
import com.nms.user.repo.CategoryRepository
import com.nms.user.repo.ProductRepository
import com.nms.user.service.Authentication
import com.nms.user.utils.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment(), ProductsAdapter.ClickListener
{

    private lateinit var userFullName: String
    private lateinit var userProfile : String
    private lateinit var rvCategories: RecyclerView
    private lateinit var rvProducts: RecyclerView
    private lateinit var imageSlider : ImageSlider
    private lateinit var btnIcoNotification: ImageView
    private lateinit var btnIcoShoppingBag: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize views
        initializeViews()

        // Cart and Notification button click listener
        btnIcoNotification.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, NotificationFragment())?.commit()
        }

        btnIcoShoppingBag.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, MyOrdersFragment())?.commit()
        }

        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.card_home,   ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider,ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider_1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider_2, ScaleTypes.CENTER_CROP))

        imageSlider.setImageList(imageList)

        imageSlider.setItemClickListener(object : ItemClickListener
        {
             override fun onItemSelected(position: Int) {
                 imageList.add(SlideModel(R.drawable.card_home,  ScaleTypes.CENTER_CROP))
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

        // fetch categories
        fetchCategories()

        // fetch products
        fetchProducts()

    }

    // Function to initialize views
    private fun initializeViews(){
        rvProducts = view?.findViewById(R.id.rvProducts)!!
        rvCategories = view?.findViewById(R.id.rvTopCategories)!!
        imageSlider = view?.findViewById(R.id.offerImageSlider)!!
        btnIcoNotification = view?.findViewById(R.id.imageIcoNotification)!!
        btnIcoShoppingBag = view?.findViewById(R.id.imageIcoShoppingBag)!!

        // get user full name and profile
        initializeUserData()
    }


    // Function to Initialize userdata from Token
    private fun initializeUserData(){
        userFullName = Authentication.getDataFromToken(requireContext(), "full_name")!!
        view?.findViewById<TextView>(R.id.txtFullName)?.text = "Hi, $userFullName"
    }

    // Function to Fetch categories from API
    private fun fetchCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = CategoryRepository.getAllCategories()
            val categories = Gson().fromJson(response.data, Array<CategoryModel>::class.java)
            if (response.code == 200){
                withContext(Dispatchers.Main){
                    // if categories are not empty then set categories
                    if (categories.isNotEmpty()){
                        // layout manager
                        rvCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        rvCategories.adapter = CategoriesAdapter(requireContext(), categories)
                    }
                }
            }
        }
    }

    // Function to Fetch products from API
    private fun fetchProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ProductRepository.getAllProducts()
            val products = Gson().fromJson(response.data, Array<ProductModel>::class.java)
            if (response.code == 200){
                withContext(Dispatchers.Main){
                    // if products are not empty then set products
                    if (products.isNotEmpty()){
                        // layout manager
                        rvProducts.layoutManager = GridLayoutManager(context,2)
                        rvProducts.adapter = ProductsAdapter(requireContext(), products, this@HomeFragment)
                    }
                }
            }
        }
    }

    override fun onProductCardClick(ProductModel: ProductModel) {
        // Open Product Details Activity with ProductModel
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra("productId", ProductModel.id)
        startActivity(intent)
    }
}

