package com.yanghyeonjin.androidexamples.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.yanghyeonjin.androidexamples.SMSReceiverActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SMSReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "SMSReceiver";
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

    @Override
    public void onReceive(Context context, Intent intent) {

        /* 인텐트 안에 들어 있는 SMS 메시지를 파싱한다.*/
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        if (messages != null && messages.length > 0) {
            /* SMS 발신 번호 */
            String sender = messages[0].getOriginatingAddress();

            /* SMS 메시지 내용 */
            String contents = messages[0].getMessageBody();

            /* SMS 수신 시간 */
            Date receivedDate = new Date(messages[0].getTimestampMillis());

            sendToActivity(context, sender, contents, receivedDate);
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objects = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objects.length];

        int smsCount = objects.length;
        for (int i = 0; i < smsCount; i++) {

            /* PDU 포맷으로 되어 있는 메시지를 복원한다 */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                /* API 23 이상 */
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }
        }

        return messages;
    }

    /* 메시지 내용을 보여줄 액티비티를 띄운다 */
    private void sendToActivity(Context context, String sender, String contents, Date receivedDate) {

        Intent intent = new Intent(context, SMSReceiverActivity.class);

        /* 플래그 이용 */
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.putExtra("receivedDate", format.format(receivedDate));

        context.startActivity(intent);
    }
}
