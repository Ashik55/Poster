package com.app.poster.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.poster.R
import com.app.poster.data.model.CatProduct
import com.app.poster.data.products.Product

class ProductAdapter(
    var productList: List<Product>,
    private val onItemClick: (Product) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val description: TextView = itemView.findViewById(R.id.description)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productList[position]

        holder.itemView.setOnClickListener { onItemClick(item) }

        holder.apply {
            title.text = item.title
            description.text = item.description
            price.text = item.price.toString()
            image.load(item.image) {
                crossfade(true)
            }

        }
    }

    override fun getItemCount(): Int = productList.size


    fun addNewProduct(response: List<Product>) {
        productList = response
        notifyDataSetChanged()
    }
}