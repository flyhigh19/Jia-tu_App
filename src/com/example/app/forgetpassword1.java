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
import android.widget.Toast;

public class forgetpassword1 extends Activity implements OnClickListener{
	private Button uploadpassword;  //下一步
	private EditText setpassword;   //重设密码
	private EditText confirm;       //再次输入
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
								application app=application.getInstance();
								String user=(String)app.getMap().get("userzh1");
								String setpassword1=setpassword.getText().toString().trim();
								String confirm1=confirm.getText().toString().trim();
								//找回密码后重设密码
								String sendmsg=user+"|"+confirm1+"/"+"重设密码";
								dos.writeUTF(sendmsg);
								// 读取服务器发来的数据
								cContent = dis.readUTF();
								if(cContent.equals("设置成功")){
									cHandler.sendEmptyMessage(0x02);
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
					Intent intent1 =new Intent(forgetpassword1.this,forgetpassword2.class);
					//启动
					startActivity(intent1);
					break;
				/*case 0x03:
					Toast.makeText(forgetpassword1.this, "重设密码失败，请稍后重试！！", Toast.LENGTH_SHORT).show();
					break;*/
				case 0x04:
					Toast.makeText(forgetpassword1.this, "设置密码！=确认密码，请重新输入", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgetpassword1);
		uploadpassword = (Button) findViewById(R.id.uploadpassword);
		setpassword = (EditText) findViewById(R.id.setpassword);
		confirm = (EditText) findViewById(R.id.confirm);
		uploadpassword.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.uploadpassword:
			String setpassword2=setpassword.getText().toString().trim();
			String confirm2=confirm.getText().toString().trim();
			if(setpassword2.equals(confirm2)){
				cHandler.sendEmptyMessage(0x01);
			}else{
				cHandler.sendEmptyMessage(0x04);
			}
		}
	}
}
