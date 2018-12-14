package com.wjn.qrcode.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.wjn.qrcode.R;
import com.wjn.qrcode.utils.FileHelper;
import com.wjn.qrcode.utils.QRCodeUtil;

import java.io.File;

public class CreateQRCodeActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    private String QrCodeUrl="https://blog.csdn.net/weixin_37730482";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createqrcode);

        imageView=findViewById(R.id.activity_createqrcode_imageview);

        //生成带有Logo的二维码
        bitmap=BitmapFactory.decodeResource(getResources(), R.mipmap.aaa);
        if(null!=bitmap){
            if(FileHelper.isSdCardExist()){
                createQRImage(QrCodeUrl,bitmap);
            }
        }
    }

    /**
     * 生成二维码
     * */

    private void createQRImage(final String url,final Bitmap logobitmap){
        final String filePath = getFileRoot(CreateQRCodeActivity.this) + File.separator + "qr_" + System.currentTimeMillis() + ".jpg";
        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = QRCodeUtil.createQRImage(url, 800, 800, logobitmap, filePath);
                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 保存二维码文件存储根目录
     * */

    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=bitmap&&!bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

}
