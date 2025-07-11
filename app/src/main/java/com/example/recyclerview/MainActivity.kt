package com.example.recyclerview

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() ,RecyclerAdapter.OnClickInterface{

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: RecyclerAdapter
    private var list= arrayListOf<ListData>()

    private val db= Firebase.firestore
    private var collectionName= "Users"

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

//        list.add(ListData(id = "one", title ="Name", description = "About student" ))
//        list.add(ListData(id = "two",title ="class",description = "About" ))
//        list.add(ListData(id = "one",title ="Name", description = "About student" ))
//        list.add(ListData(id = "one",title ="Name", description = "About student" ))
//        list.add(ListData(id = "one",title ="Name", description = "About student" ))
//        list.add(ListData(id = "one",title ="Name", description = "About student" ))

        recyclerAdapter= RecyclerAdapter(list , this)
        binding.rv1.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rv1.adapter=recyclerAdapter
        recyclerAdapter.notifyDataSetChanged()

        db.collection(collectionName).addSnapshotListener{ snapshorts, e ->
            if (e != null){
                return@addSnapshotListener
            }
            for ( snapshort in snapshorts!!.documentChanges){

                val userModel = convertObject(snapshort.document)

                when(snapshort.type){
                    DocumentChange.Type.ADDED -> {
                        userModel?.let {list.add(it)}
                        Log.e("", "userModelList ${list.size}")
                        Log.e("", "userModelList ${list}")
                    }
                    DocumentChange.Type.MODIFIED -> {
                        userModel?.let {
                            val index = getIndex(userModel)
                            if (index > -1)
                                list.set(index, it)
                        }
                    }
                    DocumentChange.Type.REMOVED ->{
                        userModel?.let {
                            var index = getIndex(userModel)
                            if (index > -1)
                                list.removeAt(index)
                        }
                    }
                }
            }
            recyclerAdapter.notifyDataSetChanged()
        }
// Floating Action Button
        binding.fabBtn1.setOnClickListener {

            val dialog= Dialog(this)
            dialog.setContentView(R.layout.dialog_layout)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            dialog.show()

            val txtName= dialog.findViewById<EditText>(R.id.et1)
            val txtAbout= dialog.findViewById<EditText>(R.id.et2)
            val txtDes= dialog.findViewById<EditText>(R.id.et3)
            val btnSave= dialog.findViewById<Button>(R.id.btnSave)
            val btnCancel= dialog.findViewById<Button>(R.id.btnCancel)

            btnSave.setOnClickListener {

                try {

                val name=txtName.text.toString()
                val about=txtAbout.text.toString()
                val des=txtDes.text.toString()
//                list.add(ListData(id = name, title = about, description = des))
                db.collection(collectionName).add(ListData(title = name, about = about, description = des))

                    .addOnSuccessListener {
                        recyclerAdapter.notifyDataSetChanged()

                        Toast.makeText(this@MainActivity,
                            "${txtName.text}${txtDes.text}${txtAbout.text}", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Add Failed", Toast.LENGTH_SHORT).show()
                    }

                }catch (e :Exception){
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
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


    private fun convertObject(snapshot: QueryDocumentSnapshot):ListData?{
        val categoriesModel:ListData? =snapshot.toObject(ListData::class.java)
        categoriesModel?.id = snapshot.id ?: ""
        return categoriesModel
    }
    private fun getIndex(categoriesModel: ListData): Int{

        var index = -1
        index = list.indexOfFirst { element ->
            element.id?.equals(categoriesModel.id) == true
        }
        return index
    }

    override fun update(position: Int) {

        val dialog= Dialog(this)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
        dialog.show()

        val txtName= dialog.findViewById<EditText>(R.id.et1)
        val txtAbout= dialog.findViewById<EditText>(R.id.et2)
        val txtDes= dialog.findViewById<EditText>(R.id.et3)
        var btnSave= dialog.findViewById<Button>(R.id.btnSave)
        var btnCancel= dialog.findViewById<Button>(R.id.btnCancel)

        btnSave.setOnClickListener {

            try {

                var name=txtName.text.toString()
                var about=txtAbout.text.toString()
                var des=txtDes.text.toString()

//                list.add(ListData(id = name, title = about, description = des))
                db.collection(collectionName).add(ListData(title = name, about = about, description = des))

                    .addOnSuccessListener {
                        recyclerAdapter.notifyDataSetChanged()
                        Toast.makeText(this@MainActivity,
                            "${txtName.text}${txtDes.text}${txtAbout.text}", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Add Failed", Toast.LENGTH_SHORT).show()
                    }

            }catch (e :Exception){
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
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

//        list[position].id?.let{
//            db.collection(collectionName).document(it)
//                .set(ListData(id = "Document Updated", title = "Document Updated", description = "Document Updated"))
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(this, "Update Failed ", Toast.LENGTH_SHORT).show()
//
//                }
//        }

    }








    override fun delete(position: Int) {

        AlertDialog.Builder(this).apply {

            setTitle("Delete")
            setMessage("Are you sure you want to delete")
            setPositiveButton("yes"){_,_ ->
                list.removeAt(position)
                Toast.makeText(this@MainActivity,"Update Clicked",Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("no"){_,_ ->}
            setNeutralButton("No"){_,_ ->

            }
        }

        list.removeAt(position).id?.let {
           db.collection(collectionName).document(it).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()

            }

       }
    }

}