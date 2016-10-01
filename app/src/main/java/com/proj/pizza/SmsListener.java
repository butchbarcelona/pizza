package com.proj.pizza;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mbarcelona on 10/1/16.
 */
public class SmsListener extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("pizza","SmsListener onReceive");

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        String[] msgsBody = msgBody.split(",");
                        openLocationService(context,Double.parseDouble(msgsBody[0]),Double.parseDouble(msgsBody[1]), msg_from);
                    }
                }catch(Exception e){
                    Log.d("pizza",e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void openLocationService(Context context, double latitude, double longitude, String msgFrom){
        Log.d("pizza","opening locationservice");
        Intent serviceIntent = new Intent(context,LocationService.class);
        serviceIntent.putExtra("latitude",latitude);
        serviceIntent.putExtra("longitude",longitude);
        serviceIntent.putExtra("msgFrom",msgFrom);
        context.startService(serviceIntent);
    }

}