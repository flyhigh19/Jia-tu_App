package com.example.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class register extends Activity implements OnClickListener {
	private Button zhuce;   //注册后返回
	private EditText userzc;   //注册手机号
	private EditText passwordzc;  //输入密码
	private EditText passwordqd;  //确定密码
	private ImageView iv_one;   //验证码获取
	private EditText ma;     //输入验证码
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
								// 阻塞函数，正常连接后才会向下继续执行
								cSocket = new Socket(ADDRESS, PORT);
								dis = new DataInputStream(cSocket.getInputStream());
								dos = new DataOutputStream(
										cSocket.getOutputStream());
								// 向服务器写数据
								String userzc1=userzc.getText().toString().trim();
								String passwordzc1=passwordzc.getText().toString().trim();
								String passwordqd1=passwordqd.getText().toString().trim();
								String sendmsg=userzc1+"#"+passwordzc1+"|"+passwordqd1+"/"+"请求注册";
								dos.writeUTF(sendmsg);
								// 读取服务器发来的数据
								cContent = dis.readUTF();
								if(cContent.equals("注册成功！！")){
									cHandler.sendEmptyMessage(0x02);
								}/*else if(cContent.equals("不允许注册")){
									cHandler.sendEmptyMessage(0x03);
								}*/else if(cContent.equals("该账户已经注册！！")){
									cHandler.sendEmptyMessage(0x04);
								}/*else if(cContent.equals("注册密码!=确认密码")){
									cHandler.sendEmptyMessage(0x05);
								}*/
								
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
					Intent intent1=new Intent(register.this,login.class);
					Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
					startActivity(intent1);
					break;
				/*case 0x03:
					Toast.makeText(register.this, "注册失败", Toast.LENGTH_SHORT).show();
					break;*/
				case 0x04:
					Toast.makeText(register.this, "该用户已经注册过，请重新输入账号,密码！！", Toast.LENGTH_SHORT).show();
					break;
				/*case 0x05:
					Toast.makeText(register.this, "注册密码!=确认密码,请重新输入密码", Toast.LENGTH_SHORT).show();
					break;*/
				case 0x06:
					Toast.makeText(register.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
					break;
				case 0x07:
					Toast.makeText(register.this, "注册密码!=确认密码,请重新输入密码", Toast.LENGTH_SHORT).show();
					break;
				case 0x08:
					Toast.makeText(register.this, "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		zhuce = (Button)findViewById(R.id.zhuce);
		zhuce.setOnClickListener(this);
		userzc = (EditText) findViewById(R.id.userzc);     //手机号框
		/*userzc.addTextChangedListener(new TextWatcher() {
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
						userzc.setText(sb.toString());
						userzc.setSelection(index);
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
		passwordzc = (EditText) findViewById(R.id.passwordzc);   //密码框注册
		passwordqd = (EditText) findViewById(R.id.passwordqd);   //密码框确定
		ma = (EditText) findViewById(R.id.ma);            //验证码验证
		iv_one = (ImageView) findViewById(R.id.iv_one);   //验证码图片获取
		iv_one.setOnClickListener(this);
		Drawable drawable = getResources().getDrawable(R.drawable.b);
	        drawable .setBounds(0, 0, 95, 95);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
	        userzc.setCompoundDrawables(drawable , null, null, null);//只放左边
	    Drawable drawable1 = getResources().getDrawable(R.drawable.i17);
	        drawable1 .setBounds(0, 0, 95, 95);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
	        passwordzc.setCompoundDrawables(drawable1 , null, null, null);//只放左边
	    Drawable drawable2 = getResources().getDrawable(R.drawable.i17);
	        drawable2 .setBounds(0, 0, 95, 95);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
	        passwordqd.setCompoundDrawables(drawable2 , null, null, null);//只放左边
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.zhuce:
			String userzc2=userzc.getText().toString().trim();
			String passwordzc2=passwordzc.getText().toString().trim();
			String passwordqd2=passwordqd.getText().toString().trim();
			String ma1=ma.getText().toString().trim();
			//如果手机号不是11位数字
			boolean boo = isMobileNO(userzc2);
			if (boo) {
				//System.out.println("电话号码正确！-->" + userzc2);
				if(passwordzc2.equals(passwordqd2)){
				//System.out.println("密码正确！-->" + passwordzc2);	
					if(ma1.equals("0408")){
						cHandler.sendEmptyMessage(0x01);
					}else {
						cHandler.sendEmptyMessage(0x08);
					}
				}else{
					cHandler.sendEmptyMessage(0x07);
				}
			}else {
				//System.out.println("电话号码错误！***>" + userzc2);
				cHandler.sendEmptyMessage(0x06);
			}
		case R.id.iv_one:
			iv_one.setImageResource(R.drawable.y3);
		}
	}
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			//判断是否为手机号 13********* ,15********,18*********
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
 
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
