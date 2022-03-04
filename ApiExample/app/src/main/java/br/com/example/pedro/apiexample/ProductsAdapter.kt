package br.com.example.pedro.apiexample

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.apiexample.model.Product
import com.bumptech.glide.Glide

class ProductsAdapter(private val context: Context,
                      private var productList: List<Product>) : RecyclerView.Adapter<ProductsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layout = LayoutInflater.
            from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductsViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        Log.d("Adapter", "on bind")
        val item = productList[position]
        holder.bindViews(context, item)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setList(list: List<Product>){
        this.productList = list
        notifyDataSetChanged()
    }

}

class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val productImage: ImageView = itemView.findViewById(R.id.prod_image)
    private val productPrice: TextView = itemView.findViewById(R.id.prod_price)
    private val productName: TextView = itemView.findViewById(R.id.prod_name)

    fun bindViews(context: Context, product: Product) {
        productName.text = product.prname
        productPrice.text = product.prprice
        Glide.with(context).load(product.primage).into(productImage)
    }

}
