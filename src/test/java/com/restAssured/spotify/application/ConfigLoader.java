package com.restAssured.spotify.application;

import com.restAssured.spotify.utility.PropertyUtils;

import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        properties= PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance(){
        if(configLoader==null){
            configLoader= new ConfigLoader();
        }
        return configLoader;
    }

    public String getBasePath()
    {
        String prop= properties.getProperty("BASE_PATH");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property BASE_PATH is not defined in config.property file");
    }

    public String getBaseUri()
    {
        String prop= properties.getProperty("BASE_URI");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property BASE_URI is not defined in config.property file");
    }

    public String getUserId()
    {
        String prop= properties.getProperty("USER_ID");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property USER_ID is not defined in config.property file");
    }
    public String getClientId()
    {
        String prop= properties.getProperty("CLIENT_ID");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property CLIENT_ID is not defined in config.property file");
    }

    public String getClientSecret()
    {
        String prop= properties.getProperty("CLIENT_SECRET");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property CLIENT_SECRET is not defined in config.property file");
    }

    public String getAccountBaseURI()
    {
        String prop= properties.getProperty("ACCOUNT_BASE_URI");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property ACCOUNT_BASE_URI is not defined in config.property file");
    }
    public String getPlaylistId()
    {
        String prop= properties.getProperty("PLAYLIST_ID");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property PLAYLIST_ID is not defined in config.property file");
    }
}
