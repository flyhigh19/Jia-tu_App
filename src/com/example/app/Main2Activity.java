package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main2Activity extends Activity implements OnClickListener{
	private Button btn_data; //数据
	private Button btn_run;  //行程
	private Button btn_message; //个人信息
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		btn_data  = (Button) findViewById(R.id.btn_data);
		btn_data.setOnClickListener(this);
		btn_run  = (Button) findViewById(R.id.btn_run);
		btn_run.setOnClickListener(this);
		btn_message  = (Button) findViewById(R.id.btn_message);
		btn_message.setOnClickListener(this);
	}
	@Override
	public void onClick(View v1) {
		// TODO Auto-generated method stub
		switch (v1.getId()){
		case R.id.btn_data:
			// 给bnt1添加点击响应事件
			Intent intent2 =new Intent(Main2Activity.this,MainActivity.class);
			//启动
			startActivity(intent2);
			break;
		case R.id.btn_run:
			// 给bnt1添加点击响应事件
			Intent intent3 =new Intent(Main2Activity.this,Main3Activity.class);
			//启动
			startActivity(intent3);
			break;
		case R.id.btn_message:
			// 给bnt1添加点击响应事件
			Intent intent4 =new Intent(Main2Activity.this,MessageActivity.class);
			//启动
			startActivity(intent4);
			break;
		}
	}
}
