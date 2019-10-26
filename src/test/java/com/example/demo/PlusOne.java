package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlusOne {
	/**
	 * 数组加1
	 */
	@Test
	public void contextLoads() {
		int []z={8,9,9,9};
		int []s=plusOne2(z);
		System.out.println(s);
	}

    /**
     * 第一个想法
	 * 倒叙循环，加值之后顺便复制给当前位置。如有进位则下一个值加一
	 * 如果为999这样的特殊 情况 直接返回长度+1的数组1000这种
	 * @param digits
     * @return
     */
	public int [] plusOne( int []digits) {
		//由题意可知 只有99999这种情况才会进数组多一位。
		int carry=0;
		int count=0;
		for (int i = digits.length-1; i >=0 ; i--) {
			int cur=digits[i];
			if(i==digits.length-1){
				count=1;
			}else{
				count=0;
			}
			if(cur+count+carry==10){
				digits[i]=0;
				carry=1;
			}else{
				if(i==digits.length-1){
					digits[i]=cur+1+carry;
				}else{
					digits[i]=cur+carry;
				}
				carry=0;
				break;
			}
		}
		if(carry>0){
			int zw[]=new int[digits.length+1];
			for (int i = 0; i <zw.length ; i++) {
				if(i==0){
					zw[i]=1;
				}else{
					zw[i]=0;
				}
			}
			return zw;
		}
 		return digits;
	}

	/**
	 * 第二个想法
	 * 判断当前位是否为9 不为九就直接break
	 * 但是不知道怎么减少内存消耗
	 * @param digits
	 * @return
	 */
	public int [] plusOne2( int []digits) {
		int carry=0;
		for (int i = digits.length-1; i >=0 ; i--) {
			if(digits[i]==9){
				digits[i]=0;
				carry=1;
				continue;
			}else{
				if(i == (digits.length-1) ){
					digits[i]=digits[i]+1;
				}else{
					digits[i]=digits[i]+carry;
				}
				carry=0;
				break;
			}
		}
		if(carry>0){
			int zw[]=new int[digits.length+1];
			zw[0]=1;
			return zw;
		}
		return digits;
	}
}
