package com.jesper.seckill.controller;

import com.alibaba.druid.util.StringUtils;
import com.jesper.seckill.result.Result;
import com.jesper.seckill.service.UserService;
import com.jesper.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * Created by jiangyunxiong on 2018/5/21.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;


    @RequestMapping("/to_login")
    public String toLogin() {
    	System.out.println("==> login/to_login");
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {//加入JSR303参数校验
//    	System.out.println("$$$$$$$$$login/do_login$$$$$$$$$$$$$$$$$");
//    	System.out.println("记录校验：");
        log.info(loginVo.toString());
        String token = userService.login(response, loginVo);
        if(!StringUtils.isEmpty(token)){
        	System.out.println("登陆成功~~");
        }else{
        	System.out.println("登陆失败~~");
        }
//        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");
        return Result.success(token);
    }

}
