package com.nms.user

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.nms.user.models.ProductModel
import com.nms.user.repo.ProductRepository
import com.nms.user.utils.Helper
import com.taufiqrahman.reviewratings.BarLabels
import com.taufiqrahman.reviewratings.RatingReviews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var imageSlider: ImageSlider
    private lateinit var txtProductName : TextView
    private lateinit var txtProductPrice : TextView
    private lateinit var txtProductDescription : TextView
    private lateinit var txtProductImage : ImageView
    private lateinit var txtProductRating : TextView
    private lateinit var txtProductTotalRating : TextView
    private lateinit var txtProductTotalReviews : TextView
    private lateinit var btnIcoBack : ImageView
    private lateinit var btnIcoNotification : ImageView
    private lateinit var btnIcoShoppingBag : ImageView
    private lateinit var ivIcoAddToCartPlus : ImageView
    private lateinit var txtAddToCart : TextView

    private lateinit var productId : String
    private lateinit var productName : String
    private lateinit var productPrice : String
    private lateinit var productDescription : String

    private lateinit var ratingReviews: RatingReviews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Initialize views
        initViews()

        // Get the product id from intent if not found then finish the activity
        if (intent.getStringExtra("productId") == null) {
            finish()
        }
        productId = intent.getStringExtra("productId").toString()
        Snackbar.make(findViewById(android.R.id.content), "Product id: $productId", Snackbar.LENGTH_LONG).show()

    }

    override fun onResume() {
        super.onResume()
        // Get product details from server and set to views if product id is not null or empty
        if (productId.isNotEmpty()) {
            getProductDetails()
        }else{
            finish()
        }
    }

    // Function to handler image slider item change
    private fun handleProductImageSlider() {
        val imageSlider = findViewById<ImageSlider>(R.id.prdImageSlider)
        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.card_home, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider_1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.card_slider_2, ScaleTypes.CENTER_CROP))

        imageSlider.setImageList(imageList)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                imageList.add(SlideModel(R.drawable.card_home, ScaleTypes.CENTER_CROP))
            }
        })

        imageSlider.setTouchListener(object : TouchListener {
            override fun onTouched(touched: ActionTypes) {
                if (touched == ActionTypes.DOWN) {
                    imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP) {
                    imageSlider.startSliding(5000)
                }
            }
        })
    }

    // Function to initialize the views
    private fun initViews() {
        imageSlider = findViewById(R.id.prdImageSlider)
        txtProductName = findViewById(R.id.txtProductName)
        txtProductPrice = findViewById(R.id.txtProductPrice)
        txtProductDescription = findViewById(R.id.txtProductDescription)
        ratingReviews = findViewById(R.id.txtProductRating)
        // txtProductTotalRating = findViewById(R.id.txtProductTotalRating)
        // txtProductTotalReviews = findViewById(R.id.txtProductTotalReviews)
        btnIcoBack = findViewById(R.id.btnIcoBackArrow)
        btnIcoNotification = findViewById(R.id.btnIcoNotification)
        btnIcoShoppingBag = findViewById(R.id.btnIcoShoppingBag)
        ivIcoAddToCartPlus = findViewById(R.id.ivIcoAddToCartPlus)
        txtAddToCart = findViewById(R.id.txtAddToCart)

        // Handle product image slider
        handleProductImageSlider()

        // Initialize rating reviews
        initRatingReviews()

        // Handle common clicks
        handleCommonClicks()
    }

    // Function to initialize rating reviews
    private fun initRatingReviews() {
        val rating = intArrayOf(
            Random().nextInt(100),
            Random().nextInt(100),
            Random().nextInt(100),
            Random().nextInt(100),
            Random().nextInt(100)
        )
        ratingReviews.createRatingBars(100, BarLabels.STYPE3, Color.parseColor("#0f9d58"), rating)
    }

    // Function to Handle Common Clicks
    private fun handleCommonClicks() {
        btnIcoBack.setOnClickListener {
            finish()
        }
        btnIcoNotification.setOnClickListener {
            Helper.showSnackBar(findViewById(android.R.id.content), "Notification Clicked")
        }
        btnIcoShoppingBag.setOnClickListener {
            Helper.showSnackBar(findViewById(android.R.id.content), "Shopping Bag Clicked")
        }
        ivIcoAddToCartPlus.setOnClickListener {
            Helper.showSnackBar(findViewById(android.R.id.content), "Add to cart Clicked")
        }
        txtAddToCart.setOnClickListener {
            Helper.showSnackBar(findViewById(android.R.id.content), "Add to cart Clicked")
        }
    }

    // Function to get the product details from server and set the data to views
    private fun getProductDetails() {
        CoroutineScope(Dispatchers.IO).launch{
            val response = ProductRepository.getProductById(productId)
            val products = Gson().fromJson(response.data, ProductModel::class.java)
            withContext(Dispatchers.Main){
                if(response.code == 200) {
                    txtProductName.text = products.name
                    txtProductPrice.text = products.price
                    txtProductDescription.text = products.description
                } else {
                    Helper.showSnackBar(findViewById(android.R.id.content), "Error: ${response.message}")
                }
            }
        }
    }
}