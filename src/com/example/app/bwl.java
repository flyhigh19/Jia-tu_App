package com.example.app;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class bwl extends Activity {
	private EditText editText1, editText2, editText3, editText4, editText5;

	String path =Environment.getExternalStorageDirectory()+ "/lisa/";
	File filex = new File(path);
	File m1 = new File(path + "m1.txt");
	File m2 = new File(path + "m2.txt");
	File m3 = new File(path + "m3.txt");
	File m4 = new File(path + "m4.txt");
	File m5 = new File(path + "m5.txt");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bwl);
		if (!filex.exists()) {
			try {
				filex.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!m1.exists()) {
			try {
				m1.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);
		editText4 = (EditText) findViewById(R.id.editText4);
		editText5 = (EditText) findViewById(R.id.editText5);
		DataInputStream in1,in2,in3,in4,in5;
		try {
			in1 = new DataInputStream(new FileInputStream(path + "m1.txt"));
			editText1.setText(in1.readUTF());
			in1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in2 = new DataInputStream(new FileInputStream(path + "m2.txt"));
			editText2.setText(in2.readUTF());
			in2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in3 = new DataInputStream(new FileInputStream(path + "m3.txt"));
			editText3.setText(in3.readUTF());
			in3.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in4 = new DataInputStream(new FileInputStream(path + "m4.txt"));
			editText4.setText(in4.readUTF());
			in4.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in5 = new DataInputStream(new FileInputStream(path + "m5.txt"));
			editText5.setText(in5.readUTF());
			in5.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Button Button1 = (Button) findViewById(R.id.button1);
		Button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DataOutputStream out1,out2,out3,out4,out5;
				String s1 = editText1.getText().toString();
				String s2 = editText2.getText().toString();
				String s3 = editText3.getText().toString();
				String s4 = editText4.getText().toString();
				String s5 = editText5.getText().toString();
				try {
					if (s1.length() != 0) {
						m1.delete();
						m1.createNewFile();
					}
					out1 = new DataOutputStream(new FileOutputStream(path
							+ "m1.txt"));
					out1.writeUTF(s1);
					out1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					if (s2.length() != 0) {
						m2.delete();
						m2.createNewFile();
					}
					out2 = new DataOutputStream(new FileOutputStream(path
							+ "m2.txt"));
					out2.writeUTF(s2);
					out2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (s3.length() != 0) {
						m3.delete();
						m3.createNewFile();
					}
					out3 = new DataOutputStream(new FileOutputStream(path
							+ "m3.txt"));
					out3.writeUTF(s3);
					out3.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (s4.length() != 0) {
						m4.delete();
						m4.createNewFile();
					}
					out4 = new DataOutputStream(new FileOutputStream(path
							+ "m4.txt"));
					out4.writeUTF(s4);
					out4.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (s5.length() != 0) {
						m5.delete();
						m5.createNewFile();
					}
					out5 = new DataOutputStream(new FileOutputStream(path
							+ "m5.txt"));
					out5.writeUTF(s5);
					out5.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
