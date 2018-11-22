package com.jesper.seckill.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jesper.seckill.interceptor.MyInterceptor;

/**
 * Created by jiangyunxiong on 2018/5/22.
 *
 * 
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Autowired
    UserArgumentResolver userArgumentResolver;
    
    @Autowired
    MyInterceptor myInterceptor;

    /**
     * SpringMVC框架回调addArgumentResolvers，然后给Controller的参数赋值
     * 自定参数解析器, 作用：改变SpringMVC的Controller传入参数，实现可以User替换Token做为参数从登陆页面传到商品列表页面
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
    
    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// addPathPatterns 添加拦截规则。  addPathPatterns("/**")对所有请求都拦截，但是排除了/toLogin和/login请求的拦截。
    	// excludePathPatterns 排除拦截的规则
    	// 默认不会拦截静态资源
    	registry.addInterceptor(myInterceptor).addPathPatterns("/**").excludePathPatterns("/**/to_login","/**/do_login");
    	super.addInterceptors(registry);
    }
}
