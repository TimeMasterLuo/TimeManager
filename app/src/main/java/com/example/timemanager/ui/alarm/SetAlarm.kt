package com.example.timemanager.ui.alarm

import android.content.DialogInterface
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.timemanager.R
import com.example.timemanager.utils.tools.AlarmTools
import com.example.timemanager.utils.tools.FileTools
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK
import com.loper7.date_time_picker.DateTimeConfig
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.activity_set_alarm.note_text
import kotlinx.android.synthetic.main.layout_title.*
import java.text.SimpleDateFormat
import java.util.*

class SetAlarm : AppCompatActivity() {
    private val TYPE_ADD :String = "ADD"

    private val TYPE_UPDATE :String = "UPDATE"
    //判断是新增还是修改,默认新增ADD
    private var TYPE : String = TYPE_ADD;

    private var model = T_ALARM_CLOCK();

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
            to_text.text = model.TO;
            task_text.text=model.Task;
            note_text.text = model.NOTE;
            sound_text.text= FileTools.getFileName(FileTools.getRealFilePath(this, Uri.parse(model.SOUND)));
            button_delete.visibility= View.VISIBLE;
        }
        if (TYPE==TYPE_ADD){
            model.ID = UUID.randomUUID().toString();
        }

        dateTimePicker.setOnDateTimeChangedListener { millisecond -> time_edit(millisecond)  }
        card_sound.setOnClickListener { doPickPingtone(); }
        card_note.setOnClickListener { alert_edit(); }
        card_task.setOnClickListener{alertTaskSelect();}
        button_submit.setOnClickListener{SaveClock();}
        button_delete.setOnClickListener{deleteClock();}
        //testSetAlarm()

    }
    private fun time_edit(millisecond:Long){
        val l=Date(millisecond);
        model.TIMESTAMP= millisecond.toString();
        println(l);
        val sdf = SimpleDateFormat("HH:mm")
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
    public enum class WeekDAY(val chnName:String){
        Never("从不"),
        Monday("每周一"),
        Tuesday("每周二"),
        Wednesday("每周三"),
        Thursday("每周四"),
        Friday("每周五"),
        Saturday("每周六"),
        Sunday("每周日")
    }
    public enum class Task(val chnName:String){
        None("无"),
        Buttons("Press Button"),
        Random("随机")
    }
    private fun alertTaskSelect(){
        val taskList = arrayOf<CharSequence>(
            Task.None.chnName, Task.Buttons.chnName, Task.Random.chnName);

        var newSelected :String = "" ;
        val daySelectDialog = AlertDialog.Builder(this).setTitle("Select Task")
            .setSingleChoiceItems(taskList,0,
                DialogInterface.OnClickListener {
                        dialog, which ->
                    newSelected= taskList[which] as String;
                })
            .setPositiveButton("好", DialogInterface.OnClickListener {
                    dialog, which ->
                model.Task = if(newSelected.isNullOrBlank()) Task.None.chnName else newSelected;
                task_text.text  = model.Task;
            })
            .setNegativeButton("取消",null);
        daySelectDialog.show();
    }
    fun testSetAlarm(){
        model.ID = UUID.randomUUID().toString()
        model.TIME="17:50"
        model.ACTIVE="1"
        model.Date="2020/11/11"
        model.UPDATE_TIME = SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format(Date());
        model.FROM="来自：me"
        model.TO="me"
        model.NOTE="This is a test alarm"
        DbTool.saveOrUpdate(model);
        setAlarmClock();
        val intent = Intent(applicationContext, AlarmActivity::class.java).apply {
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.putExtra("ID", model.ID)
        applicationContext!!.startActivity(intent)
        //finish();
    }
    private fun setAlarmClock(){
        AlarmTools.setAlarm(this,model);
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
        setAlarmClock();
        finish();
    }
    private fun deleteClock(){
        DbTool.delete(model as Object);
        finish();
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
