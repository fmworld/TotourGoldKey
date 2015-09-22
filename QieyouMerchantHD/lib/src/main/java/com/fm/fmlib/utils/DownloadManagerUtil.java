package com.fm.fmlib.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.fm.fmlib.TourApplication;

import java.io.File;

/**
 * Created by zhoufeng'an on 2015/9/21.
 */
public class DownloadManagerUtil {
    public static long download(Context context, Uri uri) {
        // uri 是你的下载地址，可以使用Uri.parse("http://")包装成Uri对象
        DownloadManager.Request req = new DownloadManager.Request(uri);

        // 通过setAllowedNetworkTypes方法可以设置允许在何种网络下下载，
        // 也可以使用setAllowedOverRoaming方法，它更加灵活
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

        // 此方法表示在下载过程中通知栏会一直显示该下载，在下载完成后仍然会显示，
        // 直到用户点击该通知或者消除该通知。还有其他参数可供选择
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // 设置下载文件存放的路径，同样你可以选择以下方法存放在你想要的位置。
        // setDestinationUri
        // setDestinationInExternalPublicDir
        Log.v("updatetour", "path " + context.getExternalFilesDir("Download"));
        String apkName = uri.toString().substring(uri.toString().lastIndexOf("/")+1);
        req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, apkName);

        // 设置一些基本显示信息

        req.setTitle(apkName);
        req.setDescription("下载完后请点击打开");
        req.setMimeType("application/vnd.android.package-archive");
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // Ok go!
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = dm.enqueue(req);
        return downloadId;
    }

    /**
     * 安装apk
     */
    public void installApk(String saveFileName){
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        TourApplication.instance().startActivity(i);

    }

    /**
     * 安装apk
     */
    public static void installApk(Context context,long downloadId){
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = dm.query(query);
        if (c != null) {
            if (c.moveToFirst()) {
                String fileUri = c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setDataAndType(Uri.parse("file://" + fileUri), "application/vnd.android.package-archive");
                context.startActivity(i);
            }
            c.close();
        }
    }

    public static int[] getBytesAndStatus(DownloadManager dm, long downloadId,  int[] bytesAndStatus) {
        //COLUMN_STATUS 2 下载中  8已完成下载
//        int[] bytesAndStatus = new int[]{-2, -1, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            c = dm.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return bytesAndStatus;
    }

    public static void getBytesAndStatus(DownloadManager dm, DownloadManager.Query query,long downloadId,  int[] bytesAndStatus) {
        //COLUMN_STATUS 2 下载中  8已完成下载
//        int[] bytesAndStatus = new int[]{-2, -1, 0};
//        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            c = dm.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }


}
