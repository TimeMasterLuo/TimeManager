package com.example.timemanager.ui.alarm

import android.content.DialogInterface
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.utils.tools.AlarmTools
import com.example.timemanager.utils.tools.FileTools
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK
import com.example.timemanager.utils.networkRequest.MySingleton
import com.google.gson.GsonBuilder
import com.google.gson.internal.UnsafeAllocator.create
import com.loper7.date_time_picker.DateTimeConfig
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.activity_set_alarm.note_text
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SetAlarm : AppCompatActivity() {
    private val TYPE_ADD :String = "ADD"

    private val TYPE_UPDATE :String = "UPDATE"
    //判断是新增还是修改,默认新增ADD
    private var TYPE : String = TYPE_ADD;

    private var model = T_ALARM_CLOCK();
    private lateinit var friendList: kotlin.Array<CharSequence>;

    //用于选择铃声后作相应的判断标记
    private val REQUEST_CODE_PICK_RINGTONE = 1
    //保存铃声的Uri的字符串形式
    private var mRingtoneUri: Uri? = null
    var friend_name = ""
    var chooseFriendFlag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_set_alarm)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        button_backward.setOnClickListener(ButtonBackward(this))

        dateTimePicker.setDisplayType(intArrayOf(
            DateTimeConfig.YEAR,//显示年
            DateTimeConfig.MONTH,//显示月
            DateTimeConfig.DAY,//显示日
            DateTimeConfig.HOUR,//显示时
            DateTimeConfig.MIN))//显示分

        text_title.text = "设置闹钟"
        note_text.text = model.NOTE;
        to_text.text = model.TO;
        task_text.text=model.Task;
        sound_text.text =
            FileTools.getFileName(FileTools.getRealFilePath(this,Uri.parse(model.SOUND)),"暂无系统默认铃声");

        val bundle = intent.extras;
        if (bundle != null) {
            TYPE = bundle.getString("TYPE").toString()
        }
        this.supportActionBar?.hide()
        if(TYPE==TYPE_UPDATE){
            model = bundle!!.getParcelable("MODEL")!!;
            val defaultMillisecond=Date().time;
            dateTimePicker.setDefaultMillisecond(defaultMillisecond)
            //to_text.text = model.TO;
            task_text.text=model.Task;
            note_text.text = model.NOTE;
            sound_text.text= FileTools.getFileName(FileTools.getRealFilePath(this, Uri.parse(model.SOUND)));
            button_delete.visibility= View.VISIBLE;
        }
        if (TYPE==TYPE_ADD){
            model.ID = TimeManager.instance().alarmCount++;
        }

        dateTimePicker.setOnDateTimeChangedListener { millisecond -> time_edit(millisecond)  }
        card_sound.setOnClickListener { doPickPingtone(); }
        card_note.setOnClickListener { alert_edit(); }
        card_task.setOnClickListener{alertTaskSelect();}
        //card_to.setOnClickListener{alertToSelect();}
        button_submit.setOnClickListener{SaveClock();}
        button_delete.setOnClickListener{deleteClock();}

        //getFriendList()
        //testSetAlarm()

    }
    private fun getFriendList(){
        val url2 = "http://59.78.38.19:8080/getFriends"
        //var param= mutableMapOf("username" to TimeManager.instance().username)
        var param= mutableMapOf("username" to "123456")
        val params = JSONObject(param as Map<*, *>)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            { response ->
                println("fetch data response:$response")
                var array=response.get("friend_names") as JSONArray;
                array.put(TimeManager.instance().username);
               // var gson=GsonBuilder().create()
                //var list=gson.fromJson(array.toString(),String::class.java)
                //friendList = arrayOf<CharSequence>(array);
                //friendList=list;
                println("FriendList:$friendList");
            },
            { error ->
                // TODO: Handle error
                println("AlarmManage.kt:126: fetch data error:$error")
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT)
                    .show();
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    private fun time_edit(millisecond:Long){
        val l=Date(millisecond);

        println(l);
        val tdf = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
        tdf.timeZone = TimeZone.getTimeZone("GMT+8:00");
        //println(tdf.format(Date(model.TIMESTAMP.toLong())));
        model.TIMESTAMP= tdf.format(l);
        val sdf = SimpleDateFormat("HH:mm:SS")
        sdf.timeZone = TimeZone.getTimeZone("GMT+8:00");
        model.TIME= sdf.format(l);
        val df = SimpleDateFormat("yyyy/MM/dd")
        df.timeZone = TimeZone.getTimeZone("GMT+8:00");
        model.Date=df.format(l);
    }
    private fun alert_edit() {
        val et = EditText(this)
        et.setSingleLine(true);
        et.setText(model.NOTE)
        AlertDialog.Builder(this).setTitle("Note:")
            .setView(et)
            .setPositiveButton("确定") { dialogInterface, i ->
                model.NOTE = et.text.toString();
                note_text.text = model.NOTE;
            }.setNegativeButton("取消", null).show()
    }
    public enum class Task(val chnName:String){
        None("无"),
        Click("简单的点击"),
        PUZZLE("PUZZLE"),
        //PUZZLE2("2PUZZLE")
//        Random("随机")
    }
    private fun alertTaskSelect(){
        val taskList = arrayOf<CharSequence>(
            Task.None.chnName,Task.Click.chnName, Task.PUZZLE.chnName);

        var newSelected :String = "" ;
        val daySelectDialog = AlertDialog.Builder(this).setTitle("Select Task")
            .setSingleChoiceItems(taskList,0,
                DialogInterface.OnClickListener {
                        dialog, which ->
                    newSelected= taskList[which] as String;
                    println("selected task:"+newSelected)
                })
            .setPositiveButton("好", DialogInterface.OnClickListener {
                    dialog, which ->
                model.Task = if(newSelected.isNullOrBlank()) Task.None.chnName else newSelected;
                task_text.text  = model.Task;
            })
            .setNegativeButton("取消",null);
        daySelectDialog.show();
    }
//    private fun alertToSelect(){//TODO:添加ToId
//
//        var newSelected :String = "" ;
//        val toSelectDialog = AlertDialog.Builder(this).setTitle("选择设置闹钟的对象")
//            .setSingleChoiceItems(friendList,0,
//                DialogInterface.OnClickListener {
//                        dialog, which ->
//                    newSelected= friendList[which] as String;
//                    println("selected friend:"+newSelected)
//                })
//            .setPositiveButton("好", DialogInterface.OnClickListener {
//                    dialog, which ->
//                model.TO = if(newSelected.isNullOrBlank()) TimeManager.instance().username else newSelected;
//                to_text.text  = model.TO;
//            })
//            .setNegativeButton("取消",null);
//        toSelectDialog.show();
//    }
    private fun setAlarmClock(){
        AlarmTools.setAlarm(this,model);
        UploadAlarm();
        
    }
    private fun doPickPingtone(){
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT,true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE,"选择铃声");
        intent.putExtra(
            RingtoneManager.EXTRA_RINGTONE_TYPE,
            RingtoneManager.TYPE_RINGTONE);
        // Don't show 'Silent'
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        val ringtoneUri: Uri;
        ringtoneUri = mRingtoneUri?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        // Put checkmark next to the current ringtone for this contact
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringtoneUri)
        // Launch!
        // startActivityForResult(intent, REQUEST_CODE_PICK_RINGTONE);
        startActivityForResult(intent, REQUEST_CODE_PICK_RINGTONE)
    }

    private fun SaveClock(){
        model.ACTIVE = "1";
        model.UPDATE_TIME = SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format(Date());
        DbTool.saveOrUpdate(model);
        println("saving model:"+model.ID);
        println("saving model task:"+model.Task);
        setAlarmClock();
        finish();
    }
    private fun deleteClock(){
        DbTool.delete(model as Object);
        finish();
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?:return;
        try {
            val pickedUri = data.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            mRingtoneUri = pickedUri;
            model.SOUND=mRingtoneUri.toString();
            sound_text.text= FileTools.getFileName(FileTools.getRealFilePath(this, Uri.parse(model.SOUND)))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun UploadAlarm(){
        val url2 = "http://59.78.38.19:8080/setAlarm"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
        sdf.timeZone = TimeZone.getTimeZone("GMT+8:00");
        var param= mutableMapOf(
            "from" to TimeManager.instance().username,
            "to" to TimeManager.instance().username,//TO DO:完成好友相关功能
            "to_id" to TimeManager.instance().uid,
            "alarmId" to model.RemoteID,
            "timestamp" to model.TIMESTAMP,
            "note" to model.NOTE,
            //"score" to model.Score,
            "score" to 80,
            //"coins" to model.Coins,
            "coins" to 10,
            "task" to model.Task,
            //"status" to model.Status
            "status" to "unfinished"
        )
        val params = JSONObject(param as Map<*, *>)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            { response ->
                println("fetch data response:$response")
                model.RemoteID= response.get("message").toString().toInt()
            },
            { error ->
                // TODO: Handle error
                println("SetAlarm.kt:217:fetch data error:$error")
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT)
                    .show();
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
//    fun onCheckboxClicked(view: View) {
//        if (view is CheckBox) {
//            val checked: Boolean = view.isChecked
//
//            when (view.id) {
//                R.id.checkBox2 -> {
//                    if (checked)
//                    {
//                        //pump a message
//                        val selectedItems = ArrayList<Int>() // Where we track the selected items
//                         AlertDialog.Builder(this).apply {
//                             setTitle("好友列表:")
//                             setMultiChoiceItems(R.array.my_friends, null,
//                                 DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
//                                     if (isChecked) {
//                                         // If the user checked the item, add it to the selected items
//                                         selectedItems.add(which)
//                                     } else if (selectedItems.contains(which)) {
//                                         // Else, if the item is already in the array, remove it
//                                         selectedItems.remove(Integer.valueOf(which))
//                                     }
//                                 })
//                                 // Set the action buttons
//                             setPositiveButton("OK",
//                                 DialogInterface.OnClickListener { dialog, id ->
//                                     // User clicked OK, so save the selectedItems results somewhere
//                                     // or return them to the component that opened the dialog
//                                    })
//                             setNegativeButton("CANCEL",
//                                 DialogInterface.OnClickListener { dialog, id ->
//                                 })
//                            show()
//                        }
//                        //change some component visible
//                        val friendName : TextView = findViewById(R.id.friend_name)
//                        friendName.setVisibility(View.VISIBLE);
//                        friendName.setText("小花")
//                        val setPraise : TextView = findViewById(R.id.set_praise)
//                        setPraise.setVisibility(View.VISIBLE);
//                        set_praise2.setVisibility(View.VISIBLE);
//                        val price : EditText = findViewById(R.id.price)
//                        price.setVisibility(View.VISIBLE);
//                        coins.setVisibility(View.VISIBLE);
//                    }
//                    else
//                    {
//                        //change some component invisible
//                        val friendName : TextView = findViewById(R.id.friend_name)
//                        friendName.setVisibility(View.GONE);
//                        val setPraise : TextView = findViewById(R.id.set_praise)
//                        setPraise.setVisibility(View.GONE);
//                        val price : EditText = findViewById(R.id.price)
//                        price.setVisibility(View.GONE);
//                        set_praise2.setVisibility(View.GONE);
//                        coins.setVisibility(View.GONE);
//                    }
//                }
//            }
//        }
//    }

}
