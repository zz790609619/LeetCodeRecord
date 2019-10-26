package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntInversion {
	/**
	 * 证书反转
	 */
	@Test
	public void contextLoads() {
		String z="z";
		String[] ids=z.split(",");
		int s=reverse2(123);
		System.out.println(s);
	}

    /**
     * 第一个想法
	 * @param s
     * @return
     */
	public int reverse(int s) {
		String str=String.valueOf(s);
		String result="";
		int o=0;
		try{
			for (int i = str.length()-1;i >=0 ; i--) {
				String cur=String.valueOf(str.charAt(i));
				if(cur.equals("-")){
					result="-"+result;
				}else{
					result+=cur;
				}
			}
			o=Integer.valueOf(result).intValue();
		}
		catch (NumberFormatException e) {
			return 0;
		}
		return o;


	}
	/**
	 * 第一个想法
	 * @param x
	 * @return
	 */
	public int reverse2(int x) {
		long result=0;
		if(Integer.MIN_VALUE<x||x<Integer.MAX_VALUE){
			while(x!=0){
				result=result*10+x%10;
				x=x/10;
			}
		}
		return (int)result;
	}

}
