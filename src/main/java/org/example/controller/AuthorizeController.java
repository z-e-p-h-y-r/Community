package org.example.controller;

import org.example.dto.AccessTokenDTO;
import org.example.dto.GithubUser;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {  // 认证controller
    @Autowired
    public GithubProvider githubProvider;
    @Autowired
    public UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code , HttpServletRequest request){
        AccessTokenDTO tokenDTO = new AccessTokenDTO();
        tokenDTO.setClient_id("Ov23liZAEJWnw3KHVC5I");
        tokenDTO.setClient_secret("f442c0154a77b01bb5005d93159860e5c05a06af");
        tokenDTO.setCode(code);
        tokenDTO.setRedirect_uri("http://localhost:8887/callback");
        String accessToken = githubProvider.getAccessToken(tokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            //登录成功,写cookie和session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountID(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";

        }else{
            //登录失败,重新登录
            return "redirect:/";
        }
    }
}