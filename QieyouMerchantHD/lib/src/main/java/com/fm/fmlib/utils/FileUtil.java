package com.fm.fmlib.utils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.fm.fmlib.TourApplication;

import java.io.File;

/**
 * Created by zhoufeng'an on 2015/8/18.
 */
public class FileUtil {
    public static File getFileFromUri(Uri uri) {
        if(null ==uri)
        {
            return null;
        }
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = TourApplication.instance().getContentResolver().query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        return new File(img_path);
    }
}
