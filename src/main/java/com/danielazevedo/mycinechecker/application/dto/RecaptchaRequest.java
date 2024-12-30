package com.danielazevedo.mycinechecker.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecaptchaRequest {

    @JsonProperty("event")
    private RecaptchaEvent event;

    public RecaptchaRequest(String token, String action) {
        this.event = new RecaptchaEvent(token, action);
    }

    public static class RecaptchaEvent {
        private String token;
        private String expectedAction;

        public RecaptchaEvent(String token, String expectedAction) {
            this.token = token;
            this.expectedAction = expectedAction;
        }

        public String getToken() {
            return token;
        }

        public String getExpectedAction() {
            return expectedAction;
        }
    }
}
