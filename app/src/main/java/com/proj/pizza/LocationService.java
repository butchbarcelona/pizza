package com.proj.pizza;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationService extends Service {
    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null) {
            double latitude = intent.getDoubleExtra("latitude", 0);
            double longitude = intent.getDoubleExtra("longitude", 0);
            String number = intent.getStringExtra("msgFrom");
            String address = getCompleteAddressString(latitude, longitude);
            Log.d("pizza", "complete address:" + address);
            String cityNumber = getCity(latitude, longitude);
            sendSMS(number, "["+cityNumber+"]"+address);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public String getCity(Double lat, Double lon){
    /*    double lat = //14.4321175f;
                //14.481467f;
                //14.642676; //antipolo
                //14.471007; //pque mendoza
                //14.070423; //batangas
                //locs[0].getLatitude();
        double lon =
                //120.9913723f;
                //121.008370f;
                //121.228273;//antipolo
                //121.014710; //pque mendoza
                //120.770954; //batangas
                locs[0].getLongitude();*/

        String cityName = "";
        String address = "";


        Geocoder gcd = new Geocoder(LocationService.this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(lat, lon, 1);

            if (addresses.size() > 0) {
                Cities currCity = Cities.MANILA;

                if(addresses.get(0).getSubAdminArea() != null && currCity.getCity(addresses.get(0).getSubAdminArea()) != Cities.NONE ){
                    cityName = addresses.get(0).getSubAdminArea();
                    //currCity = currCity.getCity(addresses.get(0).getSubAdminArea());
                    Log.d("pizza","subadminarea cityname:"+cityName);
                }else if (addresses.get(0).getSubLocality() != null && currCity.getCity(addresses.get(0).getSubLocality()) != Cities.NONE ){
                    //currCity = currCity.getCity( addresses.get(0).getSubLocality());
                    cityName = addresses.get(0).getSubLocality();
                    Log.d("pizza","sublocality cityname:"+cityName);
                }else if (addresses.get(0).getAdminArea() != null && currCity.getCity(addresses.get(0).getAdminArea()) != Cities.NONE ){
                    //currCity = currCity.getCity(addresses.get(0).getAdminArea());
                    cityName = addresses.get(0).getAdminArea();
                    Log.d("pizza","adminare cityname:"+cityName);
                }else{
                    //currCity = currCity.getCity(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                    Log.d("pizza","locality cityname:"+cityName);
                }


                Cities currCities = Cities.NONE.getCity(cityName.replace("ñ","n"));

                if(currCities == Cities.NONE)
                    currCities = Cities.MANILA;
                cityName = currCities.getStringLabel();
                address = cityName;
                return currCities.getStringNum();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Cities.NONE.getStringNum();
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                }
                strAdd = strReturnedAddress.toString();
                Log.d("pizza","My Current loction address " + strReturnedAddress.toString());
            } else {
                Log.d("pizza","My Current loction address No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("pizza","My Current loction address Canont get Address!");
        }
        return strAdd;
    }


    private void sendSMS(String phoneNumber, String message) {

        Log.d("pizza","sending text msg:"+message+" number:"+phoneNumber);
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.d("pizza","SMS sent");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.d("pizza","SMS generic failure");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.d("pizza","SMS no service");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.d("pizza","SMS null pdu");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.d("pizza","SMS radio off");
                        break;
                }
            }
        }, new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.d("pizza","SMS delivered");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.d("pizza","SMS not delivered");
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, "", message, sentPI, deliveredPI);
    }

}
