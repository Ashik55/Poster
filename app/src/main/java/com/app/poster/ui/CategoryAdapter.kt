package com.app.poster.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.poster.R
import com.app.poster.data.model.CatProduct
import com.app.poster.data.products.Product

class CategoryAdapter(
    var catProductList: List<CatProduct>,
    private val onProductClick: (Product) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val productRecyclerview: RecyclerView = itemView.findViewById(R.id.productList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = catProductList[position]


        holder.apply {
            categoryName.text = item.categoryName?.capitalize()
            productRecyclerview.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            productRecyclerview.addItemDecoration(
                ItemSpacingDecoration(
                    horizontal = 4,
                    vertical = 16
                )
            )
            productRecyclerview.adapter = ProductAdapter(item.productList) {
                onProductClick
            }


        }
    }

    override fun getItemCount(): Int = catProductList.size


    fun addNewCategory(response: List<CatProduct>) {
        catProductList = response
        notifyDataSetChanged()
    }
}