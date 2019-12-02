package com.yanghyeonjin.androidexamples.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 네트워크 상태 값 받아오기

        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null) {
                NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
                if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
                    // 네트워크 연결 상태이면...

                    // NetworkCheckActivity.tv_network_state.setText("네트워크 연결 완료");
                } else if (detailedState == NetworkInfo.DetailedState.DISCONNECTED) {
                    // 네트워크 연결 해제이면...

                    // NetworkCheckActivity.tv_network_state.setText("네트워크 연결 해제");
                    Toast.makeText(context, "인터넷 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
