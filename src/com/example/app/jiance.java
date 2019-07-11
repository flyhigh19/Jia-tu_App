package com.example.app;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class jiance  extends Activity implements OnClickListener {
	private LineChart mLineChart; //折线图
	private BarChart mChart;     //柱形图
	private TextView jrlj;      //今日累计
	private Handler handler=new Handler(){
		//处理消息
		public void handleMessage(Message msg){
			String str=(String)msg.obj;
			if(str.length()==3){
				jrlj.setPadding(145, 0, 0, 0);
				jrlj.setText(str);
			}else if(str.length()==5){
				jrlj.setPadding(100, 0, 0, 0);
				jrlj.setText(str);
			}else if(str.length()==6){
				jrlj.setPadding(60, 0, 0, 0);
				jrlj.setText(str);
			}
		}
	};
	private TextView sskl;      //瞬时客流
	private Handler handler1=new Handler(){
		//处理消息
		public void handleMessage(Message msg){
			String str1=(String)msg.obj;
			if(str1.length()==3){
				sskl.setPadding(145, 0, 0, 0);
				sskl.setText(str1);
			}else if(str1.length()==5){
				sskl.setPadding(95, 0, 0, 0);
				sskl.setText(str1);
			}else if(str1.length()==6){
				sskl.setPadding(60, 0, 0, 0);
				sskl.setText(str1);
			}
		}
	};
	private Button return1;
	private Spinner spinner1;  //下拉列表框
	private Spinner spinner2;  //下拉列表框
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jiance);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		initSpinner1();
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		initSpinner2();
		jrlj = (TextView) findViewById(R.id.jrlj);
		//创建子线程并启动
		MyThread myth=new MyThread();
		myth.start();
		sskl = (TextView) findViewById(R.id.sskl);
		MyThread1 myth1=new MyThread1();
		myth1.start();		
		return1 = (Button) findViewById(R.id.return1);
		return1.setOnClickListener(this);
		mLineChart = (LineChart) findViewById(R.id.spread_line_chart); 
		// mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf"); 
		LineData mLineData = getLineData(24, 12); 
		showChart(mLineChart, mLineData, Color.rgb(255,255,255)); 
		mChart = (BarChart) findViewById(R.id.chart);
	    showChart(getBarData());
	}
	//自定义子线程
	class MyThread extends Thread{
		public void run(){
			try{
				String[] str={"200","589","1,153","2,025","3,149","4,469","5,929","7,473","9,045","10,554","11,951","13,180","14,185"};
				for(int i=0;i<str.length;i++){
					Message msg=new Message();
					msg.obj=str[i];
					//发送消息
					Thread.sleep(900);
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
					String[] str1={"200","389","564","872","1,124","1,320","1,460","1,544","1,572","1,509","1,397","1,229","1,005","872"};
					for(int i=0;i<str1.length;i++){
						Message msg=new Message();
						msg.obj=str1[i];
						//发送消息
						Thread.sleep(900);
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
			mLegend.setTextColor(Color.BLUE);// 颜色 
	 		//mLegend.setTypeface(mTf);// 字体 
			lineChart.animateX(14800); // 立即执行的动画,x轴 
			
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
//	       leftAxis.setValueFormatter();//自定义Y轴数据格式
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
				xValues.add(""+i+":00"); 
			} 
			// y轴的数据 
			ArrayList<Entry> yValues = new ArrayList<Entry>(); 
			for (int i = 0; i <=count; i++) { 
				//float value = (float) (Math.random() * range) + 3; 
				float value1 = (float) (-7*i*i+196*i+200); //模拟函数用来表示模拟该时间节点上景区的人数
				yValues.add(new Entry(value1, i)); 
			} 
			// create a dataset and give it a type 
			// y轴的数据集合 
			application app=application.getInstance();
			String result=(String)app.getMap().get("result0");
			LineDataSet lineDataSet = new LineDataSet(yValues, "2019年2月9日"+result+"实时人数" /*显示在比例图上*/); 
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
	        lineDataSet.setFillColor(Color.rgb(0, 255, 255));//折线图下方填充颜色设置
			LineData lineData = new LineData(xValues, lineDataSets); 
	 		return lineData;
		}

		   /**
		    * 显示柱状图表
		    *
		    * @param barData
		    */
		   private void showChart(BarData barData) {
		       // 设置描述
		       mChart.setDescription("");
		       mChart.setTouchEnabled(true); // 设置是否可以触摸 
				// enable scaling and dragging 
		       mChart.setDragEnabled(true);// 是否可以拖拽 
		       mChart.setScaleEnabled(true);// 是否可以缩放 
				// if disabled, scaling can be done on x- and y-axis separately 
		       mChart.setPinchZoom(true);// X,Y轴同时缩放
		       mChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
		       // 设置图表数据
		       mChart.setData(barData);
		       // 设置动画
		       mChart.animateY(3000);
		       /**
		         * 设置X轴
		         * */
		        XAxis xAxis = mChart.getXAxis();
		        xAxis.setEnabled(false);//显示X轴
		        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴位置
		        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
		        xAxis.setSpaceBetweenLabels(3);
		        xAxis.setDrawGridLines(true);   //x轴的坐标线
		   }

		   /**
		    * 获取柱状数据
		    *
		    * @return
		    */
		   private BarData getBarData() {
			   ArrayList<String> xValues = new ArrayList<String>(); 
				for (int i = 0; i <=9; i++) { 
					// x轴显示的数据，这里默认使用数字下标显示 
					xValues.add(""+i); 
				} 
		       ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

		       float quarterly1 = 3120;
		       float quarterly2 = 2650;
		       float quarterly3 = 3105;
		       float quarterly4 = 4550;
		       float quarterly5 = 4860;
		       float quarterly6 = 4250;
		       float quarterly7 = 4020;
		       float quarterly8 = 3550;
		       float quarterly9 = 3150;
		       float quarterly10 = 3450;

		       yValues.add(new BarEntry(quarterly1, 0));
		       yValues.add(new BarEntry(quarterly2, 1));
		       yValues.add(new BarEntry(quarterly3, 2));
		       yValues.add(new BarEntry(quarterly4, 3));
		       yValues.add(new BarEntry(quarterly5, 4));
		       yValues.add(new BarEntry(quarterly6, 5));
		       yValues.add(new BarEntry(quarterly7, 6));
		       yValues.add(new BarEntry(quarterly8, 7));
		       yValues.add(new BarEntry(quarterly9, 8));
		       yValues.add(new BarEntry(quarterly10, 9));
		       application app=application.getInstance();
		       String result=(String)app.getMap().get("result0");
		       BarDataSet barDataSet = new BarDataSet(yValues, "2019年2月7日-17日"+result+"累计人数");
		       /*ArrayList<Integer> colors = new ArrayList<Integer>();
		       colors.add(Color.rgb(255, 123, 124));
		       barDataSet.setColors(colors);*/
		       BarData barData = new BarData(xValues, barDataSet);
		       return barData;
		   }
	/** 初始化spinner1 */
	private void initSpinner1() {
		String[] strArr = new String[] { "2019-02-09", "2019-02-01", "2019-01-25", "2019-01-02" };
		// 1.上下文 2.列表项的layout 资源ID 3.spinner 下拉列表要绑定数据
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, strArr);
		spinner1.setAdapter(adapter);
	}
	/** 初始化spinner2 */
	private void initSpinner2() {
		String[] strArr = new String[] { "2019-02-07", "2019-02-05", "2019-01-20", "2019-01-08" };
		// 1.上下文 2.列表项的layout 资源ID 3.spinner 下拉列表要绑定数据
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, strArr);
		spinner2.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.return1:
			// 给bnt1添加点击响应事件
			Intent intent2 =new Intent(jiance.this,search.class);
			//启动
			startActivity(intent2);
			break;
		}
	}
}
