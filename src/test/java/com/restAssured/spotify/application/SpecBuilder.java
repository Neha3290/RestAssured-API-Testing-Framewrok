package com.restAssured.spotify.application;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.restAssured.spotify.application.TokenManager.getToken;
import static io.restassured.RestAssured.config;

public class SpecBuilder {

    //public static String access_token="BQCUglAskQuj-ofyBNijkkncAbSo9Of7dAIvNeh_-3lBsd-_zR4ZnyV-o7o_TbglclpjfJ9xaLWfZy-NL1N5XfVsdyra2f4du55ChzZbH5575oRAfYYO-UHipqQI7Qi2_kgwxM_nxeD_Qlty0EiXZiyfdsFfi0Tapb9I_C_uLThQh_kBITzfDtTeUCxApdXbMJa5O2FpujaukdIb8bK8PqomdNgBZCPYVgt1bFwljkZxUa_LIyX8wHrUh5_MpOvHspJDL104NdUkzYXg";


    public static RequestSpecification getRequestSpec(){

        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().getBasePath()).
                setBasePath("/v1").
                addHeader("Authorization", "Bearer "+getToken()).
                setContentType(ContentType.JSON).
                setConfig(config.logConfig(LogConfig.logConfig().blacklistHeader("Authorization"))).
                log(LogDetail.ALL).
                build();

    }

    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).build();

    }


}
