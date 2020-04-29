package com.example.demo.controller;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.example.demo.entity.model.ResponseDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/test")
public class UserNodeController {
    @RequestMapping(value = "/index")
    public String index(){
        return "/index";
    }

    /**
     * 异常报错
     * 访问路径：http://127.0.0.1:8090/test/exception
     * 返回结果： {"errorCode": 1001,"message": "Exception:4","data": null }
     */
    @RequestMapping(value = "/exception")
    public String getException(){
        int[] arr = {1, 2, 3};
        System.out.println(arr[4]);
        ResponseDto dto=new ResponseDto();
        dto.setMessage("NoEnterGlobalException");
        return JSON.toJSONString(dto);
    }
    /**
     *
     * 空指针异常报错
     * 访问路径：http://127.0.0.1:8090/test/nullPointException
     * 返回结果：{"errorCode": 1002,"message": "NullPointerException:null","data": null }
     */
    @RequestMapping(value = "/nullPointException")
    public ResponseDto getNullPointException(){
        Object obj = null;
        obj.toString();
        ResponseDto dto=new ResponseDto();
        dto.setMessage("NoEnterGlobalNullPointException");
        return dto;
    }
    /**
     * 获取全局变量
     * 访问路径：http://127.0.0.1:8090/test/getGlobalParm
     * 返回结果：{"ww":{"ww":"helloQ"}}
     */
    @RequestMapping(value = "/getGlobalParm")
    public String getGlobalParm(Model model){
        Map<String, Object> map = model.asMap();
        return JSON.toJSONString(map);
    }

    /**
     * 获取预处理后的数据
     * 访问地址及参数：http://127.0.0.1:8090/test/getPreprocessedData?helloA.errorCode=1&helloA.message=a&helloA.data=a&helloB.errorCode=2&helloB.message=b&helloB.data=b
     * 返回结果：dtoA:{"data":"a","errorCode":1,"message":"a"},dtoB:{"data":"b","errorCode":2,"message":"b"}
     */
    @RequestMapping(value = "/getPreprocessedData")
    public String getPreprocessedData(@ModelAttribute("helloA")ResponseDto dtoA,@ModelAttribute("helloB")ResponseDto dtoB){
        return "dtoA:"+JSON.toJSONString(dtoA)+",dtoB:"+JSON.toJSONString(dtoB);
    }
}
