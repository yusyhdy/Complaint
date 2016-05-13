package com.smkpgri2.alaska.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.path.android.jobqueue.JobManager;

//import com.smkpgri2.alaska.R;
import com.smkpgri2.alaska.AlaskaApplication;
import com.smkpgri2.alaska.event.LoginEvent;
import com.smkpgri2.alaska.job.LoginManualJob;
import com.smkpgri2.alaska.utils.AuthenticationUtils;

import org.meruvian.midas.core.service.TaskService;
import org.meruvian.midas.social.SocialVariable;
import org.meruvian.midas.social.activity.WebViewActivity;
import org.meruvian.midas.social.task.facebook.RequestAccessFacebook;
import org.meruvian.midas.social.task.facebook.RequestTokenFacebook;

import de.greenrobot.event.EventBus;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class LoginActivity extends AddComplaintActivity implements TaskService{
 private JobManager jobManager;
    private Button submit;
    private TextView username;
    private TextView password;
    private View loginProgress;
    private ImageButton facebookButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EventBus.getDefault().register(this);
        jobManager = AlaskaApplication.getInstance().getJobManager();

        submit = (Button) findViewById(R.id.button_login);
        facebookButton = (ImageButton) findViewById(R.id.login_facebook);

        username = (TextView) findViewById(R.id.edit_username);
        password = (TextView) findViewById(R.id.edit_password);
        loginProgress = findViewById(R.id.login_progress);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManualJob loginJob = new LoginManualJob(username.getText().toString(), password.getText().toString());
                jobManager.addJobInBackground(loginJob);
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestAccessFacebook requestAccessFacebook = new RequestAccessFacebook(LoginActivity.this, LoginActivity.this);
                requestAccessFacebook.execute();
            }
        });

    }

    public void onEventMainThread(LoginEvent.LoginSuccess loginSuccess) {
        goToMainActivity();
    }

    public void onEventMainThread(LoginEvent.LoginFailed loginFailed) {
        loginProgress.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
        username.setEnabled(true);
        password.setEnabled(true);
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, ComplaintActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AuthenticationUtils.getCurrentAuthentication() != null) {
            goToMainActivity();
        }
    }


    @Override
    public void onExecute(int code) {

    }

    @Override
    public void onSuccess(int code, Object result) {
        switch (code){
            case SocialVariable.FACEBOOK_REQUEST_ACCESS:
                if (result != null) {
                    Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                    intent.putExtra("url", result.toString());
                    intent.setData(Uri.parse(result.toString()));
                    startActivityForResult(intent, SocialVariable.FACEBOOK_REQUEST_ACCESS);
                }
                break;
            case SocialVariable.FACEBOOK_REQUEST_TOKEN_TASK:
                String resultObjct = (String) result;
                if (resultObjct != null && !"".equalsIgnoreCase(resultObjct)){
                    goToMainActivity();
                }
        }
    }

    @Override
    public void onCancel(int code, String message) {

    }

    @Override
    public void onError(int code, String message) {

    }

    private String parseCode(Intent data){
        Uri uri = data.getData();
        if (uri != null && uri.toString().startsWith(SocialVariable.ALASKA_CALLBACK)){
            String code = uri.getQueryParameter("code");
            if (code != null && !"".equalsIgnoreCase(code)){
                Log.d("code ", code);
            }
        }
        return "null";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == SocialVariable.FACEBOOK_REQUEST_ACCESS){
                new RequestTokenFacebook(LoginActivity.this, LoginActivity.this).execute(parseCode(data));
            }
        }
    }
}
