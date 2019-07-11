package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class account extends Activity implements OnClickListener{
	private Button return1;   //返回个人信息界面
	private Button exitlogin;  //退出登录
	private TextView mobile;   //手机号码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		return1  = (Button) findViewById(R.id.return1);
		return1.setOnClickListener(this);
		mobile  = (TextView) findViewById(R.id.mobile);
		application app=application.getInstance();
		String user=(String)app.getMap().get("USER");
		mobile.setText(user);
		exitlogin  = (Button) findViewById(R.id.exitlogin);
		exitlogin.setOnClickListener(this);
	}
	@Override
	public void onClick(View v1) {
		// TODO Auto-generated method stub
		switch (v1.getId()){
		case R.id.return1:
			// 给bnt1添加点击响应事件
			Intent intent1 =new Intent(account.this,MessageActivity.class);
			//启动
			startActivity(intent1);
			break;
		/*case R.id.exitlogin:
			// 给bnt1添加点击响应事件
			ActivityCollector.finishAll();  // 销毁所有活动
			Intent intent2 =new Intent(account.this,login.class);
			//启动
			startActivity(intent2);
			break;*/
		}
	}
}
