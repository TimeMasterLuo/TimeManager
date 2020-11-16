package com.example.timemanager.StudyEamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timemanager.R
import android.R.attr.password
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.LruCache
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import org.json.JSONException
import org.json.JSONObject


class MySingleton constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: MySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MySingleton(context).also {
                    INSTANCE = it
                }
            }
    }
    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap {
                    return cache.get(url)
                }

                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }
    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}

class SendHttpRequestExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_http_request_example)
        val textView = findViewById<TextView>(R.id.text)
        val log = findViewById<TextView>(R.id.log)
        val requestcontent = findViewById<TextView>(R.id.request)
        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = "https://www.baidu.com"
        val url2 = "http://59.78.38.19:8080/login"
        val url3 = "http://apis.baidu.com/apistore/weatherservice/citylist"
        // Formulate the request and handle the response.
//        val stringRequest = StringRequest(Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                // Do something with the response
//                textView.text = "Response is: ${response.substring(0, 1500)}"
//            },
//            Response.ErrorListener { error ->
//                // Handle error
//                textView.text = "ERROR: %s".format(error.toString())
//            })

//        // Add the request to the RequestQueue.
//        requestQueue.add(stringRequest)

        val params = JSONObject()
        try {
            params.put("username", "mikeshaw")
            params.put("password", "123456")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val params2 = JSONObject("""{"username":"mike name222222", "password":"123456"}""")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url2, params2,
            Response.Listener { response ->
                textView.text = "Response: %s${response}"
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT)
                    .show();
                log.text = "Response: %s".format(error.toString())
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        requestcontent.text=jsonObjectRequest.toString()

        // ...

    }
    fun onclick(view: View){
        val textView = findViewById<TextView>(R.id.text)
        val log = findViewById<TextView>(R.id.log)
        val requestcontent = findViewById<TextView>(R.id.request)
        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = "https://www.baidu.com"
        val url2 = "http://59.78.38.19:8080/login"
        val url3 = "http://apis.baidu.com/apistore/weatherservice/citylist"
        // Formulate the request and handle the response.
//        val stringRequest = StringRequest(Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                // Do something with the response
//                textView.text = "Response is: ${response.substring(0, 1500)}"
//            },
//            Response.ErrorListener { error ->
//                // Handle error
//                textView.text = "ERROR: %s".format(error.toString())
//            })

//        // Add the request to the RequestQueue.
//        requestQueue.add(stringRequest)

        val params1 = JSONObject()
        try {
            params1.put("username", "mikeshaw")
            params1.put("password", "123456")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //Toast.makeText(getApplicationContext(), params1.toString(), Toast.LENGTH_SHORT).show();
        val map: MutableMap<String, String> = HashMap()
        map["username"] = "mikeshaw"
        map["password"] = "123456"
        val params2 = JSONObject("""{"username":"mike name", "password":"123456"}""")
        Toast.makeText(getApplicationContext(), params2.toString(), Toast.LENGTH_SHORT).show();
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url2, params2,
            Response.Listener { response ->
                textView.text = "Response: %s${response}"
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                log.text = "Response: %s".format(error.toString())
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        requestcontent.text=jsonObjectRequest.toString()
    }
}