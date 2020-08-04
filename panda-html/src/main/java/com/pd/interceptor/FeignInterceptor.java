package com.pd.interceptor;

import com.pd.hystrix.FeignHystrixConcurrencyStrategy;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Enumeration;

/**
 * @author GTY
 */
@Configuration
@EnableFeignClients(basePackages = "com.pd")
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    template.header(name, value);
                }
            }
        }
        String sessionId = request.getSession().getId();
        if (sessionId != null) {
            template.header("Cookie", "SESSION=" + base64Encode(request.getSession().getId()));
        }
    }
    @Bean
    public FeignHystrixConcurrencyStrategy feignHystrixConcurrencyStrategy() {
        return new FeignHystrixConcurrencyStrategy();
    }

    public static String base64Encode(String value) {
        //Base64转码（session）
        byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
        return new String(encodedCookieBytes);
    }
}
