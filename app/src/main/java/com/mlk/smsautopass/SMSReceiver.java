package com.mlk.smsautopass;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.mlk.smsautopass.sql.DBPhoneNumberAdapter;
import com.mlk.smsautopass.sql.DBSendLogAdapter;
import com.mlk.smsautopass.vo.PhoneNumberInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by mingeek on 2016-12-26.
 */

public class SMSReceiver extends BroadcastReceiver {
    static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    static final String logTag = "SmsReceiver";
    private ArrayList<PhoneNumberInfo> arrayPhoneNumber = new ArrayList();
    private DBPhoneNumberAdapter dbPhoneNumberAdapter;
    private DBSendLogAdapter dbSendLogAdapter;

    private ArrayList<PhoneNumberInfo> getPhoneNumberList() {
        this.arrayPhoneNumber.clear();
        Cursor localCursor = this.dbPhoneNumberAdapter.fetchAll();
        if (localCursor.getCount() != 0) {
            while (localCursor.moveToNext()) {
                int i = localCursor.getInt(0);
                String str1 = localCursor.getString(1);
                String str2 = localCursor.getString(2);
                boolean bool = Boolean.valueOf(localCursor.getString(3)).booleanValue();
                String str3 = localCursor.getString(4);
                String str4 = localCursor.getString(5);
                this.arrayPhoneNumber.add(new PhoneNumberInfo(i, str1, str2, bool, str3, str4));
            }
        }
        return this.arrayPhoneNumber;
    }

    public void onReceive(Context paramContext, Intent paramIntent) {
        this.dbPhoneNumberAdapter = new DBPhoneNumberAdapter(paramContext);
        this.dbPhoneNumberAdapter.open();
        this.dbSendLogAdapter = new DBSendLogAdapter(paramContext);
        this.dbSendLogAdapter.open();

        if (paramIntent.getAction().equals(ACTION)) {
            Bundle bundle = paramIntent.getExtras();
            if (bundle != null) {
                Object messages[] = (Object[]) bundle.get("pdus");
                SmsMessage[] arrayOfSmsMessage = new SmsMessage[messages.length];

                Iterator localIterator = getPhoneNumberList().iterator();

                for (int i = 0; i < messages.length; i++) {
                    arrayOfSmsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
                    while (localIterator.hasNext()) {
                        PhoneNumberInfo localPhoneNumberInfo = (PhoneNumberInfo) localIterator.next();
                        if ((localPhoneNumberInfo.isEnabled()) && (localPhoneNumberInfo.getRules() != null)) {
                            String[] arrayOfString = localPhoneNumberInfo.getRules().split(",");
                            int j = 0;
                            while (j < arrayOfString.length) {
                                if (arrayOfSmsMessage[i].getMessageBody().replace("\n", " ").indexOf(arrayOfString[j]) > -1) {
                                    passSMS(paramContext, arrayOfString[j], localPhoneNumberInfo, arrayOfSmsMessage[i]);
                                }
                                j += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    public void passSMS(Context paramContext, String paramString, PhoneNumberInfo paramPhoneNumberInfo, SmsMessage paramSmsMessage) {
        Object localObject = PendingIntent.getBroadcast(paramContext, 0, new Intent("SMS_SENT"), 0);
        PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent("SMS_DELIVERY"), 0);
        SmsManager.getDefault().sendTextMessage(paramPhoneNumberInfo.getPhoneNumber(), null, paramSmsMessage.getMessageBody(), (PendingIntent) localObject, localPendingIntent);
        localObject = new Date();
        localObject = new SimpleDateFormat("yyyy-MM-dd (E) HH:mm:ss", new Locale("ko", "KOREA")).format((Date) localObject);
        this.dbPhoneNumberAdapter.updateRecentTime(paramPhoneNumberInfo.getId(), (String) localObject);
        this.dbSendLogAdapter.insertSendLog(paramString, paramSmsMessage.getOriginatingAddress(), paramPhoneNumberInfo.getPhoneNumber(), paramSmsMessage.getMessageBody(), (String) localObject, "true");
        Toast.makeText(paramContext, paramPhoneNumberInfo.getPhoneNumber() + "번호로\n" + paramString + "규칙 문자\n전송 완료", 1).show();
    }
}