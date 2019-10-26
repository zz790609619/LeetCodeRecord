package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringToInt {
    /**
     * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
     * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
     * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
     * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
     * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
     * 说明：
     * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，请返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
     */
    @Test
    public void contextLoads() {
        int s = myAtoi2("2147483648");
        System.out.println(s);
    }

    /**
     * 第一个想法将所有空字符串取消掉,判断可能出现的情况，进行处理
     *
     * @param str
     * @return
     */

    public int myAtoi(String str) {
        String result = "";
        boolean www = false;//空格出现的位置是否为字符串的前端
        try {
            for (int i = 0; i < str.length(); i++) {
                char cur = str.charAt(i);
                if (cur == ' ') {
                    if (!www) {
                        continue;
                    } else {
                        break;
                    }
                }
                //如果是+ -号 就追加到结果字符串上，如果是已经存在了就跳出循环，剩余字符串即使有数字也不再考虑
                if (cur == '-' || cur == '+') {
                    if (result.contains("-") || result.contains("+")) {
                        break;
                    }
                    www = true;
                    result += cur;
                    continue;
                } else {
                    //当前字符转成int 用来判断当前字符是否为数字 如果不为数字则抛出异常
                    int z = Integer.valueOf(String.valueOf(cur));
                    result += z;
                }
                www = true;
            }
            //如果是 + -102 则返回0
            if (result.contains("-") && result.contains("+")) {
                if (result.indexOf("-") < result.indexOf("+")) {
                    return 0;
                }
            }
            if (result.contains("-") && result.indexOf("-") == result.length() - 1) {
                result = result.replace("-", "");
            } else if (result.contains("+")) {
                result = result.replace("+", "");
            } else if (result.contains("-") && result.indexOf("-") != 0) {
                return 0;
            }

            if (str.length() == 0) {
                return 0;
            }
            try {
                int s = Integer.valueOf(result);
            } catch (NumberFormatException e) {
                if (result.length() <= 1) {
                    return 0;
                }
                if (result.contains("-")) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }

        } catch (Exception e) {
            //这是最后一个字符无法转换为数字抛出的异常
            try {
                //如果字符含有+ - 或者太大或者太小 则抛出异常
                return Integer.valueOf(result);
            } catch (NumberFormatException es) {
                if (result.length() <= 1) {
                    return 0;
                }
                //判断是最大值还是最小值
                if (result.contains("-")) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }

        }
        return Integer.valueOf(result);
    }

    /**
     * 整理下第一个方法的思路
     * 写一个便于理解的想法
     * @param str
     * @return
     */
    public int myAtoi2(String str) {
        if(str.equals("")||str==null||str.length()==0){
            return 0;
        }
        int result=0;
        int icon=1;
        //去除首尾的空格
        str=str.trim();
        for (int i = 0; i <str.length() ; i++) {
            char s=str.charAt(i);
            if((s=='-'||s=='+')&&i==0){
                if(s=='-'){
                    icon=-1;
                }
                continue;
            }
            //中间遇到空格或非数字则结束循环
            if(s<48||s>57){
                break;
            }
            if(icon==-1&&result!=0){
                if(result*icon<Integer.MIN_VALUE/10){
                    return Integer.MIN_VALUE;

                }else if(result*icon==Integer.MIN_VALUE/10){
                    if(s - 48 > 8){
                        return Integer.MIN_VALUE;
                    }
                }
            }else{
                if( result>Integer.MAX_VALUE/10){
                    return Integer.MAX_VALUE;
                }else if( result==Integer.MAX_VALUE/10){
                    if(s-48>Integer.MAX_VALUE%10){
                        return Integer.MAX_VALUE;
                    }
                }
            }
            result=result*10+(s-48);

        }
        return result*icon;

    }
}
