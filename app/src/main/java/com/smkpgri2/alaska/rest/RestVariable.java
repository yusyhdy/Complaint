package com.smkpgri2.alaska.rest;

/**
 * Created by smkpgri2 on 13/05/16.
 */
public class RestVariable {
    public static final String SERVER_URL = "http://192.168.2.100:8080/";
    public static final String SERVER_URL_OAUTH = "http://192.168.2.100:8080";


    public static final String APP_ID = "419c6697-14b7-4853-880e-b68e3731e316";
    public static final String SECRET = "s3cr3t";

    public static final int COMPLAINTS_GET_TASK = 1;
    public static final int COMPLAINTS_POST_TASK = 2;
    public static final int COMPLAINTS_DELETE_TASK = 3;
    public static final int COMPLAINTS_PUT_TASK = 4;

    public static final String PGA_REQUEST_TOKEN = "/oauth/token";
    public static final String PGA_CURRENT_ME = "/api/users/me";
    public static final String PGA_CURRENT_ROLE = "/api/users/me/roles";
}
