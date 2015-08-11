package com.totour.qieyoumerchanthd.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.totour.qieyoumerchanthd.R;

/**
 * Created by zhoufeng'an on 2015/8/11.
 */
public class AlertDialogUtil {
    public static void showTourPhoneDialog(final Context context, final String phone){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(phone).setPositiveButton(context.getString(R.string.positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        }).setNegativeButton(context.getString(R.string.navitive),null).show();
    }
}
