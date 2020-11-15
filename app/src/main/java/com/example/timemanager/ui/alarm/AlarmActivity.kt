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
import com.example.timemanager.puzzle.PuzzleFragment
import com.example.timemanager.tools.AlarmTools
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK
import kotlinx.android.synthetic.main.layout_alarm.*
import kotlinx.android.synthetic.main.layout_title.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AlarmActivity: AppCompatActivity() {
    var  mMediaPlayer = MediaPlayer();
    private var model = T_ALARM_CLOCK();
    private var id :String ="";
    private var task :String ="";
    val fragmentManager: FragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.layout_alarm)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        text_title.text = "Finish the task!"
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
        id = intent.getStringExtra("ID")
        task = intent.getStringExtra("TASK")
        doAsync {
            model = DbTool.getDbManager().selector(T_ALARM_CLOCK::class.java).
            where("ID","=",id).findFirst();
            uiThread {
                if (model==null) return@uiThread
                stop_rt.visibility= View.VISIBLE;
                if (model.ACTIVE == "1"){
                    initMediaPlayer();
                    stop_rt.setOnClickListener {
                        if (mMediaPlayer.isPlaying){
                            mMediaPlayer.stop()
                        }
                        finish();
                    }
                }
            }
        }
        if(task.equals("PUZZLE")){
            showPuzzleFragment(3)
        };
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
        mMediaPlayer.release();
    }

    private fun resetActiveType(){
        if(model!=null && model.Date== SetAlarm.WeekDAY.Never.chnName){
            model.ACTIVE="0"
            DbTool.saveOrUpdate(model);
            AlarmTools.cancelAlarm(this,model);
        }
    }
    fun showPuzzleFragment(newN: Int) {
        val transaction = fragmentManager.beginTransaction()
        val fragment = PuzzleFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
       // if(fragment.)

    }
}