package com.example.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class forgetpassword extends Activity implements OnClickListener{
	private Button btn_next1;  //下一步
	private EditText userzh;   //手机号
	private EditText ma;       //注册码找回
	private ImageView iv_two;  //获取验证码
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
							String userzh1=userzh.getText().toString().trim();
							String ma1=ma.getText().toString().trim();
							String sendmsg=userzh1+"|"+ma1+"/"+"找回密码";
							dos.writeUTF(sendmsg);
							// 读取服务器发来的数据
							cContent = dis.readUTF();
							if(cContent.equals("成功找回密码！！")){
								cHandler.sendEmptyMessage(0x02);
							}else if(cContent.equals("错误")){
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
				// 给bnt1添加点击响应事件
				Intent intent1 =new Intent(forgetpassword.this,forgetpassword1.class);
				String usernumber=userzh.getText().toString().trim();
				application app=application.getInstance();
				app.getMap().put("userzh1", usernumber);
				//启动
				startActivity(intent1);
				break;
			case 0x03:
				Toast.makeText(forgetpassword.this, "错误,无法找回", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgetpassword);
		btn_next1 = (Button) findViewById(R.id.btn_next1);
	    userzh = (EditText) findViewById(R.id.userzh);
	    ma = (EditText) findViewById(R.id.ma);
		btn_next1.setOnClickListener(this);
		iv_two = (ImageView) findViewById(R.id.iv_two);   //验证码
		iv_two.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btn_next1:
			cHandler.sendEmptyMessage(0x01);
		case R.id.iv_two:
			iv_two.setImageResource(R.drawable.y2);
		}
	}
}
