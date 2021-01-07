package com.example.timemanager.ui.systemconfig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.home.Home
import com.example.timemanager.ui.login.LoginViewModel
import com.example.timemanager.ui.login.LoginViewModelFactory
import com.example.timemanager.ui.profile.afterTextChanged3
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONObject

class ResetPassword : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_reset_password)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "修改密码"

        val oldPD = findViewById<EditText>(R.id.old_password)
        val newPD = findViewById<EditText>(R.id.new_password)
        val newPD_again = findViewById<EditText>(R.id.new_password_again)
        val commit = findViewById<Button>(R.id.resetPD)
        val loading = findViewById<ProgressBar>(R.id.loading4)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            commit.isEnabled = loginState.isDataValid

            if (loginState.passwordError != null) {
                newPD.error = getString(loginState.passwordError)
            }
            if (loginState.passwordComfirmError != null) {
                newPD_again.error = getString(loginState.passwordComfirmError)
            }
        })


        newPD.afterTextChanged3 {
            loginViewModel.newPDChanged(
                newPD.text.toString(),
                newPD_again.text.toString()
            )
        }
        newPD_again.afterTextChanged3 {
            loginViewModel.newPDChanged(
                newPD.text.toString(),
                newPD_again.text.toString()
            )
        }
    }
    fun resetOnclick(view: View){
        val globalData: TimeManager = application as TimeManager
        val oldPD = findViewById<View>(R.id.old_password) as EditText
        val textContent = oldPD.text.toString()
        val newPD = findViewById<View>(R.id.new_password) as EditText
        val textContent2 = newPD.text.toString()
        val url2 = "http://59.78.38.19:8080/changePW"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":${globalData.username},"password":${textContent},"new_password":${textContent2}}""")
        //Toast.makeText(this, params.toString(), Toast.LENGTH_SHORT).show();
        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            //成功获取返回时的callback
            { response ->
                if (response.get("message") == "Your request is processed successfully") {
                    // TODO: 2020/11/17  success
                    Toast.makeText(this, "修改成功，请重新登入。", Toast.LENGTH_SHORT).show();
                    globalData.login_flag=false
                    //Complete and destroy commit activity once successful
                    setResult(RESULT_OK)
                    finish()
                    val intent = Intent(this, Home::class.java).apply {
                        //清空之前堆叠的栈
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    startActivity(intent)
                } else {
                    // TODO: 2020/11/17 fail
                    Toast.makeText(this, "原密码错误，请重试。", Toast.LENGTH_SHORT).show();
                }
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(this, "服务器连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        )
        //下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
