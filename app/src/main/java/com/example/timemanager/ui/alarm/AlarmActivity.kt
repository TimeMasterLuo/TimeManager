package com.example.timemanager.ui.alarm

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.timemanager.R
import com.example.timemanager.ui.task.click.ClickFragment
import com.example.timemanager.ui.task.compute.ComputeFragment
import com.example.timemanager.ui.task.customize.CustomizeFragment
import com.example.timemanager.ui.task.none.NoneFragment
import com.example.timemanager.ui.task.puzzle.PuzzleFragment
import com.example.timemanager.ui.task.react.ReactFragment
import com.example.timemanager.utils.tools.AlarmTools
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.android.synthetic.main.layout_alarm.*
import kotlinx.android.synthetic.main.layout_title.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AlarmActivity: AppCompatActivity() {
    var  mMediaPlayer = MediaPlayer();
    var model = T_ALARM_CLOCK();
    private var id :Int=0;
    private var task :String ="";

    val fragmentManager: FragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.layout_alarm)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        text_title.text = "任务闹钟"
        button_backward.visibility=View.INVISIBLE
        this.supportActionBar?.hide()
        initViews();
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initViews(){
        id = intent.getIntExtra("ID",0)
        println("awake alarm id"+id);
        //task = intent.getStringExtra("TASK")
        doAsync {
            model = DbTool.getDbManager().selector(T_ALARM_CLOCK::class.java).
            where("RemoteID","=",id).findFirst();
            //println("alarm activity :"+model.toString());
            uiThread {
                if (model==null) return@uiThread
                //stop_rt.visibility= View.VISIBLE;
                if (true){
                    initMediaPlayer();
                    if(model.Task == "PUZZLE"||model.Task=="数字华容道"){
                        showPuzzleFragment()
                    }else if(model.Task == "无"){
                        showNoneFragment()
                    }else if(model.Task == "简单的点击"){
                        showClickFragment()
                    } else if(model.Task == "简单算数"){
                        showComputeFragment()
                    } else if(model.Task == "快速反应"){
                        showReactFragment()
                    } else if(model.Task == "自定义乘法"){
                        showCustomizeFragment()
                    } else {
                        showNoneFragment()
                        println("task error")
                    };

//                    stop_rt.setOnClickListener {
//                        if (mMediaPlayer.isPlaying){
//                            mMediaPlayer.stop()
//                        }
                        //finish();
                    //}
                }
            }
        }
        //println(task);

    }

    private fun initMediaPlayer() {
        try {
            mMediaPlayer.setDataSource(this@AlarmActivity, Uri.parse(model.SOUND));
            mMediaPlayer.prepare()
            mMediaPlayer.start()
            mMediaPlayer.setOnCompletionListener {
                mMediaPlayer.start()
                mMediaPlayer.isLooping =true;
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        resetActiveType();
        if (mMediaPlayer.isPlaying){
            mMediaPlayer.stop()
        }
        mMediaPlayer.release();
    }

    private fun resetActiveType(){
        if(model!=null ){
            model.ACTIVE="0"
            DbTool.saveOrUpdate(model);
            AlarmTools.cancelAlarm(this,model);
        }
    }
    private fun showPuzzleFragment() {
        val transaction = fragmentManager.beginTransaction()
        val fragment = PuzzleFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
    private fun showNoneFragment(){
        val transaction = fragmentManager.beginTransaction()
        val fragment = NoneFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun showClickFragment(){
        val transaction = fragmentManager.beginTransaction()
        val fragment = ClickFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun showComputeFragment(){
        val transaction = fragmentManager.beginTransaction()
        val fragment = ComputeFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun showReactFragment(){
        val transaction = fragmentManager.beginTransaction()
        val fragment = ReactFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun showCustomizeFragment(){
        val transaction = fragmentManager.beginTransaction()
        val fragment = CustomizeFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}