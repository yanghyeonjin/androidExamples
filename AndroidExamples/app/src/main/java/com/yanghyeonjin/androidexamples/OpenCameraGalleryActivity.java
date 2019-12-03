package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class OpenCameraGalleryActivity extends AppCompatActivity {

    private Button cameraOpenButton, galleryOpenButton, cameraGalleryOpenButton, galleryOpenButton2;

    /* request code */
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;
    private static final int CAMERA_GALLERY_REQUEST_CODE = 3;

    /* 권한 체크 */
    private int cameraPermissionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera_gallery);

        /* 카메라 권한 체크 */
        cameraPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        /* 권한설정 */
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("앱 사용을 위하여 권한을 설정해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

        /* 아이디 연결 */
        cameraOpenButton = findViewById(R.id.btn_camera_open);
        galleryOpenButton = findViewById(R.id.btn_gallery_open);
        cameraGalleryOpenButton = findViewById(R.id.btn_camera_gallery_open);
        galleryOpenButton2 = findViewById(R.id.btn_gallery_open2);

        /* 카메라 오픈 */
        cameraOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCameraAvailable()){
                    if (cameraPermissionCheck == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                        Toast.makeText(getApplicationContext(), "카메라 오픈", Toast.LENGTH_SHORT).show();
                    } else if (cameraPermissionCheck == PackageManager.PERMISSION_DENIED) {
                        TedPermission.with(OpenCameraGalleryActivity.this)
                                .setPermissionListener(permissionListener)
                                .setRationaleMessage("앱 사용을 위하여 권한을 설정해주세요.")
                                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
                                .setPermissions(Manifest.permission.CAMERA)
                                .check();
                    }

                }
            }
        });

        /* 갤러리 오픈 */
        galleryOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 다중선택
                startActivityForResult(Intent.createChooser(intent, "앱을 선택하세요"), GALLERY_REQUEST_CODE);
                Toast.makeText(getApplicationContext(), "갤러리 오픈", Toast.LENGTH_SHORT).show();
            }
        });

        /* ACTION_GET_CONTENT 사용하여 갤러리 오픈 */
        galleryOpenButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 다중선택
                startActivityForResult(Intent.createChooser(intent, "앱을 선택하세요"), GALLERY_REQUEST_CODE);
                Toast.makeText(getApplicationContext(), "갤러리 오픈", Toast.LENGTH_SHORT).show();
            }
        });

        /* 카메라와 갤러리 중 선택 */
        cameraGalleryOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 다중선택
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Intent chooser = Intent.createChooser(galleryIntent, "앱을 선택하세요");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });
                startActivityForResult(chooser, CAMERA_GALLERY_REQUEST_CODE);

                Toast.makeText(getApplicationContext(), "카메라 갤러리 오픈", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* 카메라 유무 확인 */
    public boolean isCameraAvailable(){
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /* 권한 설정 리스너 */
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한 거부", Toast.LENGTH_SHORT).show();
        }
    };
}
