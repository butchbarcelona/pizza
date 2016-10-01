package com.proj.pizza;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.permissioneverywhere.PermissionEverywhere;
import com.permissioneverywhere.PermissionResponse;
import com.permissioneverywhere.PermissionResultCallback;

public class PermissionService extends Service {
    public PermissionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        PermissionEverywhere.getPermission(this,
            new String[]{Manifest.permission.RECEIVE_SMS
                    , Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.INTERNET
                    , Manifest.permission.SEND_SMS},
            1,
            "Tracker App",
            "This app needs a write permission",
            R.mipmap.ic_launcher)
            .enqueue(new PermissionResultCallback() {
                @Override
                public void onComplete(PermissionResponse permissionResponse) {
                    Toast.makeText(PermissionService.this, "is Granted " + permissionResponse.isGranted(), Toast.LENGTH_SHORT).show();
                }
            });

        return super.onStartCommand(intent, flags, startId);
    }
}
