package com.example.curd_onlinesql_betterstructure.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curd_onlinesql_betterstructure.Model.mymodel
import com.example.curd_onlinesql_betterstructure.R


class myAdapter(
    var context: Context,
    var datalist: ArrayList<mymodel>,
    var OnBtnClick:OnclickFunction
)
    : RecyclerView.Adapter<myAdapter.ViewHolder>() {

    interface OnclickFunction{
        fun OnbtnUpdate(position: Int,name:String,lastname:String,userid:Int)
        fun OnbtnDelete(position: Int,name:String,lastname:String,userid:Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(context)
            .inflate(R.layout.userdata, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var mymodel=datalist[position]
        holder.tv_name.text=mymodel.name
        holder.tv_lastname.text=mymodel.email

        holder.btn_update.setOnClickListener {
            OnBtnClick.OnbtnUpdate(position, mymodel.name,mymodel.email,mymodel.id)
        }

        holder.btn_delete.setOnClickListener {
            OnBtnClick.OnbtnDelete(position,mymodel.name,mymodel.email,mymodel.id)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }




    class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        var tv_name=itemview.findViewById<TextView>(R.id.tv_name)
        var tv_lastname=itemview.findViewById<TextView>(R.id.tv_lastname)
        var btn_update=itemview.findViewById<TextView>(R.id.btn_update)
        var btn_delete=itemview.findViewById<TextView>(R.id.btn_delete)

    }
}