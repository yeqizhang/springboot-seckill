package com.jesper.seckill.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.jesper.seckill.bean.User;
import com.jesper.seckill.service.UserService;

@Component
public class MyInterceptor implements HandlerInterceptor{
	
    @Autowired
    UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println("************* MyInterceptor 校验登陆状态 ***************");
    	//获取sessionId
        String sessionId=request.getSession().getId();
        //System.out.println("session AttributeNames:" + request.getSession().getAttributeNames());
    	Date d = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	System.out.println("当前时间：" + sdf.format(d));
    	System.out.println("sessionId:" + sessionId);
    	
    	String url = "http://" + request.getServerName() //服务器地址  
        + ":"   
        + request.getServerPort()           //端口号  
        + request.getContextPath()      //项目名称  
        + request.getServletPath()      //请求页面或其他地址  
        + "?" + (request.getQueryString()); //参数 
        System.out.println("请求地址：" + url);
    	
		//检验浏览器是否有cookies.或者cookies对应的session是否过期
		String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);	//url传递
        String cookieToken = getCookieValue(request, UserService.COOKIE_NAME_TOKEN);	//cookie传递
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
        	response.sendRedirect("/login/to_login");
        	System.out.println("*************没有传递token 未登陆状态***********");
        	System.out.println("");
        	return false;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        User user = userService.getByToken(response, token);
		if(user == null){
			response.sendRedirect("/login/to_login");
			
			System.out.println("**********redis没有对应的token的记录 未登陆状态********");
			System.out.println("");
			return false;
		}
		
		System.out.println("*************已登陆状态***************");
		System.out.println("");
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
	}

	//遍历所有cookie，找到需要的那个cookie
    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
