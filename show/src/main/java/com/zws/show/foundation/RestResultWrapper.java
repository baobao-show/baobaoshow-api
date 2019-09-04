package com.zws.show.foundation;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *
 * https://blog.csdn.net/futao__/article/details/82826564
 * https://blog.csdn.net/qq_32917699/article/details/81486060
 * https://blog.csdn.net/leilecoffee/article/details/80225548
 * 返回Rest风格的数据
 */

@ControllerAdvice(basePackages = "com.zws.controller")
public class RestResultWrapper implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        RestResult result = new RestResult(true, "0", body, null);
        return JSONObject.toJSON(body);
    }
}