package org.example.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.example.dto.AccessTokenDTO;
import org.example.dto.GithubUser;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    //如果参数超过两个，应当封装一个类来存放。
    public String getAccessToken(AccessTokenDTO tokenDTO){

        MediaType mediaType = MediaType.get("application/json");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(tokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];
            return token;
        } catch (IOException e) {
        }
        return null;

    }
    public GithubUser getUser(String accessToken) {

        OkHttpClient client =new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user").header("Authorization","token "+accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (Exception e){}
        return null;
    }
}
