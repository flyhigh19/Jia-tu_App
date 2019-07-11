package com.example.app;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageActivity extends Activity implements OnClickListener{
	application app=application.getInstance();   //账号同步
	//String path1 =Environment.getExternalStorageDirectory()+ "/sign/";
	private ImageView ivHead;//头像显示  
	private Button btn_find;
	private Button btn_data;
	private Button btn_run;
	private Button iv_head;  //点击我的头像进入我的账号设置界面
	private Button m2;    //设置
	private Button c;    //头像更换
	//private TextView text_sign; //个性签名内容
	//private Button btn_sign; //个性签名更改
	//private Button bwl;  //备忘录
	//private TextView now_id;  //账号
	//private static String path = "/sdcard/DemoHead/";//sd路径  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		btn_find = (Button) findViewById(R.id.btn_find);
		btn_find.setOnClickListener(this);
		btn_run  = (Button) findViewById(R.id.btn_run);
		btn_run.setOnClickListener(this);
		btn_data  = (Button) findViewById(R.id.btn_data);
		btn_data.setOnClickListener(this);
		iv_head  = (Button) findViewById(R.id.iv_head);
		iv_head.setOnClickListener(this);
		m2  = (Button) findViewById(R.id.m2);   
		m2.setOnClickListener(this);
		/*bwl  = (Button) findViewById(R.id.bwl);          //备忘录
		bwl.setOnClickListener(this);
		tv_id  = (TextView) findViewById(R.id.tv_id);   //个性签名
		btn_sign  = (Button) findViewById(R.id.btn_sign);   //个性签名
		btn_sign.setOnClickListener(this);
		text_sign  = (TextView) findViewById(R.id.text_sign);   //个性签名
		DataInputStream in1;
		try {
			in1 = new DataInputStream(new FileInputStream(path1 + "m1.txt"));
			text_sign.setText(in1.readUTF());
			in1.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		c=(Button) findViewById(R.id.c);              //头像更改
		c.setOnClickListener(this);
		ivHead = (ImageView) findViewById(R.id.iv_head); 
		Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap 
		if (bt != null) {  
	           //如果本地有头像图片的话  
	           ivHead.setImageBitmap(bt);  
	       } else {  
	           //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略  
	             
	       } 
		
		String a=(String)app.getMap().get("user");
		now_id.setText(a);
	}
	public void onDestroy(){
		super.onDestroy();
		app.getMap().remove("user");*/
	}
	@Override
	public void onClick(View v4) {
		// TODO Auto-generated method stub
		switch (v4.getId()){
		case R.id.btn_find:
			// 给bnt1添加点击响应事件
			Intent intent1 =new Intent(MessageActivity.this,Main2Activity.class);
			//启动
			startActivity(intent1);
			break;
		case R.id.btn_run:
			// 给bnt1添加点击响应事件
			Intent intent2 =new Intent(MessageActivity.this,Main3Activity.class);
			//启动
			startActivity(intent2);
			break;
		case R.id.btn_data:
			// 给bnt1添加点击响应事件
			Intent intent3 =new Intent(MessageActivity.this,MainActivity.class);
			//启动
			startActivity(intent3);
			break;
		case R.id.iv_head:
			// 给bnt1添加点击响应事件
			Intent intent4 =new Intent(MessageActivity.this,account.class);
			//启动
			startActivity(intent4);
			break;
		case R.id.m2:
			// 给bnt1添加点击响应事件
			Intent intent5 =new Intent(MessageActivity.this,set.class);
			//启动
			startActivity(intent5);
			break;
		/*case R.id.c:
			// 给bnt1添加点击响应事件
			Intent intent4 =new Intent(MessageActivity.this,ImagechangeActivity1.class);
			//启动
			startActivity(intent4);
			break;
		case R.id.btn_sign:
			// 给bnt1添加点击响应事件
			Intent intent5 =new Intent(MessageActivity.this,signature.class);
			//启动
			startActivity(intent5);
			break;
		case R.id.bwl:
			// 给bnt1添加点击响应事件
			Intent intent6 =new Intent(MessageActivity.this,bwl.class);
			//启动
			startActivity(intent6);
			break;*/
		}
	}
}