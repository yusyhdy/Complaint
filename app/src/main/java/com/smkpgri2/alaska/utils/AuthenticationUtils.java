package com.smkpgri2.alaska.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


import com.smkpgri2.alaska.AlaskaApplication;
import com.smkpgri2.alaska.entity.Authentication;
/**
 * Created by smkpgri2 on 13/05/16.
 */
public class AuthenticationUtils {
    private static final String AUTHENTICATION = "AUTHENTICATION";

    public static void registerAuthentication(Authentication authentication) {
        ObjectMapper mapper = AlaskaApplication.getInstance().getJsonMapper();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AlaskaApplication.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        try {
            editor.putString(AUTHENTICATION, mapper.writeValueAsString(authentication));
        } catch (JsonProcessingException e) {
            Log.e(AuthenticationUtils.class.getSimpleName(), e.getMessage(), e);
        }
        editor.apply();
    }

    public static Authentication getCurrentAuthentication() {
        AlaskaApplication instance = AlaskaApplication.getInstance();
        ObjectMapper mapper = instance.getJsonMapper();
//        ObjectMapper mapper = QrscanApplication.getInstance().getJsonMapper();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AlaskaApplication.getInstance());
        String jsonAuth = preferences.getString(AUTHENTICATION, "");

        if (!jsonAuth.equals("")) {
            try {
                return mapper.readValue(jsonAuth, Authentication.class);
            } catch (IOException e) {
                Log.e(AuthenticationUtils.class.getSimpleName(), e.getMessage(), e);
            }
        }

        return null;
    }

    public static void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AlaskaApplication.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(AUTHENTICATION);
        editor.apply();
    }
}
