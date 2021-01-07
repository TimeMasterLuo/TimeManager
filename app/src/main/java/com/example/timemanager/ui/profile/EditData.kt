package com.example.timemanager.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.home.Home
import com.example.timemanager.ui.login.LoginViewModel
import com.example.timemanager.ui.login.LoginViewModelFactory
import com.example.timemanager.ui.register.afterTextChanged2
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.activity_edit_data.*
import kotlinx.android.synthetic.main.activity_edit_intro.*
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONObject

class EditData : AppCompatActivity() {
    var sex = "male"
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_edit_data)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "修改基本资料"

        new_email.setText((this.application as TimeManager).email)
        newIntro2.setText((this.application as TimeManager).intro)

        val email = findViewById<EditText>(R.id.new_email)
        val intro = findViewById<EditText>(R.id.newIntro2)
        val commit = findViewById<Button>(R.id.new_data_commit)
        val loading = findViewById<ProgressBar>(R.id.loading3)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            commit.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                email.error = getString(loginState.emailError)
            }
        })


        email.afterTextChanged3 {
            loginViewModel.editDataChanged(
                email.text.toString()
            )
        }
    }

    fun commitOnclick(view: View){
        val globalData: TimeManager = application as TimeManager
        val new_email = findViewById<View>(R.id.new_email) as EditText
        val textContent = new_email.text.toString()
        val new_intro = findViewById<View>(R.id.newIntro2) as EditText
        val textContent2 = new_intro.text.toString()
        val url2 = "http://59.78.38.19:8080/editIntro"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":${globalData.username},"email":${textContent},"intro":${textContent2},"sex":${sex}}""")
        //Toast.makeText(this, params.toString(), Toast.LENGTH_SHORT).show();
        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            //成功获取返回时的callback
            { response ->
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                if (response.get("message") == "success") {
                    // TODO: 2020/11/17  success
                    globalData.intro=textContent2
                    globalData.email=textContent
                    globalData.gender= sex
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
                    Toast.makeText(this, "修改失败，请重试。", Toast.LENGTH_SHORT).show();
                }
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        )
        //下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun isMale(view: View){
        val maleButton = findViewById<View>(R.id.maleButton) as RadioButton
        val femaleButton = findViewById<View>(R.id.femaleButton) as RadioButton
        maleButton.isChecked=true;
        femaleButton.isChecked=false;
        sex = "male"
    }
    fun isFemale(view: View){
        val maleButton = findViewById<View>(R.id.maleButton) as RadioButton
        val femaleButton = findViewById<View>(R.id.femaleButton) as RadioButton
        maleButton.isChecked=false;
        femaleButton.isChecked=true;
        sex = "female"
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged3(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}