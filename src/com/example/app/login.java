package com.example.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends Activity implements OnClickListener {
	private CheckBox remenber;  //记住密码
	private SharedPreferences config;  //配置文件
	private Button btn_login;   //登陆
	private TextView register;     //注册
	private TextView forgetpassword;   //找回密码
	private EditText user;
	private EditText password;
	// Socket用于连接服务器获取输入输出流
	private Socket cSocket;
	// 服务器server/IP地址(当前PC的IP地址)
	private final String ADDRESS = "134.175.66.2";
	// 服务器端口
	private final int PORT = 8888;
	// 消息处理的线程
	private Thread cThread;
		// 消息的内容
	private String cContent;
		// 处理消息机制
	Handler cHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0x01:
				cThread = new Thread() {
					@Override
					public void run() {
						super.run();
						DataInputStream dis = null;
						DataOutputStream dos = null;
						try {
							cSocket = new Socket(ADDRESS, PORT);
							// 阻塞函数，正常连接后才会向下继续执行
							dis = new DataInputStream(cSocket.getInputStream());
							dos = new DataOutputStream(
									cSocket.getOutputStream());
							// 向服务器写数据
							String usernumber=user.getText().toString().trim();
							String password1=password.getText().toString().trim();
							String sendmsg=usernumber+"|"+password1+"/"+"请求登录";
							dos.writeUTF(sendmsg);
							// 读取服务器发来的数据
							cContent = dis.readUTF();
							if(cContent.equals("允许登录")){
								cHandler.sendEmptyMessage(0x02);
							}else if(cContent.equals("不允许登录")){
								cHandler.sendEmptyMessage(0x03);
							}
						} catch (UnknownHostException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								if (dis != null) {
									dis.close();
								}
								if (dos != null) {
									dos.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				};
				cThread.start();
				break;
			case 0x02:
				Intent intent1 =new Intent(login.this,MainActivity.class);
				Toast.makeText(login.this, "登陆成功", Toast.LENGTH_SHORT).show();
				//启动
				//application app=application.getInstance();
				//app.getMap().put("user", usernumber2);
				startActivity(intent1);
				break;
			case 0x03:
				Toast.makeText(login.this, "登陆失败，您尚未注册", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		btn_login = (Button) findViewById(R.id.btn_login);  //登录
		btn_login.setOnClickListener(this);
		register = (TextView) findViewById(R.id.register);  //注册
		register.setOnClickListener(this);
		forgetpassword = (TextView) findViewById(R.id.forgetpassword); //忘记密码
		forgetpassword.setOnClickListener(this); 
		user = (EditText) findViewById(R.id.user);     //手机号框
		/*user.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {	
				if (s.length()== 0)						
					return;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < s.length(); i++) {	
					if (i != 3 && i != 8 && s.charAt(i) == ' ') {
						continue;
					}else {
						sb.append(s.charAt(i));
						if ((sb.length() == 4 || sb.length() == 9)
								&& sb.charAt(sb.length() - 1) != ' ') {
							sb.insert(sb.length() - 1, ' ');
						}
					}
				}
				if (!sb.toString().equals(s.toString())) {
					int index = start + 1;
					if (sb.charAt(start) == ' ') {
						if (before == 0) {
							index++;
						} else {
							index--;
						}
					} else {
						if (before == 1) {
							index--;
						}
					}
					user.setText(sb.toString());
					user.setSelection(index);
				}
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}
        });*/
		password = (EditText) findViewById(R.id.password);   //密码框
		remenber = (CheckBox) findViewById(R.id.remenber);  //记住密码
		config=getSharedPreferences("config", MODE_PRIVATE);   //文件名，模式
		//是否记住了密码
		boolean ischecked=config.getBoolean("ischecked", false);
		//如果记住了，下一次打开就自动填写相应的状态
		if(ischecked){
			user.setText(config.getString("usernumber2", ""));
			password.setText(config.getString("password2", ""));
		}
		remenber.setChecked(ischecked);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btn_login:
			Editor edit=config.edit();  //得到编辑器，然后来操作配置来保存账号和密码以及选中的状态
			String usernumber2=user.getText().toString().trim();
			String password2=password.getText().toString().trim();
			application app=application.getInstance();
			app.getMap().put("USER",usernumber2);
			boolean ischecked=remenber.isChecked();
			//存CheckBox的状态
			edit.putBoolean("ischecked", ischecked);
			if(ischecked){
				edit.putString("usernumber2", usernumber2).putString("password2", password2);
			}else{
				edit.remove("usernumber2").remove("password2");
			}
			//提交内存的配置信息到本地
			edit.commit();
			if((usernumber2.equals("001"))&&(password2.equals("login"))){
				cHandler.sendEmptyMessage(0x02);	
			}else{
				cHandler.sendEmptyMessage(0x01);
			}
			break;
		case R.id.register:
			// 给bnt1添加点击响应事件
			Intent intent2 =new Intent(login.this,register.class);
			//启动
			startActivity(intent2);
			break;	
		case R.id.forgetpassword:
			// 给bnt1添加点击响应事件
			Intent intent3 =new Intent(login.this,forgetpassword.class);
			//启动
			startActivity(intent3);
			break;
		}
	}
}
