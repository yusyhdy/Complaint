package com.smkpgri2.alaska.job;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import com.smkpgri2.alaska.core.commons.Role;
import com.smkpgri2.alaska.core.commons.User;
import com.smkpgri2.alaska.core.commons.UserRole;
import com.smkpgri2.alaska.entity.Authentication;
import com.smkpgri2.alaska.entity.PageEntity;
import com.smkpgri2.alaska.event.LoginEvent;
import com.smkpgri2.alaska.rest.RestVariable;
import com.smkpgri2.alaska.utils.AuthenticationUtils;
import com.smkpgri2.alaska.utils.JsonRequestUtils;


/**
 * Created by smkpgri2 on 13/05/16.
 */
public abstract class LoginJob extends Job {
    protected LoginJob(Params params) {
        super(params);
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new LoginEvent.DoLogin());
    }

    protected void registerAuthentication(JsonRequestUtils.HttpResponseWrapper<Authentication> responseWrapper) {
        HttpResponse response = responseWrapper.getHttpResponse();

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            Authentication authentication = responseWrapper.getContent();
            AuthenticationUtils.registerAuthentication(authentication);
            User user = requestUser();

            user.setRoles(new ArrayList<Role>());
            for (UserRole userRole : requestRoles().getContent()) {
                user.getRoles().add(userRole.getRole());
            }
            authentication.setUser(user);

            AuthenticationUtils.registerAuthentication(authentication);
            Log.i(getClass().getSimpleName(), "ACCESS_TOKEN : " + authentication.getAccessToken());

            EventBus.getDefault().post(new LoginEvent.LoginSuccess(responseWrapper.getContent()));
        } else {
            EventBus.getDefault().post(new LoginEvent.LoginFailed(response.getStatusLine().getStatusCode()));
        }
    }

    protected User requestUser() {
        JsonRequestUtils requestUtils = new JsonRequestUtils(RestVariable.SERVER_URL_OAUTH + RestVariable.PGA_CURRENT_ME);
        return requestUtils.get(new TypeReference<User>() {}).getContent();
    }

    protected PageEntity<UserRole> requestRoles() {
        JsonRequestUtils requestUtils = new JsonRequestUtils(RestVariable.SERVER_URL_OAUTH + RestVariable.PGA_CURRENT_ROLE);
        return requestUtils.get(new TypeReference<PageEntity<UserRole>>() {}).getContent();
    }

    @Override
    protected void onCancel() {
        EventBus.getDefault().post(new LoginEvent.LoginFailed(0));
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        Log.e(LoginJob.class.getSimpleName(), throwable.getMessage(), throwable);

        return false;
    }
}
