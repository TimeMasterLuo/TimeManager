package com.example.timemanager.ui.login

import android.R.attr
import android.app.Application
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.utils.networkRequest.MySingleton
import org.json.JSONArray
import org.json.JSONObject
import java.util.regex.Pattern


class LoginViewModel: ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(application: Application, context: Context, username: String, password: String) {
        // can be launched in a separate asynchronous job
        val url2 = "http://139.196.200.26:8080/login"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":${username}, "password":${password}}""")
        //Toast.makeText(context, params.toString(), Toast.LENGTH_SHORT).show();
        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            //成功获取返回时的callback
            { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.get("id") != 0) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = username))

                    try {
                        val jsonObject = JSONObject(response.toString())

                        //设置全局数据，记入登录状态
                        /**
                         * 为什么要使用jsonObject.optString， 不使用jsonObject.getString
                         * 因为jsonObject.optString获取null不会报错
                         */
                        val globalData: TimeManager = application as TimeManager
                        globalData.login_flag = true
                        globalData.username = jsonObject.optString("name", null)
                        globalData.uid = jsonObject.optString("id", null)
                        globalData.email = jsonObject.optString("email", null)
                        globalData.intro = jsonObject.optString("intro", null)
                        globalData.gender = jsonObject.optString("gender", null)
                        globalData.userLevel = jsonObject.optString("level", null)

                        val jsonArray: JSONArray = jsonObject.getJSONArray("friends")


                        for (i in 0 until jsonArray.length()) {
                            globalData.friendlist.add(jsonArray.getString(i))
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        )
        // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }


    fun register(application: Application, context: Context, username: String, password: String, phone: String) {
        // can be launched in a separate asynchronous job
        val url2 = "http://59.78.38.19:8080/signin"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":${username}, "password":${password}},"phone":${phone}}""")
        //Toast.makeText(context, params.toString(), Toast.LENGTH_SHORT).show();
        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            //成功获取返回时的callback
            { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.get("id") != 0) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = username))
                    //设置全局数据，记入登录状态
                    val globalData: TimeManager = application as TimeManager
                    globalData.login_flag = true
                    globalData.username = response.get("name").toString()
                    globalData.uid = response.get("id") as String
                    globalData.email = response.get("email") as String
                    globalData.intro = response.get("intro") as String
                } else {
                    _loginResult.value = LoginResult(error = R.string.register_failed)
                }
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                _loginResult.value = LoginResult(error = R.string.register_failed)
            }
        )
        // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        }
//        else if (!isPasswordValid(password)) {
//            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
//        }
        else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun registerDataChanged(username: String, password: String, phone:String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        }
        else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        }
        else if (!isPhoneValid(phone)) {
            _loginForm.value = LoginFormState(phoneError = R.string.invalid_phone)
        }
        else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        var mainRegex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{8,18}\$"
        var p = Pattern.compile(mainRegex)
        val m = p.matcher(password)
        return m.matches()
    }

    // A placeholder password validation check
    private fun isPhoneValid(phone: String): Boolean {
        var mainRegex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,1,2,3,5-9])|(177))\\d{8}$"
        var p = Pattern.compile(mainRegex)
        val m = p.matcher(phone)
        return m.matches()
    }
    // switch password visibility

}