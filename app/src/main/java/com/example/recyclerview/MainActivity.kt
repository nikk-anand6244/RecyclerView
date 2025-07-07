package com.example.recyclerview

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() ,RecyclerAdapter.OnClickInterface{

    lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: RecyclerAdapter
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

        list.add(ListData(id = "one", title ="Name", description = "About student" ))
        list.add(ListData(id = "two",title ="class",description = "About" ))
        list.add(ListData(id = "one",title ="Name", description = "About student" ))
        list.add(ListData(id = "one",title ="Name", description = "About student" ))
        list.add(ListData(id = "one",title ="Name", description = "About student" ))
        list.add(ListData(id = "one",title ="Name", description = "About student" ))

        recyclerAdapter= RecyclerAdapter(list , this)
        binding.rv1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rv1.adapter=recyclerAdapter
        recyclerAdapter.notifyDataSetChanged()



        binding.fabBtn1.setOnClickListener {

            var dialog= Dialog(this)


            dialog.setContentView(R.layout.dialog_layout)

            var txtName= dialog.findViewById<EditText>(R.id.et1)
            var txtAbout= dialog.findViewById<EditText>(R.id.et2)
            var txtDes= dialog.findViewById<EditText>(R.id.et3)

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            dialog.show()


            var btnSave= dialog.findViewById<Button>(R.id.btnSave)
            var btnCancel= dialog.findViewById<Button>(R.id.btnCancel)

            btnSave.setOnClickListener {

                var name=txtName.text.toString()
                var about=txtAbout.text.toString()
                var des=txtDes.text.toString()
                recyclerAdapter.notifyDataSetChanged()
                list.add(ListData(id = name, title = about, description = des))
                Toast.makeText(this@MainActivity,
                    "${txtName.text}${txtDes.text}${txtAbout.text}", Toast.LENGTH_SHORT).show()

                dialog.dismiss()
            }

            btnCancel.setOnClickListener {

                list.add(ListData(id = txtName.text.toString()))
                list.add(ListData(title = txtDes.text.toString()))
                list.add(ListData(description = txtAbout.text.toString()))
                recyclerAdapter.notifyDataSetChanged()
                Toast.makeText(this@MainActivity,
                    "${txtName.text}${txtDes.text}${txtAbout.text}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

        }

    }

    override fun update(position: Int) {
        Toast.makeText(this, "Update Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun delete(position: Int) {
       list.removeAt(position)
        Toast.makeText(this, "Delete Clicked", Toast.LENGTH_SHORT).show()
    }


}