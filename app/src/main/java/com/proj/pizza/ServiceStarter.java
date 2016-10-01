package com.proj.pizza;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.permissioneverywhere.PermissionEverywhere;
import com.permissioneverywhere.PermissionResponse;
import com.permissioneverywhere.PermissionResultCallback;

public class ServiceStarter extends BroadcastReceiver {
    public ServiceStarter() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("pizza","ServiceStarter onReceive");
        Intent serviceIntent = new Intent(context,PermissionService.class);
        context.startService(serviceIntent);
    }

}

