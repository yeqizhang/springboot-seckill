package com.jesper.seckill.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jesper.seckill.bean.User;
import com.jesper.seckill.redis.RedisService;
import com.jesper.seckill.result.Result;
import com.jesper.seckill.service.UserService;

/**
 * Created by jiangyunxiong on 2018/5/23.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<User> info(Model model, User user) {
        return Result.success(user);
    }
    
    @RequestMapping("/getSession")
    @ResponseBody
    public String getSession(HttpServletRequest request, HttpServletResponse response,Model model, User user) {
//    	System.out.println("##/goods/to_list跳转的goods_list.html页面中 请求/user/getSession展示session##");
    	//获取sessionId
        String sessionId=request.getSession().getId();
    	int serverPort = request.getServerPort();
    	JSONObject json = new JSONObject();
    	json.put("sessionId", sessionId);
    	json.put("serverPort", serverPort);
    	//使用Date
//    	Date d = new Date();
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	System.out.println("当前时间：" + sdf.format(d));
//    	System.out.println("sessionId:" + sessionId);
//    	System.out.println("serverPort:" + serverPort);
//    	System.out.println("######################");
    	
        return json.toJSONString();
        
    }
}