package com.example.timemanager.ui.alarm

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK
import com.example.timemanager.utils.networkRequest.MySingleton
import com.example.timemanager.utils.tools.AlarmTools
import com.example.timemanager.utils.tools.JsonTools
import kotlinx.android.synthetic.main.activity_alarm_manage.*
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.layout_title.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class AlarmManage : AppCompatActivity(){
    var mContext=this;
    private  var clockList = ArrayList<T_ALARM_CLOCK>();

    private lateinit var dialog : ProgressDialog;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_alarm_manage)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "管理闹钟"
        this.supportActionBar?.hide()

        fab.setOnClickListener { view ->
            val intent = Intent(this, SetAlarm::class.java).apply {
            }
            intent.putExtra("TYPE", "ADD")
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this);
        //loadData();
    }

    override fun onResume() {
        super.onResume()
        //fetchData();
        loadData();
    }

    internal inner class ClockListAdapter(val mDatas:List<T_ALARM_CLOCK>) :
        RecyclerView.Adapter<ClockListAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            var viewHolder= MyViewHolder(
                LayoutInflater.from(
                this@AlarmManage).inflate(
                    R.layout.layout_alarm_list, parent,
                false))

            return viewHolder;
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder?.time_tv?.text = mDatas.get(position).TIME;
            holder?.day_tv?.text = mDatas.get(position).Date;
            holder?.note_tv?.text = mDatas.get(position).NOTE;
            holder?.from_tv?.text = "来自："+mDatas.get(position).FROM;
            holder?.clock_sw.isChecked =  if(mDatas.get(position).ACTIVE =="1" )
                true else false;
            holder?.rootView.setOnClickListener {
                val intent = Intent(mContext, SetAlarm::class.java).apply {
                }
                intent.putExtra("TYPE", "UPDATE")
                intent.putExtra("MODEL",mDatas.get(position))
                startActivity(intent)

            }
        }

        override fun getItemCount(): Int {
            return mDatas.size;
        }

        internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            var time_tv: TextView
            var day_tv : TextView
            var note_tv : TextView
            var from_tv : TextView
            var clock_sw : Switch
            var rootView : CardView
            init {
                time_tv = view.findViewById(R.id.time_text) as TextView
                day_tv = view.findViewById(R.id.day_text) as TextView
                note_tv = view.findViewById(R.id.note_text) as TextView
                from_tv = view.findViewById(R.id.from_text) as TextView
                clock_sw = view.findViewById(R.id.clock_switch) as Switch
                rootView = view.findViewById(R.id.rootView) as CardView
            }
        }


    }

    fun clearData(){//请在logout时调用此函数，清空数据
        var localalarm =DbTool.findAll(T_ALARM_CLOCK::class.java);
        localalarm?.forEach{
                item->
            //println("delete item:"+item.toString());
            DbTool.delete(item)
            AlarmTools.cancelAlarm(this,item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchData(){
        val url2 = "http://59.78.38.19:8080/getAllClock"
        var param= mutableMapOf("toid" to TimeManager.instance().uid)
        val params = JSONObject(param as Map<*, *>)
        val toid=TimeManager.instance().uid;
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            { response ->
                println("fetch uid:$toid response:$response")
                var alarmArray = response.get("data").toString();
                var alarmList= JsonTools.parserJsonArrToMapList(alarmArray);
                println("alarmList:$alarmList");
                alarmList?.forEach { item->
                    var model=DbTool.getDbManager().selector(T_ALARM_CLOCK::class.java).where("RemoteID","=",item["id"].toString().toInt()).findFirst();
                    println("find model:$model")
                    if (model==null)model = T_ALARM_CLOCK();
                    model.RemoteID= item["id"].toString().toInt()
                    model.TO= item["toName"] as String
                    model.ToID= item["toId"].toString().toInt()
                    model.FROM= item["fromName"] as String
                    model.NOTE=item["note"] as String
                    model.Status=item["status"] as String
                    model.Score=item["score"].toString().toInt()
                    model.TIMESTAMP=item["recordTime"] as String
                    model.Task=item["task"] as String

                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
                    var df = SimpleDateFormat("yyyy-MM-dd")
                    val time=LocalDateTime.now();
                    if(sdf.parse(item["recordTime"] as String).after(Date())){ model.ACTIVE="1"}
                    else model.ACTIVE="0"
                    //df.timeZone = TimeZone.getTimeZone("GMT+8:00");
                    model.Date=df.format(sdf.parse(item["recordTime"] as String)).toString()
                    df = SimpleDateFormat("HH:mm:SS")
                    //df.timeZone = TimeZone.getTimeZone("GMT+8:00");
                    model.TIME= df.format(sdf.parse(item["recordTime"] as String)).toString()
                    DbTool.saveOrUpdate(model);
                    if(model.ACTIVE == "1"){AlarmTools.setAlarm(this,model)}
                }
            },
            { error ->
                // TODO: Handle error
                println("AlarmManage.kt:158: fetch data error:$error")
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT)
                    .show();
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    private fun loadData(){
        dialog = indeterminateProgressDialog("正在加载数据");

        doAsync {
            fetchData();
            clockList.clear();
            clockList.addAll(DbTool.getDbManager().selector(T_ALARM_CLOCK::class.java)
                .orderBy("UPDATE_TIME",true).findAll())
            uiThread {
                if(recyclerView.adapter == null)
                    recyclerView.adapter = ClockListAdapter(clockList);
                else
                    recyclerView.adapter!!.notifyDataSetChanged();

                if(dialog.isShowing) dialog.dismiss();
            }

        }
    }
}