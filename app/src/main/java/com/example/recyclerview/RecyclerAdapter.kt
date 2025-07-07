package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var list: ArrayList<ListData>, var clickInterface: OnClickInterface):

    RecyclerView.Adapter<RecyclerAdapter.Viewholder>(){
    class Viewholder( var view : View):RecyclerView.ViewHolder(view) {

        var name=view.findViewById<TextView>(R.id.tv1)
        var about=view.findViewById<TextView>(R.id.tv2)
        var des=view.findViewById<TextView>(R.id.tv3)
        var update=view.findViewById<Button>(R.id.btnUpdate)
        var delete=view.findViewById<Button>(R.id.btnDelete)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.Viewholder{
        var view= LayoutInflater.from(parent.context).inflate(R.layout.itemlayout,parent,false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
            return list.size
    }

    override fun onBindViewHolder(holder:RecyclerAdapter.Viewholder, position: Int) {

        holder.apply {
            name.setText(list[position].id)
            about.setText(list[position].title)
            des.setText(list[position].description)
            update.setOnClickListener {
               clickInterface.update(position)

            }
            delete.setOnClickListener {
                clickInterface.delete(position)

            }



        }
    }


    interface OnClickInterface {
        fun update(position: Int)
        fun delete(position: Int)
    }


}







