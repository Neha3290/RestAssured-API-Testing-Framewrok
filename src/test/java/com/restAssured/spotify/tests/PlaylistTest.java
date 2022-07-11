package com.restAssured.spotify.tests;


import com.restAssured.spotify.application.ConfigLoader;
import com.restAssured.spotify.pojo.ErrorPojo;
import com.restAssured.spotify.pojo.PlaylistPojo;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static com.restAssured.spotify.application.SpecBuilder.getRequestSpec;
import static com.restAssured.spotify.application.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify OAuth2.0")
public class PlaylistTest {

    @Step
    @Link("http://example.com")
    @Link(name="example", value="myLink")
    @Feature("Playlist Feature")
    @Description("Should be create a new playlist")
    @Test(description = "It should create a new playlist with valid data and valid credentials")
    public void shouldBeCreateANewPlaylist(){

        PlaylistPojo requestPlaylist = new PlaylistPojo();
        requestPlaylist.setName("RestAssured Playlist1");
        requestPlaylist.setDescription("New playlist description1");
        requestPlaylist.setPublic(false);

        PlaylistPojo responsePlaylist = given(getRequestSpec()).
                body(requestPlaylist).
        when().
                post("/users"+ConfigLoader.getInstance().getUserId()+"/playlists").
        then().spec(getResponseSpec()).
                assertThat().
                statusCode(201).
                extract().
                response().
                as(PlaylistPojo.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));

    }

    @Step
    @Feature("Playlist Feature")
    @Test(description = "It should get the details of a playlist by using playlistID")
    public void shouldGetAPlaylist(){

        PlaylistPojo responsePlaylist = given(getRequestSpec()).
        when().
                get("/playlists"+ConfigLoader.getInstance().getPlaylistId()).
        then().spec(getResponseSpec()).
                assertThat().
                statusCode(200).
                extract().
                response().
                as(PlaylistPojo.class);
        assertThat(responsePlaylist.getName(), equalTo("RestAssured Playlist1"));
        assertThat(responsePlaylist.getDescription(), equalTo("New playlist description1"));
        assertThat(responsePlaylist.getPublic(), equalTo(false));

    }

    @Step
    @Issue("Issue-001")
    @TmsLink("www.google.com")
    @Feature("Playlist Feature")
    @Test(description = "It should update playlist by using playlist ID")
    public void shouldUpdateThePlaylist(){
        PlaylistPojo requestPlaylist = new PlaylistPojo();
        requestPlaylist.setName("Updated RestAssured Playlist1");
        requestPlaylist.setDescription("Updated New playlist description1");
        requestPlaylist.setPublic(false);

        given(getRequestSpec()).
                body(requestPlaylist).
        when().
                put("playlists/4sq0QAmRjSMBDSAP1tJaox").
        then().spec(getResponseSpec()).
                assertThat().
                statusCode(200);

    }

    @Step
    @Test(description = "It should not create a new playlist with blank name")
    public void shouldNotCreateAPlaylistWithoutName(){
        PlaylistPojo requestPlaylist = new PlaylistPojo();
        requestPlaylist.setName("");
        requestPlaylist.setDescription("Updated New playlist description1");
        requestPlaylist.setPublic(false);

       ErrorPojo responsePlaylist = given(getRequestSpec()).
                body(requestPlaylist).
        when().
                post("users"+ConfigLoader.getInstance().getUserId()+"/playlists").
        then().spec(getResponseSpec()).
                assertThat().
                statusCode(400).
               extract().
               response().
               as(ErrorPojo.class);

       assertThat(responsePlaylist.getError().getStatus(),equalTo(400));
       assertThat(responsePlaylist.getError().getMessage(),equalTo("Missing required field: name"));

    }

    @Step
    @Test(description = "It should give error of expired token")
    public void shouldNotCreateAPlaylistWitExpiredToken(){
        PlaylistPojo requestPlaylist = new PlaylistPojo();
        requestPlaylist.setName("Expired Token Playlist");
        requestPlaylist.setDescription("Updated New playlist description1");
        requestPlaylist.setPublic(false);

        ErrorPojo responsePlaylist = given().
                baseUri(ConfigLoader.getInstance().getBasePath()+ConfigLoader.getInstance().getBaseUri()).
                header("Authorization","Bearer BQDzq6Nhg71OIEOm5jqm4AVpEKLd-lHOEkePH0ydUckVKVmkA1MngmjDSwtRoA7TtUd2LW3ThPg1t1Ne-QklTpXqh7F-0CUBXHZ95GVt41e5CGYpqkjrpMOBKxey_O2sBD8mQOfuQu8u7iFvr8grkL841J92ERa28fC6lkXrlAX8ouAgr9IIuWQNpv9EMhYdloUHhiwisD7hsh_yMhFz5_J31YUDP5XJ_gmm-qNX61Y6aQFTsuwfBNPca15l6JaSbR99T1v_6aD1lRZE").
                header("Content-Type","application/json").
                body(requestPlaylist).
                log().all().
                when().
                post("/users"+ConfigLoader.getInstance().getUserId()+"/playlists").
                then().spec(getResponseSpec()).
                extract().
                response().
                as(ErrorPojo.class);
        assertThat(responsePlaylist.getError().getStatus(),equalTo(401));
        assertThat(responsePlaylist.getError().getMessage(),equalTo("The access token expired"));

    }



}
