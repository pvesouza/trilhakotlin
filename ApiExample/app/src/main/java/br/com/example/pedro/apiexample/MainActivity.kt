package br.com.example.pedro.apiexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.apiexample.api.MyRetrofit
import br.com.example.pedro.apiexample.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recycleView:RecyclerView
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.recycleView = findViewById(R.id.product_list_recycler)
        this.recycleView.layoutManager = LinearLayoutManager(this)
        this.productsAdapter = ProductsAdapter(this, mutableListOf())
        this.recycleView.adapter = this.productsAdapter

        getData()
    }

    private fun getData() {
        val call: Call<List<Product>> =
            MyRetrofit.instance?.productApi()?.getProductApi() as Call<List<Product>>
        call.enqueue(Calback())
    }

    inner class Calback : Callback<List<Product>> {

        override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
            var list = response.body()
            if (list != null){
                Log.d("OnResponse", list[0].prname)
                productsAdapter.setList(list)
            }else{
                Log.d("OnResponse", "Lista nula")
            }
        }

        override fun onFailure(call: Call<List<Product>>, t: Throwable) {
            Toast.makeText(this@MainActivity, R.string.label_requisition_error, Toast.LENGTH_LONG).show()
        }
    }
}


