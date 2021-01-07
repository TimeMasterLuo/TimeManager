package com.example.timemanager.ui.dashboard

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.clock.Clock
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.layout_title.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Analyze : AppCompatActivity() {
    private lateinit var clocks: ArrayList<Clock>
    private var totalCommon:Float=0F
    private var totalDeep:Float=0F
    private var friendsSuccess:Float=0F
    private var selfSuccess:Float=0F
    private var totalSelfClock:Float=0F
    private var totalFriendsClock:Float=0F

    private var weekCommonTime:Array<Float> = arrayOf(0F, 0F, 0F, 0F, 0F, 0F, 0F)
    private var weekDeepTime:Array<Float> = arrayOf(0F, 0F, 0F, 0F, 0F, 0F, 0F)
    private var weekTotalCommon:Float=0F
    private var weekTotalDeep:Float=0F
    private var weekFriendsSuccess:Float=0F
    private var weekSelfSuccess:Float=0F
    private var weekTotalSelfClock:Float=0F
    private var weekTotalFriendsClock:Float=0F

    private var monthCommonTime:Array<Float> = arrayOf(0F, 0F, 0F, 0F,0F)
    private var monthDeepTime:Array<Float> = arrayOf(0F, 0F, 0F, 0F,0F)
    private var monthTotalCommon:Float=0F
    private var monthTotalDeep:Float=0F
    private var monthFriendsSuccess:Float=0F
    private var monthSelfSuccess:Float=0F
    private var monthTotalSelfClock:Float=0F
    private var monthTotalFriendsClock:Float=0F

    private var yearCommonTime:Array<Float> = arrayOf(0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F)
    private var yearDeepTime:Array<Float> = arrayOf(0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F)
    private var yearTotalCommon:Float=0F
    private var yearTotalDeep:Float=0F
    private var yearFriendsSuccess:Float=0F
    private var yearSelfSuccess:Float=0F
    private var yearTotalSelfClock:Float=0F
    private var yearTotalFriendsClock:Float=0F

    private var strDateFormat = "MM-dd"
    @SuppressLint("SimpleDateFormat")
    var sdf: SimpleDateFormat = SimpleDateFormat(strDateFormat)
    private var weekCategory:Array<String> = arrayOf("","","","","","","")
    private var monthCategory:Array<String> = arrayOf("","","","","")

    @Suppress("UNCHECKED_CAST")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_analyze)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()
        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "数据分析"

        clocks= intent.getSerializableExtra("clocks") as ArrayList<Clock>

        val currentTime:Calendar= Calendar.getInstance()
        val tmpTime:Calendar= Calendar.getInstance()

        val daysOfMonth=currentTime.getActualMaximum(Calendar.DAY_OF_MONTH)
        val currentMonth=currentTime.get(Calendar.MONTH)+1

        tmpTime.set(Calendar.HOUR_OF_DAY, 0)
        currentTime.set(Calendar.HOUR_OF_DAY, 0)
        tmpTime.set(Calendar.MINUTE, 0)
        currentTime.set(Calendar.MINUTE, 0)
        tmpTime.set(Calendar.SECOND, 0)
        currentTime.set(Calendar.SECOND, 0)
        currentTime.add(Calendar.DATE, 1)
        tmpTime.add(Calendar.DATE, -6)

        weekCategory[6]=sdf.format(Date(currentTime.time.time-1000*24*60*60))
        weekCategory[5]=sdf.format(Date(currentTime.time.time-2*1000*24*60*60))
        weekCategory[4]=sdf.format(Date(currentTime.time.time-3*1000*24*60*60))
        weekCategory[3]=sdf.format(Date(currentTime.time.time-4*1000*24*60*60))
        weekCategory[2]=sdf.format(Date(currentTime.time.time-5*1000*24*60*60))
        weekCategory[1]=sdf.format(Date(currentTime.time.time-6*1000*24*60*60))
        weekCategory[0]=sdf.format(Date(currentTime.time.time-7*1000*24*60*60))

        monthCategory[0]= "$currentMonth-1至$currentMonth-7"
        monthCategory[1]= "$currentMonth-8至$currentMonth-14"
        monthCategory[2]= "$currentMonth-15至$currentMonth-21"
        monthCategory[3]= "$currentMonth-22至$currentMonth-28"
        if (daysOfMonth==28){
            monthCategory=monthCategory.sliceArray(0..3)
        }else{
            monthCategory[4]= "$currentMonth-29至$currentMonth-$daysOfMonth"

        }
//        Log.e("current",currentTime.time.toString())
          Log.e("A day ago",currentMonth.toString())
        for (i in clocks.indices){
            val tmptime:Calendar= Calendar.getInstance()
            tmptime.time=clocks[i].start_time
            var thisWeek=false
            var thisMonth=false
            var thisYear=false
            if (tmptime.get(Calendar.YEAR)==currentTime.get(Calendar.YEAR)){
                thisYear=true
                if (tmptime.get(Calendar.MONTH)==currentTime.get(Calendar.MONTH)) {
                    thisMonth = true
                    if (tmptime.time.after(tmpTime.time)) {
                        thisWeek = true
                    }
                }
            }
            if(clocks[i].kind=="远离手机"){
                Log.e("num",i.toString())
                if(clocks[i].type=="普通模式"){
                    totalCommon+=clocks[i].last_time.toFloat()
                    if(thisYear){
                        yearTotalCommon+=clocks[i].last_time.toFloat()
                        yearCommonTime[tmptime.get(Calendar.MONTH)]+=clocks[i].last_time.toFloat()
                        if(thisMonth){
                           monthTotalCommon+=clocks[i].last_time.toFloat()
                            monthCommonTime[tmptime.get(Calendar.DATE) / 7]+=clocks[i].last_time.toFloat()
                            if(thisWeek){
                                weekTotalCommon+=clocks[i].last_time.toFloat()
                                weekCommonTime[7+((currentTime.timeInMillis - tmptime.timeInMillis) / (1000 * 60 * 60 * 24)).toInt() - 1]+=clocks[i].last_time.toFloat()
                            }
                        }
                    }
                }else{
                    Log.e("num",i.toString()+"1")
                    totalDeep+=clocks[i].last_time.toFloat()
                    if(thisYear){
                        yearTotalDeep+=clocks[i].last_time.toFloat()
                        yearDeepTime[tmptime.get(Calendar.MONTH)]+=clocks[i].last_time.toFloat()
                        if(thisMonth){
                            monthTotalDeep+=clocks[i].last_time.toFloat()
                            monthDeepTime[tmptime.get(Calendar.DATE) / 7]+=clocks[i].last_time.toFloat()
                            if(thisWeek){
                                weekTotalDeep+=clocks[i].last_time.toFloat()
                                weekDeepTime[7+((currentTime.timeInMillis - tmptime.timeInMillis) / (1000 * 60 * 60 * 24)).toInt() - 1]+=clocks[i].last_time.toFloat()
                            }
                        }
                    }
                }
            }else{
                if (clocks[i].type=="自设闹钟"){
                    totalSelfClock+=1F
                    if(clocks[i].status!="unfinished"){
                        selfSuccess+=1F
                    }
                    if(thisYear){
                        yearTotalSelfClock+=1F
                        if(clocks[i].status!="unfinished"){
                            yearSelfSuccess+=1F
                        }
                        if(thisMonth){
                            monthTotalSelfClock+=1F
                            if(clocks[i].status!="unfinished"){
                                monthSelfSuccess+=1F
                            }
                            if(thisWeek){
                                weekTotalSelfClock+=1F
                                if(clocks[i].status!="unfinished"){
                                    weekSelfSuccess+=1F
                                }
                            }
                        }
                    }
                }else{
                    totalFriendsClock+=1F
                    if(clocks[i].status!="unfinished"){
                        friendsSuccess+=1F
                    }
                    if(thisYear){
                        yearTotalFriendsClock+=1F
                        if(clocks[i].status!="unfinished"){
                            yearFriendsSuccess+=1F
                        }
                        if(thisMonth){
                            monthTotalFriendsClock+=1F
                            if(clocks[i].status!="unfinished"){
                                monthFriendsSuccess+=1F
                            }
                            if(thisWeek){
                                weekTotalFriendsClock+=1F
                                if(clocks[i].status!="unfinished"){
                                    weekFriendsSuccess+=1F
                                }
                            }
                        }
                    }
                }
            }
        }
        if(totalSelfClock==0F){
            totalSelfClock+=1F
        }
        if(totalFriendsClock==0F){
            totalFriendsClock+=1F
        }
        if(weekTotalFriendsClock==0F){
            weekTotalFriendsClock+=1F
        }
        if(weekTotalSelfClock==0F){
            weekTotalSelfClock+=1F
        }
        if(monthTotalFriendsClock==0F){
            monthTotalFriendsClock+=1F
        }
        if(monthTotalSelfClock==0F){
            monthTotalSelfClock+=1F
        }
        if(yearTotalFriendsClock==0F){
            yearTotalFriendsClock+=1F
        }
        if(yearTotalSelfClock==0F){
            yearTotalSelfClock+=1F
        }
        initChart()
        val tabLayout=findViewById<TabLayout>(R.id.tab_analyze)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tag = tab.text as String
                changeChart(tag)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun initChart(){
        val aaChartView = findViewById<AAChartView>(R.id.aa_chart_view)
        val aaChartModel = AAChartModel()
                .chartType(AAChartType.Area)
                .title("title")
                .yAxisTitle("时间")
                .animationType(AAChartAnimationType.EaseInCirc)
                .dataLabelsEnabled(true)
                .backgroundColor("#ffffff")
                .categories(arrayOf("January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"))
                .series(arrayOf(
                        AASeriesElement()
                                .name("Tokyo")
                                .data(arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)),
                        AASeriesElement()
                                .name("NewYork")
                                .data(arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5)),
                        AASeriesElement()
                                .name("London")
                                .data(arrayOf(0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0)),
                        AASeriesElement()
                                .name("Berlin")
                                .data(arrayOf(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8))
                )
                )
        aaChartView.aa_drawChartWithChartModel(aaChartModel)
        val chart1=findViewById<LinearLayout>(R.id.chart1)
        chart1.visibility=GONE
        val aaChartView1=findViewById<AAChartView>(R.id.aa_chart_view1)
        val aaChartModel1=AAChartModel()
                .chartType(AAChartType.Pie)
                .title("各功能使用时间")
                .backgroundColor("#ffffff")
                .dataLabelsEnabled(true)
                .yAxisTitle("分钟")
                .series(
                        arrayOf(
                                AASeriesElement()
                                        .name("使用时间（分钟）")
                                        .data(
                                                arrayOf(
                                                        arrayOf("深度模式", totalDeep / 60F),
                                                        arrayOf("普通模式", totalCommon / 60F)
                                                )
                                        )
                        )
                )
        aaChartView1.aa_drawChartWithChartModel(aaChartModel1)
        val aaChartView2=findViewById<AAChartView>(R.id.aa_chart_view2)
        val aaChartModel2=AAChartModel()
                .chartType(AAChartType.Bar)
                .title("闹钟完成率")
                .dataLabelsEnabled(true)
                .backgroundColor("#ffffff")
                .yAxisTitle("成功率")
                .categories(arrayOf(
                        "自设闹钟",
                        "好友闹钟"
                ))
                .series(arrayOf(AASeriesElement()
                        .name("完成率")
                        .data(arrayOf(selfSuccess/totalSelfClock, friendsSuccess/totalFriendsClock))))
        aaChartView2.aa_drawChartWithChartModel(aaChartModel2)

    }
    private fun changeChart(tag: String){
        if(tag=="总览"){
            val chart1=findViewById<LinearLayout>(R.id.chart1)
            chart1.visibility=GONE
            val aaChartModel1=AAChartModel()
                    .chartType(AAChartType.Pie)
                    .title("各功能使用时间")
                    .backgroundColor("#ffffff")
                    .dataLabelsEnabled(true)
                    .yAxisTitle("分钟")
                    .series(
                            arrayOf(
                                    AASeriesElement()
                                            .name("使用时间（分钟）")
                                            .data(
                                                    arrayOf(
                                                            arrayOf("深度模式", totalDeep / 60F),
                                                            arrayOf("普通模式", totalCommon / 60F)
                                                    )
                                            )
                            )
                    )
            val aaChartModel2=AAChartModel()
                    .chartType(AAChartType.Bar)
                    .title("各功能完成率")
                    .dataLabelsEnabled(true)
                    .backgroundColor("#ffffff")
                    .yAxisTitle("成功率")
                    .categories(arrayOf(
                            "自设闹钟",
                            "好友闹钟"
                    ))
                    .series(arrayOf(AASeriesElement()
                            .name("完成率")
                            .data(arrayOf(selfSuccess, friendsSuccess))))
            val aaChartView1=findViewById<AAChartView>(R.id.aa_chart_view1)
            val aaChartView2=findViewById<AAChartView>(R.id.aa_chart_view2)
            aaChartView1.aa_refreshChartWithChartModel(aaChartModel1)
            aaChartView2.aa_refreshChartWithChartModel(aaChartModel2)
        }else if(tag=="周"){
            val chart1=findViewById<LinearLayout>(R.id.chart1)
            chart1.visibility= VISIBLE
            val aaChartModel = AAChartModel()
                    .chartType(AAChartType.Area)
                    .title("周使用时间")
                    .yAxisTitle("时间")
                    .animationType(AAChartAnimationType.EaseInCirc)
                    .animationDuration(1000)
                    .dataLabelsEnabled(true)
                    .backgroundColor("#ffffff")
                    .categories(weekCategory)
                    .series(arrayOf(
                            AASeriesElement()
                                    .name("普通模式使用时间")
                                    .data(arrayOf(weekCommonTime[0]/60F,weekCommonTime[1]/60F,weekCommonTime[2]/60F,weekCommonTime[3]/60F,weekCommonTime[4],weekCommonTime[5]/60F,weekCommonTime[6]/60F))
                                    .name("深度模式使用时间")
                                    .data(arrayOf(weekDeepTime[0]/60F,weekDeepTime[1]/60F,weekDeepTime[2]/60F,weekDeepTime[3]/60F,weekDeepTime[4]/60F,weekDeepTime[5]/60F,weekDeepTime[6]/60F))
                    )
                    )
            val aaChartModel1=AAChartModel()
                    .chartType(AAChartType.Pie)
                    .title("各功能使用时间")
                    .backgroundColor("#ffffff")
                    .dataLabelsEnabled(true)
                    .yAxisTitle("分钟")
                    .series(
                            arrayOf(
                                    AASeriesElement()
                                            .name("使用时间（分钟）")
                                            .data(
                                                    arrayOf(
                                                            arrayOf("深度模式", weekTotalCommon/60F),
                                                            arrayOf("普通模式", weekTotalDeep/60F)
                                                    )
                                            )
                            )
                    )
            val aaChartModel2=AAChartModel()
                    .chartType(AAChartType.Bar)
                    .title("各功能完成率")
                    .dataLabelsEnabled(true)
                    .backgroundColor("#ffffff")
                    .yAxisTitle("成功率")
                    .categories(arrayOf(
                            "自设闹钟",
                            "好友闹钟"
                    ))
                    .series(arrayOf(AASeriesElement()
                            .name("完成率")
                            .data(arrayOf(weekSelfSuccess/weekTotalSelfClock, weekFriendsSuccess/weekTotalFriendsClock))))
            val aaChartView=findViewById<AAChartView>(R.id.aa_chart_view)
            val aaChartView1=findViewById<AAChartView>(R.id.aa_chart_view1)
            val aaChartView2=findViewById<AAChartView>(R.id.aa_chart_view2)
            aaChartView.aa_refreshChartWithChartModel(aaChartModel)
            aaChartView1.aa_refreshChartWithChartModel(aaChartModel1)
            aaChartView2.aa_refreshChartWithChartModel(aaChartModel2)
        }else if(tag=="月"){
            val chart1=findViewById<LinearLayout>(R.id.chart1)
            chart1.visibility= VISIBLE
            val aaChartModel = AAChartModel()
                    .chartType(AAChartType.Area)
                    .title("月使用时间")
                    .yAxisTitle("时间")
                    .animationType(AAChartAnimationType.EaseInCirc)
                    .animationDuration(1000)
                    .dataLabelsEnabled(true)
                    .backgroundColor("#ffffff")
                    .categories(monthCategory)
                    .series(arrayOf(
                            AASeriesElement()
                                    .name("普通模式(分钟)")
                                    .data(arrayOf(monthCommonTime[0]/60F,monthCommonTime[1]/60F,monthCommonTime[2]/60F,monthCommonTime[3]/60F,monthCommonTime[4]/60F))
                                    .name("深度模式(分钟)")
                                    .data(arrayOf(monthDeepTime[0]/60F,monthDeepTime[1]/60F,monthDeepTime[2]/60F,monthDeepTime[3]/60F,monthDeepTime[4]/60F))
                    )
                    )
            val aaChartModel1=AAChartModel()
                    .chartType(AAChartType.Pie)
                    .title("各功能使用时间")
                    .backgroundColor("#ffffff")
                    .dataLabelsEnabled(true)
                    .yAxisTitle("分钟")
                    .series(
                            arrayOf(
                                    AASeriesElement()
                                            .name("使用时间（分钟）")
                                            .data(
                                                    arrayOf(
                                                            arrayOf("深度模式", monthTotalCommon/60F),
                                                            arrayOf("普通模式", monthTotalDeep/60F)
                                                    )
                                            )
                            )
                    )
            val aaChartModel2=AAChartModel()
                    .chartType(AAChartType.Bar)
                    .title("各功能完成率")
                    .dataLabelsEnabled(true)
                    .backgroundColor("#ffffff")
                    .yAxisTitle("成功率")
                    .categories(arrayOf(
                            "自设闹钟",
                            "好友闹钟"
                    ))
                    .series(arrayOf(AASeriesElement()
                            .name("完成率")
                            .data(arrayOf(monthSelfSuccess/monthTotalSelfClock, monthFriendsSuccess/monthTotalFriendsClock))))
            val aaChartView=findViewById<AAChartView>(R.id.aa_chart_view)
            val aaChartView1=findViewById<AAChartView>(R.id.aa_chart_view1)
            val aaChartView2=findViewById<AAChartView>(R.id.aa_chart_view2)
            aaChartView.aa_refreshChartWithChartModel(aaChartModel)
            aaChartView1.aa_refreshChartWithChartModel(aaChartModel1)
            aaChartView2.aa_refreshChartWithChartModel(aaChartModel2)
        }else{
            val chart1=findViewById<LinearLayout>(R.id.chart1)
            chart1.visibility= VISIBLE
            val aaChartModel = AAChartModel()
                    .chartType(AAChartType.Column)
                    .title("年使用时间")
                    .yAxisTitle("时间")
                    .animationType(AAChartAnimationType.EaseInCirc)
                    .animationDuration(1000)
                    .dataLabelsEnabled(true)
                    .backgroundColor("#ffffff")
                    .categories(arrayOf("一月",
                            "二月",
                            "三月",
                            "四月",
                            "五月",
                            "六月",
                            "七月",
                            "八月",
                            "九月",
                            "十月",
                            "十一月",
                            "十二月"

                    ))
                    .series(arrayOf(
                            AASeriesElement()
                                    .name("普通模式(分钟)")
                                    .data(arrayOf(yearCommonTime[0]/60F,yearCommonTime[1]/60F,yearCommonTime[2]/60F,yearCommonTime[3]/60F,yearCommonTime[4]/60F,yearCommonTime[5]/60F,yearCommonTime[6]/60F,yearCommonTime[7]/60F,yearCommonTime[8]/60F,yearCommonTime[9]/60F,yearCommonTime[10]/60F,yearCommonTime[11]/60F))
                                    .name("普通模式(分钟)")
                                    .data(arrayOf(yearDeepTime[0]/60F,yearDeepTime[1]/60F,yearDeepTime[2]/60F,yearDeepTime[3]/60F,yearDeepTime[4]/60F,yearDeepTime[5]/60F,yearDeepTime[6]/60F,yearDeepTime[7]/60F,yearDeepTime[8]/60F,yearDeepTime[9]/60F,yearDeepTime[10]/60F,yearDeepTime[11]/60F))
                    )
                    )
            val aaChartModel1=AAChartModel()
                    .chartType(AAChartType.Pie)
                    .title("各功能使用时间")
                    .backgroundColor("#ffffff")
                    .dataLabelsEnabled(true)
                    .yAxisTitle("分钟")
                    .series(
                            arrayOf(
                                    AASeriesElement()
                                            .name("使用时间（分钟）")
                                            .data(
                                                    arrayOf(
                                                            arrayOf("深度模式", yearTotalCommon/60F),
                                                            arrayOf("普通模式", yearTotalDeep/60F)
                                                    )
                                            )
                            )
                    )
            val aaChartModel2=AAChartModel()
                    .chartType(AAChartType.Bar)
                    .title("各功能完成率")
                    .dataLabelsEnabled(true)
                    .backgroundColor("#ffffff")
                    .yAxisTitle("成功率")
                    .categories(arrayOf(
                            "自设闹钟",
                            "好友闹钟"
                    ))
                    .series(arrayOf(AASeriesElement()
                            .name("完成率")
                            .data(arrayOf(yearSelfSuccess/yearTotalSelfClock, yearFriendsSuccess/yearTotalFriendsClock))))
            val aaChartView=findViewById<AAChartView>(R.id.aa_chart_view)
            val aaChartView1=findViewById<AAChartView>(R.id.aa_chart_view1)
            val aaChartView2=findViewById<AAChartView>(R.id.aa_chart_view2)
            aaChartView.aa_refreshChartWithChartModel(aaChartModel)
            aaChartView1.aa_refreshChartWithChartModel(aaChartModel1)
            aaChartView2.aa_refreshChartWithChartModel(aaChartModel2)
        }
    }
}