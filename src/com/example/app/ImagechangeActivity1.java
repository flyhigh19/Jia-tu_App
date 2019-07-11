package com.example.app;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
  
import android.net.Uri;  
import android.os.Bundle;  
import android.os.Environment;  
import android.provider.MediaStore;  
import android.app.Activity;  
import android.content.Intent;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.ImageView;  
import android.widget.Toast;  
  
public class ImagechangeActivity1 extends Activity implements OnClickListener {  
    private ImageView ivHead;//头像显示  
    private Button btnTakephoto;//拍照  
    private Button btnPhotos;//相册  
    private Button btnmyimage;//我的图片库
    private Button btnreturn;//我的图片库
    private Bitmap head;//头像Bitmap  
    private static String path = "/sdcard/DemoHead/";//sd路径  
    //(/sdcard/  目录怎么感觉跟Environment.getExternalStorageDirectory()得到的目录是一个效果？)  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.imagechange1);  
        initView();  
    }  
  
    private void initView() {  
        //初始化控件  
        btnPhotos = (Button) findViewById(R.id.btn_photos);  
        btnTakephoto = (Button) findViewById(R.id.btn_takephoto);  
        btnmyimage = (Button) findViewById(R.id.btn_myimage); 
        btnreturn = (Button) findViewById(R.id.btn_return);
        btnPhotos.setOnClickListener(this);  
        btnTakephoto.setOnClickListener(this); 
        btnmyimage.setOnClickListener(this); 
        btnreturn.setOnClickListener(this); 
        ivHead = (ImageView) findViewById(R.id.iv_head);  
          
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap  
        if (bt != null) {  
            //如果本地有头像图片的话  
            ivHead.setImageBitmap(bt);  
        } else {  
            //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略  
              
        }  
    }  
  
    @Override  
    public void onClick(View v) {  
        switch (v.getId()) {  
            case R.id.btn_photos://从相册里面取照片  
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI  
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI  
//                System.out.println("MediaStore.Images.Media.EXTERNAL_CONTENT_URI  ------------>   "  
//                        + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//   content://media/external/images/media  
                startActivityForResult(intent1, 1);  
                break;  
            case R.id.btn_takephoto://调用相机拍照  
                //最好用try/catch包裹一下，防止因为用户未给应用程序开启相机权限，而使程序崩溃  
                try {  
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//开启相机应用程序获取并返回图片（capture：俘获）  
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),  
                            "head.jpg")));//指明存储图片或视频的地址URI  
                    startActivityForResult(intent2, 2);//采用ForResult打开  
                } catch (Exception e) {  
                    Toast.makeText(ImagechangeActivity1.this, "相机无法启动，请先开启相机权限", Toast.LENGTH_LONG).show();  
                }  
                break; 
            case R.id.btn_myimage:
    			// 给bnt1添加点击响应事件
    			Intent intent4 =new Intent(ImagechangeActivity1.this,ImagechangeActivity.class);
    			//启动
    			startActivity(intent4);
    			break;
            case R.id.btn_return:
    			// 给bnt1添加点击响应事件
    			Intent intent5 =new Intent(ImagechangeActivity1.this,MessageActivity.class);
    			//启动
    			startActivity(intent5);
    			break;
            default:  
                break;  
        }  
    }  
  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        switch (requestCode) {  
            //从相册里面取相片的返回结果  
            case 1:  
                if (resultCode == RESULT_OK) {  
                    cropPhoto(data.getData());//裁剪图片  
                }  
  
                break;  
            //相机拍照后的返回结果  
            case 2:  
                if (resultCode == RESULT_OK) {  
                    File temp = new File(Environment.getExternalStorageDirectory()  
                            + "/head.jpg");  
                    cropPhoto(Uri.fromFile(temp));//裁剪图片  
                }  
  
                break;  
            //调用系统裁剪图片后  
            case 3:  
                if (data != null) {  
                    Bundle extras = data.getExtras();  
                    head = extras.getParcelable("data");  
                    if (head != null) {  
                        /**  
                         * 上传服务器代码  
                         */  
                        setPicToView(head);//保存在SD卡中  
                        ivHead.setImageBitmap(head);//用ImageView显示出来  
                    }  
                }  
                break;  
            default:  
                break;  
  
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }  
  
    ;  
  
    /** 
     * 调用系统的裁剪 
     * 
     * @param uri 
     */  
    public void cropPhoto(Uri uri) {  
        Intent intent = new Intent("com.android.camera.action.CROP");  
        //找到指定URI对应的资源图片  
        intent.setDataAndType(uri, "image/*");  
        intent.putExtra("crop", "true");  
        // aspectX aspectY 是宽高的比例  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        // outputX outputY 是裁剪图片宽高  
        intent.putExtra("outputX", 150);  
        intent.putExtra("outputY", 150);  
        intent.putExtra("return-data", true);  
        //进入系统裁剪图片的界面  
        startActivityForResult(intent, 3);  
    }  
  
    private void setPicToView(Bitmap mBitmap) {  
        String sdStatus = Environment.getExternalStorageState();  
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd卡是否可用  
            return;  
        }  
        FileOutputStream b = null;  
        File file = new File(path);  
        file.mkdirs();// 创建以此File对象为名（path）的文件夹  
        String fileName = path + "head.jpg";//图片名字  
        try {  
            b = new FileOutputStream(fileName);  
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件（compress：压缩）  
  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                //关闭流  
                b.flush();  
                b.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
  
        }  
    }  
}  