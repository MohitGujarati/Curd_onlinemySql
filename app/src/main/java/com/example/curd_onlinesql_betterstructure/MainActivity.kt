package com.example.curd_onlinesql_betterstructure

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curd_onlinesql_betterstructure.Adapter.myAdapter
import com.example.curd_onlinesql_betterstructure.Apis.retrofit_object
import com.example.curd_onlinesql_betterstructure.Model.mymodel
import com.example.curd_onlinesql_betterstructure.Model.update_method
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    lateinit var recview: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ed_name = findViewById<EditText>(R.id.ed_name)
        var ed_lastname = findViewById<EditText>(R.id.ed_lastname)
        var btn_submit = findViewById<Button>(R.id.btn_submit)
        recview = findViewById(R.id.recview)

        loaddata()

        btn_submit.setOnClickListener {

            val retrofit = retrofit_object.getapimethod

            val result = retrofit.complain(
                ed_name.text.toString(),
                ed_lastname.text.toString(),
                ed_name.text.toString(),
                ed_name.text.toString(),
            )

            result.enqueue(object : Callback<List<mymodel>?> {
                override fun onResponse(
                    call: Call<List<mymodel>?>,
                    response: Response<List<mymodel>?>
                ) {
                    loaddata()
                }

                override fun onFailure(call: Call<List<mymodel>?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "faild but done", Toast.LENGTH_SHORT).show()
                }
            })

            loaddata()
            Toast.makeText(this, "data inserted", Toast.LENGTH_SHORT).show()
            ed_name.text.clear()
            ed_lastname.text.clear()


        }

    }

    private fun loaddata() {

        recview.layoutManager = LinearLayoutManager(this)

        var retrofit = retrofit_object.getapimethod

        val result = retrofit.user_complains()
        var complaindatalist: ArrayList<mymodel> = ArrayList()

        result.enqueue(object : Callback<List<mymodel>?> {
            override fun onResponse(
                call: Call<List<mymodel>?>,
                response: Response<List<mymodel>?>
            ) {
                val data = response.body()
                if (data != null) {
                    complaindatalist.addAll(data)
                }

                var adapter = myAdapter(
                    this@MainActivity,
                    complaindatalist, object : myAdapter.OnclickFunction {
                        override fun OnbtnUpdate(
                            position: Int,
                            name: String,
                            lastname: String,
                            userid: Int
                        ) {
                            UpdateData(position, name, lastname, userid)
                            loaddata()


                        }

                        override fun OnbtnDelete(
                            position: Int,
                            name: String,
                            lastname: String,
                            userid: Int
                        ) {
                            DeleteData(position, name, lastname, userid)
                            loaddata()
                        }

                    }
                )

                recview.adapter = adapter


            }

            override fun onFailure(call: Call<List<mymodel>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "faild but done", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun DeleteData(position: Int, name: String, lastname: String, userid: Int) {

        val retrofit = retrofit_object.getapimethod
        val result = retrofit.delete_complain(userid)

        result.enqueue(object : Callback<List<mymodel>?> {
            override fun onResponse(
                call: Call<List<mymodel>?>,
                response: Response<List<mymodel>?>
            ) {
                recview.adapter?.notifyDataSetChanged()
                Toast.makeText(this@MainActivity, "deleted", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<List<mymodel>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "faild but done", Toast.LENGTH_SHORT).show()

            }
        })

    }

    private fun UpdateData(position: Int, name: String, lastname: String, userid: Int) {
        var d = Dialog(this)
        d.setContentView(R.layout.dialog_layout)
        d.setCancelable(true)
        var btn = d.findViewById<Button>(R.id.btnupdate)
        var dname = d.findViewById<EditText>(R.id.updated_name)
        var dlastname = d.findViewById<EditText>(R.id.updated_lastname)
        d.getWindow()?.setBackgroundDrawable(ColorDrawable(0))
        d.show()

        btn.setOnClickListener {
            var retrofit = retrofit_object.getapimethod

            val result = retrofit.user_complains_update(dname.text.toString(), userid)

            result.enqueue(object : Callback<List<update_method>?> {
                override fun onResponse(
                    call: Call<List<update_method>?>,
                    response: Response<List<update_method>?>
                ) {
                    Log.d(
                        "complainsupdate",
                        "${userid} ${dname} ${dlastname}," +
                                "response:- ${response.toString()}"
                    )
                    Toast.makeText(this@MainActivity, "complain updated", Toast.LENGTH_SHORT).show()
                    loaddata()
                    d.dismiss()
                }

                override fun onFailure(call: Call<List<update_method>?>, t: Throwable) {
                    Log.d(
                        "complainsupdate",
                        "${userid} ${dname} ${dlastname}," +
                                "response:- ${t.toString()}"
                    )
                    Toast.makeText(this@MainActivity, "Updated", Toast.LENGTH_SHORT).show()

                    loaddata()
                }
            })
            d.dismiss()

        }

    }

}