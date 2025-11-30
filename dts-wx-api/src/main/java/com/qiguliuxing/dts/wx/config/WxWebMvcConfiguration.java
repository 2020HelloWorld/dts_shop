package com.qiguliuxing.dts.wx.config;

import java.util.List;

import com.qiguliuxing.dts.wx.interceptor.MyWxLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.qiguliuxing.dts.wx.annotation.support.LoginUserHandlerMethodArgumentResolver;

@Configuration
public class WxWebMvcConfiguration implements WebMvcConfigurer {
	@Autowired
	private MyWxLoginInterceptor myWxLoginInterceptor;
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(myWxLoginInterceptor)
//				.addPathPatterns("/wx/**")
//				.excludePathPatterns("/wx/auth/**");
//	}

    /**
     * 静态资源映射，把 /storage/** 映射到本地存储目录
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String storagePath = "dts/storage"; // 项目相对路径，也可以改成绝对路径
        registry.addResourceHandler("/storage/**")
                .addResourceLocations("file:" + storagePath + "/");
    }

}
