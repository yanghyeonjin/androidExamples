package com.yanghyeonjin.androidexamples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity {

    private Context fbLoginContext;

    private CallbackManager callbackManager, callbackManagerFirebase;
    private LoginButton btnFacebookLogin, btnFacebookLoginFirebase;
    private FacebookLoginCallback facebookLoginCallback; // 직접 만든 클래스

    private static final String LOG_TAG = "FacebookLogin";

    /* 파이어베이스 인증 객체 */
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);

        fbLoginContext = getApplicationContext();

        /* 파이어베이스 인증 객체 선언 */
        firebaseAuth = FirebaseAuth.getInstance();



        /* 페이스북 콜백 등록 (facebook developers) */
        callbackManager = CallbackManager.Factory.create();
        facebookLoginCallback = new FacebookLoginCallback();

        btnFacebookLogin = findViewById(R.id.btn_login_facebook);
        btnFacebookLogin.setPermissions(Arrays.asList("public_profile", "email"));
        btnFacebookLogin.registerCallback(callbackManager, facebookLoginCallback);



        /* 페이스북 콜백 등록 (use firebase) */
        callbackManagerFirebase = CallbackManager.Factory.create();
        btnFacebookLoginFirebase = findViewById(R.id.btn_login_facebook_use_firebase);
        btnFacebookLoginFirebase.setPermissions("email", "public_profile");
        btnFacebookLoginFirebase.registerCallback(callbackManagerFirebase, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });



        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        Log.e(LOG_TAG, String.valueOf(isLoggedIn));

        if (isLoggedIn) {
            facebookLoginCallback.requestMe(accessToken);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /* 페이스북 콜백 등록 */
        callbackManager.onActivityResult(requestCode, resultCode, data);
        callbackManagerFirebase.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    class FacebookLoginCallback implements FacebookCallback<LoginResult> {

        @Override
        public void onSuccess(LoginResult loginResult) {
            /* 로그인 성공. 사용자 정보 요청 */
            Log.e(LOG_TAG, "facebook login success");
            Log.e(LOG_TAG, "facebook token: " + loginResult.getAccessToken().getToken());
            requestMe(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {
            /* 로그인 취소 (창을 닫은 경우) */
            Log.e(LOG_TAG, "facebook login cancel");
        }

        @Override
        public void onError(FacebookException error) {
            /* 로그인 실패 */
            Log.e(LOG_TAG, "facebook login error: " + error.getMessage());
        }

        /* 사용자 정보 요청 */
        void requestMe(AccessToken token) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.e(LOG_TAG, "facebook object result: " + object.toString());
                    Log.e(LOG_TAG, "facebook graph result: " + response.toString());

                    Intent intent = new Intent(FacebookLoginActivity.this, FacebookLoginSuccessActivity.class);
                    try {
                        intent.putExtra("facebookID", object.getString("id"));
                        intent.putExtra("facebookName", object.getString("name"));
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            /* Bundle로 원하는 필드 값들을 파라미터로 넘겨준 뒤, JSON 형태로 결과를 받을 수 있다. */
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /* 로그인 성공 */
                            Log.e(LOG_TAG, "페이스북 로그인 성공 (파이어베이스)");
                        } else {
                            /* 로그인 실패 */
                            Log.e(LOG_TAG, "페이스북 로그인 실패 (파이어베이스)");
                        }
                    }
                });
    }


}
