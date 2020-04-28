package com.example.demo.config;

import com.example.demo.entity.model.ResponseDto;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {
    /**
     * 所有异常处理
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    ResponseDto exceptionHandler(HttpServletRequest request,Exception e)throws Exception{
        ResponseDto dto=new ResponseDto();
        dto.setErrorCode(1001);
        dto.setMessage("Exception:"+e.getMessage());
        return dto;
    }

    /**
     * 空指针异常处理
     * @param request
     * @param e
     * @return
     * @throws NullPointerException
     */
    @ExceptionHandler(value = NullPointerException.class)
    public @ResponseBody
    ResponseDto exceptionHandler(HttpServletRequest request,NullPointerException e)throws NullPointerException{
        ResponseDto dto=new ResponseDto();
        dto.setErrorCode(1002);
        dto.setMessage("NullPointerException:"+e.getMessage());
        return dto;
    }

    /**
     * 全局变量
     * @return
     */
    @ModelAttribute(name="ww")
    public Map<String,Object> globalData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ww", "helloQ");
        return map;
    }

    /**
     * 数据预处理
     * @param binder
     */
    @InitBinder("helloA")
    public void b(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("helloA.");
    }
    @InitBinder("helloB")
    public void a(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("helloB.");
    }

}
