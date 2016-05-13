package org.meruvian.midas.social.task.mervid;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.meruvian.midas.core.service.TaskService;
import org.meruvian.midas.social.R;
import org.meruvian.midas.social.SocialVariable;

/**
 * Created by ludviantoovandi on 14/01/15.
 */
public class RequestAccessMervID extends AsyncTask<Void, Void, String> {
    private Context context;
    private TaskService service;

    public RequestAccessMervID(Context context, TaskService service) {
        this.context = context;
        this.service = service;
    }

    @Override
    protected void onPreExecute() {
        service.onExecute(SocialVariable.MERVID_REQUEST_ACCESS);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            OAuthClientRequest request = OAuthClientRequest.authorizationLocation(SocialVariable.ALASKA_AUTH_URL)
                    .setClientId(SocialVariable.ALASKA_APP_ID)
                    .setRedirectURI(SocialVariable.ALASKA_CALLBACK)
                    .setResponseType(ResponseType.CODE.toString())
                    .setScope("read write")
                    .buildQueryMessage();

            return request.getLocationUri();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String string) {
        if (string != null) {
            service.onSuccess(SocialVariable.MERVID_REQUEST_ACCESS, string);
        } else {
            service.onError(SocialVariable.MERVID_REQUEST_ACCESS, context.getString(R.string.failed_recieve));
        }
    }
}
