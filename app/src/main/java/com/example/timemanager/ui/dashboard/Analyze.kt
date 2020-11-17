package com.example.timemanager.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_analyze.*
import kotlinx.android.synthetic.main.layout_title.*


class Analyze : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_analyze)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "数据分析"
        initChart()
        val tabLayout=findViewById<TabLayout>(R.id.tab_analyze)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tag=tab.text as String
                changeChart(tag)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
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
                                                        arrayOf("自设闹钟", 666),
                                                        arrayOf("好友闹钟", 487),
                                                        arrayOf("深度模式", 556),
                                                        arrayOf("普通模式", 1000)
                                                )
                                        )
                        )
                )
        aaChartView1.aa_drawChartWithChartModel(aaChartModel1)
        val aaChartView2=findViewById<AAChartView>(R.id.aa_chart_view2)
        val aaChartModel2=AAChartModel()
                .chartType(AAChartType.Bar)
                .title("各功能完成率")
                .dataLabelsEnabled(true)
                .backgroundColor("#ffffff")
                .yAxisTitle("成功率")
                .categories(arrayOf(
                        "自设闹钟",
                        "好友闹钟",
                        "深度模式",
                        "普通模式"
                ))
                .series(arrayOf(AASeriesElement()
                        .name("完成率")
                        .data(arrayOf(0.95, 0.8, 0.9, 0.7))))
        aaChartView2.aa_drawChartWithChartModel(aaChartModel2)

    }
    private fun changeChart(tag:String){
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
                                                            arrayOf("自设闹钟", 666),
                                                            arrayOf("好友闹钟", 487),
                                                            arrayOf("深度模式", 556),
                                                            arrayOf("普通模式", 1000)
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
                            "好友闹钟",
                            "深度模式",
                            "普通模式"
                    ))
                    .series(arrayOf(AASeriesElement()
                            .name("完成率")
                            .data(arrayOf(0.95, 0.8, 0.9, 0.7))))
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
                    .categories(arrayOf("11-16",
                            "11-17",
                            "11-18",
                            "11-19",
                            "11-20",
                            "11-21",
                            "11-22"
                            ))
                    .series(arrayOf(
                            AASeriesElement()
                                    .name("使用时间")
                                    .data(arrayOf(200, 156, 60, 0, 0, 0, 0))
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
                                                            arrayOf("自设闹钟", 100),
                                                            arrayOf("好友闹钟", 90),
                                                            arrayOf("深度模式", 50),
                                                            arrayOf("普通模式", 110)
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
                            "好友闹钟",
                            "深度模式",
                            "普通模式"
                    ))
                    .series(arrayOf(AASeriesElement()
                            .name("完成率")
                            .data(arrayOf(0.9, 0.85, 0.6, 0.5))))
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
                    .categories(arrayOf("11-1至11-5",
                            "11-6至11-10",
                            "11-11至11-15",
                            "11-16至11-20",
                            "11-21至11-25",
                            "11-26至11-30"
                    ))
                    .series(arrayOf(
                            AASeriesElement()
                                    .name("使用时间(分钟)")
                                    .data(arrayOf(500, 700, 300, 170, 0, 0))
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
                                                            arrayOf("自设闹钟", 600),
                                                            arrayOf("好友闹钟", 700),
                                                            arrayOf("深度模式", 500),
                                                            arrayOf("普通模式", 660)
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
                            "好友闹钟",
                            "深度模式",
                            "普通模式"
                    ))
                    .series(arrayOf(AASeriesElement()
                            .name("完成率")
                            .data(arrayOf(0.7, 0.65, 0.8, 0.7))))
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
                                    .name("使用时间(分钟)")
                                    .data(arrayOf(1000,2045,3055,1024,500,777,3232,452,900,2222,500,0))
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
                                                            arrayOf("自设闹钟", 5555),
                                                            arrayOf("好友闹钟", 3256),
                                                            arrayOf("深度模式", 2356),
                                                            arrayOf("普通模式", 1579)
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
                            "好友闹钟",
                            "深度模式",
                            "普通模式"
                    ))
                    .series(arrayOf(AASeriesElement()
                            .name("完成率")
                            .data(arrayOf(0.8, 0.9, 0.7, 0.85))))
            val aaChartView=findViewById<AAChartView>(R.id.aa_chart_view)
            val aaChartView1=findViewById<AAChartView>(R.id.aa_chart_view1)
            val aaChartView2=findViewById<AAChartView>(R.id.aa_chart_view2)
            aaChartView.aa_refreshChartWithChartModel(aaChartModel)
            aaChartView1.aa_refreshChartWithChartModel(aaChartModel1)
            aaChartView2.aa_refreshChartWithChartModel(aaChartModel2)
        }
    }
}