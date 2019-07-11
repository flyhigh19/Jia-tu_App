package com.example.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.amap.api.maps.MapView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable.Callback;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class Main3Activity extends Activity implements OnClickListener{
	private Button btn_data; //数据
	private Button btn_find; //发现
	//private Button btn_sq;  //社区
	private Button btn_message; //个人信息
	private WebView webview;
	//MapView mMapView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		btn_data  = (Button) findViewById(R.id.btn_data);
		btn_data.setOnClickListener(this);
		btn_find  = (Button) findViewById(R.id.btn_find);
		btn_find.setOnClickListener(this);
		btn_message  = (Button) findViewById(R.id.btn_message);
		btn_message.setOnClickListener(this);
	    //mMapView = (MapView) findViewById(R.id.map);
		    //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
		//mMapView.onCreate(savedInstanceState);
		 webview = (WebView) findViewById(R.id.webView);
	        //设置WebView属性，能够执行Javascript脚本
	        webview.getSettings().setJavaScriptEnabled(true);
	        try {
	        	WebSettings web=webview.getSettings();
	        	String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath(); 

	        	//启用地理定位  
	        	web.setGeolocationEnabled(true);  
	        	//设置定位的数据库路径  
	        	web.setGeolocationDatabasePath(dir);  
	        	web.setJavaScriptEnabled(true);
	        	//一定要设置，不然定位不了
	        	web.setDomStorageEnabled(true);
	        	webview.setWebChromeClient(new WebChromeClient()
	        	{
	        		//配置权限（同样在WebChromeClient中实现）
	                @Override
	               //当前页面请求是否允许H5定位
	                public void onGeolocationPermissionsShowPrompt(String origin,GeolocationPermissions.Callback callback) {
	                	callback.invoke(origin, true, false);
	                	super.onGeolocationPermissionsShowPrompt(origin, callback);
	                }
	        	});
	        	
	            //设置打开的页面地址
	            webview.loadUrl("http://www.amap.com/");
	            webview.setWebViewClient(new WebViewClient());
	        }
	        catch(Exception ex)
	        {
	            ex.printStackTrace();
	        }
//		try{
//			URL url=new URL("https://www.amap.com/");
//			HttpURLConnection con=(HttpURLConnection) url.openConnection();
//			con.setRequestMethod("GET"); //使用get请求 
//			InputStream is = con.getInputStream();   //获取输入流，此时才真正建立链接  
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
	}
	@Override
	public void onClick(View v3) {
		// TODO Auto-generated method stub
		switch (v3.getId()){
		case R.id.btn_data:
			// 给bnt1添加点击响应事件
			Intent intent1 =new Intent(Main3Activity.this,MainActivity.class);
			//启动
			startActivity(intent1);
			break;
		case R.id.btn_find:
			// 给bnt1添加点击响应事件
			Intent intent2 =new Intent(Main3Activity.this,Main2Activity.class);
			//启动
			startActivity(intent2);
			break;
		case R.id.btn_message:
			// 给bnt1添加点击响应事件
			Intent intent3 =new Intent(Main3Activity.this,MessageActivity.class);
			//启动
			startActivity(intent3);
			break;
		}
	}
//	@Override
//	  protected void onDestroy() {
//	    super.onDestroy();
//	    //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//	    mMapView.onDestroy();
//	  }
//	 @Override
//	 protected void onResume() {
//	    super.onResume();
//	    //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//	    mMapView.onResume();
//	    }
//	 @Override
//	 protected void onPause() {
//	    super.onPause();
//	    //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//	    mMapView.onPause();
//	    }
//	 @Override
//	 protected void onSaveInstanceState(Bundle outState) {
//	    super.onSaveInstanceState(outState);
//	    //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//	    mMapView.onSaveInstanceState(outState);
//	  } 
}
