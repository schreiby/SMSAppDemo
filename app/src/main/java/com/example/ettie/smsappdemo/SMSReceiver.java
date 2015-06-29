package com.example.ettie.smsappdemo;

/**
 * Created by Ettie on 6/27/2015.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //stop the broadcast event
        abortBroadcast();

        //get extras from Intent
        Bundle extras = intent.getExtras();
        SmsMessage[] messages = null;
        String senderNumber = "";
        String msgBody = "";

        if (extras != null) {
            //retrieve the SMS message chunks
            Object[] pdus = (Object[])extras.get("pdus");
            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                if (i == 0) {
                    //get the sender number
                    senderNumber = messages[i].getOriginatingAddress();
                }
                //get message body
                msgBody += messages[i].getMessageBody();
            }

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(senderNumber, null, "This is an auto reply SMS.", null, null);

            Toast.makeText(context, "SMS received from: " +
                senderNumber + "\nSMS content: " +
                msgBody, Toast.LENGTH_LONG).show();
        }
    }
}
