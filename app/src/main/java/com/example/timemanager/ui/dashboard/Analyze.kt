package com.example.timemanager.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.timemanager.R
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import kotlinx.android.synthetic.main.activity_analyze.*


class Analyze : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_analyze)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()
        val aaChartView = findViewById<AAChartView>(R.id.aa_chart_view)
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Area)
            .title("title")
                .yAxisTitle("时间")
                .animationType(AAChartAnimationType.EaseInCirc)
            .subtitle("subtitle")
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
        val aaChartView1=findViewById<AAChartView>(R.id.aa_chart_view1)
        val aaChartModel1=AAChartModel()
                .chartType(AAChartType.Pie)
                .title("各功能使用时间")
                .backgroundColor("#ffffff")
                .subtitle("virtual data")
                .dataLabelsEnabled(true)
                .yAxisTitle("分钟")
                .series(
                arrayOf(
                        AASeriesElement()
                                .name("使用时间（分钟）")
                                .data(
                                        arrayOf(
                                                arrayOf("自设闹钟", 20),
                                                arrayOf("好友闹钟", 50),
                                                arrayOf("深度模式", 100),
                                                arrayOf("普通模式", 200)
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
}