package com.example.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract.Helpers;

public class MyServer {
	// 服务器连接
	public static ServerSocket cServerSocket;
	// 连接
	public static Socket cSocket;
	// 端口
	public static final int PORT = 8888;
    public static void main(String[] args){
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Connection con = null;    //用于连接数据库  
		Statement stmt=null;     //用于执行数据库相关操作SQL语句	
		try {
				cServerSocket = new ServerSocket(PORT);
				System.out.println("---智能景区服务器开始启动---");
				while (true) {
				System.out.println("正在等待客户连接...");
				// 这里处于等待状态，如果没有客户端连接，程序不会向下执行
				cSocket = cServerSocket.accept();
				dis = new DataInputStream(cSocket.getInputStream());
				dos = new DataOutputStream(cSocket.getOutputStream());
				// 读取数据
				String clientStr = dis.readUTF();
				// 写出数据
				char[] c = clientStr.toCharArray();				
				int one = -1;    //用来查找"|"
				int two = -1;    //用来查找"#"
				int three=-1;    //用来查找"/"（标示用户是请求注册/请求登录/忘记密码）
				for (int i = 0; i < c.length; i++) {
					if((c[i] == '/')){
						three = i;
					}
				}
				String flag = new String(c, three+1 , c.length -three- 1);//用来判断执行的是注册任务还是登陆任务	
				if(flag.equals("请求登录")){
					// 找到第一个标识符
					for (int i = 0; i < c.length; i++) {
						if((c[i] == '#')){
							one =i;
						}else if((c[i] == '|')) {
							two = i;
						}else if((c[i] == '/')){
							three = i;
						}
					}
					System.out.println("---客户端已成功连接---");
					// 得到客户端的IP
					System.out.println("客户端的IP=" + cSocket.getInetAddress());
					// 得到客户端的端口号
					System.out.println("客户端的端口号=" + cSocket.getPort());
					// 得到本地端口号
					System.out.println("本地服务器端口号=" + cSocket.getLocalPort());
					//得到客户端传来的未经处理的信息内容
					System.out.println("客户端登录账号|密码/操作请求:" + clientStr);
					//处理信息（分开存放）				
					String user1 = new String(c, 0, two);
					System.out.println("客户端登录账号user:" + user1);
					String password1 = new String(c, two+1, three-two-1);   //起始位置和长度来获取子字符串
					System.out.println("客户端登录密码password:" + password1);	
					String judge = new String(c, three+1, c.length - three - 1);
					System.out.println("客户端具体操作judge:" + judge);
					System.out.println();
					//链接数据库用于匹配和查找
					try{
						   System.out.println("尝试接驳”智能景区App注册信息数据库“");
						   Class.forName("org.sqlite.JDBC");
						   con = DriverManager.getConnection("jdbc:sqlite:login.db");
						   con.setAutoCommit(false);    //false：sql命令的提交由应用程序负责，程序必须调用commit或者rollback方法
						   System.out.println("Opened database successfully!!");   //与数据库成功建立连接
						   //如果是第一次创建该表则执行以下代码块
					       stmt = con.createStatement();
					       //SQL语句匹配数据库
						       System.out.println("数据匹配中");
					           ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
					           String msg1=null;
					           while ( rs.next() ) {
					        	   //取出注册时的用户账号USER和密码PASSWORD与登录时的账号user1和密码password1进行比较
					              String  userdata = rs.getString("USER");
					              String  passworddata = rs.getString("PASSWORD");
					              if((user1.equals(userdata))&&(password1.equals(passworddata))){
					            	    msg1="允许登录";
										//System.out.println("登录数据与注册数据一致，允许登录！！");
										break;
									}else{
										msg1="不允许登录";
					              		//System.out.println("登录数据与注册数据不一致，不允许登录！！");
									}
					           }
					           System.out.println(msg1);
					           dos.writeUTF(msg1);
					       rs.close();
						   stmt.close();
						   con.commit();
						   con.close();	
						   System.out.println();
						}catch ( Exception e ) {
						   System.err.println( e.getClass().getName() + ": " + e.getMessage() );
						   System.exit(0);
						}
					System.out.println("---------------------------------");
				}else if(flag.equals("请求注册")){
					for (int i = 0; i < c.length; i++) {
						if((c[i] == '#')){
							one =i;
						}else if((c[i] == '|')) {
							two = i;
						}else if((c[i] == '/')){
							three = i;
						}
					}
					System.out.println("---客户端已成功连接---");
					// 得到客户端的IP
					System.out.println("客户端的IP=" + cSocket.getInetAddress());
					// 得到客户端的端口号
					System.out.println("客户端的端口号=" + cSocket.getPort());
					// 得到本地端口号
					System.out.println("本地服务器端口号=" + cSocket.getLocalPort());
					//得到客户端传来的未经处理的信息内容
					System.out.println("客户端注册账号#密码|确认密码/操作请求:" + clientStr);
					//处理信息（分开存放）				
					String user1 = new String(c, 0, one);
					System.out.println("客户端注册账号user:" + user1);
					String password1 = new String(c, one+1, two-one-1);   //起始位置和长度来获取子字符串
					System.out.println("客户端注册密码password:" + password1);	
					String password2 = new String(c, two+1, three-two-1);   //起始位置和长度来获取子字符串
					System.out.println("客户端确认密码password:" + password2);
					String judge = new String(c, three+1, c.length -three- 1);
					System.out.println("客户端具体操作judge:" + judge);
					try{
							System.out.println("尝试接驳”智能景区App注册信息数据库“");
							Class.forName("org.sqlite.JDBC");
						    con = DriverManager.getConnection("jdbc:sqlite:login.db");
							con.setAutoCommit(false);    //false：sql命令的提交由应用程序负责，程序必须调用commit或者rollback方法
							System.out.println("Opened database successfully!!");   //与数据库成功建立连接
						    stmt = con.createStatement();
						    //用注册数据匹配数据库，如果存在该账户，不允许插入具体项
						    con.setAutoCommit(false);
						    //初始化f=0  账号重复则f=1 遍历全部数据库没有重复的账号则f！=1
						    int f=0;   //用来遍历sqlite数据库的账号和密码，如果账号重复则跳出，如果没有，则添加相应的注册账号密码
						    ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
					        while (rs.next() ) {
					        //取出注册时的用户账号USER和密码PASSWORD与登录时的账号user1和密码password1进行比较
						       String  userdata = rs.getString("user");
					        //System.out.println(userdata);
					           String  passworddata= rs.getString("password");
					        //System.out.println(passworddata);
					        //从数据库里面取出账户和密码，与正在注册的账号，密码进行比对
					           if(user1.equals(userdata)){
							      System.out.println("该用户已经注册过，无法重复注册！！");
								  dos.writeUTF("该账户已经注册！！");
								  f=1;
								  break;
							     }
					         }
					           if(f!=1){
					       //更新数据库，在数据库中添加相应的注册账号和密码
					           String sql ="insert into company(id,user,password) values(null,'" + user1 + "','" + password2 + "')"; 
					           stmt.executeUpdate(sql);
									 System.out.println("注册成功！！");
									 dos.writeUTF("注册成功！！");
					           }
					           rs.close();
					           stmt.close();
					           con.commit();
							   con.close();	
							   System.out.println();
						    }catch ( Exception e ) {
							   System.err.println( e.getClass().getName() + ": " + e.getMessage() );
							   System.exit(0);
							}
					     System.out.println("---------------------------------");
			         }
				else if(flag.equals("找回密码")){
					// 找到第一个标识符
					for (int i = 0; i < c.length; i++) {
						if((c[i] == '#')){
							one =i;
						}else if((c[i] == '|')) {
							two = i;
						}else if((c[i] == '/')){
							three = i;
						}
					}
					System.out.println("---客户端已成功连接---");
					// 得到客户端的IP
					System.out.println("客户端的IP=" + cSocket.getInetAddress());
					// 得到客户端的端口号
					System.out.println("客户端的端口号=" + cSocket.getPort());
					// 得到本地端口号
					System.out.println("本地服务器端口号=" + cSocket.getLocalPort());
					//得到客户端传来的未经处理的信息内容
					System.out.println("客户端密码找回|验证码/操作请求:" + clientStr);
					//处理信息（分开存放）				
					String user1 = new String(c, 0, two);
					System.out.println("客户端账号user:" + user1);
					String ma = new String(c, two+1, three-two-1);   //起始位置和长度来获取子字符串
					System.out.println("客户端验证码captcha:" + ma);	
					String judge = new String(c, three+1, c.length - three - 1);
					System.out.println("客户端具体操作judge:" + judge);
						try{
							System.out.println("尝试接驳”智能景区App注册信息数据库“");
							Class.forName("org.sqlite.JDBC");
						    con = DriverManager.getConnection("jdbc:sqlite:login.db");
							con.setAutoCommit(false);    //false：sql命令的提交由应用程序负责，程序必须调用commit或者rollback方法
							System.out.println("Opened database successfully!!");   //与数据库成功建立连接
						    stmt = con.createStatement();
						    //用注册数据匹配数据库，如果存在该账户，不允许插入具体项
						    con.setAutoCommit(false);
						    int f=0; //初始化f=0  账号重复则f=1 遍历全部数据库没有重复的账号则f！=1
						    ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
					        while (rs.next() ) {
					        //取出注册时的用户账号USER和密码PASSWORD与登录时的账号user1和密码password1进行比较
						         String  userdata = rs.getString("user");
					        //System.out.println(userdata);
					        //从数据库里面取出账户和密码，与正在注册的账号，密码进行比对
					             if((user1.equals(userdata))&&(ma.equals("12345"))){
							       System.out.println("成功找到该用户账号");
								   dos.writeUTF("成功找回密码！！");
								   f=1;
								   break;
							     }
					           }
					        if(f==0){
					        	System.out.println("没有找到该用户账号");
					        	dos.writeUTF("错误");
					        }
					           rs.close();
					           stmt.close();
					           con.commit();
							   con.close();	
							   System.out.println();
						    }catch ( Exception e ) {
							   System.err.println( e.getClass().getName() + ": " + e.getMessage() );
							   System.exit(0);
							}
						 System.out.println("---------------------------------");
			   }else if(flag.equals("重设密码")){
					// 找到第一个标识符
					for (int i = 0; i < c.length; i++) {
						if((c[i] == '#')){
							one =i;
						}else if((c[i] == '|')) {
							two = i;
						}else if((c[i] == '/')){
							three = i;
						}
					}
					System.out.println("---客户端已成功连接---");
					// 得到客户端的IP
					System.out.println("客户端的IP=" + cSocket.getInetAddress());
					// 得到客户端的端口号
					System.out.println("客户端的端口号=" + cSocket.getPort());
					// 得到本地端口号
					System.out.println("本地服务器端口号=" + cSocket.getLocalPort());
					//得到客户端传来的未经处理的信息内容
					System.out.println("客户端密码找回|验证码/操作请求:" + clientStr);
					//处理信息（分开存放）				
					String user1 = new String(c, 0, two);
					System.out.println("客户端账号user:" + user1);
					String newpassword = new String(c, two+1, three-two-1);   //起始位置和长度来获取子字符串
					System.out.println("客户端重设密码newpassword:" + newpassword);	
					String judge = new String(c, three+1, c.length - three - 1);
					System.out.println("客户端具体操作judge:" + judge);
						try{
							System.out.println("尝试接驳”智能景区App注册信息数据库“");
							Class.forName("org.sqlite.JDBC");
						    con = DriverManager.getConnection("jdbc:sqlite:login.db");
							con.setAutoCommit(false);    //false：sql命令的提交由应用程序负责，程序必须调用commit或者rollback方法
							System.out.println("Opened database successfully!!");   //与数据库成功建立连接
						    stmt = con.createStatement();
						    //用注册数据匹配数据库，如果存在该账户，不允许插入具体项
						    con.setAutoCommit(false);
						    String sql ="update company set password="+newpassword+" where user="+user1; 
						    //System.out.println(sql);
					        stmt.executeUpdate(sql);
					        System.out.println("重设密码成功！！");
						    dos.writeUTF("设置成功");
					        stmt.close();
					        con.commit();
							con.close();	
							System.out.println();
						}catch ( Exception e ) {
							System.err.println( e.getClass().getName() + ": " + e.getMessage() );
							System.exit(0);
					 }
						 System.out.println("---------------------------------");
				}
			}
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
}
