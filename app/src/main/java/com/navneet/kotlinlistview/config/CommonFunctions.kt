package com.navneet.kotlinlistview.config

import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.content.DialogInterface
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Patterns
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.navneet.kotlinlistview.R
import com.navneet.kotlinlistview.reciver.ConnectivityReceiver
import com.nispok.snackbar.Snackbar
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object CommonFunctions {
    var errMessage = ""
    var tag = "CommonFunctions :"
    /**
     * Create Progress bar
     */
    var pd: ProgressDialog? = null

    /**
     * Check Internet connection available or not
     *
     * @return
     */
    fun checkConnection(activity: Activity): Boolean {
        val isConnected = ConnectivityReceiver.isConnected()
        if (!isConnected)
            showSnack(activity, activity.resources.getString(R.string.msg_NO_INTERNET_MSG))
        return isConnected

    }

    // Showing the status in Snackbar
    fun showSnack(activity: Activity, errMessage: String) {
        Snackbar.with(activity) // context
            .text(errMessage) // text to display
            .textColor(Color.RED)
            .color(Color.BLACK)
            .show(activity) // activity where it is displayed
    }

    fun getIpaddress(activity: Activity): String {
        var ipAddress = ""
        try {
            val wifiManager = activity.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
            ipAddress = wifiManager.connectionInfo.ipAddress.toString();

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ipAddress
    }


   /* @Throws(IOException::class)
    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader = java.io.BufferedReader(
            java.io.InputStreamReader(inputStream)
        )
        var line = ""
        var result = ""
        while ((line = bufferedReader.readLine()) != null)
            result += line
        inputStream.close()
        return result

    }*/

    fun isNetworkAvailable(mContext: Context): Boolean {
        val connec = mContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        // ARE WE CONNECTED TO THE NET
        if (connec.getNetworkInfo(0).state == android.net.NetworkInfo.State.CONNECTED
            || connec.getNetworkInfo(0).state == android.net.NetworkInfo.State.CONNECTING
            || connec.getNetworkInfo(1).state == android.net.NetworkInfo.State.CONNECTING
            || connec.getNetworkInfo(1).state == android.net.NetworkInfo.State.CONNECTED
        ) {
            // MESSAGE TO SCREEN FOR TESTING (IF REQ)
            // Toast.makeText(this, connectionType + " connected",
            // Toast.LENGTH_SHORT).show();
            return true

        } else if (connec.getNetworkInfo(0).state == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(
                1
            ).state == android.net.NetworkInfo.State.DISCONNECTED
        ) {
            // System.out.println("Not Connected");
            return false
        }
        return false
    }

    /**
     * Convert Stream to String
     *
     * @param is
     * @return
     * @throws Exception
     */
  /*  @Throws(Exception::class)
    fun convertStreamToString(`is`: InputStream): String {
        val reader = java.io.BufferedReader(
            java.io.InputStreamReader(
                `is`,
                "UTF-8"
            )
        )
        val sb = StringBuilder()
        var line: String? = null
        while ((line = reader.readLine()) != null) {
            sb.append(line!! + "\n")
        }
        `is`.close()
        return sb.toString()
    }*/

    /**
     * Create Directories
     *
     * @param filePath
     * @return
     */
    fun createDirs(filePath: String): Boolean {
        Logger.debugE(filePath.substring(0, filePath.lastIndexOf("/")))
        val f = java.io.File(filePath.substring(0, filePath.lastIndexOf("/")))
        Logger.debugE((f.mkdirs()).toString() + "")
        return f.mkdirs()
    }

    fun showAlert(c: Context, title: String, msg: String) {
        val builder = AlertDialog.Builder(c)

           builder.setMessage(msg)
        builder.setTitle(title)
        builder.setPositiveButton(c.getString(android.R.string.ok),
                DialogInterface.OnClickListener { arg0, arg1 -> }).show()
    }

    @android.annotation.SuppressLint("NewApi")
    fun showAlert(c: Context, title: String, msg: String, icon: Int) {
        val builder = AlertDialog.Builder(c)

        builder
            .setMessage(msg)
            .setIcon(icon)
            .setTitle(title)
            .setPositiveButton(c.getString(android.R.string.ok),
                DialogInterface.OnClickListener { arg0, arg1 -> }).show()
    }

    fun showAlert(
        c: Context, title: String, msg: String, icon: Int,
        listener: DialogInterface.OnClickListener
    ) {
        android.app.AlertDialog.Builder(c).setMessage(msg).setIcon(icon)
            .setTitle(title).setCancelable(false)
            .setPositiveButton(c.getString(android.R.string.ok), listener).show()
    }

    fun showAlert(
        c: Context, title: String, msg: String, icon: Int,
        listener: DialogInterface.OnClickListener,
        cancelListener: DialogInterface.OnClickListener
    ) {
        android.app.AlertDialog.Builder(c)
            .setMessage(msg)
            .setIcon(icon)
            .setTitle(title)
            .setPositiveButton(c.getString(android.R.string.ok), listener)
            .setNegativeButton(c.getString(android.R.string.cancel), cancelListener)
            .show()
    }

    fun showAlert(
        c: Context, title: String, msg: String, icon: Int,
        listener: DialogInterface.OnClickListener,
        cancelListener: DialogInterface.OnClickListener, cancalable: Boolean
    ) {
        android.app.AlertDialog.Builder(c)
            .setMessage(msg)
            .setIcon(icon)
            .setTitle(title)
            .setPositiveButton(c.getString(android.R.string.ok), listener)
            .setCancelable(cancalable)
            .setNegativeButton(c.getString(android.R.string.cancel), cancelListener)
            .show()
    }

    fun showAlert(
        c: Context, title: String, msg: String, icon: Int,
        listener: DialogInterface.OnClickListener, cancalable: Boolean
    ) {
        android.app.AlertDialog.Builder(c).setMessage(msg).setIcon(icon)
            .setTitle(title)
            .setPositiveButton(c.getString(android.R.string.ok), listener)
            .setCancelable(cancalable).show()
    }

    fun setPreference(c: Context, pref: String, `val`: Boolean) {
        val e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putBoolean(pref, `val`)
        e.commit()
    }

    fun getPreference(
        context: Context, pref: String,
        def: Boolean
    ): Boolean {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(pref, def)
    }

    fun setPreference(c: Context, pref: String, `val`: Int) {
        val e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putInt(pref, `val`)
        e.commit()
    }

    fun getPreference(context: Context, pref: String, def: Int): Int {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context).getInt(
            pref, def
        )
    }

    fun setPreference(c: Context, pref: String, `val`: Float) {
        val e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putFloat(pref, `val`)
        e.commit()
    }

    fun getPreference(context: Context, pref: String, def: Float): Float {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context).getFloat(
            pref, def
        )
    }

    fun setPreference(c: Context, pref: String, `val`: Long) {
        val e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putLong(pref, `val`)
        e.commit()
    }

    fun getPreference(context: Context, pref: String, def: Long): Long {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context).getLong(
            pref, def
        )
    }

    fun setPreference(c: Context, pref: String, `val`: Double) {
        val e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putLong(pref, java.lang.Double.doubleToLongBits(`val`))
        e.commit()
    }

    fun getPreference(context: Context, pref: String, def: Double): Double {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context).getLong(
            pref, java.lang.Double.doubleToLongBits(def)
        ).toDouble()
    }

    fun setPreference(c: Context, pref: String, `val`: String) {
        val e = android.preference.PreferenceManager.getDefaultSharedPreferences(c).edit()
        e.putString(pref, `val`)
        e.commit()
    }

    fun getPreference(context: Context, pref: String, def: String): String? {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context)
            .getString(pref, def)
    }

    fun title(string: String): String {
        var ret = ""
        val sb = StringBuffer()
        val match = Pattern.compile(
            "([a-z])([a-z]*)",
            Pattern.CASE_INSENSITIVE
        ).matcher(string)
        while (match.find()) {
            match.appendReplacement(sb, match.group(1).toUpperCase() + match.group(2).toLowerCase())
        }
        ret = match.appendTail(sb).toString()
        return ret
    }

 /*   fun toTitleCase(input: String): String {
        val titleCase = StringBuilder()
        var nextTitleCase = true
        for (c in input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c)
                nextTitleCase = false
            }
            titleCase.append(c)
        }
        return titleCase.toString()
    }*/

    /**
     * Enables/Disables all child views in a view group.
     *
     * @param viewGroup the view group
     * @param enabled   `true` to enable, `false` to disable the
     * views.
     */
    fun enableDisableViewGroup(
        viewGroup: android.view.ViewGroup,
        enabled: Boolean, exceptionalViews: Array<android.view.View>
    ) {
        val childCount = viewGroup.childCount
        for (i in 0 until childCount) {
            val view = viewGroup.getChildAt(i)
            view.isEnabled = enabled
            for (j in exceptionalViews.indices)
                if (view.id == exceptionalViews[j].id) {
                    view.isEnabled = true
                    break
                }
            if (view is android.view.ViewGroup) {
                enableDisableViewGroup(
                    view as android.view.ViewGroup, enabled,
                    exceptionalViews
                )
            }
        }
    }

    fun createProgressBar(
        context: Context?,
        strMsg: String?
    ): ProgressDialog? {
        if (pd == null) {
            pd = ProgressDialog(context, R.style.ProThemeOrange)
            pd!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pd!!.setMessage(strMsg)
            pd!!.setCancelable(false)
            pd!!.show()
        } else {
            pd!!.dismiss()
            pd = null
            pd = ProgressDialog(context, R.style.ProThemeOrange)
            pd!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pd!!.setMessage(strMsg)
            pd!!.setCancelable(false)
            pd!!.show()

        }
        return pd
    }

    fun createProgressBar(
        context: Context,
        strMsg: String, isCancelable: Boolean
    ): ProgressDialog {
        pd = ProgressDialog(context, R.style.ProThemeOrange)
        pd!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd!!.setMessage(strMsg)
        pd!!.setCancelable(isCancelable)
        pd!!.show()
        return pd as ProgressDialog
    }

    /**
     * Destroy progress bar
     */
    fun destroyProgressBar() {
        if (pd != null)
            pd!!.dismiss()
    }

    // Region startactivity
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun changeactivity(context: Context, Act_des: Class<*>) {
        val i = android.content.Intent(context, Act_des)

        (context as Activity).startActivityForResult(i, 0)
        (context as Activity).finish()
        if (GlobalVariable.slide_act_flag) {
            (context as Activity).overridePendingTransition(
                R.anim.slide_in_left, R.anim.slide_out_right
            )
            GlobalVariable.slide_act_flag = false
        } else {
            (context as Activity).overridePendingTransition(
                R.anim.slide_in_right, R.anim.slide_out_left
            )
            GlobalVariable.slide_act_flag = true
        }
    }
    // EndRegion

    // Region hidekeyboad
    fun hideSoftKeyboard(activity: Activity, e: android.widget.EditText) {
        val inputMethodManager = activity
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(e.windowToken, 0)
    }

    // EndRegion
    // Region Toast function
    fun ToastMessage(a: Activity, message: String) {
        try {
            android.widget.Toast.makeText(a, message, android.widget.Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // TODO: handle exception
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

    // EndRegion
    //Region exit from application
    @android.annotation.SuppressLint("NewApi")
    fun alertboxshow(activity: Activity) {
        // TODO Auto-generated method stub
        try {
            val builder = android.app.AlertDialog.Builder(
                activity, android.app.AlertDialog.THEME_HOLO_LIGHT
            )
            builder.setTitle(activity.resources.getString(R.string.app_name))
            //	builder.setIcon(R.drawable.iconlogo);
            builder.setMessage("Do you want to exit?")
            builder.setPositiveButton("YES",
                DialogInterface.OnClickListener { dialog, which ->
                    // System.exit(0);
                    // Home.this.finish();
                    val relaunch = android.content.Intent(
                        activity,
                        activity.javaClass
                    )
                        .addFlags(
                            (android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                                    or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    or android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        )
                    activity.startActivity(relaunch)
                    activity.finish()
                })
            builder.setNegativeButton("NO",
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            val dialog = builder.create()
            dialog.show()
        } catch (e: Exception) {
            // TODO: handle exception
            Logger.debugE("LoginActivity", (e).toString() + "")
        }

    }

    //EndRegion
   /* @Throws(org.json.JSONException::class)
    fun jsonToMap(json: JSONObject): Map<*, *> {
        var retMap: Map<String, Any> = java.util.HashMap()
        if (json !== JSONObject.NULL) {
            retMap = toMap(json)
        }
        return retMap
    }*/

    @Throws(org.json.JSONException::class)
    fun toMap(`object`: JSONObject): Map<*, *> {
        val map = java.util.HashMap<String, Any>()
        val keysItr = `object`.keys()
        while (keysItr.hasNext()) {
            val key = keysItr.next()
            var value = `object`.get(key)
            if (value is org.json.JSONArray) {
                value = toList(value as org.json.JSONArray)
            } else if (value is JSONObject) {
                value = toMap(value as JSONObject)
            }
            map[key] = value
        }
        return map
    }

    @Throws(org.json.JSONException::class)
    fun toList(array: org.json.JSONArray): List<*> {
        val list = java.util.ArrayList<Any>()
        for (i in 0 until array.length()) {
            var value = array.get(i)
            if (value is org.json.JSONArray) {
                value = toList(value as org.json.JSONArray)
            } else if (value is JSONObject) {
                value = toMap(value as JSONObject)
            }
            list.add(value)
        }
        return list
    }

    fun validateEmailAddress(email: String): Boolean {
        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" + "@"
                    + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "." + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+"
        )
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }


    fun getDeviceMenufacture(): String {
        var dm = ""
        try {
            dm = Build.MANUFACTURER
            return dm
        } catch (e: Exception) {
            e.printStackTrace()
            return dm
        }

    }

    fun getDeviceModel(): String {
        var dm = ""
        try {
            dm = Build.MODEL
            return dm
        } catch (e: Exception) {
            e.printStackTrace()
            return dm
        }

    }

    fun getDeviceOSVersion(): String {
        var dm = ""
        try {
            dm = Build.VERSION.RELEASE
            return dm
        } catch (e: Exception) {
            e.printStackTrace()
            return dm
        }

    }

    fun getDeviceOEMBuildNumber(): String {
        var dm = ""
        try {
            dm = Build.FINGERPRINT
            return dm
        } catch (e: Exception) {
            e.printStackTrace()
            return dm
        }

    }

    fun getDeviceUID(activity: Activity): String {
        try {
            return Settings.Secure.getString(
                activity.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } catch (e: Exception) {
            Logger.debugE(e.toString())
            if (e.message == null) {
                Logger.debugE("" + " getDeviceUID : ")
            } else {
                Logger.debugE("" + " getDeviceUID : " + e.message)

            }
            return ""
        }

    }

    fun getDeviceSerialNumber(): String {
        var dm = ""
        try {
            dm = Build.SERIAL
            return dm
        } catch (e: Exception) {
            e.printStackTrace()
            return dm
        }

    }


    fun applicationVersion(context: Context): String {
        var version = ""
        val verCode: Int
        try {
            val manager = context.getPackageManager()
            val info = manager.getPackageInfo(context.getPackageName(), 0)
            version = info.versionName
            return version
        } catch (e: Exception) {
            e.printStackTrace()
            return version
        }

    }

    fun getDeviceType(): String {
        return "Android"
    }

    fun displayErrorMessage(ctx: Context, title: String, error: String) {
        val alert = android.app.AlertDialog.Builder(ctx)
        alert.setTitle(title)
        alert.setMessage(error)
        alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // dismiss the dialog
            dialog.cancel()
        })
        alert.setCancelable(true)
        alert.create().show()
    }

   /* fun loadJSONFromAsset(activity: Activity, filename: String): JSONObject {
        var json: String? = null
        var result = JSONObject()
        try {
            val `is` = activity.assets.open(filename)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, "UTF-8")
            result = JSONObject(json)
        } catch (ex: Exception) {
            ex.printStackTrace()

        }

        return result
    }
*/

    fun getDateFromUnixTime(sTimestamp: String?): Date? {
        if (sTimestamp == null || sTimestamp!!.isEmpty()) {
            return null
        }
        var iTimeStamp = 0
        try {
            iTimeStamp = Integer.parseInt(sTimestamp!!)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        return Date(iTimeStamp * 1000L) // *1000 is to convert seconds to milliseconds
    }

    fun getDateFromUnixTime(sTimestamp: String?, dateformate: String): Date? {
        if (sTimestamp == null || sTimestamp!!.isEmpty()) {
            return null
        }
        var iTimeStamp = 0
        try {
            iTimeStamp = Integer.parseInt(sTimestamp!!)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        return Date(iTimeStamp * 1000L) // *1000 is to convert seconds to milliseconds
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches())
    }

    fun toBoolean(target: String?): Boolean {
        return if (target == null) false else target!!.matches(("(?i:^(1|true|yes|oui|vrai|y)$)").toRegex())
    }

    fun dpToPx(dp: Int, activity: Activity): Int {
        val density = activity.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    fun getMessaerHeight(width: Int, height: Int, activity: Activity): Int {
        try {
            val displayMetrics = activity.resources.displayMetrics
            val widthD = displayMetrics.widthPixels
            val heightD = displayMetrics.heightPixels
            return ((height.toDouble() * widthD.toDouble() * 0.95) / width).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    fun parseDateToddMMyyyy(inputPattern: String, outputPattern: String, time: String): String? {

        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }
}