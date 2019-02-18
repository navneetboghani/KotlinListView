package com.navneet.kotlinlistview.config


import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.navneet.kotlinlistview.AppControl
import com.navneet.kotlinlistview.R
import org.json.JSONObject



class WebService  {

    var serviceUrl = ""
    var jsonObject: JSONObject? = null
    var ishowPrograssBar = true
    var context: Context? = null

    var onResult: OnResult? = null

    interface OnResult {
        fun OnSuccess(result: JSONObject)

        fun OnFail(error: String)
    }
    constructor(serviceUrl: String, jsonObject: JSONObject, ishowPrograssBar: Boolean, context: Context)
    {
        this.serviceUrl = serviceUrl
        this.jsonObject = jsonObject
        this.ishowPrograssBar = ishowPrograssBar
        this.context = context
    }

    fun WebService(serviceUrl: String, jsonObject: JSONObject, ishowPrograssBar: Boolean, context: Context){
        this.serviceUrl = serviceUrl
        this.jsonObject = jsonObject
        this.ishowPrograssBar = ishowPrograssBar
        this.context = context


    }

    fun getData(method: Int, onResult: OnResult) {
        try {
            this.onResult = onResult
            if (ishowPrograssBar)
                CommonFunctions.createProgressBar(context, context?.getString(R.string.msg_please_wait))
            val jsonObjReq = JsonObjectRequest(method, serviceUrl, jsonObject, RSJsonObjectListener, REsErrorListener)
            jsonObjReq.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            Logger.debugE("jsonObjReq url--->", serviceUrl + " jsondata\n " + jsonObject.toString())
            if (AppControl.getInstance() != null)
                AppControl.getInstance()?.addToRequestQueue(jsonObjReq)
            else
                onResult.OnFail(context?.getString(R.string.msg_server_error)!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getData(onResult: OnResult) {
        try {
            this.onResult = onResult
            if (ishowPrograssBar)
                CommonFunctions.createProgressBar(context, context?.getString(R.string.msg_please_wait))
            val jsonObjReq =
                JsonObjectRequest(Request.Method.POST, serviceUrl, jsonObject, RSJsonObjectListener, REsErrorListener)
            jsonObjReq.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            Logger.debugE("jsonObjReq url--->", "$serviceUrl jsondata\n $jsonObject")
            if (AppControl.getInstance() != null)
                AppControl.getInstance()?.addToRequestQueue(jsonObjReq)
            else
                onResult.OnFail(context!!.getString(R.string.msg_server_error))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    var RSJsonObjectListener: Response.Listener<JSONObject> = object : Response.Listener<JSONObject> {

        override fun onResponse(response: JSONObject) {
            try {
                if (ishowPrograssBar)
                    CommonFunctions.destroyProgressBar()
                Logger.debugE("RSJsonObjectListener Response--->", response.toString())
                onResult?.OnSuccess(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    var REsErrorListener: Response.ErrorListener = object : Response.ErrorListener {
        override fun onErrorResponse(error: VolleyError) {
            try {
                if (ishowPrograssBar)
                    CommonFunctions.destroyProgressBar()
                VolleyLog.d("", "Error: " + error.message)
                onResult!!.OnFail(context!!.getString(R.string.msg_server_error))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}