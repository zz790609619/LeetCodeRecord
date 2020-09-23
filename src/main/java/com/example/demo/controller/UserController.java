package com.example.demo.controller;

import com.example.demo.entity.MakeUpBonus;
import com.example.demo.entity.SubmitUserPayOrderRequest;
import com.example.demo.mq.AliMqComponent;
import com.example.demo.service.MakeUpBonusService;
import com.example.demo.service.UserPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController{
    @Autowired
    private UserPayOrderService userPayOrderService;
    @Autowired
    private MakeUpBonusService makeUpBonusService;

    @Autowired
    private AliMqComponent aliMqComponent;
    /**
     * 根据订单号获取商户id
     * @param userOrderId 订单id
     * @return
     */
    @PostMapping("/userOrderMerchantId")
    public String userOrderMerchantId(@RequestParam("userOrderId") String userOrderId){
        //UserPayOrder vo=userPayOrderService.userOrderMerchantId(userOrderId);
        //String messageId = aliMqComponent.sendCommonMessage("ww_test_topic","ww_test_tag","ssss",JSON.toJSONBytes(vo));
        return userPayOrderService.userOrderMerchantId(userOrderId);
    }

    @PostMapping("/submit")
    public String submit(@RequestBody SubmitUserPayOrderRequest userPayOrderRequest){
        return userPayOrderService.submit(userPayOrderRequest);
    }

//    @PostMapping("/testCache")
//    public void testMybatisCache(){
//        ConfigerCache configerCache=new ConfigerCache();
//        configerCache.setCacheQ("11","11");
//        System.out.println("1111");
//    }
    @PostMapping("/insertMake")
    public void testMybatisCache(@RequestBody MakeUpBonus makeUpBonus){
        makeUpBonusService.insertMakeUpBonus(makeUpBonus);
    }

    @PostMapping("/testMake")
    public void testMake(@RequestBody MakeUpBonus makeUpBonus){
        makeUpBonusService.testMake(makeUpBonus);
    }

    @GetMapping("/list")
    public List<MakeUpBonus> testMake(){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        return makeUpBonusService.getMakeUpBonusList();
    }

    /**
     * 测试拦截器中token校验后放入用户信息，接口是否能够从request获取到用户信息
     * @param request
     * @param token
     */
    @GetMapping("/queryUser")
    public void queryUser(HttpServletRequest request,@RequestParam("token") String token){
        //在拦截器中验证token并获取到这个token对应的信息
        System.out.println(request.getAttribute("test"));
        System.out.println(token);
    }

    /**
     * 测试拦截器中token校验后放入用户信息，接口是否能够从request获取到用户信息
     */
    @GetMapping("/testAnnotation")
    //@AnnoClass
    public String queryUser(){
        MakeUpBonus makeUpBonus=null;
        try {
            makeUpBonus=new MakeUpBonus();
            makeUpBonus.setOrderId("111");
            makeUpBonus=makeUpBonusService.test111(makeUpBonus);
            if(makeUpBonus==null){
                return "1";
            }else{
                return "2";
            }
        }catch (Exception e){

            System.out.println("111");
        }
        return "3";
    }
}