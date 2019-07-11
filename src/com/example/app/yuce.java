package com.example.app;

import java.util.ArrayList;

import com.example.app.jiance.MyThread;
import com.example.app.jiance.MyThread1;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class yuce  extends Activity implements OnClickListener {
	private Button return1;
	private LineChart mLineChart; //折线图
	private LineChart mLineChart1; //折线图
	private TextView nowpeople;      //今日累计
	private Handler handler=new Handler(){
		//处理消息
		public void handleMessage(Message msg){
			String str=(String)msg.obj;
			if(str.length()==3){
				nowpeople.setPadding(145, 0, 0, 0);
				nowpeople.setText(str);
			}else if(str.length()==5){
				nowpeople.setPadding(100, 0, 0, 0);
				nowpeople.setText(str);
			}else if(str.length()==6){
				nowpeople.setPadding(60, 0, 0, 0);
				nowpeople.setText(str);
			}else if(str.length()==2){
				nowpeople.setPadding(185, 0, 0, 0);
				nowpeople.setText(str);
			}
		}
	};
	private TextView bear;      //承载量
	private Handler handler1=new Handler(){
		//处理消息
		public void handleMessage(Message msg){
			String str1=(String)msg.obj;
			if(str1.length()==3){
				bear.setPadding(145, 0, 0, 0);
				bear.setText(str1);
			}else if(str1.length()==5){
				bear.setPadding(95, 0, 0, 0);
				bear.setText(str1);
			}else if(str1.length()==6){
				bear.setPadding(60, 0, 0, 0);
				bear.setText(str1);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yuce);
		return1 = (Button) findViewById(R.id.return1);
		return1.setOnClickListener(this);
		nowpeople = (TextView) findViewById(R.id.nowpeople);
		//创建子线程并启动
		MyThread myth=new MyThread();
		myth.start();
		bear = (TextView) findViewById(R.id.bear);
		MyThread1 myth1=new MyThread1();
		myth1.start();	
		mLineChart = (LineChart) findViewById(R.id.spread_line_chart); 
		LineData mLineData = getLineData(24, 12); 
		showChart(mLineChart, mLineData, Color.rgb(255,255,255));
		mLineChart1 = (LineChart) findViewById(R.id.spread1_line_chart); 
		LineData mLineData1 = getLineData1(7); 
		showChart1(mLineChart1, mLineData1, Color.rgb(255,255,255));
	}
	//自定义子线程
	class MyThread extends Thread{
		public void run(){
			try{
				String[] str={"3,149","5,469","8,529","11,473","13,545","14,954","14,851","13,550","10,480","6,785","7,550","6,032","3,200","1,200","400","50","20","15","12","25","320","1,200","2,400","3,700","5,250"};
				String[] str1={"3149"};
				for(int i=0;i<str1.length;i++){
					Message msg=new Message();
					msg.obj=str[i];
					//发送消息
					Thread.sleep(500);
					handler.sendMessage(msg);
				}		
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	//自定义子线程
		class MyThread1 extends Thread{
			public void run(){
				try{
					String[] str1={"17,500"};
					for(int i=0;i<str1.length;i++){
						Message msg=new Message();
						msg.obj=str1[i];
						//发送消息
						Thread.sleep(500);
						handler1.sendMessage(msg);
					}		
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		// 设置显示的样式 
				private void showChart(LineChart lineChart, LineData lineData, int color) { 
					//lineChart.setDrawBorders(true); //是否在折线图上添加边框 
					// no description text 
					lineChart.setDescription(" ");// 数据描述 
					// 如果没有数据的时候，会显示这个，类似listview的emtpyview 
					//lineChart.setNoDataTextDescription("You need to provide data for the chart."); 
					// enable / disable grid background 
					lineChart.setDrawGridBackground(false); // 是否显示表格颜色 
					lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度 
					// enable touch gestures 
					lineChart.setTouchEnabled(true); // 设置是否可以触摸 
					// enable scaling and dragging 
					lineChart.setDragEnabled(true);// 是否可以拖拽 
					lineChart.setScaleEnabled(true);// 是否可以缩放 
					// if disabled, scaling can be done on x- and y-axis separately 
					lineChart.setPinchZoom(true);// X,Y轴同时缩放
					lineChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
					//lineChart.getXAxis().setGridColor(Color.TRANSPARENT);//去掉网格中竖线的显示
					lineChart.setBackgroundColor(color);// 设置背景 
					// add data 
					lineChart.setData(lineData); // 设置数据 
					// get the legend (only possible after setting data) 
					Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的 
					// modify the legend ... 
					//mLegend.setPosition(LegendPosition.LEFT_OF_CHART); 
					mLegend.setForm(LegendForm.CIRCLE);// 样式 
					mLegend.setFormSize(7f);// 字体 
					mLegend.setTextColor(Color.BLACK);// 颜色 
			 		//mLegend.setTypeface(mTf);// 字体 
					lineChart.animateX(12000); // 立即执行的动画,x轴 					
					/**
			         * 设置X轴
			         * */
			        XAxis xAxis = lineChart.getXAxis();
			        xAxis.setEnabled(true);//显示X轴
			        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴位置
			        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
			        xAxis.setSpaceBetweenLabels(3);
			        xAxis.setDrawGridLines(true);   //x轴的坐标线
			        /**
			        *
			        * 设置左侧Y轴
			        * */
			       YAxis leftAxis = lineChart.getAxisLeft();
			       leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//			       leftAxis.setValueFormatter();//自定义Y轴数据格式
			       leftAxis.setStartAtZero(true);//设置Y轴的数据不是从0开始
			       leftAxis.setDrawTopYLabelEntry(true);
				} 
				/** 
				 * 生成一个数据 
				 * @param count 表示图表中有多少个坐标点 
				 * @param range 用来生成range以内的随机数 
				 * @return 
				 */
				private LineData getLineData(int count, float range) { 
					ArrayList<String> xValues = new ArrayList<String>(); 
					for (int i = 0; i <=count; i++) { 
						// x轴显示的数据，这里默认使用数字下标显示 
						if((i+8)>24)
						{
							xValues.add(""+(i-16)+":00");
						}else{
							xValues.add(""+(i+8)+":00"); 
						}	
					} 
					// y轴的数据 
					ArrayList<Entry> yValues = new ArrayList<Entry>(); 
//					for (int i = 0; i <=count; i++) { 
						//float value = (float) (Math.random() * range) + 3; 
						//float value1 = (float) (-7*i*i+196*i+200); //模拟函数用来表示模拟该时间节点上景区的人数
						//yValues.add(new Entry(value1, i)); 
						   float quarterly1 = 3149;
					       float quarterly2 = 5469;
					       float quarterly3 = 8529;
					       float quarterly4 = 11473;
					       float quarterly5 = 13545;
					       float quarterly6 = 14954;
					       float quarterly7 = 14851;
					       float quarterly8 = 13550;
					       float quarterly9 = 10480;
					       float quarterly10 = 6785;
					       float quarterly11 = 7550;
					       float quarterly12 = 6032;
					       float quarterly13 = 3200;
					       float quarterly14 = 1200;
					       float quarterly15 = 400;
					       float quarterly16 = 50;
					       float quarterly17 = 20;
					       float quarterly18 = 15;
					       float quarterly19 = 12;
					       float quarterly20 = 25;
					       float quarterly21 = 320;
					       float quarterly22 = 1200;
					       float quarterly23 = 2400;
					       float quarterly24 = 3700;
					       float quarterly25 = 5250;

					       yValues.add(new Entry(quarterly1, 0));
					       yValues.add(new Entry(quarterly2, 1));
					       yValues.add(new Entry(quarterly3, 2));
					       yValues.add(new Entry(quarterly4, 3));
					       yValues.add(new Entry(quarterly5, 4));
					       yValues.add(new Entry(quarterly6, 5));
					       yValues.add(new Entry(quarterly7, 6));
					       yValues.add(new Entry(quarterly8, 7));
					       yValues.add(new Entry(quarterly9, 8));
					       yValues.add(new Entry(quarterly10, 9));
					       yValues.add(new Entry(quarterly11, 10));
					       yValues.add(new Entry(quarterly12, 11));
					       yValues.add(new Entry(quarterly13, 12));
					       yValues.add(new Entry(quarterly14, 13));
					       yValues.add(new Entry(quarterly15, 14));
					       yValues.add(new Entry(quarterly16, 15));
					       yValues.add(new Entry(quarterly17, 16));
					       yValues.add(new Entry(quarterly18, 17));
					       yValues.add(new Entry(quarterly19, 18));
					       yValues.add(new Entry(quarterly20, 19));
					       yValues.add(new Entry(quarterly21, 20));
					       yValues.add(new Entry(quarterly22, 21));
					       yValues.add(new Entry(quarterly23, 22));
					       yValues.add(new Entry(quarterly24, 23));
					       yValues.add(new Entry(quarterly25, 24));
//					} 
					// create a dataset and give it a type 
					// y轴的数据集合 
					application app=application.getInstance();
					String result=(String)app.getMap().get("result0");
					LineDataSet lineDataSet = new LineDataSet(yValues, "未来24小时"+result+"预测人数" /*显示在比例图上*/); 
					// mLineDataSet.setFillAlpha(110); 
					// mLineDataSet.setFillColor(Color.RED); 
					//用y轴的集合来设置参数 
					lineDataSet.setLineWidth(1.75f); // 线宽 
					lineDataSet.setCircleSize(4f);// 显示的圆形大小 
					lineDataSet.setColor(Color.CYAN);// 显示颜色 
					lineDataSet.setCircleColor(Color.TRANSPARENT);// 圆形的颜色 
					lineDataSet.setHighLightColor(Color.GREEN); // 高亮的线的颜色 
					lineDataSet.setDrawValues(true);//隐藏折线图每个数据点的值
					ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>(); 
					lineDataSets.add(lineDataSet); // add the datasets 
					// create a data object with the datasets 
					lineDataSet.setDrawCircles(true);//图表上的数据点是否用小圆圈表示
			        lineDataSet.setDrawCubic(true);//允许设置平滑曲线
			        lineDataSet.setDrawFilled(true);//是否填充折线下方
			        //lineDataSet.setFillColor(Color.rgb(0, 255, 255));//折线图下方填充颜色设置
					LineData lineData = new LineData(xValues, lineDataSets); 
			 		return lineData;
				}
				
				
				// 设置显示的样式 
				private void showChart1(LineChart lineChart, LineData lineData, int color) { 
					//lineChart.setDrawBorders(true); //是否在折线图上添加边框 
					// no description text 
					lineChart.setDescription("");// 数据描述 
					// 如果没有数据的时候，会显示这个，类似listview的emtpyview 
					//lineChart.setNoDataTextDescription("You need to provide data for the chart."); 
					// enable / disable grid background 
					lineChart.setDrawGridBackground(false); // 是否显示表格颜色 
					lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度 
					// enable touch gestures 
					lineChart.setTouchEnabled(true); // 设置是否可以触摸 
					// enable scaling and dragging 
					lineChart.setDragEnabled(true);// 是否可以拖拽 
					lineChart.setScaleEnabled(true);// 是否可以缩放 
					// if disabled, scaling can be done on x- and y-axis separately 
					lineChart.setPinchZoom(true);// X,Y轴同时缩放
					lineChart.getAxisRight().setEnabled(false); // 隐藏右边的坐标轴
					//lineChart.getAxisLeft().setEnabled(false);  // 隐藏左边的坐标轴
					//lineChart.getXAxis().setGridColor(Color.TRANSPARENT);//去掉网格中竖线的显示
					lineChart.setBackgroundColor(color);// 设置背景 
					// add data 
					lineChart.setData(lineData); // 设置数据 
					// get the legend (only possible after setting data) 
					Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的 
					// modify the legend ... 
					//mLegend.setPosition(LegendPosition.LEFT_OF_CHART); 
					mLegend.setForm(LegendForm.CIRCLE);// 样式 
					mLegend.setFormSize(7f);// 字体 
					mLegend.setTextColor(Color.BLACK);// 颜色 
			 		//mLegend.setTypeface(mTf);// 字体 
					lineChart.animateX(13500); // 立即执行的动画,x轴 					
					/**
			         * 设置X轴
			         * */
			        XAxis xAxis = lineChart.getXAxis();
			        xAxis.setEnabled(true);//显示X轴
			        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴位置
			        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
			        xAxis.setSpaceBetweenLabels(3);
			        xAxis.setDrawGridLines(true);   //x轴的坐标线
			        /**
			        *
			        * 设置左侧Y轴
			        * */
			       YAxis leftAxis = lineChart.getAxisLeft();
			       leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//			       leftAxis.setValueFormatter();//自定义Y轴数据格式
			       leftAxis.setStartAtZero(true);//设置Y轴的数据不是从0开始
			       leftAxis.setDrawTopYLabelEntry(false);
				} 
				/** 
				 * 生成一个数据 
				 * @param count 表示图表中有多少个坐标点 
				 * @return 
				 */
				private LineData getLineData1(int count) { 
					ArrayList<String> xValues = new ArrayList<String>(); 
					for (int i = 0; i <=count; i++) { 
						// x轴显示的数据，这里默认使用数字下标显示 
						if(i==0)
						{
							xValues.add("今日"); 
						}else{
							xValues.add("3/"+(i+23)); 	
						}				
					} 
					// y轴的数据 
					ArrayList<Entry> yValues = new ArrayList<Entry>(); 
//					for (int i = 0; i <=count; i++) { 
						//float value = (float) (Math.random() * range) + 3; 
						//float value1 = (float) (-7*i*i+196*i+200); //模拟函数用来表示模拟该时间节点上景区的人数
						//yValues.add(new Entry(value1, i)); 
						   float quarterly1 = 13550;
					       float quarterly2 = 14000;
					       float quarterly3 = 14250;
					       float quarterly4 = 13473;
					       float quarterly5 = 12745;
					       float quarterly6 = 13454;
					       float quarterly7 = 14051;
					       float quarterly8 = 14150;
					      
					       yValues.add(new Entry(quarterly1, 0));
					       yValues.add(new Entry(quarterly2, 1));
					       yValues.add(new Entry(quarterly3, 2));
					       yValues.add(new Entry(quarterly4, 3));
					       yValues.add(new Entry(quarterly5, 4));
					       yValues.add(new Entry(quarterly6, 5));
					       yValues.add(new Entry(quarterly7, 6));
					       yValues.add(new Entry(quarterly8, 7));
					      
//					} 
					// create a dataset and give it a type 
					// y轴的数据集合 
					application app=application.getInstance();
					String result=(String)app.getMap().get("result0");
					LineDataSet lineDataSet = new LineDataSet(yValues, "未来7天"+result+"预测人数" /*显示在比例图上*/); 
					// mLineDataSet.setFillAlpha(110); 
					// mLineDataSet.setFillColor(Color.RED); 
					//用y轴的集合来设置参数 
					lineDataSet.setLineWidth(1.75f); // 线宽 
					lineDataSet.setCircleSize(4f);// 显示的圆形大小 
					lineDataSet.setColor(Color.CYAN);// 显示颜色 
					lineDataSet.setCircleColor(Color.TRANSPARENT);// 圆形的颜色 
					lineDataSet.setHighLightColor(Color.GREEN); // 高亮的线的颜色 
					lineDataSet.setDrawValues(true);//隐藏折线图每个数据点的值
					ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>(); 
					lineDataSets.add(lineDataSet); // add the datasets 
					// create a data object with the datasets 
					lineDataSet.setDrawCircles(true);//图表上的数据点是否用小圆圈表示
			        lineDataSet.setDrawCubic(true);//允许设置平滑曲线
			        lineDataSet.setDrawFilled(true);//是否填充折线下方
			        //lineDataSet.setFillColor(Color.rgb(0, 255, 255));//折线图下方填充颜色设置
					LineData lineData = new LineData(xValues, lineDataSets); 
			 		return lineData;
				}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.return1:
			// 给bnt1添加点击响应事件
			Intent intent2 =new Intent(yuce.this,search.class);
			//启动
			startActivity(intent2);
			break;
		}
	}
}
