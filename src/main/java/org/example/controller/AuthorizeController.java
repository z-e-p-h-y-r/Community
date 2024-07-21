package org.example.controller;

import org.example.dto.AccessTokenDTO;
import org.example.dto.GithubUser;
import org.example.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {  // 认证controller
    @Autowired
    public GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code){
        AccessTokenDTO tokenDTO = new AccessTokenDTO();
        tokenDTO.setClient_id("Ov23liZAEJWnw3KHVC5I");
        tokenDTO.setClient_secret("f442c0154a77b01bb5005d93159860e5c05a06af");
        tokenDTO.setCode(code);
        tokenDTO.setRedirect_uri("http://localhost:8887/callback");
        String accessToken = githubProvider.getAccessToken(tokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
