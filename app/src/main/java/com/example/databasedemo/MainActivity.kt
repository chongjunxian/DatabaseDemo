package com.example.databasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.databasedemo.dao.ProductDao
import com.example.databasedemo.database.ProductDB
import com.example.databasedemo.databinding.ActivityMainBinding
import com.example.databasedemo.entity.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = ProductDB.getInstance(applicationContext).productDao

        binding.btnInsert.setOnClickListener(){

            val p = Product(0,"Iphone",350.00)

            CoroutineScope(IO).launch {
                dao.addProduct(p)
            }



            //dao.addProduct(p)
        }

        binding.btnRead.setOnClickListener(){

            var nameList = ""

            CoroutineScope(IO).launch {
                val productList = dao.getAll()

                productList.forEach{ product ->       //forEach = for loop
                    nameList += product.description + "\n"
                }

                CoroutineScope(Main).launch{
                    binding.tvResult.text = nameList
                }
            }
        }
    }
}