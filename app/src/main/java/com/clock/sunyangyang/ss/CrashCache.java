package com.clock.sunyangyang.ss;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunyangyang on 16/8/19.
 */
public class CrashCache implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "Activity";
    private Context mContext;
    private final String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
    private static CrashCache mInstance = new CrashCache();

    public static CrashCache getInstance() {
        if (mInstance == null) {
            synchronized (CrashCache.class) {
                if (mInstance == null) {
                    mInstance = new CrashCache();
                }
            }
        }
        return mInstance;
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveInfoToSD(mContext, ex);
    }

    /**
     * 获取系统未捕捉的错误信息
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();

        Log.e(TAG, mStringWriter.toString());
        return mStringWriter.toString();
    }

    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     * @param context
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context){
        HashMap<String, String> map = new HashMap<String, String>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);

        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" +  Build.PRODUCT);

        return map;
    }

    /**
     * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
     * @param context
     * @param ex
     * @return
     */
    private String saveInfoToSD(Context context, Throwable ex){
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        for (Map.Entry<String, String> entry : obtainSimpleInfo(context).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        sb.append(obtainExceptionInfo(ex));

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File dir = new File(SDCARD_ROOT + File.separator + "crash" + File.separator);
            if(! dir.exists()){
                dir.mkdir();
            }
            try{
                fileName = dir.toString() + File.separator + paserTime(System.currentTimeMillis()) + ".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        return fileName;
    }


    /**
     * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式
     * @param milliseconds
     * @return
     */
    private String paserTime(long milliseconds) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String times = format.format(new Date(milliseconds));

        return times;
    }

    /**
     * 为我们的应用程序设置自定义Crash处理
     */
    public void setCustomCrash(Context context){
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
