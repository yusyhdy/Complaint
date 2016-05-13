package com.smkpgri2.alaska.event;

import com.smkpgri2.alaska.entity.Authentication;

/**
 * Created by smkpgri2 on 13/05/16.
 */
public class LoginEvent {
    public static class DoLogin {}

    public static class LoginSuccess {
        private Authentication authentication;

        public LoginSuccess(Authentication authentication) {
            this.authentication = authentication;
        }

        public Authentication getAuthentication() {
            return authentication;
        }
    }

    public static class LoginFailed {
        private final int statusCode;

        public LoginFailed(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}
