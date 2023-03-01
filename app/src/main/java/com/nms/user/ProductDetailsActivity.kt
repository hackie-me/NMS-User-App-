package com.nms.user

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.Gson
import com.nms.user.models.CartModel
import com.nms.user.models.ProductModel
import com.nms.user.repo.CartRepository
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
    private lateinit var txtProductName: TextView
    private lateinit var txtProductPrice: TextView
    private lateinit var txtProductDescription: TextView
    private lateinit var txtProductImage: ImageView
    private lateinit var txtProductRating: TextView
    private lateinit var txtProductTotalRating: TextView
    private lateinit var txtProductTotalReviews: TextView
    private lateinit var btnIcoBack: ImageView
    private lateinit var btnIcoNotification: ImageView
    private lateinit var btnIcoShoppingBag: ImageView
    private lateinit var ivIcoAddToCartPlus: ImageView
    private lateinit var txtAddToCart: TextView
    private lateinit var linearColumnaddtocart: LinearLayout

    private lateinit var productId: String
    private lateinit var productName: String
    private lateinit var productPrice: String
    private lateinit var productDescription: String

    private lateinit var ratingReviews: RatingReviews
    private lateinit var txtProductAverageRating: TextView
    private lateinit var txtProductRatingCount: TextView

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

        // Check if product is already in cart
        isProductInCart()
    }

    override fun onResume() {
        super.onResume()
        // Get product details from server and set to views if product id is not null or empty
        if (productId.isNotEmpty()) {
            getProductDetails()
        } else {
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
        txtProductAverageRating = findViewById(R.id.txtProductAverageRating)
        txtProductRatingCount = findViewById(R.id.txtProductRatingCount)
        linearColumnaddtocart = findViewById(R.id.linearColumnaddtocart)

        // Handle product image slider
        handleProductImageSlider()

        // Initialize rating reviews
        initRatingReviews()

        // Handle common clicks
        handleCommonClicks()

        // Handle add to cart click
        linearColumnaddtocart.setOnClickListener {
            handleAddToCartClick()
        }
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

        // Random Average Rating for testing in float value (0.0 to 5.0)
        val randomRating = Random().nextInt(5)
        val ratingDecimal = Random().nextInt(9)
        var averageRating = "$randomRating.$ratingDecimal"
        if (randomRating == 0) {
            averageRating = "1.$ratingDecimal"
        }
        txtProductAverageRating.text = averageRating

        // Random Rating Count for testing in integer value (0 to 999999)
        val ratingCount = Random().nextInt(999999)
        txtProductRatingCount.text = ratingCount.toString()
    }

    // Function to Handle Common Clicks
    private fun handleCommonClicks() {
        btnIcoBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnIcoNotification.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fragment", "notification")
            startActivity(intent)
        }
        btnIcoShoppingBag.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    // Function to get the product details from server and set the data to views
    private fun getProductDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ProductRepository.getProductById(productId)
            val products = Gson().fromJson(response.data, ProductModel::class.java)
            withContext(Dispatchers.Main) {
                if (response.code == 200) {
                    txtProductName.text = products.name
                    txtProductPrice.text = products.price
                    productPrice = products.price
                    txtProductDescription.text = products.description
                } else {
                    Helper.showSnackBar(
                        findViewById(android.R.id.content),
                        "Error: ${response.message}"
                    )
                }
            }
        }
    }

    // Function to Add product to cart
    private fun addToCart() {
        val cartModel = CartModel(
            pid = productId,
            price = txtProductPrice.text.toString()
        )
        CoroutineScope(Dispatchers.IO).launch {
            val response = CartRepository.addToCart(this@ProductDetailsActivity, cartModel)
            withContext(Dispatchers.Main) {
                if (response.code == 201) {
                    Helper.showSnackBar(
                        findViewById(android.R.id.content),
                        "Product added to cart successfully"
                    )
                    txtAddToCart.text = "Go to Cart"
                }
            }
        }
    }

    // Function to check if product is in cart
    private fun isProductInCart(): Boolean {
        CoroutineScope(Dispatchers.IO).launch {
            val response = CartRepository.getCartItems(this@ProductDetailsActivity)
            withContext(Dispatchers.Main) {
                if (response.code == 200) {
                    val cartItems = Gson().fromJson(response.data, Array<CartModel>::class.java)
                    for (item in cartItems) {
                        if (item.pid == productId) {
                            txtAddToCart.text = "Go to Cart"
                            return@withContext
                        } else {
                            txtAddToCart.text = "Add to Cart"
                        }
                    }
                }
            }
        }
        return false
    }

    // Function to handle add to cart click
    private fun handleAddToCartClick() {
        if (txtAddToCart.text == "Add to Cart") {
            addToCart()
        } else {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}