package com.example.user.projects_team;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public String loginMethod;
    public boolean logined;

    //네이버 연동 시 주석 해제
    /**private OAuthLogin mOAuthLoginModule;

    private static String OAUTH_CLIENT_ID = "y_0iOzmLlkQrh49JbQOQ";
    private static String OAUTH_CLIENT_SECRET = "YbXo3Ff5iG";
    private static String OAUTH_CLIENT_NAME = "DongsanLife";

    private OAuthLoginButton mOAuthLoginButton;*/
    private Context context;


    //네이버 연동 시 주석 해제
    /**private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if(success) {
                //로그인 성공 후 작업
                logined = true;
                loginMethod = "naver";

                String accessToken = mOAuthLoginModule.getAccessToken(context);
                String refreshToken = mOAuthLoginModule.getRefreshToken(context);
                long expiresAt = mOAuthLoginModule.getExpiresAt(context);
                String tokenType = mOAuthLoginModule.getTokenType(context);

                Toast.makeText(LoginActivity.this, accessToken, Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                 mOAuthAT.setText(accessToken);
                 mOauthRT.setText(refreshToken);
                 mOauthExpires.setText(String.valueOf(expiresAt));
                 mOauthTokenType.setText(tokenType);
                 mOAuthState.setText(mOAuthLoginModule.getState(context).toString());
            }

            else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(context).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(context);
                Toast.makeText(context, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                Log.e("NaverLogin", "errorCode:" + errorCode + ", errorDesc:" + errorDesc);
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        /**mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                LoginActivity.this
                , OAUTH_CLIENT_ID
                , OAUTH_CLIENT_SECRET
                , OAUTH_CLIENT_NAME
        );

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);*/
    }


}
