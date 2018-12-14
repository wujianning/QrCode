package com.wjn.qrcode.utils;

import android.os.Environment;

/**
 * Created by wjn on 2017/11/24.
 * 文件工具类
 */

public class FileHelper {

    private static FileHelper mInstance = new FileHelper();

    /**
     * 获取对象的静态方法
     * */

    public static FileHelper getInstance() {
        return mInstance;
    }

    /**
     * 判断当前设备是否有外部存储(SD卡)
     * */

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}

