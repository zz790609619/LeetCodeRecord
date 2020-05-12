package com.example.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.mapper.main.UserMapper;
import com.example.demo.entity.User;
import com.example.demo.entity.model.ResponseDto;
import com.example.demo.util.ApplicationContextUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;

public class RequestInterceptor implements HandlerInterceptor {

        /**
         * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
         * 返回值：
         * true表示继续流程（如调用下一个拦截器或处理器）；
         * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
         */
        @Override
        public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
            JSONObject map = new JSONObject();
            Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = httpServletRequest.getHeader(key);
                map.put(key, value);
            }
            map.put("token", httpServletRequest.getHeader("AUTH-TOKEN"));
            if("111".equals(map.get("token"))){
                //token正确 继续下一步拦截器(如果有)
                System.out.println("token is right");
                UserMapper userMapper= ApplicationContextUtil.getBean(UserMapper.class);
                User user=userMapper.getUserByToken(String.valueOf(map.get("token")));
                httpServletRequest.setAttribute("test",JSON.toJSONString(user));
                return true;
            }else
                //token错误 返回错误response
                System.out.println("token is error");
                PrintWriter writer = null;
                try {
                    ResponseDto dto=new ResponseDto();
                    dto.setErrorCode(1002);
                    dto.setMessage("RequestInterceptor");
                    httpServletResponse.setCharacterEncoding("utf-8");
                    httpServletResponse.setHeader("Content-Type","application/json");
                    writer = httpServletResponse.getWriter();
                    writer.write(JSON.toJSONString(dto));
                    writer.flush();
                } catch (Exception e) {

                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    return false;
                }
            }

        /**
         * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
         */
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        }

        /**
         * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中
         */
        @Override
        public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        }


}
