package com.smkpgri2.alaska.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.path.android.jobqueue.JobManager;

//import com.smkpgri2.alaska.R;
import com.smkpgri2.alaska.AlaskaApplication;
import com.smkpgri2.alaska.event.LoginEvent;
import com.smkpgri2.alaska.job.LoginManualJob;
import com.smkpgri2.alaska.utils.AuthenticationUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class LoginActivity extends AddComplaintActivity {
 private JobManager jobManager;
    private Button submit;
    private TextView username;
    private TextView password;
    private View loginProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EventBus.getDefault().register(this);
        jobManager = AlaskaApplication.getInstance().getJobManager();

        submit = (Button) findViewById(R.id.button_login);
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


}
