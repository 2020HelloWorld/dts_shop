package com.qiguliuxing.dts.wx.config;

import java.util.List;

import com.qiguliuxing.dts.wx.interceptor.MyWxLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

}
