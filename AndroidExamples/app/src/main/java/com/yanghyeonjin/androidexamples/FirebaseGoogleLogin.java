package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseGoogleLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SignInButton btnFirebaseGoogleLogin;
    private FirebaseAuth auth;
    private GoogleApiClient googleApiClient;
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_google_login_example);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance(); // 파이어베이스 인증 객체 초기화

        btnFirebaseGoogleLogin = findViewById(R.id.btn_firebase_google_login);
        btnFirebaseGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 구글 로그인 인증을 요청했을 때 결과값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // 인증 결과가 성공적이면..

                // account 라는 데이터는 구글 로그인 정보를 담고 있다.
                // 닉네임, 프로필사진 URL, 이메일주소 등...
                GoogleSignInAccount account = result.getSignInAccount();
                resultLogin(account); // 로그인 결과 값 출력 수행하라는 메소드
            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 로그인에 실제로 성공 했는지
                        if (task.isSuccessful()) {
                            // 로그인이 성공했으면..
                            Toast.makeText(FirebaseGoogleLogin.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), FirebaseGoogleLoginSuccess.class);

                            intent.putExtra("displayName", account.getDisplayName());

                            // account.getPhotoUrl 은 uri 객체라서 string 형태로 바꿔주어야 함.
                            intent.putExtra("photoURL", String.valueOf(account.getPhotoUrl()));



                            startActivity(intent);
                        } else {
                            // 로그인이 실패했으면 ...
                            Toast.makeText(FirebaseGoogleLogin.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
