package com.app.poster.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.app.poster.R
import com.app.poster.data.products.Product

class ProductDetails : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var price: TextView
    private lateinit var description: TextView
    private var product: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        viewModel.isViewed = true

        product = intent.getParcelableExtra<Product>("PRODUCT")

        image = findViewById(R.id.image)
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        price = findViewById(R.id.price)

        image.load(product?.image) {
            crossfade(true)
        }

        title.text = product?.title
        price.text = "${product?.price} tk"
        description.text = product?.description


    }
}