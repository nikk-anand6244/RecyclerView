package com.example.recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var recyclerAdapter: RecyclerAdapter
    var list= arrayListOf<ListData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        list.add(ListData(title ="Name", description = "About student" ))
        list.add(ListData(title ="class", description = "About student" ))
        list.add(ListData(title ="Name", description = "About student" ))

        recyclerAdapter=RecyclerAdapter(list)
        binding.rv1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rv1.adapter=recyclerAdapter
        recyclerAdapter.notifyDataSetChanged()

    }
}