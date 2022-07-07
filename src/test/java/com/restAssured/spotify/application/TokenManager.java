package com.restAssured.spotify.application;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TokenManager {
    private static String accessToken;
    private static Instant expiryTime;

    public static String getToken() {

        try {
            if (accessToken == null || Instant.now().isAfter(expiryTime)) {
                System.out.println("Renewing The Token.......");
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryDurationInSec = response.path("expires_in");
                expiryTime = Instant.now().plusSeconds(expiryDurationInSec - 300);
            } else {
                System.out.println("Token Is Good To Use.............");
            }

        } catch (Exception e) {
            throw new RuntimeException("ABORT!!! Failed To Get Token.......");
        }
        return accessToken;

    }


    private static Response renewToken() {

        String refreshToken = "AQDsWazQUSkdXnEYwdLSpGO7ONAIFrV31WXa0gRc2GladWkMzV-sb5h4sGJjcUS-lxfXS4_AmvfmWHkcNbdXQ19VT-fmgrGEzgfFvNHGQ5pQ6hHXyx9J3gkt1oze-E-hmFU";

        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("grant_type", "refresh_token");
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParams.put("refresh_token", refreshToken);

        Response response = given().
                baseUri(ConfigLoader.getInstance().getAccountBaseURI()).
                contentType(ContentType.URLENC).
                formParams(formParams).
                when().
                post("/api/token").
                then().extract().response();

        return response;
    }
}

