package com.pd.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 操作session的工具类
 *
 * @author GTY
 */
public class Sessions {

    /**
     * 获取session，通过springMVC提供的RequestContextHolder工具类,
     * 得到Http Servlet API: request,response,session
     */
    public static HttpSession getSesson() {
        return ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
    }

    /**
     * 向session中存储数据
     * @param key session域的键
     * @param value session域的值
     * @param time session的存活时间  单位：分钟
     */
    public static void setSessionData(String key,Object value,int time){
        HttpSession session = getSesson();
        session.setAttribute(key,value);
        session.setMaxInactiveInterval(time*60);
    }
}
