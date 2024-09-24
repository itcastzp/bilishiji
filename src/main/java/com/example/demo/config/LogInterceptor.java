package com.example.demo.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LogInterceptor implements HandlerInterceptor {


    private Log logger = LogFactory.getLog(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("入栈" + request.getMethod() + " 请求  ：IP:" + request.getRemoteAddr() + "HOST:" + request.getRemoteHost() + "请求URI:" + request.getRequestURI());
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) SpringContextUtils.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = requestMappingHandlerMapping.getHandlerMethods();
        Map<String, String> urlMap = new HashMap<>();
        for (RequestMappingInfo info : methodMap.keySet()) {
            //获取请求路径
            Set<String> directPaths = info.getDirectPaths();
            // 获取全部请求方式
            //Set<RequestMethod> Methods = info.getMethodsCondition().getMethods();
            //获取全部请求名称
            String urlName = info.getName();
//            logger.info(urlName + "directPaths:" + directPaths);
            if (request.getRequestURI().contains(directPaths.stream().findFirst().get())) {
                return true;
            }

        }
        logger.info("出现非法请求" + request.getRequestURI() + "--------------");
        request.getRequestDispatcher("/error").forward(request, response);
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}