package com.navneet.kotlinlistview

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley






class AppControl : Application() {

    val TAG = AppControl    ::class.java!!
        .getSimpleName()
    private var mRequestQueue: RequestQueue? = null
    public  val mImageLoader: ImageLoader? = null


    init{
        mInstance = this
    }

    override fun onCreate() {
        super.onCreate()





        // Enable Fabric only on Testing :

        // Crashlytics.getInstance().crash();
        mInstance = this

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    companion object{
        public  var mInstance: AppControl? = null
        fun getInstance() = mInstance;
    }
        @Synchronized
    fun getInstance(): AppControl? {
        return mInstance
    }


    fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(applicationContext)
        }

        return this.mRequestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        // set the default tag if tag is empty
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue()?.add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue()!!.add(req)
    }

    fun cancelPendingRequests(tag: Any) {
        mRequestQueue?.cancelAll(tag)
    }

    private fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            val runningProcesses = am.runningAppProcesses
            for (processInfo in runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (activeProcess in processInfo.pkgList) {
                        if (activeProcess == context.getPackageName()) {
                            isInBackground = false
                        }
                    }
                }
            }
        } else {

            val taskInfo = am.getRunningTasks(1)
            val componentInfo = taskInfo[0].topActivity
            if (componentInfo.packageName == context.getPackageName()) {
                isInBackground = false
            }
        }

        return isInBackground
    }
}