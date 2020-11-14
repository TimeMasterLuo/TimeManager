package com.example.timemanager.ui.alarm

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK
import kotlinx.android.synthetic.main.activity_alarm_manage.*
import kotlinx.android.synthetic.main.layout_title.*
import org.jetbrains.anko.*

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
    }

    override fun onResume() {
        super.onResume()
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

    private fun loadData(){
        dialog = indeterminateProgressDialog("正在加载数据");
        doAsync {
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