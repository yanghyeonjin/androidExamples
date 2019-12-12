package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yanghyeonjin.androidexamples.service.GpsTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CurrentLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final String LOG_TAG = "CurrentLocation";

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private TextView tvCurrentLocation, tvLatitude, tvLongitude;
    private Button btnGetCurrentLocation;

    private Context currentLocationContext;
    private Activity currentLocationActivity;

    private SupportMapFragment supportMapFragment;

    private GoogleMap currentLocationMap;
    private Marker currentMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        currentLocationContext = CurrentLocationActivity.this;
        currentLocationActivity = CurrentLocationActivity.this;

        /* 구글맵 */
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_current_location);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }


        /* 아이디 연결 */
        tvCurrentLocation = findViewById(R.id.tv_current_location);
        tvLatitude = findViewById(R.id.tv_latitude);
        tvLongitude = findViewById(R.id.tv_longitude);
        btnGetCurrentLocation = findViewById(R.id.btn_get_current_location);

        if (!checkLocationServicesStatus()) {
            /* 위치 서비스가 켜져있지 않다면 */
            showDialogForLocationServiceSetting();
        } else {
            /* 위치 서비스 켜져 있으면 퍼미션 확인 */
            checkRunTimePermission();
        }



        btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(currentLocationContext);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                LatLng curLatLng = new LatLng(latitude, longitude);

                String strLatitude = "위도: " + latitude;
                String strLongitude = "경도: " + longitude;
                Address addressInfo = getCurrentAddress(latitude, longitude);

                if (addressInfo != null) {
                    String detailAddress = addressInfo.getAddressLine(0) + "\n";
                    String markerTitle = addressInfo.getAdminArea() + " " + addressInfo.getSubLocality() + " " + addressInfo.getThoroughfare();

                    tvLatitude.setText(strLatitude);
                    tvLongitude.setText(strLongitude);
                    tvCurrentLocation.setText(detailAddress);

                    setCurrentLocationMarker(curLatLng, markerTitle, detailAddress);
                } else {
                    /* addressInfo is null */
                    tvLatitude.setText("현재 위도는?");
                    tvLongitude.setText("현재 경도는?");
                    tvCurrentLocation.setText("현재 위치는?");
                }

            }
        });

    }

    /* ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드 */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            /* 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면 */

            boolean checkResult = true;

            /* 모든 퍼미션을 허용했는지 체크합니다. */
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false;
                    break;
                }
            }


            if (checkResult) {
                /* 위치 값을 가져올 수 있음*/
            } else {
                /* 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.*/

                if (ActivityCompat.shouldShowRequestPermissionRationale(currentLocationActivity, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(currentLocationActivity, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(currentLocationContext, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(currentLocationContext, "퍼미션이 거부되었습니다. 휴대폰 설정(앱 정보)에서 퍼미션을 허용해야 합니다.", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    /* 런타임 퍼미션 처리 */
    private void checkRunTimePermission() {

        /* 위치 퍼미션을 가지고 있는지 체크한다 */
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(currentLocationContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(currentLocationContext, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            /* 이미 퍼미션을 가지고 있다면 (안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 것으로 인식합니다. */

            /* 위치 값을 가져올 수 있음 */
        } else {
            /* 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. */

            /* 사용자가 퍼미션 거부를 한 적이 있는 경우에는 */
            if (ActivityCompat.shouldShowRequestPermissionRationale(currentLocationActivity, REQUIRED_PERMISSIONS[0])) {

                /* 요청을 진행하기 전에 사용자에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다. */
                Toast.makeText(currentLocationContext, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();

                /* 그 다음, 사용자에게 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionsResult에서 수신됩니다. */
                ActivityCompat.requestPermissions(currentLocationActivity, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);

            } else {
                /* 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다. */

                /* 요청 결과는 onRequestPermissionsResult에서 수신됩니다. */
                ActivityCompat.requestPermissions(currentLocationActivity, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    /* 위도, 경도로 현재 주소 가져오기 */
    public Address getCurrentAddress(double latitude, double longitude) {

        /* 지오코더... GPS를 주소로 변환*/
        Geocoder geocoder = new Geocoder(currentLocationContext, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            /* 네트워크 문제 */
            Toast.makeText(currentLocationContext, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return null;
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(currentLocationContext, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return null;
        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(currentLocationContext, "주소 미발견", Toast.LENGTH_LONG).show();
            return null;
        }

        Address address = addresses.get(0);
        Log.e(LOG_TAG, "getCurrentAddress --- adminArea: " + address.getAdminArea() + ", subLocality: " + address.getSubLocality() + ", thoroughfare: " + address.getThoroughfare() + ", subThoroughfare: " + address.getThoroughfare());
        return address;
    }


    /* GPS 활성화를 위한 메소드들 */
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(currentLocationContext);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 서비스를 활성화 해주세요.");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_ENABLE_REQUEST_CODE) {/* 사용자가 GPS 활성 시켰는지 검사 */
            if (checkLocationServicesStatus()) {
                Log.d(LOG_TAG, "onActivityResult : GPS 활성화 되어있음");
                checkRunTimePermission();
            }
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } else {
            Log.d(LOG_TAG, "checkLocationServicesStatus : locationManager is null");
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        currentLocationMap = googleMap;

        /* 지도의 초기위치를 서울로 설정*/
        setDefaultLocation();
    }

    public void setDefaultLocation() {
        LatLng defaultLocation = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";

        if (currentMarker != null) {
            currentMarker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(defaultLocation);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        currentMarker = currentLocationMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(defaultLocation, 15);
        currentLocationMap.moveCamera(cameraUpdate);
    }

    public void setCurrentLocationMarker(LatLng latLng, String markerTitle, String detailAddress) {

        if (currentMarker != null) {
            currentMarker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(detailAddress);

        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = currentLocationMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        currentLocationMap.moveCamera(cameraUpdate);
    }
}
