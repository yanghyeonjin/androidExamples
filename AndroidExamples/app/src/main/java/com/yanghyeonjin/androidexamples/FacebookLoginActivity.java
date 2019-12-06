package com.yanghyeonjin.androidexamples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity {

    private Context fbLoginContext;

    private CallbackManager callbackManager;
    private LoginButton btnFacebookLogin;
    private FacebookLoginCallback facebookLoginCallback; // 직접 만든 클래스

    private static final String LOG_TAG = "FacebookLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);

        fbLoginContext = getApplicationContext();

        callbackManager = CallbackManager.Factory.create();
        facebookLoginCallback = new FacebookLoginCallback();

        btnFacebookLogin = findViewById(R.id.btn_login_facebook);
        btnFacebookLogin.setPermissions(Arrays.asList("public_profile", "email"));
        btnFacebookLogin.registerCallback(callbackManager, facebookLoginCallback);



        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        Log.e(LOG_TAG, String.valueOf(isLoggedIn));

        if (isLoggedIn) {
            facebookLoginCallback.requestMe(accessToken);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
}
