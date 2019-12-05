package com.yanghyeonjin.androidexamples;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class NaverLoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NaverLogin";

    private static String OAUTH_CLIENT_ID = "";
    private static String OAUTH_CLIENT_SECRET = "";
    private static String OAUTH_CLIENT_NAME = "";

    private static OAuthLogin mOAuthLoginModule;
    private OAuthLoginButton mOAuthLoginButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_login);

        mContext = this;

        OAUTH_CLIENT_ID = getString(R.string.naver_client_id);
        OAUTH_CLIENT_SECRET = getString(R.string.naver_client_secret);
        OAUTH_CLIENT_NAME = getString(R.string.naver_client_name);

        /* 네이버 아이디로 로그인 인스턴스를 초기화 */
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(NaverLoginActivity.this, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        /* 자동로그인 ? */
        mOAuthLoginModule.startOauthLoginActivity(NaverLoginActivity.this, mOAuthLoginHandler);

        /* 네이버 아이디로 로그인 버튼 */
        mOAuthLoginButton = findViewById(R.id.btn_naver_login);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        // mOAuthLoginButton.setBgResourceId(R.drawable.img_loginbtn_usercustom);

        mOAuthLoginButton.setOnClickListener(view -> mOAuthLoginModule.startOauthLoginActivity(NaverLoginActivity.this, mOAuthLoginHandler));
    }

    private OAuthLoginHandler mOAuthLoginHandler = new NaverLoginHandler(this);

    private static class NaverLoginHandler extends OAuthLoginHandler {

        private final WeakReference<NaverLoginActivity> mActivity;

        NaverLoginHandler(NaverLoginActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run(boolean success) {
            NaverLoginActivity activity = mActivity.get();

            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(activity);
                String refreshToken = mOAuthLoginModule.getRefreshToken(activity);
                long expiresAt = mOAuthLoginModule.getExpiresAt(activity);
                String tokenType = mOAuthLoginModule.getTokenType(activity);

                Log.e(LOG_TAG, "accessToken: " + accessToken);
                Log.e(LOG_TAG, "refreshToken: " + refreshToken);
                Log.e(LOG_TAG, "expireAt: " + expiresAt);
                Log.e(LOG_TAG, "tokenType: " + tokenType);

                /* 사용자 정보 가져오는 업무 담당 */
                ProfileTask profileTask = new ProfileTask();
                profileTask.execute(accessToken);

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(activity).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(activity);
                Toast.makeText(activity, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }

        }

        class ProfileTask extends AsyncTask<String, Void, String> {
            String result;

            @Override
            protected String doInBackground(String... strings) {
                String token = strings[0]; // 네이버 로그인 접근 토큰
                String header = "Bearer " + token; // Bearer 다음에 공백 추가

                try {
                    String apiURL = "https://openapi.naver.com/v1/nid/me";
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Authorization", header);

                    int responseCode = con.getResponseCode();
                    BufferedReader br;

                    if (responseCode == 200) {
                        /* 정상 호출 */
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    } else {
                        /* 에러 발생 */
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    result = response.toString();
                    br.close();

                    Log.e("NaverLogin", response.toString());
                } catch (Exception e) {
                    Log.e("NaverLogin", String.valueOf(e));
                }

                /* result 값은 JSON 형태로 넘어온다. */
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    /* 넘어온 result 값을 JSONObject로 변환해주고, 값을 가져오면 된다. */
                    JSONObject object = new JSONObject(result);

                    if (object.getString("resultcode").equals("00")) {
                        JSONObject jsonObject = new JSONObject(object.getString("response"));

                        String naverID = jsonObject.getString("id");
                        String naverNickname = jsonObject.getString("nickname");
                        String naverName = jsonObject.getString("name");
                        String naverEmail = jsonObject.getString("email");
                        String naverGender = jsonObject.getString("gender");
                        String naverAge = jsonObject.getString("age");
                        String naverBirthday = jsonObject.getString("birthday");
                        String naverProfileImage = jsonObject.getString("profile_image");

                        Intent intent = new Intent(mActivity.get(), NaverLoginSuccessActivity.class);
                        intent.putExtra("naverID", naverID);
                        intent.putExtra("naverNickname", naverNickname);
                        intent.putExtra("naverName", naverName);
                        intent.putExtra("naverEmail", naverEmail);
                        intent.putExtra("naverGender", naverGender);
                        intent.putExtra("naverAge", naverAge);
                        intent.putExtra("naverBirthday", naverBirthday);
                        intent.putExtra("naverProfileImage", naverProfileImage);
                        mActivity.get().startActivity(intent);
                        mActivity.get().finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
