package com.navneet.kotlinlistview

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.android.volley.Request
import com.google.gson.Gson
import com.navneet.kotlinlistview.adpter.DataAdapter
import com.navneet.kotlinlistview.config.CommonFunctions
import com.navneet.kotlinlistview.config.WebService
import com.navneet.kotlinlistview.model.DataRes
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    @BindView(R.id.rv_list)
    lateinit var rv_list: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var  datadapter:DataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initcomnets();
    }

    @SuppressLint("WrongConstant")
    private fun initcomnets() {
        try {
            ButterKnife.bind(this);

            if(CommonFunctions.checkConnection(this))
            {
                getData()
                linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                rv_list.layoutManager = linearLayoutManager

            }
        } catch (e: Exception) {
        }


    }

    private fun getData() {
        try {
            var url:String="http://moneyfounder.com:9090/Advisory/ApiPackage";
            val rootObject= JSONObject()

            val webService = WebService(url,rootObject, true, this@MainActivity)
            webService.getData(Request.Method.POST, object : WebService.OnResult {

                override fun OnSuccess(result: JSONObject) {
                    val gson = Gson()
                    var dataResp = gson.fromJson(result.toString(), DataRes::class.java)
                        setadapter(dataResp)
                    Toast.makeText(this@MainActivity, result.toString(), Toast.LENGTH_SHORT).show()

                }

                override  fun OnFail(error: String) {
                    Toast.makeText(this@MainActivity, R.string.msg_server_error, Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
        }
    }

    private fun setadapter(dataResp: DataRes?) {
        try {
            datadapter= DataAdapter(this, dataResp?.data);
            rv_list.adapter=datadapter;
        } catch (e: Exception) {
        }
    }
}
